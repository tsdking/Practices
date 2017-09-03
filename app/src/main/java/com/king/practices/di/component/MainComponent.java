package com.king.practices.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.king.practices.di.module.MainModule;
import com.king.practices.mvp.ui.activity.MainActivity;

import dagger.Component;

/**
 * des:
 * author:xqf
 * date:2017/9/3 19:18
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
