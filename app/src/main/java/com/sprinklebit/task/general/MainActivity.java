package com.sprinklebit.task.general;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.sprinklebit.task.R;
import com.sprinklebit.task.accounts.view.AccountsFragment;
import com.sprinklebit.task.base.BaseActivity;
import com.sprinklebit.task.interfaces.MainScreenPage;
import com.sprinklebit.task.transactions.view.TransactionsFragment;

import butterknife.BindView;


import static com.sprinklebit.task.interfaces.MainScreenPage.*;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    @MainScreenPage
    private int currentPage = -1;
    private int page;

    @BindView(R.id.container)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        showPage(DASHBOARD);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStop() {
        bottomNavigation.setOnNavigationItemSelectedListener(null);
        super.onStop();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard:
                page = DASHBOARD;
                break;
            case R.id.transactions:
                page = TRANSACTIONS;
                break;
            case R.id.accounts:
                page = ACCOUNTS;
                break;
        }
        showPage(page);
        return true;
    }

    private void showPage(int showPage) {
        if (currentPage != showPage) {
            currentPage = showPage;
            setPage(showPage);
        }
    }

    private void setPage(@MainScreenPage int page) {
        currentPage = page;
        switch (page) {
            case DASHBOARD:
                setTitle(R.string.dashboard);
                break;
            case TRANSACTIONS:
                setTitle(R.string.transactions);
                break;
            case ACCOUNTS:
                setTitle(R.string.accounts);
                break;
        }
        viewPager.setCurrentItem(page);
    }

    private static class MainPagerAdapter extends FragmentPagerAdapter {

        MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(@MainScreenPage int position) {
            switch (position) {
                case DASHBOARD:
                    return TransactionsFragment.newInstance(true);
                case TRANSACTIONS:
                    return TransactionsFragment.newInstance();
                case ACCOUNTS:
                    return AccountsFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

}
