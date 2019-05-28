package com.example.servicedemo.glide.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.example.servicedemo.glide.BitmapRequest;

/**
 * @author andysong
 * @data 2019-05-27
 * @discription xxx
 */
public class MemoryLruCache implements BitmapCache  {


    private static volatile MemoryLruCache instance;

    private static final byte[] lock = new byte[0];


    private LruCache<String,Bitmap> mLruCache;


    public static MemoryLruCache getInstance(){
        if (instance == null){
            synchronized (lock){
                if (instance == null){
                    instance = new MemoryLruCache();
                }
            }
        }
        return instance;
    }

    private MemoryLruCache() {
        int maxMemorySize = (int) (Runtime.getRuntime().maxMemory()/16);
        if (maxMemorySize <= 0){
            maxMemorySize = 10*1024*1024;
        }

        mLruCache = new LruCache<String, Bitmap>(maxMemorySize){

            @Override
            protected int sizeOf(String key, Bitmap value) {
                //一张图片的大小
                return value.getRowBytes()*value.getHeight();
            }
        };
    }









    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        if (bitmap!=null){
            mLruCache.put(request.getUrlMd5(),bitmap);
        }
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        return mLruCache.get(bitmapRequest.getUrlMd5());
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
         mLruCache.remove(bitmapRequest.getUrlMd5());
    }
}
