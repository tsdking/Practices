package com.king.practices.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.practices.mvp.contract.GankDetailContract;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/9/3.
 */
@ActivityScope
public class GankDetailModel extends BaseModel implements GankDetailContract.Model {
    @Inject
    public GankDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
