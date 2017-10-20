package com.tao.databind_lamda.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tao.databind_lamda.R;
import com.tao.databind_lamda.databinding.ActivityLambdaBinding;
import com.tao.databind_lamda.viewmodel.LambdaViewModel;

/**
 * Created by SDT14324 on 2017/10/16.
 */

public class LambdaActivity extends AppCompatActivity {
    ActivityLambdaBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lambda);
        binding.setViewModel(new LambdaViewModel(LambdaActivity.this));

    }
}
