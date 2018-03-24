package com.tao.usecase.recycle_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.widget.TextView;

import com.tao.usecase.R;
import com.tao.usecase.recycle_demo.view.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public class RecycleDemoActivity extends AppCompatActivity {
    private List<String> mDatas;
    private MyRecyAdapter<String> mInnerAdapter;

    private HeadAndFootAdapter<String> mHeadAndFootAdapter;


    @BindView(R.id.demo_recy_view)RecyclerView mRecycleView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_recycle);
        ButterKnife.bind(this);
        initData();
        initRecycleView();


    }

    private void initRecycleView() {
        mInnerAdapter = new MyRecyAdapter<String>(RecycleDemoActivity.this,
                R.layout.item_recy_demo,mDatas){
            @Override
            protected void convert(MyViewHolder holder, String s, int position) {
                holder.setText(R.id.id_num,s);
            }
        };

        mHeadAndFootAdapter = new HeadAndFootAdapter<>(mInnerAdapter);

        TextView tvHead = new TextView(RecycleDemoActivity.this);
        tvHead.setText("这是标题");
        tvHead.setTextSize(25);

        TextView tvTail = new TextView(RecycleDemoActivity.this);
        tvTail.setText("这是结尾");
        tvTail.setTextSize(25);
        tvTail.setTextColor(Color.RED);


        mHeadAndFootAdapter.addHeaderView(tvHead);
        mHeadAndFootAdapter.addFooterView(tvTail);

        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        LinearItemDecoration itemDecoration = new LinearItemDecoration(RecycleDemoActivity.this,
                DividerItemDecoration.VERTICAL, 50, R.color.green);
        mRecycleView.addItemDecoration(itemDecoration);

        mRecycleView.setAdapter(mHeadAndFootAdapter);
    }


    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'D'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }
}
