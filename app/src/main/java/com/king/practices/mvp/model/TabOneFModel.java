package com.king.practices.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.practices.app.utils.CommonUtil;
import com.king.practices.app.utils.DBManager;
import com.king.practices.mvp.contract.TabOneFContract;
import com.king.practices.mvp.model.api.service.GankService;
import com.king.practices.mvp.model.entity.GankHistoryDate;

import java.util.List;

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
    public Observable<String> getLasteDatas() {
        List<GankHistoryDate> list = DBManager.getGankHistoryDao().queryBuilder().limit(1).list();
        return mRepositoryManager.obtainRetrofitService(GankService.class)
                .getEveryDayDatas(CommonUtil.converDateFormat(list.get(0).getMDate()));
    }
}
