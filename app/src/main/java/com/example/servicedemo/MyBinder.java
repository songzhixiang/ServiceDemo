package com.example.servicedemo;

import android.os.Binder;

/**
 * @author andysong
 * @data 2019/4/11
 * @discription xxx
 */
public class MyBinder extends Binder {

    public String getStringInfo(){
        return "这是我MyBinder";
    }
}
