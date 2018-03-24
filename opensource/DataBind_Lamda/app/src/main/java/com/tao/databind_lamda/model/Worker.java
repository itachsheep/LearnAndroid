package com.tao.databind_lamda.model;

import android.databinding.ObservableArrayMap;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableMap;

/**
 * Created by SDT14324 on 2017/10/20.
 */

public class Worker {

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableInt id = new ObservableInt();
    public final ObservableMap<String,String> map = new ObservableArrayMap<>();


    public Worker(String name,int id){
        this.name.set(name);
        this.id.set(id);
        map.put("key","this is a map!");
    }
}
