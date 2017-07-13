package com.sprinklebit.task.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sprinklebit.task.R;
import com.sprinklebit.task.widget.EmptyView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by voltazor on 12/07/17.
 */
public abstract class BaseListFragment<T, P extends IBasePresenter> extends BaseMvpFragment<P> {

    @BindView(R.id.emptyView)
    protected EmptyView emptyView;

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;

    protected BaseAdapter<T, ?> adapter;

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    protected abstract BaseAdapter<T, ?> newAdapterInstance(List<T> items);

    protected void setItems(List<T> items) {
        boolean empty = items == null || items.isEmpty();
        emptyView.setVisibility(empty ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(empty ? View.GONE :View.VISIBLE);
        if (adapter == null) {
            adapter = newAdapterInstance(items);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setItems(items);
        }
    }

}
