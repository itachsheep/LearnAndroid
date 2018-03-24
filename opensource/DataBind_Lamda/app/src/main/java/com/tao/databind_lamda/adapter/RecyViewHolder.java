package com.tao.databind_lamda.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by SDT14324 on 2017/10/16.
 */

public class RecyViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder{
    private T mBinding;

    public RecyViewHolder(T binding) {
        super(binding.getRoot());
        this.mBinding = binding;
    }

    public T getmBinding(){
        return mBinding;
    }
}
