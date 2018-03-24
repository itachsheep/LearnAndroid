package com.tao.databind_lamda.model;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

/**
 * Created by SDT14324 on 2017/10/16.
 */

public class Employee implements Observable{
    private transient PropertyChangeRegistry mCallbacks;
    private String mLastName;
    private String mFirstName;

    public Employee(String lastName, String firstName) {
        mLastName = lastName;
        mFirstName = firstName;

    }

    @Bindable
    public String getFirstName() {
        return mFirstName;
    }

    @Bindable
    public String getLastName() {
        return mLastName;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
       /* synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);*/
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        /*synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(callback);*/
    }
}
