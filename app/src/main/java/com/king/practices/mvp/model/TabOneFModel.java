package com.king.practices.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.practices.app.Constants;
import com.king.practices.app.utils.DBManager;
import com.king.practices.mvp.contract.TabOneFContract;
import com.king.practices.mvp.model.api.Api;
import com.king.practices.mvp.model.api.service.Cateory;
import com.king.practices.mvp.model.api.service.GankService;
import com.king.practices.mvp.model.entity.BaseGank;
import com.king.practices.mvp.model.entity.Gank;
import com.king.practices.mvp.model.entity.GankEveryDay;
import com.king.practices.mvp.model.entity.GankHistoryDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/9/3.
 */
@ActivityScope
public class TabOneFModel extends BaseModel implements TabOneFContract.Model {
    @Inject
    public TabOneFModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseGank<GankEveryDay>> getLasteDatas(@Constants.RequestType int mRequestype) {
        List<GankHistoryDate> list=new ArrayList<>();
        if (mRequestype == Constants.RequestType.LASTED) {
            list = DBManager.getGankHistoryDao().queryBuilder().limit(1).list();
        }else if (mRequestype == Constants.RequestType.RANDMOD){
            long count = DBManager.getGankHistoryDao().queryBuilder().count()-1;
            list = DBManager.getGankHistoryDao().queryBuilder().offset(new Random().nextInt((int) count)).limit(1).list();
        }
        if (list==null || list.size()==0){
            throw  new IllegalArgumentException("未查询到任何特定日期");
        }
        String date = list.get(0).getMDate();
        String[] split = date.split("-");
        return mRepositoryManager.obtainRetrofitService(GankService.class)
                .getEveryDayDatas(split[0], split[1], split[2]);
    }

    @Override
    public Observable<BaseGank<List<Gank>>> getCateoryData(@Constants.RequestType int mRequestype, int pageIndex) {
        String cateory = Cateory.ALL.getType();
        switch (mRequestype) {
            case Constants.RequestType.ANDROID:
                cateory = Cateory.ANDROID.getType();
                break;
            case Constants.RequestType.EXTEND:
                cateory = Cateory.EXTEND.getType();
                break;
            case Constants.RequestType.FULI:
                cateory = Cateory.FULI.getType();
                break;
            case Constants.RequestType.IOS:
                cateory = Cateory.IOS.getType();
                break;
            case Constants.RequestType.VIDEO:
                cateory = Cateory.VIDEO.getType();
                break;
            case Constants.RequestType.WEB:
                cateory = Cateory.WEB.getType();
                break;
        }
        return mRepositoryManager.obtainRetrofitService(GankService.class)
                .getCategoryDatas(cateory, Api.PAGE_SIZE, pageIndex);
    }
}
