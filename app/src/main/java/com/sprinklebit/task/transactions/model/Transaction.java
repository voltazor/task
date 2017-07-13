package com.sprinklebit.task.transactions.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by voltazor on 12/07/17.
 */
public class Transaction extends RealmObject {

    @PrimaryKey
    private long id;

    private String title;

    private float amount;

    private long date;

    private long accountId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

}
