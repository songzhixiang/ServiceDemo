package com.example.servicedemo.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * @author andysong
 * @data 2019/4/24
 * @discription xxx
 */
public class DemoJobCreator implements JobCreator {
    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag) {
            case DemoSyncJob.TAG:
                return new DemoSyncJob();
            default:
                return null;
        }
    }
}
