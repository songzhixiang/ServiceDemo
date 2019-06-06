package com.example.servicedemo.function;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.example.servicedemo.MyApi;
import com.example.servicedemo.R;
import com.example.servicedemo.retrofit.Retrofit;
import com.example.servicedemo.retrofit.http.GET;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author andysong
 * @data 2019-06-05
 * @discription xxx
 */
public class SecondActivity extends AppCompatActivity {


    String IP = "144.34.161.97";
    String KEY = "aa205eeb45aa76c6afe3c52151b52160";
    String BASEURL = "http://apis.juhe.cn/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


    }


    public void getData(View view) {
        FunctionManager.getInstance().inovkeFunction("NoParamNoResult");
        User user = FunctionManager.getInstance().inovkeFunction("NoParamHasResult", User.class);
        LogUtils.e(user.toString());


        User user1 = FunctionManager.getInstance().inovkeFunction("HasParamHasResult", User.class, "Double Jack");
        LogUtils.e(user1.toString());


        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .build();

        MyApi myApi = mRetrofit.create(MyApi.class);


        Call call = myApi.get(IP, KEY);

        try {
            Response response = call.execute();
            if (response != null && response.body() !=null){
                LogUtils.e("请求成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        MyApi myApi = (MyApi) Proxy.newProxyInstance(MyApi.class.getClassLoader(), new Class[]{MyApi.class}, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                LogUtils.e("方法名"+method.getName());
//                GET get = method.getAnnotation(GET.class);
//                LogUtils.e(get.value());
//
//                Annotation[][] para = method.getParameterAnnotations();
//                for (Annotation[] an :
//                        para) {
//                    LogUtils.e(Arrays.toString(an));
//                }
//
//                LogUtils.e(Arrays.toString(args));
//
//                return null;
//            }
//        });
//
//        myApi.get(IP, KEY);
    }
}
