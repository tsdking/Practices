package com.king.practices.mvp.model.entity;

import java.util.List;

/**
 * Created by admin on 2017/9/6.
 */

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

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }


}

