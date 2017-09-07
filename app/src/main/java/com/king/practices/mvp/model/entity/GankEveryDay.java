package com.king.practices.mvp.model.entity;

import java.util.List;

/**
 * des:每日数据
 * author:xqf
 * date:2017/9/7 11:18
 */
public class GankEveryDay {
    private List<Gank> Android;
    private List<Gank> App;
    private List<Gank> 休息视频;
    private List<Gank> 前端;
    private List<Gank> 福利;

    public List<Gank> getAndroid() {
        return Android;
    }

    public void setAndroid(List<Gank> android) {
        Android = android;
    }

    public List<Gank> getApp() {
        return App;
    }

    public void setApp(List<Gank> app) {
        App = app;
    }

    public List<Gank> getVideo() {
        return 休息视频;
    }

    public void setVideo(List<Gank> videos) {
        this.休息视频 = videos;
    }

    public List<Gank> getWeb() {
        return 前端;
    }

    public void setWeb(List<Gank> web) {
        this.前端 = web;
    }

    public List<Gank> getFuli() {
        return 福利;
    }

    public void setFuli(List<Gank> fuli) {
        this.福利 = fuli;
    }
}
