package com.sprinklebit.task.transactions.presenter;

import com.sprinklebit.task.base.IBasePresenter;
import com.sprinklebit.task.transactions.view.ITransactionsView;

/**
 * Created by voltazor on 12/07/17.
 */
public interface ITransactionsPresenter extends IBasePresenter<ITransactionsView> {

    void setup(boolean dashboardMode);

    void requestTransactions();

}
