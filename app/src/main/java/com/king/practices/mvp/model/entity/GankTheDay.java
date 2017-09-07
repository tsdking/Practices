package com.king.practices.mvp.model.entity;

/**
 * des:特定日期的网站数据
 * author:xqf
 * date:2017/9/7 19:32
 */
public class GankTheDay {
    /**
     * _id : 59a8e852421aa901c85e5ff7
     * content : <p><img
     * created_at : 2017-09-01T12:55:46.3Z
     * publishedAt : 2017-09-01T12:54:00.0Z
     * rand_id : 1574f709-d045-4af4-8810-a47bb6f2e07e
     * title : 今日力推：微博 360°全景效果 / AT - 基于 VUE 的前端 UI 组件库
     * updated_at : 2017-09-01T12:55:46.3Z
     */
    private String _id;
    private String content;
    private String created_at;
    private String publishedAt;
    private String rand_id;
    private String title;
    private String updated_at;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getRand_id() {
        return rand_id;
    }

    public void setRand_id(String rand_id) {
        this.rand_id = rand_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
