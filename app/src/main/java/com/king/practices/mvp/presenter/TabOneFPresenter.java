package com.king.practices.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.king.practices.app.utils.DBManager;
import com.king.practices.mvp.contract.TabOneFContract;
import com.king.practices.mvp.model.entity.BaseGank;
import com.king.practices.mvp.model.entity.GankEveryDay;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * des:启动页
 * author:xqf
 * date:2017/9/3 18:49
 */
@ActivityScope
public class TabOneFPresenter extends BasePresenter<TabOneFContract.Model, TabOneFContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public TabOneFPresenter(TabOneFContract.Model model, TabOneFContract.View rootView, RxErrorHandler handler
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

    public void fetchData() {
        mModel.getLasteDatas()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<BaseGank<GankEveryDay>>() {
                    @Override
                    public void accept(BaseGank<GankEveryDay> ganks) throws Exception {
                        if (ganks!=null &&ganks.isSuccess()){
                            GankEveryDay results = ganks.getResults();
                            DBManager.getGankDao().insertOrReplaceInTx(results.getAndroid());
                            DBManager.getGankDao().insertOrReplaceInTx(results.getWeb());
                            DBManager.getGankDao().insertOrReplaceInTx(results.getVideo());
                            DBManager.getGankDao().insertOrReplaceInTx(results.getFuli());
                            DBManager.getGankDao().insertOrReplaceInTx(results.getiOS());
                        }

                    }
                })
                .subscribe(new ErrorHandleSubscriber<BaseGank<GankEveryDay>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseGank<GankEveryDay> stringBaseGank) {
                        ArmsUtils.snackbarText("lala");
                    }
                });


    }
}
