package com.sprinklebit.task.transactions.presenter;

import com.sprinklebit.task.base.IBasePresenter;
import com.sprinklebit.task.transactions.view.ITransactionView;

/**
 * Created by voltazor on 12/07/17.
 */
public interface ITransactionPresenter extends IBasePresenter<ITransactionView> {

    void setup();

    void setup(long id);

    void setTitle(String title);

    void setAmount(float amount);

    void setDate(long date);

    void save();

    void remove();

}
