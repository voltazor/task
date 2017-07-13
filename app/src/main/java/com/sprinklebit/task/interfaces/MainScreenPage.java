package com.sprinklebit.task.interfaces;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by voltazor on 12/07/17.
 */
@IntDef({MainScreenPage.DASHBOARD, MainScreenPage.TRANSACTIONS, MainScreenPage.ACCOUNTS})
@Retention(RetentionPolicy.SOURCE)
public @interface MainScreenPage {
    int DASHBOARD = 0;
    int TRANSACTIONS = 1;
    int ACCOUNTS = 2;
}
