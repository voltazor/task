package com.sprinklebit.task.accounts.model;

import com.sprinklebit.task.transactions.model.Transaction;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by voltazor on 12/07/17.
 */
public class Account extends RealmObject {

    @PrimaryKey
    private long id;

    private String name;

    private boolean selected;

    private RealmList<Transaction> transactions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public RealmList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(RealmList<Transaction> transactions) {
        this.transactions = transactions;
    }

}
