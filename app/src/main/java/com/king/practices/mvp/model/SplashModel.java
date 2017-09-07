package com.king.practices.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.practices.mvp.contract.SplashContract;

import javax.inject.Inject;

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
    public void getHistoryDate() {

    }
}
