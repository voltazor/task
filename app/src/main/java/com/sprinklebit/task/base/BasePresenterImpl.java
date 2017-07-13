package com.sprinklebit.task.base;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.sprinklebit.task.util.SimpleErrorHandler;
import com.trello.navi.Event;
import com.trello.navi.rx.RxNavi;

import rx.Subscription;
import rx.functions.Action1;
import rx.internal.util.SubscriptionList;

/**
 * Created by voltazor on 12/07/17.
 */
public abstract class BasePresenterImpl<V extends IBaseMvpView> implements IBasePresenter<V> {

    protected V view;
    private SubscriptionList subscriptionList = new SubscriptionList();

    /**
     * Attach view to presenter, also here we have subscription
     * for destroy event. On destroy event we should detach view
     * and destroy presenter
     *
     * @param view extend IBaseMvpView
     */
    @Override
    public void attachView(V view) {
        this.view = view;
        addSubscription(RxNavi.observe(view, Event.DESTROY).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                detachView();
            }
        }, new SimpleErrorHandler()));
    }

    /**
     * This method adds given rx subscription to the {@link #subscriptionList}
     * which is unsubscribes in {@link #detachView()}
     *
     * @param subscription - rx subscription that must be unsubscribed {@link #detachView()}
     */
    protected void addSubscription(@NonNull Subscription subscription) {
        subscriptionList.add(subscription);
    }

    protected String getString(@StringRes int strResId) {
        return view.getContext().getString(strResId);
    }

    protected String getString(@StringRes int strResId, Object... formatArgs) {
        return view.getContext().getString(strResId, formatArgs);
    }

    /**
     * Here we are detaching view and removing and
     * unsubscribing all subscriptions
     */
    @Override
    public void detachView() {
        subscriptionList.unsubscribe();
        subscriptionList.clear();
        view = null;
    }

}
