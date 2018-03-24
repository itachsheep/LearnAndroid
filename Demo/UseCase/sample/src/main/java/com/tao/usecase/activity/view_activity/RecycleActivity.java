package com.tao.usecase.activity.view_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tao.recycle_lib.base.ViewHolder;
import com.tao.recycle_lib.common.RecyCommonAdapter;
import com.tao.recycle_lib.wraper.HeaderAndFooterWrapper;
import com.tao.recycle_lib.wraper.LoadMoreWrapper;
import com.tao.usecase.R;
import com.tao.usecase.recycle_demo.RecycleDemoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public class RecycleActivity extends AppCompatActivity {
    private List<String> mDatas = new ArrayList<>();

    @BindView(R.id.id_recyclerview)RecyclerView mRecyclerView;
    private RecyCommonAdapter<String> mAdatper;
    private LoadMoreWrapper mLoadMoreWrapper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);
        initDatas();
        mAdatper = new RecyCommonAdapter<String>(this,R.layout.item_list,mDatas) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.id_item_list_title,s+" : "+position);
            }
        };

        initHeaderAndFooter();
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        for (int i = 0; i < 10; i++)
                        {
                            mDatas.add("Add:" + i);
                        }
                        mLoadMoreWrapper.notifyDataSetChanged();

                    }
                }, 3000);
            }
        });
//        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);
//        mRecyclerView.setAdapter(mAdatper);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mLoadMoreWrapper);

        mAdatper.setOnItemClickListener(new RecyCommonAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(RecycleActivity.this, "pos = " + position, Toast.LENGTH_SHORT).show();
//                mAdapter.notifyItemRemoved(position);
                startActivity(new Intent(RecycleActivity.this,RecycleDemoActivity.class));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdatper);
        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        TextView t2 = new TextView(this);
        t2.setText("Header 2");

        mHeaderAndFooterWrapper.addHeaderView(t1);
        mHeaderAndFooterWrapper.addHeaderView(t2);
        TextView f1 = new TextView(this);
        f1.setText("Footer 1");
        mHeaderAndFooterWrapper.addFootView(f1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_grid:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
                break;
            case R.id.action_staggered:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                break;
            case R.id.action_linearLayout:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycle_menu,menu);
        return true;
    }

    private void initDatas()
    {
        for (int i = 'A'; i <= 'z'; i++)
        {
            mDatas.add((char) i + "");
        }
    }
}
