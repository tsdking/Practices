package com.king.practices.app;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/9/8.
 */

public interface Constants {
    @IntDef({RequestType.ANDROID, RequestType.IOS, RequestType.WEB, RequestType.FULI
            , RequestType.VIDEO, RequestType.LASTED, RequestType.RANDMOD, RequestType.EXTEND})
    @Retention(RetentionPolicy.SOURCE)
    @interface RequestType {
        int ANDROID = 0;//ANDROID
        int IOS = 1;//IOS
        int WEB = 2;//WEB
        int FULI = 3;//FULI
        int VIDEO = 4;//VIDEO
        int EXTEND = 7;//拓展资源

        int LASTED = 5;//最新
        int RANDMOD = 6;//随机
    }

    @IntDef({LoadType.Refresh, LoadType.MORE})
    @Retention(RetentionPolicy.SOURCE)
    @interface LoadType {
        int Refresh = 0;//刷新
        int MORE = 1;//加载更多
    }
}
