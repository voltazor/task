package com.sprinklebit.task.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by voltazor on 12/07/17.
 */
public abstract class BaseMvpFragment<T extends IBasePresenter> extends BaseFragment implements IBaseMvpView {

    protected T presenter;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter = getPresenterInstance();
        presenter.attachView(this);
        return view;
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
    public void onDestroyView() {
        presenter = null;
        super.onDestroyView();
    }

}
