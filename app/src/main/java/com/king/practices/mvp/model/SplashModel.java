package com.king.practices.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.practices.mvp.contract.SplashContract;
import com.king.practices.mvp.model.api.service.GankService;
import com.king.practices.mvp.model.entity.BaseGank;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2017/9/3.
 */
@ActivityScope
public class SplashModel extends BaseModel implements SplashContract.Model {
    @Inject
    public SplashModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<BaseGank<List<String>>> getHistoryDate() {
        return mRepositoryManager.obtainRetrofitService(GankService.class)
                .getHistoryDate();
    }
}
