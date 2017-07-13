package com.sprinklebit.task.accounts.model;

import java.util.List;

import rx.Observable;

/**
 * Created by voltazor on 13/07/17.
 */
public interface IAccountDataSource {

    Observable<List<Account>> getAccounts();

    Observable<Account> getSelectedAccount();

    Observable<Account> getAccount(long id);

    Observable<Account> createAccount(Account account);

    Observable<Account> updateAccount(Account account);

    Observable<Account> removeAccount(long id);

}
