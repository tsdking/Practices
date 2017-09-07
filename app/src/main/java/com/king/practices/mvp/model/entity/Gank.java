package com.king.practices.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by admin on 2017/9/6.
 */
@Entity
public class Gank {

    /**
     * _id : 59acce3b421aa901c1c0a8db
     * createdAt : 2017-09-04T11:53:31.833Z
     * desc : Gradle 插件，做自动测试过程中的截屏。
     * images : ["http://img.gank.io/95075433-5796-48e0-8310-6bd9132a4988"]
     * publishedAt : 2017-09-05T11:29:05.240Z
     * source : chrome
     * type : Android
     * url : https://github.com/Karumi/Shot
     * used : true
     * who : 代码家
     */

    @Unique
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;//作者
    private String image;//文章配图




}

