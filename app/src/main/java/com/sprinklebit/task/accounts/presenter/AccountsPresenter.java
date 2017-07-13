package com.sprinklebit.task.accounts.presenter;

import com.sprinklebit.task.accounts.model.Account;
import com.sprinklebit.task.accounts.model.AccountsRepository;
import com.sprinklebit.task.accounts.view.IAccountsView;
import com.sprinklebit.task.base.BasePresenterImpl;
import com.sprinklebit.task.util.SimpleErrorHandler;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by voltazor on 13/07/17.
 */
public class AccountsPresenter extends BasePresenterImpl<IAccountsView> implements IAccountsPresenter {

    private AccountsRepository repository = new AccountsRepository();

    @Override
    public void requestAccounts() {
        addSubscription(repository.getAccounts().subscribe(new Action1<List<Account>>() {
            @Override
            public void call(List<Account> accounts) {
                view.showAccounts(accounts);
            }
        }, new SimpleErrorHandler(view)));
    }

}
