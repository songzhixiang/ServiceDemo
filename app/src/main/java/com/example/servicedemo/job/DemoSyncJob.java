package com.example.servicedemo.job;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

/**
 * @author andysong
 * @data 2019/4/24
 * @discription xxx
 */
public class DemoSyncJob extends Job {

    public static final String TAG = "job_demo_tag";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        //run your job here
        uploadTask();

        return Result.SUCCESS;
    }

    public void uploadTask(){
        Log.e(TAG,"上传任务成功");
    }

    public static void scheduleJob(){
        new JobRequest.Builder(DemoSyncJob.TAG)
                .setExecutionWindow(30_000L, 40_000L)
                .setRequiresCharging(true)
                .build()
                .schedule();
    }
}
