package com.sprinklebit.task.accounts.model;

import java.util.List;

import rx.Observable;

/**
 * Created by voltazor on 13/07/17.
 */
public class AccountsRepository {

    private IAccountDataSource localDataSource = new AccountLocalDataSource();

    public Observable<List<Account>> getAccounts() {
        return localDataSource.getAccounts();
    }

    public Observable<Account> getSelectedAccount() {
        return localDataSource.getSelectedAccount();
    }

    public Observable<Account> getAccount(long id) {
        return localDataSource.getAccount(id);
    }

    public Observable<Account> createAccount(Account account) {
        return localDataSource.createAccount(account);
    }

    public Observable<Account> updateAccount(Account account) {
        return localDataSource.updateAccount(account);
    }

    public Observable<Account> removeAccount(long id) {
        return localDataSource.removeAccount(id);
    }

}
