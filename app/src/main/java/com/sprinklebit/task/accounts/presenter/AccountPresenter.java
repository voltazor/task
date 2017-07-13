package com.sprinklebit.task.accounts.presenter;

import android.text.TextUtils;

import com.sprinklebit.task.R;
import com.sprinklebit.task.accounts.model.Account;
import com.sprinklebit.task.accounts.model.AccountsRepository;
import com.sprinklebit.task.accounts.view.IAccountView;
import com.sprinklebit.task.base.BasePresenterImpl;
import com.sprinklebit.task.util.SimpleErrorHandler;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by voltazor on 13/07/17.
 */
public class AccountPresenter extends BasePresenterImpl<IAccountView> implements IAccountPresenter {

    private AccountsRepository repository = new AccountsRepository();

    private Account account;
    private boolean newAccount;

    @Override
    public void setup(long accountId) {
        addSubscription(repository.getAccount(accountId).subscribe(new Action1<Account>() {
            @Override
            public void call(Account account) {
                setAccount(account);
            }
        }, new SimpleErrorHandler(view)));
    }

    @Override
    public void setup() {
        newAccount = true;
        Account account = new Account();
        account.setId(System.currentTimeMillis());
        setAccount(account);
    }

    private void setAccount(Account account) {
        this.account = account;
        view.showAccount(account);
        view.setRemoveButtonVisible(!newAccount);
    }

    @Override
    public void save() {
        if (TextUtils.isEmpty(account.getName())) {
            view.showNameError(R.string.name_empty);
            return;
        }
        Observable<Account> observable = newAccount ? repository.createAccount(account) : repository.updateAccount(account);
        addSubscription(observable.subscribe(new Action1<Account>() {
            @Override
            public void call(Account transaction) {
                view.onActionSucceed();
            }
        }, new SimpleErrorHandler(view)));
    }

    @Override
    public void setName(String name) {
        account.setName(name);
    }

    @Override
    public void setSelected(boolean selected) {
        account.setSelected(selected);
    }

    @Override
    public void remove() {
        if (newAccount) {
            view.onActionSucceed();
        } else {
            addSubscription(repository.removeAccount(account.getId()).subscribe(new Action1<Account>() {
                @Override
                public void call(Account account) {
                    view.onActionSucceed();
                }
            }, new SimpleErrorHandler(view)));
        }
    }

}
