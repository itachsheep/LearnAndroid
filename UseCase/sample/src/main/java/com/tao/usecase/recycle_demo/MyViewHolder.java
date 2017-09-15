package com.tao.usecase.recycle_demo;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public class MyViewHolder extends ViewHolder {
    private View mConvertView;
    private SparseArray<View> mViews;
    public MyViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        mConvertView = itemView;
    }

    public <T extends View> T getView(int resId){
        View v = mViews.get(resId);
        if(v == null){
            v = mConvertView.findViewById(resId);
            mViews.put(resId,v);
        }
        return (T)v;
    }

    public ViewHolder setText(int resId, String mes){
        TextView tv = getView(resId);
        tv.setText(mes);
        return this;
    }
}
