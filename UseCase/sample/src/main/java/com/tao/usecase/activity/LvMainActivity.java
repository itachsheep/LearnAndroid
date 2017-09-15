package com.tao.usecase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tao.adapter_lib.CommonAdapter;
import com.tao.adapter_lib.ViewHolder;
import com.tao.usecase.R;
import com.tao.usecase.activity.view_activity.RecycleActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SDT14324 on 2017/9/15.
 */

public class LvMainActivity extends AppCompatActivity {

    @BindView(R.id.id_empty_view) View emptyView;

    @BindView(R.id.id_listview_list)
    ListView mListView;
    private List<String> mDatas = new ArrayList<>(Arrays.asList(
            "MultiItem ListView",
            "RecyclerView",
            "MultiItem RecyclerView"));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lv);
        ButterKnife.bind(this);
        mListView.setAdapter(new CommonAdapter<String>(this,mDatas,R.layout.item_list) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.id_item_list_title,item);
            }
        });

        mListView.setEmptyView(emptyView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
//                        startActivity(new Intent(LvMainActivity.this,));
                        break;
                    case 1:
                        startActivity(new Intent(LvMainActivity.this, RecycleActivity.class));
                        break;
                    case 2:
                        break;
                }
            }
        });
    }
}
