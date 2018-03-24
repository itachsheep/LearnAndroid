package com.tao.usecase.recycle_demo;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public interface ItemViewController<T> {
    boolean isForViewType(T item, int position);

    void convert(MyViewHolder holder, T t, int position);

    int getItemViewLayoutId();
}
