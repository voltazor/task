package com.sprinklebit.task.util;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by voltazor on 12/07/17.
 */
public enum RealmHolder {
    i;

    private static final String STORAGE = "main";
    private static final long CURRENT_VERSION = 0;

    private Realm realmInstance;

    public void initializeRealm(final Context context) {
        Realm.init(context);
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
        builder.name(STORAGE);
        builder.deleteRealmIfMigrationNeeded();
        builder.schemaVersion(CURRENT_VERSION);
        Realm.setDefaultConfiguration(builder.build());
        realmInstance = Realm.getDefaultInstance();
    }

    public Realm getRealmInstance() {
        return realmInstance;
    }

}
