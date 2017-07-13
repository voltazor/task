package com.sprinklebit.task.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.navi.component.support.NaviFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by voltazor on 12/07/17.
 */
public abstract class BaseFragment extends NaviFragment {

    private Unbinder unbinder;

    @CallSuper
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getLayoutResourceId() > 0) {
            View view = inflater.inflate(getLayoutResourceId(), container, false);
            unbinder = ButterKnife.bind(this, view);
            return view;
        } else {
            return null;
        }
    }

    @LayoutRes
    protected abstract int getLayoutResourceId();

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

}
