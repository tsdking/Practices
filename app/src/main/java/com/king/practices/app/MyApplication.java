package com.king.practices.app;

import android.content.Context;

import com.jess.arms.base.BaseApplication;

public class MyApplication extends BaseApplication {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
