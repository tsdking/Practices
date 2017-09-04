
package com.king.practices.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.king.practices.mvp.contract.MainContract;
import com.king.practices.mvp.contract.TabOneFContract;
import com.king.practices.mvp.model.MainModel;
import com.king.practices.mvp.model.TabOneFModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/9/3.
 */
@Module
public class TabOneFModule {
    private TabOneFContract.View view;

    /**
     * TabOneFModule,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TabOneFModule(TabOneFContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TabOneFContract.View provideMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TabOneFContract.Model provideMainModel(TabOneFModel model) {
        return model;
    }

}
