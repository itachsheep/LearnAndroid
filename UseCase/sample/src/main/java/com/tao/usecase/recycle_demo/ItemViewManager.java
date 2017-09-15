package com.tao.usecase.recycle_demo;

import android.util.SparseArray;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public class ItemViewManager<T> {
    SparseArray<ItemViewController<T>> itemController = new SparseArray<>();
    public void convert(MyViewHolder holder, T item, int position)
    {
        int delegatesCount = itemController.size();
        for (int i = 0; i < delegatesCount; i++)
        {
            ItemViewController<T> delegate = itemController.valueAt(i);

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

    public ItemViewController getItemViewDelegate(int viewType)
    {
        return itemController.get(viewType);
    }
}
