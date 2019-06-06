package com.example.servicedemo;

import com.example.servicedemo.retrofit.http.Field;
import com.example.servicedemo.retrofit.http.GET;
import com.example.servicedemo.retrofit.http.POST;
import com.example.servicedemo.retrofit.http.Query;

import okhttp3.Call;

/**
 * @author andysong
 * @data 2019-06-06
 * @discription xxx
 */
public interface MyApi {



    @GET("/ip/ipNew")
    Call get(@Query("ip") String ip,@Query("key") String key);

    @POST("/ip/ipNew")
    Call post(@Field("ip") String ip,@Field("key") String key);



}
