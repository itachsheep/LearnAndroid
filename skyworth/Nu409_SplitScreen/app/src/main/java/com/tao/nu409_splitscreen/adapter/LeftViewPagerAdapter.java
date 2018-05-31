package com.tao.nu409_splitscreen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by SDT14324 on 2018/3/12.
 */

public class LeftViewPagerAdapter extends FragmentStatePagerAdapter {

//    private List<Fragment> list = new ArrayList<>();
    private List<Fragment> list;

    public LeftViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        list = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
