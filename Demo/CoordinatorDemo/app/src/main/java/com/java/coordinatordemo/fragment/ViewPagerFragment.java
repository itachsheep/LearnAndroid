package com.java.coordinatordemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.java.coordinatordemo.Adapter.RecyAdapter;
import com.java.coordinatordemo.LogUtils;
import com.java.coordinatordemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SDT14324 on 2017/9/22.
 */

public class ViewPagerFragment extends Fragment {

    private String tag = ViewPagerFragment.class.getSimpleName();
    private String title;
    public void setTitle(String mes){
        title = mes;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpage,container,false);
        initView(view);

        return view;
    }
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.frg_recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.frg_swipeRefreshLayout);

    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i(tag,"onStart ");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(tag,"onPause ");
    }

    private List<String> mDatas = new ArrayList<>();
    private RecyAdapter<String> mAdapter ;
    @Override
    public void onResume() {
        super.onResume();
        // TODO: 2017/9/22
        LogUtils.i(tag,"onResume ");


        for(int i = 0; i < 50 ; i++){
            String s = String.format(title+ " item: %d ",i);
            mDatas.add(s);
        }

        mSwipeRefreshLayout.setEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyAdapter<String>(getContext(),R.layout.recy_item,mDatas);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "刷新完成", Toast.LENGTH_SHORT).show();
                    }
                },1200);
            }
        });
    }


}
