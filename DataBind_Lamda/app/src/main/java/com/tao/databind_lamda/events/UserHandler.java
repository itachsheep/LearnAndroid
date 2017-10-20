package com.tao.databind_lamda.events;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by SDT14324 on 2017/10/19.
 */

public class UserHandler {

    private Context context;

    public UserHandler(Context ctx){
        this.context = ctx;
    }

    public void onUserNameClick(View view){
        Toast.makeText(context,"user name click!!",Toast.LENGTH_SHORT).show();
    }
}
