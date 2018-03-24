package com.tao.recycle_lib.common;

import android.content.Context;
import android.view.LayoutInflater;

import com.tao.recycle_lib.base.ItemViewDelegate;
import com.tao.recycle_lib.base.ViewHolder;

import java.util.List;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public abstract class RecyCommonAdapter<T> extends RecyMultiItemTypeAdapter<T> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    public RecyCommonAdapter(final Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                RecyCommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);

}
