package com.sprinklebit.task;

import android.app.Application;

import com.sprinklebit.task.util.RealmHolder;

import timber.log.Timber;

/**
 * Created by voltazor on 12/07/17.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
        RealmHolder.i.initializeRealm(this);
    }

    private void initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
