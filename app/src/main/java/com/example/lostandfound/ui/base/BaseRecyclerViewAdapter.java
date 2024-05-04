package com.example.lostandfound.ui.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<DB extends ViewDataBinding, T> extends RecyclerView.Adapter<BaseViewHolder<DB>> {


    private List<T> data;

    public BaseRecyclerViewAdapter(List<T> initialData) {
        this.data = initialData;
    }

    public void setItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public abstract void bind(DB binding, T item);

    public void setData(List<T> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new BaseDiffCallback<>(data, newData));
        data = newData;
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public BaseViewHolder<DB> onCreateViewHolder(ViewGroup parent, int viewType) {
        DB binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(), parent, false);
        return new BaseViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<DB> holder, int position) {
        T item = data.get(position);
        bind(holder.binding, item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public abstract @LayoutRes
    int getLayoutId();

    private OnItemClickListener<T> itemClickListener;

    private class BaseDiffCallback<T> extends DiffUtil.Callback {
        private final List<T> oldList;
        private final List<T> newList;

        public BaseDiffCallback(List<T> oldList, List<T> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }
}

interface OnItemClickListener<T> {
    void onItemClick(T item);
}

