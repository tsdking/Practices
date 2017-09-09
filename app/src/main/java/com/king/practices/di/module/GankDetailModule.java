
package com.king.practices.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.king.practices.mvp.contract.GankDetailContract;
import com.king.practices.mvp.model.GankDetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/9/3.
 */
@Module
public class GankDetailModule {
    private GankDetailContract.View view;

    /**
     * GankDetailModule,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GankDetailModule(GankDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GankDetailContract.View provideGankDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GankDetailContract.Model provideGankDetailModel(GankDetailModel model) {
        return model;
    }

}
