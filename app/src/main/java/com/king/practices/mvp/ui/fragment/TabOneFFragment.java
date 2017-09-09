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
import com.king.practices.app.Constants;
import com.king.practices.di.component.DaggerTabOneFComponent;
import com.king.practices.di.module.TabOneFModule;
import com.king.practices.mvp.contract.TabOneFContract;
import com.king.practices.mvp.model.entity.Gank;
import com.king.practices.mvp.presenter.TabOneFPresenter;
import com.king.practices.mvp.ui.adapter.GankAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

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
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private GankAdapter adapter;


    public static TabOneFFragment newInstance(String index) {
        TabOneFFragment fragment = new TabOneFFragment();
        Bundle args = new Bundle();
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
        navigationView.setNavigationItemSelectedListener(this);
        ArmsUtils.configRecycleView(recyclerView, new LinearLayoutManager(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.fetchData(false);
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.fetchData(true);
                refreshlayout.finishLoadmore(2000);
            }
        });
        mPresenter.fetchData(false);
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
                //随机
                toolbarTitle.setText("随机推荐");
                mPresenter.fetchData(Constants.RequestType.RANDMOD, false);
                navigationView.setCheckedItem(R.id.nav_latest);
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        String title = "干货";
        if (id == R.id.nav_latest) {
            //最新
            title = "最新推荐";
            mPresenter.fetchData(Constants.RequestType.LASTED, false);
        } else if (id == R.id.nav_android) {
            //android
            title = "Android";
            mPresenter.fetchData(Constants.RequestType.ANDROID, false);
        } else if (id == R.id.nav_ios) {
            //ios
            title = "IOS";
            mPresenter.fetchData(Constants.RequestType.IOS, false);
        } else if (id == R.id.nav_web) {
            //前端
            title = "前端";
            mPresenter.fetchData(Constants.RequestType.WEB, false);
        } else if (id == R.id.nav_video) {
            //视频
            title = "视频";
            mPresenter.fetchData(Constants.RequestType.VIDEO, false);
        } else if (id == R.id.nav_fuli) {
            //福利
            title = "福利";
            mPresenter.fetchData(Constants.RequestType.FULI, false);
        }
        toolbarTitle.setText(title);
        return true;
    }

    @Override
    public void updataList(List<Gank> datas, boolean isLoadMore) {
        if (adapter == null) {
            adapter = new GankAdapter(datas);
            recyclerView.setAdapter(adapter);
        } else {
            if (isLoadMore) {
                adapter.addData(datas);
            } else {
                adapter.setNewData(datas);
            }
        }
    }
}
