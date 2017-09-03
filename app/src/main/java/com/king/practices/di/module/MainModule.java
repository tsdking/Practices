
package com.king.practices.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.king.practices.mvp.contract.MainContract;
import com.king.practices.mvp.model.MainModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/9/3.
 */
@Module
public class MainModule {
    private MainContract.View view;

    /**
     * MainModule,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MainContract.Model provideMainModel(MainModel model) {
        return model;
    }

}
