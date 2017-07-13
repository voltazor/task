package com.sprinklebit.task.transactions.model;

import com.sprinklebit.task.accounts.model.Account;
import com.sprinklebit.task.accounts.model.AccountFields;
import com.sprinklebit.task.util.RealmHolder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by voltazor on 12/07/17.
 */
public class TransactionsLocalDataSource implements ITransactionsDataSource {

    private final Realm realm = RealmHolder.i.getRealmInstance();

    private RealmQuery<Transaction> getTransactionsQuery(long accountId) {
        return realm.where(Transaction.class).equalTo(TransactionFields.ACCOUNT_ID, accountId);
    }

    @Override
    public Observable<List<Transaction>> getTransactions(final long accountId) {
        return Observable.just(getTransactionsQuery(accountId).findAllSorted(TransactionFields.DATE, Sort.DESCENDING))
                .map(new Func1<RealmResults<Transaction>, List<Transaction>>() {
                    @Override
                    public List<Transaction> call(RealmResults<Transaction> transactions) {
                        return realm.copyFromRealm(transactions);
                    }
                });
    }

    @Override
    public Observable<List<Transaction>> getLastMonthTransactions(long accountId) {
        long limit = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30);
        return Observable.just(getTransactionsQuery(accountId)
                .greaterThanOrEqualTo(TransactionFields.DATE, limit)
                .findAllSorted(TransactionFields.DATE, Sort.DESCENDING))
                .map(new Func1<RealmResults<Transaction>, List<Transaction>>() {
                    @Override
                    public List<Transaction> call(RealmResults<Transaction> transactions) {
                        return realm.copyFromRealm(transactions);
                    }
                });
    }

    @Override
    public Observable<Transaction> getTransaction(long id) {
        return Observable.just(realm.copyFromRealm(getTransactionFromRealm(id)));
    }

    private Transaction getTransactionFromRealm(long id) {
        return realm.where(Transaction.class).equalTo(TransactionFields.ID, id).findFirst();
    }

    @Override
    public Observable<Transaction> createTransaction(final Transaction transaction) {
        return Observable.create(new Observable.OnSubscribe<Transaction>() {
            @Override
            public void call(final Subscriber<? super Transaction> subscriber) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Account account = realm.where(Account.class).equalTo(AccountFields.ID, transaction.getAccountId()).findFirst();
                        if (account != null) {
                            account.getTransactions().add(transaction);
                            realm.insertOrUpdate(account);
                        }
                        subscriber.onNext(transaction);
                    }
                });
            }
        });
    }

    @Override
    public Observable<Transaction> updateTransaction(final Transaction transaction) {
        return Observable.create(new Observable.OnSubscribe<Transaction>() {
            @Override
            public void call(final Subscriber<? super Transaction> subscriber) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insertOrUpdate(transaction);
                        subscriber.onNext(transaction);
                    }
                });
            }
        });
    }

    @Override
    public Observable<Transaction> removeTransaction(final long id) {
        return Observable.create(new Observable.OnSubscribe<Transaction>() {
            @Override
            public void call(final Subscriber<? super Transaction> subscriber) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Transaction transaction = getTransactionFromRealm(id);
                        if (transaction != null) {
                            Transaction tempTransaction = realm.copyFromRealm(transaction);
                            transaction.deleteFromRealm();
                            transaction = tempTransaction;
                        }
                        subscriber.onNext(transaction);
                    }
                });
            }
        });
    }

}
