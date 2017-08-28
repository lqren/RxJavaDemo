package com.project.rxjavademo;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Api {
    @GET("activity/citys")
    Observable<CityBean> getCityList();
}
