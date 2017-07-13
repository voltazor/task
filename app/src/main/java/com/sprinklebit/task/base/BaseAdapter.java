package com.sprinklebit.task.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sprinklebit.task.interfaces.OnItemClickListener;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by voltazor on 12/07/17.
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final Context context;
    private final CopyOnWriteArrayList<T> items;
    private final LayoutInflater layoutInflater;
    @Nullable
    protected final OnItemClickListener<T> itemClickListener;

    public BaseAdapter(Context context) {
        this(context, null, null);
    }

    public BaseAdapter(Context context, Collection<T> items) {
        this(context, items, null);
    }

    public BaseAdapter(Context context, @Nullable OnItemClickListener<T> listener) {
        this(context, null, listener);
    }

    public BaseAdapter(Context context, Collection<T> items, @Nullable OnItemClickListener<T> listener) {
        layoutInflater = LayoutInflater.from(context);
        itemClickListener = listener;
        this.items = (items == null) ? new CopyOnWriteArrayList<T>() : new CopyOnWriteArrayList<>(items);
        this.context = context;
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public T getItem(int position) {
        return position < 0 || position >= items.size() ? null : items.get(position);
    }

    public int getItemPosition(T item) {
        return items.indexOf(item);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        onBindViewHolder(holder, getItem(holder.getAdapterPosition()), position);
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    itemClickListener.onItemClick(getItem(position), position);
                }
            });
        }
    }

    public List<T> getItems() {
        return items;
    }

    public void addItem(T item) {
        if (items.add(item)) {
            notifyDataSetChanged();
        }
    }

    public void addItem(int position, T item) {
        items.add(position, item);
        notifyDataSetChanged();
    }

    public void addItems(Collection<T> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void setItem(int position, T item) {
        items.set(position, item);
        notifyDataSetChanged();
    }

    public void setItems(List<T> list) {
        this.items.clear();
        this.items.addAll(list);
        notifyDataSetChanged();
    }

    public void removeItem(T item) {
        if (items.remove(item)) {
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        if (items.remove(position) != null) {
            notifyDataSetChanged();
        }
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    protected Context getContext() {
        return context;
    }

    protected String getString(@StringRes int strResId) {
        return getContext().getString(strResId);
    }

    protected String getString(@StringRes int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    protected LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    protected View inflate(@LayoutRes int layoutResId, ViewGroup parent) {
        return getLayoutInflater().inflate(layoutResId, parent, false);
    }

    public abstract void onBindViewHolder(VH holder, T item, int position);

    @Override
    public int getItemCount() {
        return items.size();
    }

}
