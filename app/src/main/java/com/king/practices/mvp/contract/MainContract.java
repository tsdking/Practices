package com.king.practices.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * des:
 * author:xqf
 * date:2017/9/3 18:35
 */
public interface MainContract {

    interface View extends IView {
        //申请权限
        RxPermissions getRxPermissions();
    }

    interface Model extends IModel {

    }
}
