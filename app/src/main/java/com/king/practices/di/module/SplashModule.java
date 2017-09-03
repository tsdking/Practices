package com.king.practices.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.king.practices.mvp.contract.SplashContract;
import com.king.practices.mvp.model.SplashModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/9/3.
 */
@Module
public class SplashModule {
    private SplashContract.View view;

    /**
     * SplashModule,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SplashModule(SplashContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SplashContract.View provideSplashView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SplashContract.Model provideSplashModel(SplashModel model) {
        return model;
    }

}
