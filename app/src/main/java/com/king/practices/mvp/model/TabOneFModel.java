package com.king.practices.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.practices.mvp.contract.MainContract;
import com.king.practices.mvp.contract.TabOneFContract;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/9/3.
 */
@ActivityScope
public class TabOneFModel extends BaseModel implements TabOneFContract.Model {
    @Inject
    public TabOneFModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
