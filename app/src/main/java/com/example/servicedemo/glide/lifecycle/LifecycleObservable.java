package com.example.servicedemo.glide.lifecycle;

import com.example.servicedemo.glide.cache.DoubleLruCache;

/**
 * @author andysong
 * @data 2019-05-29
 * @discription xxx
 */
public class LifecycleObservable {

    private static class LazyLoad{
        private static final LifecycleObservable instance = new LifecycleObservable();
    }

    public static LifecycleObservable getInstance(){
        return LazyLoad.instance;
    }

    private LifecycleObservable(){

    }

    public void onStart(int activityCode){

    }


    public void onStop(int activityCode){

    }

    public void onDestory(int activityCode){
        DoubleLruCache.getInstance().remove(activityCode);
    }


}
