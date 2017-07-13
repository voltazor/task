package com.sprinklebit.task.util;

import com.sprinklebit.task.R;
import com.sprinklebit.task.base.IBaseMvpView;

import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by voltazor on 12/07/17.
 */
public class SimpleErrorHandler implements Action1<Throwable> {

    private final IBaseMvpView view;
    private final Runnable runnable;

    public SimpleErrorHandler() {
        this(null);
    }

    public SimpleErrorHandler(IBaseMvpView view) {
        this(view, null);
    }

    public SimpleErrorHandler(IBaseMvpView view, Runnable runnable) {
        this.view = view;
        this.runnable = runnable;
    }

    @Override
    public void call(Throwable throwable) {
        Timber.e(throwable);
        if (view != null) {
            view.showError(R.string.error_occurred);
        }
        if (runnable != null) {
            runnable.run();
        }
    }


}
