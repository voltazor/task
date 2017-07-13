package com.sprinklebit.task.transactions.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.sprinklebit.task.R;
import com.sprinklebit.task.accounts.view.AccountActivity;
import com.sprinklebit.task.base.BaseMvpActivity;
import com.sprinklebit.task.transactions.model.Transaction;
import com.sprinklebit.task.transactions.presenter.ITransactionPresenter;
import com.sprinklebit.task.transactions.presenter.TransactionPresenter;
import com.sprinklebit.task.widget.StyledInput;

import butterknife.BindView;
import butterknife.OnClick;

public class TransactionActivity extends BaseMvpActivity<ITransactionPresenter> implements ITransactionView {

    @BindView(R.id.title)
    StyledInput titleInput;

    @BindView(R.id.amount)
    StyledInput amountInput;

    @BindView(R.id.save)
    View saveButton;

    @BindView(R.id.remove)
    View removeButton;

    public static Intent newIntent(Context context) {
        return new Intent(context, TransactionActivity.class);
    }

    public static Intent newIntent(Context context, long transactionId) {
        return newIntent(context).putExtra(EXTRA.ID, transactionId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBackButton();
        long transactionId = getIntent().getLongExtra(EXTRA.ID, -1);
        if (transactionId != -1) {
            presenter.setup(transactionId);
        } else {
            presenter.setup();
        }
    }

    @NonNull
    @Override
    protected ITransactionPresenter getPresenterInstance() {
        return new TransactionPresenter();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_transaction;
    }

    @Override
    public void showTransaction(Transaction transaction) {
        setUIEnabled(true);
        titleInput.setText(transaction.getTitle());
        if (Math.abs(transaction.getAmount()) > 0) {
            amountInput.setText(String.valueOf(transaction.getAmount()));
        }
    }

    @Override
    public void onActionSucceed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showTitleError(@StringRes int resId) {
        titleInput.setError(getString(resId));
    }

    @Override
    public void setRemoveButtonVisible(boolean visible) {
        removeButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showNoAccountError(@StringRes int resId) {
        setUIEnabled(false);
        if (rootView != null) {
            Snackbar.make(rootView, resId, Snackbar.LENGTH_INDEFINITE).setAction(R.string.create, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(AccountActivity.newIntent(getContext()), REQUEST.UPDATE);
                }
            }).show();
        }
    }

    @Override
    public void showAmountError(@StringRes int resId) {
        amountInput.setError(getString(resId));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST.UPDATE) {
            presenter.setup();
        }
    }

    @OnClick(R.id.save)
    void saveClick() {
        presenter.setTitle(titleInput.getTextString());
        presenter.setAmount(TextUtils.isEmpty(amountInput.getTextString()) ? 0 : Float.parseFloat(amountInput.getTextString()));
        presenter.save();
    }

    @OnClick(R.id.remove)
    void removeClick() {
        presenter.remove();
    }

    private void setUIEnabled(boolean enabled) {
        titleInput.setEnabled(enabled);
        amountInput.setEnabled(enabled);
        saveButton.setEnabled(enabled);
        removeButton.setEnabled(enabled);
    }

}
