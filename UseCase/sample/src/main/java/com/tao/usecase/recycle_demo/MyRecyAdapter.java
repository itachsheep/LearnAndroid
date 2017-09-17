package com.tao.usecase.recycle_demo;

import android.content.Context;

import java.util.List;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public abstract class MyRecyAdapter<T> extends MultiTypeItemRecycleAdapter<T> {
    protected int mLayoutId;
    public MyRecyAdapter(Context context,int layoutId, List<T> ds) {
        super(context, ds);
        mLayoutId = layoutId;

        // TODO: 2017/9/15 add itemViewController

        addItemViewController(new ItemViewController<T>() {
            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(MyViewHolder holder, T t, int position) {
                MyRecyAdapter.this.convert(holder,t,position);
            }

            @Override
            public int getItemViewLayoutId() {
                return mLayoutId;
            }
        });


    }

    protected abstract void convert(MyViewHolder holder, T t, int position);
}
