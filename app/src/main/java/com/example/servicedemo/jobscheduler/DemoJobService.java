package com.example.servicedemo.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author andysong
 * @data 2019/4/17
 * @discription xxx
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DemoJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String timestamp = format.format(date);
        Log.e("szx","onStartJob"+timestamp);
        //告诉系统已经执行完毕
//        jobFinished(params,false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String timestamp = format.format(date);
        Log.e("szx","onStopJob"+timestamp);
        return false;
    }
}
