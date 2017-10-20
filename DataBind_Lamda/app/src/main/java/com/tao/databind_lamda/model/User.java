package com.tao.databind_lamda.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.tao.databind_lamda.BR;

/**
 * Created by SDT14324 on 2017/10/19.
 */

public class User extends BaseObservable{
    public String userName;
    public int userAge;

    public User(String name, int age) {
        this.userName = name;
        this.userAge = age;
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
//        notifyChange();
    }

    @Bindable
    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
        notifyPropertyChanged(BR.userAge);
//        notifyChange();
    }
}
