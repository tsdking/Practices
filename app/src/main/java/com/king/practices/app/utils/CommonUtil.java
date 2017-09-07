package com.king.practices.app.utils;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/9/7.
 */

public class CommonUtil {
    /**
     * "2017-05-01"  to "2017/05/01"
     * @param date
     * @return
     */
    public static String converDateFormat(String date) {
        if (!TextUtils.isEmpty(date)){
            String s = date.replaceAll("-", "/");
            return s;
        }
        return "";
    }
}
