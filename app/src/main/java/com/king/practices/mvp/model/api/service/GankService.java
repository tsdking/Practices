package com.king.practices.mvp.model.api.service;

import com.king.practices.mvp.model.entity.BaseGank;
import com.king.practices.mvp.model.entity.Gank;
import com.king.practices.mvp.model.entity.GankTheDay;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * des:gank相关的接口
 * author:xqf
 * date:2017/9/7 19:00
 */
public interface GankService {


    /**
     * 获取发过干货的日期s
     *
     * @return
     */
    @GET("/api/day/history")
    Observable<BaseGank<List<String>>> getHistoryDate();

    /**
     * 获取每日数据
     *
     * @param date 2017/06/01
     * @return
     */
    @GET("/api/day/{date}")
    Observable<String> getEveryDayDatas(@Path("date") String date);

    /**
     * 获取分类数据
     *
     * @param category  "Android","休息视频","前端","iOS","福利"
     * @param pageSize  请求每页的数据 ;如 20 表示每页请求20条数据
     * @param pageIndex 第几页
     * @return
     */
    @GET("/api/data/{category}/{pageSize}/{pageIndex}")
    Observable<BaseGank<List<Gank>>> getCategoryDatas(
            @Path("category") String category,
            @Path("pageSize") int pageSize,
            @Path("pageIndex") int pageIndex
    );

    /**
     * 获取特定日期网站数据
     *
     * @param date 2017/09/05
     * @return
     */
    @GET("/api/history/content/day/{date}")
    Observable<BaseGank<List<GankTheDay>>> getTheDayDatas(@Path("date") String date);


}
