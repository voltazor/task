package com.sprinklebit.task.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by voltazor on 12/07/17.
 */
public abstract class BaseMvpActivity<T extends IBasePresenter> extends BaseActivity implements IBaseMvpView {

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = getPresenterInstance();
        presenter.attachView(this);
    }

    @NonNull
    protected abstract T getPresenterInstance();

    @Override
    public void showError(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(@StringRes int resId) {
        showError(getString(resId));
    }

    @Override
    public Context getContext() {
        return this;
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        presenter = null;
        super.onDestroy();
    }

}
