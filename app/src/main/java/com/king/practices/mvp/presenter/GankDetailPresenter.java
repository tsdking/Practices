package com.king.practices.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.base.App;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.king.practices.app.utils.DBManager;
import com.king.practices.greendao.GankDao;
import com.king.practices.mvp.contract.GankDetailContract;
import com.king.practices.mvp.model.api.service.Cateory;
import com.king.practices.mvp.model.entity.Gank;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * des:启动页
 * author:xqf
 * date:2017/9/3 18:49
 */
@ActivityScope
public class GankDetailPresenter extends BasePresenter<GankDetailContract.Model, GankDetailContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private ImageLoader imageLoader;
    private Gank gank;

    @Inject
    public GankDetailPresenter(GankDetailContract.Model model, GankDetailContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
        imageLoader =((App)mApplication).getAppComponent().imageLoader();
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }


    public void getData(Intent intent) {
        Preconditions.checkNotNull(intent);
        String id = intent.getStringExtra("id");
        gank = DBManager.getGankDao().queryBuilder().where(GankDao.Properties._id.eq(id)).unique();
        if (gank == null) {
            ArmsUtils.snackbarText("未知的Id");
            mRootView.killMyself();
        } else {
            if (Cateory.FULI.getType().equals(gank.getType())) {
                //福利视图
                mRootView.updataView(0, gank.getUrl());
            } else {
                //webview视图
                mRootView.updataView(1, gank.getUrl());
            }

        }

    }

    private int offset=0;
    public List<Gank> getFuliData() {
        List<Gank> list = DBManager.getGankDao()
                .queryBuilder()
                .where(
                        GankDao.Properties.Type.eq(Cateory.FULI.getType())
                )
                .offset(offset += 20)
                .limit(20)
                .list();
        list.add(0,gank);
        return list;
    }
}
