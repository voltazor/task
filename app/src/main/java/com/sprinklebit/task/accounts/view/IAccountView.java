package com.sprinklebit.task.accounts.view;

import android.support.annotation.StringRes;

import com.sprinklebit.task.accounts.model.Account;
import com.sprinklebit.task.base.IBaseMvpView;

/**
 * Created by voltazor on 13/07/17.
 */
public interface IAccountView extends IBaseMvpView {

    void showAccount(Account account);

    void showNameError(@StringRes int resId);

    void setRemoveButtonVisible(boolean visible);

    void onActionSucceed();

}
