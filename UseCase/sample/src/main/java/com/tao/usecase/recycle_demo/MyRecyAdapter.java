package com.tao.usecase.recycle_demo;

import android.content.Context;

import java.util.List;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public abstract class MyRecyAdapter<T> extends MultiTypeItemRecycleAdapter<T> {

    public MyRecyAdapter(Context context, List<T> ds) {
        super(context, ds);

        // TODO: 2017/9/15 add itemViewController 
    }
}
