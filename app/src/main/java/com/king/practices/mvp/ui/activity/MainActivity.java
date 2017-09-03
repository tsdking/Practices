package com.king.practices.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.king.practices.R;
import com.king.practices.di.component.DaggerMainComponent;
import com.king.practices.di.module.MainModule;
import com.king.practices.mvp.contract.MainContract;
import com.king.practices.mvp.presenter.MainPresenter;
import com.king.practices.mvp.ui.widget.BottomNavigation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private RxPermissions mRxPermissions;
    private BottomNavigation navView;

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
        navView=findViewById(R.id.bnve);

        navView.enableShiftingMode(false);
        navView.enableAnimation(false);
        navView.enableItemShiftingMode(false);
        navView.setCurrentItem(0);
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
}
