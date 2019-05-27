package com.example.servicedemo.glide;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author andysong
 * @data 2019-05-27
 * @discription xxx
 */
public class RequestManager {

    private RequestManager(){
        start();
    }

    private static class LazyHolder{
        private static final RequestManager INSTANCE = new RequestManager();
    }

    public static final RequestManager getInstance(){
        return LazyHolder.INSTANCE;
    }


    public void start(){
        stopAllDispatcher();
        startAllDispatcher();
    }


    private LinkedBlockingDeque<BitmapRequest> mBitmapRequests = new LinkedBlockingDeque<>();

    //创建线程数组
    private BitmapDispatcher[] mBitmapDispatchers;


    //将图片请求对象添加到队列里面去
    public void addBitmapRequest(BitmapRequest bitmapRequest){
        if (null ==bitmapRequest){
            return;
        }
        //判断当前请求是否在队列中
        if (!mBitmapRequests.contains(bitmapRequest)){
            mBitmapRequests.add(bitmapRequest);
        }

    }

    //创建并开始所有线程
    public void startAllDispatcher(){
        //获取手机支持单个应用最大的线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        mBitmapDispatchers = new BitmapDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(mBitmapRequests);
            bitmapDispatcher.start();


            //要将每隔dispatcher放到数组中，方便统一管理
            mBitmapDispatchers[i] = bitmapDispatcher;
        }
    }

    //停止所有线程
    public void stopAllDispatcher(){
        if (mBitmapDispatchers!=null&&mBitmapDispatchers.length>0){
            for (BitmapDispatcher bitmapDispatcher:mBitmapDispatchers){
                if (!bitmapDispatcher.isInterrupted()){
                    bitmapDispatcher.interrupt();
                }
            }
        }
    }
}
