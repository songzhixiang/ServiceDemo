package com.example.servicedemo.glide.cache;

import android.graphics.Bitmap;

import com.example.servicedemo.glide.BitmapRequest;

/**
 * @author andysong
 * @data 2019-05-27
 * @discription xxx
 */
public interface BitmapCache {

    //存入主存
    void put(BitmapRequest request, Bitmap bitmap);

    //读取缓存
    Bitmap get(BitmapRequest bitmapRequest);

    //清除缓存
    void remove(BitmapRequest bitmapRequest);
}
