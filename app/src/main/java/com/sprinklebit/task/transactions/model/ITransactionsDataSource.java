package com.sprinklebit.task.transactions.model;

import java.util.List;

import rx.Observable;

/**
 * Created by voltazor on 12/07/17.
 */
public interface ITransactionsDataSource {

    Observable<List<Transaction>> getTransactions(long accountId);

    Observable<List<Transaction>> getLastMonthTransactions(long accountId);

    Observable<Transaction> getTransaction(long id);

    Observable<Transaction> createTransaction(Transaction transaction);

    Observable<Transaction> updateTransaction(Transaction transaction);

    Observable<Transaction> removeTransaction(long id);

}
