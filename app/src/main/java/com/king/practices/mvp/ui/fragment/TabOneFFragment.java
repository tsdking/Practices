package com.king.practices.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.king.practices.R;
import com.king.practices.di.component.DaggerTabOneFComponent;
import com.king.practices.di.module.TabOneFModule;
import com.king.practices.mvp.contract.TabOneFContract;
import com.king.practices.mvp.presenter.TabOneFPresenter;
import com.king.practices.mvp.ui.adapter.DrawListAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 第一个页面
 */
public class TabOneFFragment extends BaseFragment<TabOneFPresenter> implements TabOneFContract.View {


    private static final String TAG = "lalala";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.drawList)
    RecyclerView drawList;


    public static TabOneFFragment newInstance(String index) {
        TabOneFFragment fragment = new TabOneFFragment();
        Bundle args = new Bundle();
        // TODO: 2017/9/4 外部可以传递参数
        args.putString("keyOne", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerTabOneFComponent.builder()
                .appComponent(appComponent)
                .tabOneFModule(new TabOneFModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_one, null, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        //获取newInstance 传入的初始化参数
        //---------------------------------------

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        ArmsUtils.configRecycleView(drawList, new LinearLayoutManager(getActivity()));
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("item:" + i);
        }
        drawList.setAdapter(new DrawListAdapter(R.layout.item_draw_list, data));
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

}
