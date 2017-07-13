package com.sprinklebit.task.accounts.view;

import com.sprinklebit.task.accounts.model.Account;
import com.sprinklebit.task.base.IBaseMvpView;

import java.util.List;

/**
 * Created by voltazor on 13/07/17.
 */
public interface IAccountsView extends IBaseMvpView {

    void showAccounts(List<Account> accounts);

}
