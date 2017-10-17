package com.tao.databind_lamda.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tao.databind_lamda.R;
import com.tao.databind_lamda.databinding.ActivityListBinding;
import com.tao.databind_lamda.viewmodel.ListViewModel;

/**
 * Created by SDT14324 on 2017/10/16.
 */

public class ListTestActivity extends AppCompatActivity {

    ActivityListBinding mBinding ;
    ListViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        viewModel = new ListViewModel(ListTestActivity.this);
        mBinding.setViewModel(viewModel);
        viewModel.initReyclerView(mBinding.listRecyclerView);
    }
}
