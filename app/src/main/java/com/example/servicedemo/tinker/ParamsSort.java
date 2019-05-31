package com.example.servicedemo.tinker;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;

/**
 * @author andysong
 * @data 2019-05-28
 * @discription bug类
 */
public class ParamsSort {
    public void math(Context mContext){
        int a = 10;
        int b = 5;
        ToastUtils.showShort(a/b);
    }
}

