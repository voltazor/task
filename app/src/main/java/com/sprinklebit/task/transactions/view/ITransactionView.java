package com.sprinklebit.task.transactions.view;

import android.support.annotation.StringRes;

import com.sprinklebit.task.base.IBaseMvpView;
import com.sprinklebit.task.transactions.model.Transaction;

/**
 * Created by voltazor on 12/07/17.
 */
public interface ITransactionView extends IBaseMvpView {

    void showTransaction(Transaction transaction);

    void onActionSucceed();

    void showTitleError(@StringRes int resId);

    void setRemoveButtonVisible(boolean visible);

    void showNoAccountError(@StringRes int resId);

    void showAmountError(@StringRes int resId);

}
