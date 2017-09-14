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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
    private int mRequestype = Constants.RequestType.LASTED;
    @Constants.LoadType
    private int mLoadType;
    private List<Gank> datas;
    private int pageIndex = 1;//第几页

    @Inject
    public TabOneFPresenter(TabOneFContract.Model model, TabOneFContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }


    public void fetchData(@Constants.LoadType int loadType) {
        fetchData(mRequestype, loadType);
    }

    @Constants.RequestType
    public int getmRequestype() {
        return mRequestype;
    }

    public void fetchData(@Constants.RequestType int requestype, @Constants.LoadType int loadType) {
        mLoadType = loadType;
        mRequestype = requestype;
        if (mRequestype == Constants.RequestType.LASTED || mRequestype == Constants.RequestType.RANDMOD) {
            //大杂烩
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
                                saveGank(results.getExtend());
                            }
                        }
                    })

                    .subscribe(new ErrorHandleSubscriber<BaseGank<GankEveryDay>>(mErrorHandler) {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            addDispose(d);
                        }

                        @Override
                        public void onNext(@NonNull BaseGank<GankEveryDay> ganks) {
                            if (datas == null) {
                                datas = new ArrayList<Gank>();
                            }else{
                                datas.clear();
                            }
                            if (mLoadType ==Constants.LoadType.MORE) {
                                //加载更多 .offset(offset+=20)
                                datas=(DBManager.getGankDao().queryBuilder().offset(offset+=20).limit(10).list());
                            } else {
                                //刷新
                                offset = 0;
                                GankEveryDay results = ganks.getResults();
                                if ((results.getAndroid() != null)) {
                                    datas.addAll(results.getAndroid());
                                }
                                if (results.getWeb() != null) {
                                    datas.addAll(results.getWeb());
                                }
                                if (results.getiOS() != null) {
                                    datas.addAll(results.getiOS());
                                }
                                if (results.getFuli() != null) {
                                    datas.addAll(results.getFuli());
                                }
                                if (results.getVideo() != null) {
                                    datas.addAll(results.getVideo());
                                }
                                if (results.getExtend() != null) {
                                    datas.addAll(results.getExtend());
                                }
                            }
                            mRootView.updataList(datas, mLoadType);
                        }
                    });
        }

        if (mRequestype == Constants.RequestType.ANDROID
                ||mRequestype == Constants.RequestType.IOS
                ||mRequestype == Constants.RequestType.WEB
                ||mRequestype == Constants.RequestType.VIDEO
                ||mRequestype == Constants.RequestType.FULI
                ||mRequestype == Constants.RequestType.EXTEND
                ){
            //分类资源
            pageIndex=(mLoadType== Constants.LoadType.Refresh)?1:pageIndex+1;
            mModel.getCateoryData(mRequestype,pageIndex)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<BaseGank<List<Gank>>>() {
                        @Override
                        public void accept(BaseGank<List<Gank>> listBaseGank) throws Exception {
                            if (listBaseGank != null && listBaseGank.getResults()!=null && listBaseGank.getResults().size()>0) {
                                saveGank(listBaseGank.getResults());
                            }
                        }
                    })
                    .subscribe(new ErrorHandleSubscriber<BaseGank<List<Gank>>>(mErrorHandler) {
                        @Override
                        public void onNext(@NonNull BaseGank<List<Gank>> listBaseGank) {
                            mRootView.updataList(listBaseGank.getResults(), mLoadType);
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
        if (gankList == null || gankList.size() == 0) {
            return;
        }
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
