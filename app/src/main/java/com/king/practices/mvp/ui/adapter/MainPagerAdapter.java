package com.king.practices.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;

import java.util.List;

/**
 * des:
 * author:xqf
 * date:2017/9/4 16:48
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {


    private List<BaseFragment> mDatas;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainPagerAdapter(FragmentManager fm, List<BaseFragment> mDatas) {
        super(fm);
        this.mDatas = mDatas;
    }

    public List<BaseFragment> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<BaseFragment> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}
