package com.king.practices.mvp.presenter;

import android.app.Application;
import android.support.v4.app.FragmentActivity;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.king.practices.mvp.contract.MainContract;
import com.king.practices.mvp.ui.adapter.MainPagerAdapter;
import com.king.practices.mvp.ui.fragment.TabOneFFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * des:启动页
 * author:xqf
 * date:2017/9/3 18:49
 */
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }

    public void getData() {
        if (mRootView instanceof FragmentActivity){
            MainPagerAdapter adapter = new MainPagerAdapter(((FragmentActivity) mRootView).getSupportFragmentManager());
            ArrayList<BaseFragment> mDatas = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                mDatas.add(TabOneFFragment.newInstance("item:"+i));
            }
            adapter.setmDatas(mDatas);
            mRootView.setAdapter(adapter);

        }
    }
}
