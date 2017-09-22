package com.java.coordinatordemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.coordinatordemo.viewholder.MyViewHolder;

import java.util.List;

/**
 * Created by SDT14324 on 2017/9/22.
 */

public class RecyAdapter<T> extends RecyclerView.Adapter<MyViewHolder<T>> {
    private Context mContext;
    private int layoutId;
    private List<T> mDatas;
    public RecyAdapter(Context context,int resId,List<T> ls){
        mContext = context;
        layoutId = resId;
        mDatas = ls;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate( layoutId,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.updateView(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return (mDatas == null ? 0 : mDatas.size());
    }
}
