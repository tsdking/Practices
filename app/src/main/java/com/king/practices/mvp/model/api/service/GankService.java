package com.king.practices.mvp.model.api.service;

import com.king.practices.mvp.model.entity.BaseGank;
import com.king.practices.mvp.model.entity.GankHistoryDate;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface GankService {
    @GET("/api/day/history")
    Observable<BaseGank<List<GankHistoryDate>>> getHistoryDate();
}
