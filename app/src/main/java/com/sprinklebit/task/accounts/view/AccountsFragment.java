package com.sprinklebit.task.accounts.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sprinklebit.task.R;
import com.sprinklebit.task.accounts.model.Account;
import com.sprinklebit.task.accounts.presenter.AccountsPresenter;
import com.sprinklebit.task.accounts.presenter.IAccountsPresenter;
import com.sprinklebit.task.base.BaseActivity;
import com.sprinklebit.task.base.BaseAdapter;
import com.sprinklebit.task.base.BaseListFragment;
import com.sprinklebit.task.interfaces.OnItemClickListener;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by voltazor on 12/07/17.
 */
public class AccountsFragment extends BaseListFragment<Account, IAccountsPresenter> implements IAccountsView, OnItemClickListener<Account> {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    public static AccountsFragment newInstance() {
        return new AccountsFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_accounts;
    }

    @NonNull
    @Override
    protected IAccountsPresenter getPresenterInstance() {
        return new AccountsPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab.hide();
                } else if (dy < 0) {
                    fab.show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == BaseActivity.REQUEST.UPDATE) {
            presenter.requestAccounts();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestAccounts();
    }

    @Override
    protected BaseAdapter<Account, ?> newAdapterInstance(List<Account> items) {
        return new AccountsAdapter(getContext(), items, this);
    }

    @Override
    public void showAccounts(List<Account> accounts) {
        setItems(accounts);
    }

    @Override
    public void onItemClick(Account item, int position) {
        startActivityForResult(AccountActivity.newIntent(getContext(), item.getId()), BaseActivity.REQUEST.UPDATE);
    }

    @OnClick(R.id.fab)
    void fabClicked() {
        startActivityForResult(AccountActivity.newIntent(getContext()), BaseActivity.REQUEST.UPDATE);
    }

    static class AccountsAdapter extends BaseAdapter<Account, AccountsAdapter.ViewHolder> {

        AccountsAdapter(Context context, Collection<Account> items, @Nullable OnItemClickListener<Account> listener) {
            super(context, items, listener);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflate(R.layout.layout_account, parent));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, Account item, int position) {
            holder.bind(item);
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.name)
            TextView name;

            @BindView(R.id.transactions)
            TextView transactions;

            @BindView(R.id.checked)
            View checked;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bind(Account account) {
                name.setText(account.getName());
                checked.setVisibility(account.isSelected() ? View.VISIBLE : View.GONE);
            }

        }

    }

}
