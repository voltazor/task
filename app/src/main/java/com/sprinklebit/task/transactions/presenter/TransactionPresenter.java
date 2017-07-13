package com.sprinklebit.task.transactions.presenter;

import android.text.TextUtils;

import com.sprinklebit.task.R;
import com.sprinklebit.task.accounts.model.Account;
import com.sprinklebit.task.accounts.model.AccountsRepository;
import com.sprinklebit.task.base.BasePresenterImpl;
import com.sprinklebit.task.transactions.model.Transaction;
import com.sprinklebit.task.transactions.model.TransactionsRepository;
import com.sprinklebit.task.transactions.view.ITransactionView;
import com.sprinklebit.task.util.SimpleErrorHandler;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by voltazor on 13/07/17.
 */
public class TransactionPresenter extends BasePresenterImpl<ITransactionView> implements ITransactionPresenter {

    private TransactionsRepository repository = new TransactionsRepository();
    private AccountsRepository accountsRepository = new AccountsRepository();

    private Transaction transaction;
    private boolean newTransaction;

    @Override
    public void setup() {
        newTransaction = true;
        addSubscription(accountsRepository.getSelectedAccount().subscribe(new Action1<Account>() {
            @Override
            public void call(Account account) {
                if (account == null) {
                    view.showNoAccountError(R.string.no_account);
                } else {
                    prepareNewTransaction(account);
                }
            }
        }, new SimpleErrorHandler(view)));
    }

    @Override
    public void setup(long id) {
        addSubscription(repository.getTransaction(id).subscribe(new Action1<Transaction>() {
            @Override
            public void call(Transaction transaction) {
                setTransaction(transaction);
            }
        }, new SimpleErrorHandler(view)));
    }

    private void prepareNewTransaction(Account account) {
        Transaction transaction = new Transaction();
        transaction.setId(System.currentTimeMillis());
        transaction.setDate(System.currentTimeMillis());
        transaction.setAccountId(account.getId());
        setTransaction(transaction);
    }

    @Override
    public void setTitle(String title) {
        transaction.setTitle(title);
    }

    @Override
    public void setAmount(float amount) {
        transaction.setAmount(amount);
    }

    @Override
    public void setDate(long date) {
        transaction.setDate(date);
    }

    @Override
    public void save() {
        if (TextUtils.isEmpty(transaction.getTitle())) {
            view.showTitleError(R.string.title_empty);
            return;
        }
        if (Float.compare(transaction.getAmount(), 0) == 0) {
            view.showAmountError(R.string.zero_amount);
            return;
        }
        Observable<Transaction> observable = newTransaction ? repository.addTransaction(transaction) : repository.updateTransaction(transaction);
        addSubscription(observable.subscribe(new Action1<Transaction>() {
            @Override
            public void call(Transaction transaction) {
                view.onActionSucceed();
            }
        }, new SimpleErrorHandler(view)));
    }

    @Override
    public void remove() {
        if (newTransaction) {
            view.onActionSucceed();
        } else {
            addSubscription(repository.removeTransaction(transaction.getId()).subscribe(new Action1<Transaction>() {
                @Override
                public void call(Transaction transaction) {
                    view.onActionSucceed();
                }
            }, new SimpleErrorHandler(view)));
        }
    }

    private void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        view.showTransaction(transaction);
        view.setRemoveButtonVisible(!newTransaction);
    }

}
