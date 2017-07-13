package com.sprinklebit.task.accounts.model;

import com.sprinklebit.task.transactions.model.Transaction;
import com.sprinklebit.task.transactions.model.TransactionFields;
import com.sprinklebit.task.util.RealmHolder;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by voltazor on 13/07/17.
 */
public class AccountLocalDataSource implements IAccountDataSource {

    private Realm realm = RealmHolder.i.getRealmInstance();

    @Override
    public Observable<List<Account>> getAccounts() {
        return Observable.just(getAccountFromRealm());
    }

    private List<Account> getAccountFromRealm() {
        return realm.where(Account.class).findAllSorted(AccountFields.ID);
    }

    private Account getAccountFromRealm(long id) {
        return realm.where(Account.class).equalTo(AccountFields.ID, id).findFirst();
    }

    private Account getSelectedAccountFromRealm() {
        return realm.where(Account.class).equalTo(AccountFields.SELECTED, true).findFirst();
    }

    @Override
    public Observable<Account> getSelectedAccount() {
        Account account = getSelectedAccountFromRealm();
        return Observable.just(account == null ? null : realm.copyFromRealm(account));
    }

    private int getAccountsCount() {
        return realm.where(Account.class).findAll().size();
    }

    @Override
    public Observable<Account> getAccount(long id) {
        return Observable.just(realm.copyFromRealm(getAccountFromRealm(id)));
    }

    @Override
    public Observable<Account> createAccount(Account account) {
        return updateAccount(account);
    }

    @Override
    public Observable<Account> updateAccount(final Account account) {
        return Observable.create(new Observable.OnSubscribe<Account>() {
            @Override
            public void call(final Subscriber<? super Account> subscriber) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if (account.isSelected()) {
                            Account oldAccount = getSelectedAccountFromRealm();
                            if (oldAccount != null) {
                                oldAccount.setSelected(false);
                            }
                        } else {
                            if (getAccountsCount() == 0) {
                                account.setSelected(true);
                            }
                        }
                        realm.insertOrUpdate(account);
                        subscriber.onNext(account);
                    }
                });
            }
        });
    }

    @Override
    public Observable<Account> removeAccount(final long id) {
        return Observable.create(new Observable.OnSubscribe<Account>() {
            @Override
            public void call(final Subscriber<? super Account> subscriber) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Account account = getAccountFromRealm(id);
                        if (account != null) {
                            RealmResults results = realm.where(Transaction.class).equalTo(TransactionFields.ACCOUNT_ID, account.getId()).findAll();
                            results.deleteAllFromRealm();
                            Account tempAccount = realm.copyFromRealm(account);
                            account.deleteFromRealm();
                            account = tempAccount;
                            if (account.isSelected()) {
                                Account newSelected = realm.where(Account.class).findFirst();
                                if (newSelected != null) {
                                    newSelected.setSelected(true);
                                }
                            }
                        }
                        subscriber.onNext(account);
                    }
                });
            }
        });
    }

}
