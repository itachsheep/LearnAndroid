package com.tao.databind_lamda.events;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.tao.databind_lamda.LogUtils;
import com.tao.databind_lamda.model.User;
import com.tao.databind_lamda.model.Worker;

/**
 * Created by SDT14324 on 2017/10/19.
 */

public class UserPresenter {
    private Context ctx;
    public UserPresenter(Context ctx){
        this.ctx = ctx;
    }

    public void onAgeClick(User user){
        LogUtils.i("onAgeClick user: "+user.getUserName()+", "+user.getUserAge());
        user.setUserName("习近平");
        user.setUserAge(77);
        Toast.makeText(ctx,"点击了expression button ！！",Toast.LENGTH_SHORT).show();
    }

    public void onTestClick(View view, User user){
        LogUtils.i("onTestClick user: "+user.getUserName()+", "+user.getUserAge()+", view: "+view);
        Toast.makeText(ctx,"hhahah ！！",Toast.LENGTH_SHORT).show();
    }

    public void onChangeTest(User user, Worker worker){
        user.setUserName("习近平");
        user.setUserAge(77);

        worker.name.set("胡锦涛");
        worker.id.set(00111);
        worker.map.put("key","改变map value");

        Toast.makeText(ctx,"改变数据！！",Toast.LENGTH_SHORT).show();
    }
}
