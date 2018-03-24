package com.java.coordinatordemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.java.coordinatordemo.Adapter.BaseFragmentPagerAdapter;
import com.java.coordinatordemo.fragment.ViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorActivity extends AppCompatActivity {
    private TabLayout tabLayout;
//    private ViewPager viewPager;

    String[] mTitles = new String[]{
            "主页", "微博", "相册"
    };

    List<Fragment> mfragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        viewPager = (ViewPager) findViewById(R.id.ac_viewpager);




    }
    private PagerAdapter mAdapter;
    @Override
    protected void onResume() {
        super.onResume();


        for(int i  = 0 ; i < mTitles.length ; i++) {
            ViewPagerFragment fragment = new ViewPagerFragment();
            fragment.setTitle(mTitles[i]);
            mfragments.add(fragment);
        }

        mAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(),
                mfragments,mTitles);
//        viewPager.setAdapter(mAdapter);
//        tabLayout.setupWithViewPager(viewPager);
    }
}
