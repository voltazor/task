package com.sprinklebit.task.transactions.view;

import com.sprinklebit.task.base.IBaseMvpView;
import com.sprinklebit.task.transactions.model.Transaction;

import java.util.List;

/**
 * Created by voltazor on 12/07/17.
 */
public interface ITransactionsView extends IBaseMvpView {

    void showTransactions(List<Transaction> transactions);

}
