package com.sprinklebit.task.accounts.presenter;

import com.sprinklebit.task.accounts.view.IAccountsView;
import com.sprinklebit.task.base.IBasePresenter;

/**
 * Created by voltazor on 13/07/17.
 */
public interface IAccountsPresenter extends IBasePresenter<IAccountsView> {

    void requestAccounts();

}
