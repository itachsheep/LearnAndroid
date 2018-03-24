package com.tao.usecase.recycle_demo;

import android.util.SparseArray;

/**
 * Created by SDT14324 on 2017/9/15.
 * ItemViewController的管理类，RecycleView有很多种类的item
 *
 */

public class ItemViewManager<T> {
    SparseArray<ItemViewController<T>> itemControllers = new SparseArray<>();


    /***********************************************************
     *   重要的三个方法  getViewType() convert()
     ***********************************************************/

    //为每个item type增加 controller
    public ItemViewManager<T> addItemViewController(ItemViewController<T> controller){
        int size = itemControllers.size();
        if(controller != null){
            itemControllers.put(size,controller);
            size++;
        }
        return this;
    }

    //itemControllers.size代表 item种类总数,根据每个item获取特定的controller
    public int getViewType(T item,int position){
        int size = itemControllers.size();
        for(int i = size -1; i >= 0; i--){
            ItemViewController<T> tItemViewController = itemControllers.valueAt(i);
            if(tItemViewController.isForViewType(item,position)){
                return itemControllers.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    //用特定的itemController来实现不同item的布局
    public void convert(MyViewHolder holder, T item, int position)
    {
        int delegatesCount = itemControllers.size();
        for (int i = 0; i < delegatesCount; i++)
        {
            ItemViewController<T> delegate = itemControllers.valueAt(i);

            // TODO: 2017/9/15  what does this mean ?? fuck
            if (delegate.isForViewType(item, position))
            {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }


    /***************************分割线*********************************/

    //each item has only one itemViewController
    public ItemViewController getItemViewController(int viewType)
    {
        return itemControllers.get(viewType);
    }



    public int getItemViewDelegateCount(){
        return itemControllers.size();
    }
}
