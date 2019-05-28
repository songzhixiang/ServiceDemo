package com.example.servicedemo;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.evernote.android.job.JobManager;
import com.example.lib.FixDexUtils;
import com.example.servicedemo.job.DemoJobCreator;

/**
 * @author andysong
 * @data 2019/4/24
 * @discription xxx
 */
public class App extends MultiDexApplication {

    private static App INSTANCE;

    public static App getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        JobManager.create(this).addJobCreator(new DemoJobCreator());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        //  加载热修复的dex文件
        FixDexUtils.loadFixedDex(this);
    }
}
