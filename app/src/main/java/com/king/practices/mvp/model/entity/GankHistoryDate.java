package com.king.practices.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2017/9/7.
 */
@Entity
public class GankHistoryDate {
    @Id(autoincrement = true)
    private Long id;
    private String date;//"2017-09-06"

}
