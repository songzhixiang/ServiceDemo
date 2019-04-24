package com.example.servicedemo;

import android.app.Application;
import android.os.StrictMode;

import com.evernote.android.job.JobManager;
import com.example.servicedemo.job.DemoJobCreator;

/**
 * @author andysong
 * @data 2019/4/24
 * @discription xxx
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JobManager.create(this).addJobCreator(new DemoJobCreator());
    }
}
