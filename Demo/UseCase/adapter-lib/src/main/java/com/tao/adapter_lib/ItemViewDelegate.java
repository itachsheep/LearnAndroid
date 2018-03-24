package com.tao.adapter_lib;


/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);
    // TODO: 2017/9/15  what the fuck will do???
    public abstract void convert(ViewHolder holder, T t, int position);



}
