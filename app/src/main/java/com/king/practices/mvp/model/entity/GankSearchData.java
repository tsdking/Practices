package com.king.practices.mvp.model.entity;

/**
 * des:搜索数据
 * author:xqf
 * date:2017/9/7 19:34
 */
public class GankSearchData {

    /**
     * desc : Retrofit进阶
     * ganhuo_id : 57ac6af3421aa94a077b354f
     * publishedAt : 2016-08-12T11:39:10.578000
     * readability : <div>
     * type : Android
     * url : http://www.jianshu.com/p/a0fd69380735
     * who : null
     */

    private String desc;
    private String ganhuo_id;
    private String publishedAt;
    private String readability;
    private String type;
    private String url;
    private Object who;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGanhuo_id() {
        return ganhuo_id;
    }

    public void setGanhuo_id(String ganhuo_id) {
        this.ganhuo_id = ganhuo_id;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getReadability() {
        return readability;
    }

    public void setReadability(String readability) {
        this.readability = readability;
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

    public Object getWho() {
        return who;
    }

    public void setWho(Object who) {
        this.who = who;
    }
}
