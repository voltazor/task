package com.sprinklebit.task.accounts.presenter;

import com.sprinklebit.task.accounts.view.IAccountView;
import com.sprinklebit.task.base.IBasePresenter;

/**
 * Created by voltazor on 13/07/17.
 */
public interface IAccountPresenter extends IBasePresenter<IAccountView> {

    void setup(long accountId);

    void setup();

    void save();

    void setName(String name);

    void setSelected(boolean selected);

    void remove();

}
