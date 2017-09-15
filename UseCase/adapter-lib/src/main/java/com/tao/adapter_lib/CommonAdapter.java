package com.tao.adapter_lib;

import android.content.Context;

import java.util.List;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    public CommonAdapter(Context context, List datas,final int layoutId) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }
    protected abstract void convert(ViewHolder viewHolder, T item, int position);
}
