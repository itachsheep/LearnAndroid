package com.java.coordinatordemo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.java.coordinatordemo.R;

/**
 * Created by SDT14324 on 2017/9/22.
 */

public class MyViewHolder<T> extends RecyclerView.ViewHolder {
    private View mView;
    public MyViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void updateView(T dt){
        ((TextView)mView.findViewById(R.id.item_recy_tv)).setText((String)dt);
    }

}
