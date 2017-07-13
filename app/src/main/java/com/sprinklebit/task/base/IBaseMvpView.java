package com.sprinklebit.task.base;

import android.content.Context;
import android.support.annotation.StringRes;

import com.trello.navi.NaviComponent;

/**
 * Created by voltazor on 12/07/17.
 */
public interface IBaseMvpView extends NaviComponent {

    Context getContext();

    void showError(String string);

    void showError(@StringRes int resId);

}
