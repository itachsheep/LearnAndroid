package com.tao.usecase.recycle_demo;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by taowei on 2017/9/17.
 * 2017-09-17 20:35
 * UseCase
 * com.tao.usecase.recycle_demo
 */

public class HeadAndFootAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    private RecyclerView.Adapter mInerAdapter;
    public HeadAndFootAdapter(RecyclerView.Adapter adapter){
        mInerAdapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderViews.get(viewType) != null){
            MyViewHolder viewHolder = MyViewHolder.
                    createViewHolder(parent.getContext(), mHeaderViews.get(viewType));

            return viewHolder;
        }else if(mFooterViews.get(viewType) != null){
            MyViewHolder viewHolder = MyViewHolder.
                    createViewHolder(parent.getContext(), mFooterViews.get(viewType));
            return viewHolder;
        }
        return mInerAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isHeaderViewPos(position)){
            return;
        }else if(isFooterViewPos(position)){
            return;
        }
        mInerAdapter.onBindViewHolder(holder,position - getHeadersCount());
    }

    @Override
    public int getItemViewType(int position) {
        if(isHeaderViewPos(position)){
            return mHeaderViews.keyAt(position);
        }else if(isFooterViewPos(position)){
            return mFooterViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInerAdapter.getItemViewType(position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();

    }


    public void addHeaderView(View view){
        mHeaderViews.put(mHeaderViews.size()+BASE_ITEM_TYPE_HEADER,view);
    }

    public void addFooterView(View view){
        mFooterViews.put(mFooterViews.size()+BASE_ITEM_TYPE_FOOTER,view);
    }
    /***************************分割线*************************/

    private boolean isHeaderViewPos(int position)
    {
        return position < getHeadersCount();
    }

    public int getHeadersCount()
    {
        return mHeaderViews.size();
    }

    private boolean isFooterViewPos(int position)
    {
        return position >= getHeadersCount() + getRealItemCount();
    }

    private int getRealItemCount()
    {
        return mInerAdapter.getItemCount();
    }
    public int getFootersCount()
    {
        return mFooterViews.size();
    }
}
