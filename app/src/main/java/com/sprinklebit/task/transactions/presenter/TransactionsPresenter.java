package com.sprinklebit.task.transactions.presenter;

import com.sprinklebit.task.accounts.model.Account;
import com.sprinklebit.task.accounts.model.AccountsRepository;
import com.sprinklebit.task.base.BasePresenterImpl;
import com.sprinklebit.task.transactions.model.Transaction;
import com.sprinklebit.task.transactions.model.TransactionsRepository;
import com.sprinklebit.task.transactions.view.ITransactionsView;
import com.sprinklebit.task.util.SimpleErrorHandler;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by voltazor on 12/07/17.
 */
public class TransactionsPresenter extends BasePresenterImpl<ITransactionsView> implements ITransactionsPresenter {

    private TransactionsRepository repository = new TransactionsRepository();
    private AccountsRepository accountsRepository = new AccountsRepository();

    private boolean dashboardMode;

    @Override
    public void setup(boolean dashboardMode) {
        this.dashboardMode = dashboardMode;
    }

    @Override
    public void requestTransactions() {
        addSubscription(accountsRepository.getSelectedAccount().flatMap(new Func1<Account, Observable<List<Transaction>>>() {
            @Override
            public Observable<List<Transaction>> call(Account account) {
                if (account == null) {
                    List<Transaction> transactions = Collections.emptyList();
                    return Observable.just(transactions);
                } else {
                    return dashboardMode ? repository.getLastMonthTransactions(account.getId()) : repository.getTransactions(account.getId());
                }
            }
        }).subscribe(new Action1<List<Transaction>>() {
            @Override
            public void call(List<Transaction> transactions) {
                view.showTransactions(transactions);
            }
        }, new SimpleErrorHandler(view)));
    }

}
