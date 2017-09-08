package com.king.practices.mvp.model.api.service;

/**
 * gank数据分类
 */
public enum Cateory {
    ANDROID("Android"), VIDEO("休息视频"), WEB("前端"), IOS("iOS"), FULI("福利"), EXTEND("扩展资源");
    private String type;

    Cateory(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}