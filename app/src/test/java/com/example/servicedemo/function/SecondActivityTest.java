package com.example.servicedemo.function;

import com.blankj.utilcode.util.LogUtils;
import com.example.servicedemo.MyApi;
import com.example.servicedemo.retrofit.Retrofit;

import org.junit.Test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * @author andysong
 * @data 2019-06-06
 * @discription xxx
 */
public class SecondActivityTest {

    String IP = "144.34.161.97";
    String KEY = "aa205eeb45aa76c6afe3c52151b52160";
    String BASEURL = "http://apis.juhe.cn/";

    @Test
    public void test(){
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .build();

        MyApi myApi = mRetrofit.create(MyApi.class);


        Call call = myApi.get(IP, KEY);
        try {
            Response response = call.execute();
            if (response != null && response.body() !=null){
                System.out.println(response.body().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}