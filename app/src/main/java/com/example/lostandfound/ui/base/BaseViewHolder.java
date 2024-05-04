package com.example.lostandfound.ui.base;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<DB extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final DB binding;

    public BaseViewHolder(DB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
