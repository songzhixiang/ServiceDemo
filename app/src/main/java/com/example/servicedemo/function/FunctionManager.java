package com.example.servicedemo.function;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author andysong
 * @data 2019-06-05
 * @discription xxx
 */
public class FunctionManager {

    private Map<String,FunctionNoParamNoResult> mNoParamNoResultMap;
    private Map<String,FunctionNoParamHasResult> mNoParamHasResultMap;
    private Map<String,FunctionHasParamNoResult> mHasParamNoResultMap;
    private Map<String,FunctionHasParamHasResult> mHasParamHasResultMap;

    private static class LazyLoad {
        private static final FunctionManager instance = new FunctionManager();
    }

    public static FunctionManager getInstance(){
        return LazyLoad.instance;
    }

    private FunctionManager(){
        mNoParamNoResultMap = new HashMap<>();
        mNoParamHasResultMap = new HashMap<>();
        mHasParamNoResultMap = new HashMap<>();
        mHasParamHasResultMap = new HashMap<>();
    }

    //将封装的方法添加到容器(map)中
    public void addFunction(FunctionNoParamNoResult function){
        mNoParamNoResultMap.put(function.functionName,function);
    }





    public void inovkeFunction(String functionName){
        if (!TextUtils.isEmpty(functionName)){
            if (mNoParamNoResultMap !=null){
                FunctionNoParamNoResult f = mNoParamNoResultMap.get(functionName);
                if (null!=f){
                    f.function();
                }else {
                    LogUtils.e("没有找到"+functionName+"方法");
                }
            }
        }
    }

    public void addFunction(FunctionNoParamHasResult function){
        mNoParamHasResultMap.put(function.functionName,function);
    }


    public <T> T inovkeFunction(String functionName,Class<T> t){
        if (!TextUtils.isEmpty(functionName)){
            if (mNoParamHasResultMap !=null){
                FunctionNoParamHasResult f = mNoParamHasResultMap.get(functionName);
                if (null!=f){
                    if (t!=null){
                        return t.cast(f.function());
                    }

                }else {
                    LogUtils.e("没有找到"+functionName+"方法");
                }
            }

        }
        return null;
    }




    public void addFunction(FunctionHasParamNoResult function){
        mHasParamNoResultMap.put(function.functionName,function);
    }


    public <P> void inovkeFunction(String functionName,P t){
        if (!TextUtils.isEmpty(functionName)){
            if (mHasParamNoResultMap !=null){
                FunctionHasParamNoResult f = mHasParamNoResultMap.get(functionName);
                if (null!=f){
                    f.function(t);

                }else {
                    LogUtils.e("没有找到"+functionName+"方法");
                }
            }

        }

    }



    public void addFunction(FunctionHasParamHasResult function){
        mHasParamHasResultMap.put(function.functionName,function);
    }


    public <T,P> T inovkeFunction(String functionName,Class<T> t ,P p){
        if (!TextUtils.isEmpty(functionName)){
            if (mHasParamHasResultMap !=null){
                FunctionHasParamHasResult f = mHasParamHasResultMap.get(functionName);
                if (null!=f){
                    if (t!=null){
                        return t.cast(f.function(p));
                    }

                }else {
                    LogUtils.e("没有找到"+functionName+"方法");
                }
            }

        }
        return null;
    }
}
