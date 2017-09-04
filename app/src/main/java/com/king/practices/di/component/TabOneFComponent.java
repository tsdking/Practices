package com.king.practices.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.king.practices.di.module.MainModule;
import com.king.practices.di.module.TabOneFModule;
import com.king.practices.mvp.ui.activity.MainActivity;
import com.king.practices.mvp.ui.fragment.TabOneFFragment;

import dagger.Component;

/**
 * des:
 * author:xqf
 * date:2017/9/3 19:18
 */
@ActivityScope
@Component(modules = TabOneFModule.class, dependencies = AppComponent.class)
public interface TabOneFComponent {
    void inject(TabOneFFragment fragment);
}
