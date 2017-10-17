package com.tao.databind_lamda.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.tao.databind_lamda.view.LambdaActivity;
import com.tao.databind_lamda.view.ListTestActivity;

/**
 * Created by SDT14324 on 2017/10/16.
 */

public class MainViewModel {

    private Context mContext;

    public MainViewModel(Context context){
        this.mContext = context;
    }

    public void onlistClick(View view){
        mContext.startActivity(new Intent(mContext, ListTestActivity.class));
    }

    public void onlamdaClick(View view){
        mContext.startActivity(new Intent(mContext, LambdaActivity.class));
    }
}
