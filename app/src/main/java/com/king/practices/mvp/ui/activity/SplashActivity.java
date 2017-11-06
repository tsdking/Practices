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

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {
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
        mPresenter.initData();
        //测试 SpannableStringUtils
//        TextView textView = (TextView) findViewById(R.id.tv);
//        textView.setMovementMethod(LinkMovementMethod.getInstance());
//        SpannableStringBuilder stringBuilder = new SpannableStringUtils.Builder().append("喜欢你")
//                .setForegroundColor(Color.BLUE)
//                .append("满口讲胡话")
//                .setAlign(Layout.Alignment.ALIGN_CENTER)
//                .setBackgroundColor(Color.GREEN)
//                .append("你和我")
//                .setBold()
//                .append(",谢谢!")
//                .setClickSpan(new ClickableSpan() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(SplashActivity.this, "谢谢", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setFontSize(30)
//                .setUnderline()
//                .setForegroundColor(Color.BLUE)
//                .append("")
//                .create();
//        textView.setText(stringBuilder);

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
