package com.king.practices.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;

/**
 * des:
 * author:xqf
 * date:2017/9/3 18:35
 */
public interface TabOneFContract {

    interface View extends IView {

    }

    interface Model extends IModel {
        /**
         * 获取每天最新数据
         * @return
         */
        Observable<String> getLasteDatas();
    }
}
