package com.sprinklebit.task.transactions.model;

import java.util.List;

import rx.Observable;

/**
 * Created by voltazor on 12/07/17.
 */
public class TransactionsRepository {

    private ITransactionsDataSource dataSource = new TransactionsLocalDataSource();

    public Observable<List<Transaction>> getTransactions(long accountId) {
        return dataSource.getTransactions(accountId);
    }

    public Observable<List<Transaction>> getLastMonthTransactions(long accountId) {
        return dataSource.getLastMonthTransactions(accountId);
    }

    public Observable<Transaction> getTransaction(long id) {
        return dataSource.getTransaction(id);
    }

    public Observable<Transaction> addTransaction(Transaction transaction) {
        return dataSource.createTransaction(transaction);
    }

    public Observable<Transaction> updateTransaction(Transaction transaction) {
        return dataSource.updateTransaction(transaction);
    }

    public Observable<Transaction> removeTransaction(long id) {
        return dataSource.removeTransaction(id);
    }

}
