package com.king.practices.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * des:发布过干货的日期
 * author:xqf
 * date:2017/9/7 19:00
 */
@Entity
public class GankHistoryDate {
    @Id(autoincrement = true)
    private Long id;
    private String mDate;//"2017/09/06"
    @Generated(hash = 299546539)
    public GankHistoryDate(Long id, String mDate) {
        this.id = id;
        this.mDate = mDate;
    }
    @Generated(hash = 883084700)
    public GankHistoryDate() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMDate() {
        return this.mDate;
    }
    public void setMDate(String mDate) {
        this.mDate = mDate;
    }
}
