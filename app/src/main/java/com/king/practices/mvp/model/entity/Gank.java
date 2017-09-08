package com.king.practices.mvp.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.king.practices.mvp.model.api.service.Cateory;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * Created by admin on 2017/9/6.
 */
@Entity
public class Gank implements MultiItemEntity {

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
    @Transient
    private List<String> images;//文章配图

    @Generated(hash = 2063140998)
    public Gank(String _id, String createdAt, String desc, String publishedAt,
                String source, String type, String url, boolean used, String who,
                String image) {
        this._id = _id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
        this.image = image;
    }

    @Generated(hash = 116302247)
    public Gank() {
    }

    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return this.who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public int getItemType() {
        int result = -1;
        if (Cateory.ANDROID.getType().equals(type)) {
            result = 0;
        } else if (Cateory.IOS.getType().equals(type)) {
            result = 1;
        } else if (Cateory.WEB.getType().equals(type)) {
            result = 2;
        } else if (Cateory.FULI.getType().equals(type)) {
            result = 3;
        } else if (Cateory.VIDEO.getType().equals(type)) {
            result = 4;
        }else if (Cateory.EXTEND.getType().equals(type)) {
            result = 5;
        }
        return result;
    }
}

