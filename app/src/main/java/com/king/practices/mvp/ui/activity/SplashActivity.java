package com.king.practices.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.king.practices.R;
import com.king.practices.di.component.DaggerSplashComponent;
import com.king.practices.di.module.SplashModule;
import com.king.practices.mvp.contract.SplashContract;
import com.king.practices.mvp.presenter.SplashPresenter;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.disposables.Disposable;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {


    private Disposable subscribe;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerSplashComponent.builder()
                .appComponent(appComponent)
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                launchActivity(new Intent(SplashActivity.this, MainActivity.class));
                killMyself();
            }
        }, 2000);
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
}
