package com.tao.usecase.recycle_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;

import com.tao.usecase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public class RecycleDemoActivity extends AppCompatActivity {

    @BindView(R.id.demo_recy_view)RecyclerView mRecycleView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_recycle);
        ButterKnife.bind(this);


//        mRecycleView.setLayoutManager();
        //设置Item增加、移除动画
//        mRecycleView.setItemAnimator();
//        mRecycleView.setAdapter();
        //设置Item增加、移除动画
        mRecycleView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }
}
