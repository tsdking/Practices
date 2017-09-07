package com.king.practices.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.king.practices.app.utils.DBManager;
import com.king.practices.mvp.contract.SplashContract;
import com.king.practices.mvp.model.entity.BaseGank;
import com.king.practices.mvp.model.entity.GankHistoryDate;
import com.king.practices.mvp.ui.activity.MainActivity;
import com.king.practices.mvp.ui.activity.SplashActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;

/**
 * des:启动页
 * author:xqf
 * date:2017/9/3 18:49
 */
@ActivityScope
public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public SplashPresenter(SplashContract.Model model, SplashContract.View rootView, RxErrorHandler handler
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

    public void toMainPager() {
        mRootView.launchActivity(new Intent((SplashActivity) mRootView, MainActivity.class));
        mRootView.killMyself();
    }

    public void initData() {
        Timber.tag("xqf").d("db_net_begin:" + System.currentTimeMillis());
        mModel.getHistoryDate()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        toMainPager();
                    }
                })
                .subscribe(new ErrorHandleSubscriber<BaseGank<List<String>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseGank<List<String>> listBaseGank) {
                        Timber.tag("xqf").d("db_net_end:" + System.currentTimeMillis());
                        if (listBaseGank != null && listBaseGank.isSuccess() && listBaseGank.getResults() != null) {
                            List<String> results = listBaseGank.getResults();
                            GankHistoryDate historyDate;
                            Timber.tag("xqf").d("db_insert_begin:" + System.currentTimeMillis());
                            if (DBManager.getGankHistoryDao().count() == 0) {
                                for (String res : results) {
                                    historyDate = new GankHistoryDate();
                                    historyDate.setMDate(res);
                                    DBManager.getGankHistoryDao().insertOrReplace(historyDate);
                                    Timber.tag("xqf").d("db_insert:" + res);
                                }
                            } else {
                                for (int i = 0; i < 5; i++) {
                                    historyDate = new GankHistoryDate();
                                    historyDate.setMDate(results.get(i));
                                    DBManager.getGankHistoryDao().insertOrReplace(historyDate);
                                    Timber.tag("xqf").d("db_insert:" + results.get(i));
                                }
                            }
                            Timber.tag("xqf").d("db_insert_end:" + System.currentTimeMillis());
                        }
                    }
                });
    }
}
