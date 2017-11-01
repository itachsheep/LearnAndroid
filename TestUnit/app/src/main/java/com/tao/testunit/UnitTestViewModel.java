package com.tao.testunit;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by SDT14324 on 2017/11/1.
 */

public class UnitTestViewModel {

    private Context context;

    public UnitTestViewModel(Context ctx){
        context = ctx;
    }

    public void onBtSayClick(){
        Toast.makeText(context,"hahaha !!",Toast.LENGTH_SHORT).show();
    }
}
