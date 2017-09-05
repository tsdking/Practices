package com.king.practices.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.king.practices.mvp.ui.fragment.TabOneFFragment;

/**
 * des:
 * author:xqf
 * date:2017/9/4 16:48
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {


    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return TabOneFFragment.newInstance("item:"+position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
