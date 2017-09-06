package com.king.practices.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.king.practices.R;
import com.king.practices.di.component.DaggerTabOneFComponent;
import com.king.practices.di.module.TabOneFModule;
import com.king.practices.mvp.contract.TabOneFContract;
import com.king.practices.mvp.presenter.TabOneFPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 第一个页面
 */
public class TabOneFFragment extends BaseFragment<TabOneFPresenter> implements TabOneFContract.View, NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "lalala";


    @BindView(R.id.toolbar_ivback)
    ImageView toolbarIvback;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_ivmore)
    ImageView toolbarIvmore;
    @BindView(R.id.toolbar_more)
    RelativeLayout toolbarMore;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


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
        navigationView.setNavigationItemSelectedListener(this);
        ArmsUtils.configRecycleView(recyclerView, new LinearLayoutManager(getActivity()));
        mPresenter.fetchData();
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


    @OnClick({R.id.toolbar_back, R.id.toolbar_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.toolbar_more:
                ArmsUtils.snackbarText("分类");
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_latest) {
            //最新
        } else if (id == R.id.nav_random) {
            //随机推荐
        } else if (id == R.id.nav_category) {
            //分类
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
