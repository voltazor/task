package com.sprinklebit.task.base;

/**
 * Created by voltazor on 12/07/17.
 */
public interface IBasePresenter<V extends IBaseMvpView> {

    void attachView(V view);

    void detachView();

}
