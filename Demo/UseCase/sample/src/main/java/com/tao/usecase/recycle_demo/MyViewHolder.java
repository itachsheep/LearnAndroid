package com.tao.usecase.recycle_demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public class MyViewHolder extends ViewHolder {
    private View mConvertView;
    private SparseArray<View> mViews;
    private Context mContext;
    public MyViewHolder(Context context,View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        mConvertView = itemView;
        mContext = context;
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
    public static MyViewHolder createViewHolder(Context context,View itemView){
        MyViewHolder holder = new MyViewHolder(context,itemView);
        return holder;
    }

    public static MyViewHolder createViewHolder(Context context,
                                              ViewGroup parent, int layoutId)
    {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        MyViewHolder holder = new MyViewHolder(context,itemView);
        return holder;
    }

    public View getConvertView()
    {
        return mConvertView;
    }
}
