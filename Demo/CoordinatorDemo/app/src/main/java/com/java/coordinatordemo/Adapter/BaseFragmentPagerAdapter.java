package com.java.coordinatordemo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SDT14324 on 2017/9/22.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    protected List<Fragment> mFragmentList = new ArrayList<>();

    protected String[] mTitles;
    public BaseFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragmentList, String[] mTitles) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
