package com.sprinklebit.task.transactions.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sprinklebit.task.R;
import com.sprinklebit.task.base.BaseActivity;
import com.sprinklebit.task.base.BaseAdapter;
import com.sprinklebit.task.base.BaseListFragment;
import com.sprinklebit.task.interfaces.OnItemClickListener;
import com.sprinklebit.task.transactions.model.Transaction;
import com.sprinklebit.task.transactions.presenter.ITransactionsPresenter;
import com.sprinklebit.task.transactions.presenter.TransactionsPresenter;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import static android.text.format.DateUtils.*;

/**
 * Created by voltazor on 12/07/17.
 */
public class TransactionsFragment extends BaseListFragment<Transaction, ITransactionsPresenter> implements ITransactionsView, OnItemClickListener<Transaction> {

    private static final String ARG_MODE = "mode";

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private boolean dashboardMode;

    public static TransactionsFragment newInstance() {
        return newInstance(false);
    }

    public static TransactionsFragment newInstance(boolean dashboardMode) {
        TransactionsFragment fragment = new TransactionsFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_MODE, dashboardMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_transactions;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            dashboardMode = getArguments().getBoolean(ARG_MODE);
        }
        presenter.setup(dashboardMode);
        if (dashboardMode) {
            emptyView.setIcon(R.drawable.ic_dashboard);
            emptyView.setText(getString(R.string.empty_dashboard));
        } else {
            emptyView.setIcon(R.drawable.ic_transactions);
            emptyView.setText(getString(R.string.empty_transactions));
            fab.setVisibility(View.VISIBLE);
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
    }

    @NonNull
    @Override
    protected ITransactionsPresenter getPresenterInstance() {
        return new TransactionsPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestTransactions();
    }

    @Override
    protected BaseAdapter<Transaction, ?> newAdapterInstance(List<Transaction> items) {
        return new TransactionsAdapter(getContext(), items, dashboardMode ? null : this);
    }

    @Override
    public void showTransactions(List<Transaction> transactions) {
        setItems(transactions);
    }

    @OnClick(R.id.fab)
    void fabClick() {
        startActivityForResult(TransactionActivity.newIntent(getContext()), BaseActivity.REQUEST.UPDATE);
    }

    @Override
    public void onItemClick(Transaction item, int position) {
        startActivityForResult(TransactionActivity.newIntent(getContext(), item.getId()), BaseActivity.REQUEST.UPDATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == BaseActivity.REQUEST.UPDATE) {
            presenter.requestTransactions();
        }
    }

    static class TransactionsAdapter extends BaseAdapter<Transaction, TransactionsAdapter.ViewHolder> {

        TransactionsAdapter(Context context, Collection<Transaction> items, @Nullable OnItemClickListener<Transaction> listener) {
            super(context, items, listener);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflate(R.layout.layout_transaction, parent));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, Transaction item, int position) {
            holder.bind(getContext(), item);
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.title)
            TextView title;

            @BindView(R.id.amount)
            TextView amount;

            @BindView(R.id.date)
            TextView date;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bind(Context context, Transaction transaction) {
                title.setText(transaction.getTitle());
                amount.setTextColor(resolveColor(context, transaction.getAmount()));
                amount.setText(String.valueOf(transaction.getAmount()));
                date.setText(formatDateTime(context, transaction.getDate(), FORMAT_SHOW_TIME | FORMAT_SHOW_DATE));
            }

            @ColorInt
            private int resolveColor(Context context, float amount) {
                return ContextCompat.getColor(context, amount > 0 ? R.color.colorGreen : R.color.colorRed);
            }

        }

    }

}
