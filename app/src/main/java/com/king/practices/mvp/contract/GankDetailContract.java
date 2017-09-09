package com.king.practices.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * des:
 * author:xqf
 * date:2017/9/3 18:35
 */
public interface GankDetailContract {

    interface View extends IView {
        //申请权限
        RxPermissions getRxPermissions();

        /**
         * 切换更新视图
         *
         * @param viewType 0 福利图;1 webview加载
         * @param url
         */
        void updataView(int viewType,String url);
    }

    interface Model extends IModel {

    }
}
