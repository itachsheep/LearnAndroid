package com.tao.databind_lamda.view;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableMap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tao.databind_lamda.R;
import com.tao.databind_lamda.adapter.RecycleViewUtils;
import com.tao.databind_lamda.databinding.ActivityDemoBinding;
import com.tao.databind_lamda.events.UserHandler;
import com.tao.databind_lamda.events.UserPresenter;
import com.tao.databind_lamda.model.RecycleUtilBean;
import com.tao.databind_lamda.model.User;
import com.tao.databind_lamda.model.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SDT14324 on 2017/10/19.
 */

public class DataBindingActivity extends AppCompatActivity {

    ActivityDemoBinding mbinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        User user = new User("taowei",200);
        UserHandler handler = new UserHandler(this);
        UserPresenter presenter = new UserPresenter(this);
        List<String> list = new ArrayList<>();
        list.add("this is list index = 0");

        Worker worker = new Worker("创维",14324);

        ObservableMap<String,String> map = new ObservableArrayMap<>();
        map.put("key","i am value");

        mbinding.setUserHandler(handler);
        mbinding.setPresenter(presenter);
        mbinding.setUser(user);
        mbinding.setIndex(0);
        mbinding.setList(list);
        mbinding.setWorker(worker);

        List<RecycleUtilBean> datalist = new ArrayList<>();
        for(int i = 1; i < 10; i++){
            list.add("this is list index : "+i);
        }
        new RecycleViewUtils<RecycleUtilBean>(this,list).initRecycleView(mbinding.mainRecycleView);

    }
}
