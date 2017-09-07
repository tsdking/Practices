package com.king.practices.app.utils;

import android.content.Context;

import com.king.practices.greendao.DaoMaster;
import com.king.practices.greendao.DaoSession;
import com.king.practices.greendao.GankDao;
import com.king.practices.greendao.GankHistoryDateDao;

/**
 * Created by Administrator on 2017/9/7.
 */

public class DBManager {
    public static final String DB_NAME = "greendao.db";
    public static final int SCHEMA_VERSION = 1;
    private static DaoMaster daoMaster;

    private DBManager() {
    }
    public static void init(Context context){
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        daoMaster = new DaoMaster(openHelper.getWritableDatabase());
    }

    public static DaoSession newSession() {
        if (daoMaster == null) {
            throw new RuntimeException("sDaoMaster is null.");
        }
        return daoMaster.newSession();
    }

    public static GankDao getGankDao() {
        return newSession().getGankDao();
    }
    public static GankHistoryDateDao getGankHistoryDao() {
        return newSession().getGankHistoryDateDao();
    }

    public static void destroy() {
        try {
            if (daoMaster != null) {
                daoMaster.getDatabase().close();
                daoMaster = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
