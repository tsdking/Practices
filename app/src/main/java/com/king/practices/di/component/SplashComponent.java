package com.king.practices.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.king.practices.di.module.SplashModule;
import com.king.practices.mvp.ui.activity.SplashActivity;

import dagger.Component;

/**
 * des:
 * author:xqf
 * date:2017/9/3 19:18
 */
@ActivityScope
@Component(modules = SplashModule.class, dependencies = AppComponent.class)
public interface SplashComponent {
    void inject(SplashActivity activity);
}
