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

        //如果return false 代表 任务已经全部做完了，系统此时会解绑JobService，最终调用onDestroy方法，效果和jobFinished一样
        //如果return true 代表 任务已经启动成功，但是还没有全部做完，我们可以自行调用jobFinished




        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String timestamp = format.format(date);
        Log.e("szx","onStopJob"+timestamp);
        //返回true 表示任务计划在下次继续
        //返回false 表示终止以后不在继续
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("szx","onDestroy");
    }
}
