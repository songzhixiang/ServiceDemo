package com.example.servicedemo;


import android.content.Context;


import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

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
