package com.king.practices.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.practices.mvp.contract.MainContract;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/9/3.
 */
@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
