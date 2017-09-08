package com.king.practices.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.king.practices.app.Constants;
import com.king.practices.app.utils.DBManager;
import com.king.practices.mvp.contract.TabOneFContract;
import com.king.practices.mvp.model.entity.BaseGank;
import com.king.practices.mvp.model.entity.Gank;
import com.king.practices.mvp.model.entity.GankEveryDay;

import java.util.List;

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
    private int offset = 0;
    @Constants.RequestType
    private int mRequestype = 0;
    private boolean loadType;

    @Inject
    public TabOneFPresenter(TabOneFContract.Model model, TabOneFContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    public void fetchData(@Constants.RequestType int requestype, boolean isLoadMore) {
        loadType = isLoadMore;
        mRequestype=requestype;
        if (mRequestype== Constants.RequestType.LASTED || mRequestype== Constants.RequestType.RANDMOD){
            mModel.getLasteDatas(mRequestype)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<BaseGank<GankEveryDay>>() {
                        @Override
                        public void accept(BaseGank<GankEveryDay> ganks) throws Exception {
                            if (ganks != null && ganks.isSuccess()) {
                                GankEveryDay results = ganks.getResults();
                                saveGank(results.getAndroid());
                                saveGank(results.getWeb());
                                saveGank(results.getVideo());
                                saveGank(results.getFuli());
                                saveGank(results.getiOS());
                            }
                        }
                    })
                    .subscribe(new ErrorHandleSubscriber<BaseGank<GankEveryDay>>(mErrorHandler) {
                        @Override
                        public void onNext(@NonNull BaseGank<GankEveryDay> stringBaseGank) {
                            List<Gank> datas = DBManager.getGankDao().queryBuilder().offset(offset).limit(10).list();
                            mRootView.updataList(datas, loadType);
                        }
                    });
        }


    }

    /**
     * 保存gank数据到本地
     *
     * @param gankList
     */
    private void saveGank(List<Gank> gankList) {
        for (Gank gank : gankList) {
            List<String> images = gank.getImages();
            if (images != null && images.size() > 0) {
                gank.setImage(images.get(0));
            }
            DBManager.getGankDao().insertOrReplace(gank);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }

}
