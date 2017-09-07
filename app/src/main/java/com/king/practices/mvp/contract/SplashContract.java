package com.king.practices.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

/**
 * des:
 * author:xqf
 * date:2017/9/3 18:35
 */
public interface SplashContract {

    interface View extends IView {

    }

    interface Model extends IModel {
        void getHistoryDate();
    }
}
