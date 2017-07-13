package com.sprinklebit.task.accounts.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.sprinklebit.task.R;
import com.sprinklebit.task.accounts.model.Account;
import com.sprinklebit.task.accounts.presenter.AccountPresenter;
import com.sprinklebit.task.accounts.presenter.IAccountPresenter;
import com.sprinklebit.task.base.BaseMvpActivity;
import com.sprinklebit.task.widget.StyledInput;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountActivity extends BaseMvpActivity<IAccountPresenter> implements IAccountView, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.name)
    StyledInput nameInput;

    @BindView(R.id.selected)
    SwitchCompat selectedSwitch;

    @BindView(R.id.remove)
    View removeButton;

    public static Intent newIntent(Context context) {
        return new Intent(context, AccountActivity.class);
    }

    public static Intent newIntent(Context context, long id) {
        return newIntent(context).putExtra(EXTRA.ID, id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBackButton();
        long accountId = getIntent().getLongExtra(EXTRA.ID, -1);
        if (accountId != -1) {
            presenter.setup(accountId);
        } else {
            presenter.setup();
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_account;
    }

    @NonNull
    @Override
    protected IAccountPresenter getPresenterInstance() {
        return new AccountPresenter();
    }

    @Override
    public void showAccount(Account account) {
        nameInput.setText(account.getName());
        selectedSwitch.setChecked(account.isSelected());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        presenter.setSelected(isChecked);
    }

    @Override
    public void showNameError(@StringRes int resId) {
        nameInput.setError(getString(resId));
    }

    @Override
    public void setRemoveButtonVisible(boolean visible) {
        removeButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    @OnClick(R.id.save)
    void saveClick() {
        presenter.setName(nameInput.getTextString());
        presenter.setSelected(selectedSwitch.isChecked());
        presenter.save();
    }

    @OnClick(R.id.remove)
    void removeClick() {
        presenter.remove();
    }

    @Override
    public void onActionSucceed() {
        setResult(RESULT_OK);
        finish();
    }

}
