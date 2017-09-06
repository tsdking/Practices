package com.king.practices.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.king.practices.R;
import com.king.practices.di.component.DaggerMainComponent;
import com.king.practices.di.module.MainModule;
import com.king.practices.mvp.contract.MainContract;
import com.king.practices.mvp.presenter.MainPresenter;
import com.king.practices.mvp.ui.fragment.TabOneFFragment;
import com.king.practices.mvp.ui.widget.BottomNavigation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.bottom_nav)
    BottomNavigation navView;
    private RxPermissions mRxPermissions;
    private FragmentManager manager;
    private TabOneFFragment tabOneFFragment;
    private TabOneFFragment tabTwoFFragment;
    private TabOneFFragment tabThreeFFragment;
    private TabOneFFragment tabFourFFragment;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        mRxPermissions = new RxPermissions(this);
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        navView.enableShiftingMode(false);
        navView.enableAnimation(false);
        navView.enableItemShiftingMode(false);
        navView.setCurrentItem(0);
        navView.setOnNavigationItemSelectedListener(this);
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        tabOneFFragment = TabOneFFragment.newInstance("item:" + 1);
        transaction.add(R.id.container, tabOneFFragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = manager.beginTransaction();
        hideAll(transaction);
        switch (item.getItemId()) {
            case R.id.nav_tab1:
                if (tabOneFFragment == null) {
                    tabOneFFragment = TabOneFFragment.newInstance("item:" + 1);
                    transaction.add(R.id.container, tabOneFFragment);
                } else {
                    transaction.show(tabOneFFragment);
                }
                break;
            case R.id.nav_tab2:
                if (tabTwoFFragment == null) {
                    tabTwoFFragment = TabOneFFragment.newInstance("item:" + 2);
                    transaction.add(R.id.container, tabTwoFFragment);
                } else {
                    transaction.show(tabTwoFFragment);
                }
                break;
            case R.id.nav_tab3:
                if (tabThreeFFragment == null) {
                    tabThreeFFragment = TabOneFFragment.newInstance("item:" + 3);
                    transaction.add(R.id.container, tabThreeFFragment);
                } else {
                    transaction.show(tabThreeFFragment);
                }
                break;
            case R.id.nav_tab4:
                if (tabFourFFragment == null) {
                    tabFourFFragment = TabOneFFragment.newInstance("item:" + 4);
                    transaction.add(R.id.container, tabFourFFragment);
                } else {
                    transaction.show(tabFourFFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
        return true;
    }

    private void hideAll(FragmentTransaction transaction) {
        if (tabOneFFragment != null) {
            transaction.hide(tabOneFFragment);
        }
        if (tabTwoFFragment != null) {
            transaction.hide(tabTwoFFragment);
        }
        if (tabThreeFFragment != null) {
            transaction.hide(tabThreeFFragment);
        }
        if (tabFourFFragment != null) {
            transaction.hide(tabFourFFragment);
        }
    }
}
