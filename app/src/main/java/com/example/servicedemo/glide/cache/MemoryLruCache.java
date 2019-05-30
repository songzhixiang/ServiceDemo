package com.example.servicedemo.glide.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.example.servicedemo.glide.BitmapRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author andysong
 * @data 2019-05-27
 * @discription xxx
 */
public class MemoryLruCache implements BitmapCache {


    private static volatile MemoryLruCache instance;

    private HashMap<String, Integer> activityMode;

    private static final byte[] lock = new byte[0];


    private LruCache<String, Bitmap> mLruCache;


    public static MemoryLruCache getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new MemoryLruCache();
                }
            }
        }

        return instance;
    }

    private MemoryLruCache() {
        int maxMemorySize = (int) (Runtime.getRuntime().maxMemory() / 16);
        if (maxMemorySize <= 0) {
            maxMemorySize = 10 * 1024 * 1024;
        }

        mLruCache = new LruCache<String, Bitmap>(maxMemorySize) {

            @Override
            protected int sizeOf(String key, Bitmap value) {
                //一张图片的大小
                return value.getRowBytes() * value.getHeight();
            }
        };
        activityMode = new HashMap<>();
    }


    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        if (bitmap != null) {
            mLruCache.put(request.getUrlMd5(), bitmap);
            activityMode.put(request.getUrlMd5(), request.getContext().hashCode());
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

    @Override
    public void remove(int activity) {
        List<String> temp = new ArrayList<>();
        for (String uriMd5 : activityMode.keySet()) {
            if (activityMode.get(uriMd5).intValue() == activity) {
                temp.add(uriMd5);
            }
        }

        for (String uriMd5 :
                temp) {
            activityMode.remove(uriMd5);
            Bitmap bitmap = mLruCache.get(uriMd5);
            if (bitmap!=null&&!bitmap.isRecycled()){
                bitmap.recycle();
            }
            mLruCache.remove(uriMd5);
            bitmap = null;
        }


        if (!temp.isEmpty()){
            System.gc();
        }

    }
}
