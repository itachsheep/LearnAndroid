package com.tao.databind_lamda.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tao.databind_lamda.R;
import com.tao.databind_lamda.databinding.ActivityMainBinding;
import com.tao.databind_lamda.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setViewModel(new MainViewModel(MainActivity.this));
    }
}
