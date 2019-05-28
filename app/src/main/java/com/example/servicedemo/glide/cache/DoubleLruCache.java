package com.example.servicedemo.glide.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.servicedemo.glide.BitmapRequest;

/**
 * @author andysong
 * @data 2019-05-27
 * @discription xxx
 */
public class DoubleLruCache implements BitmapCache {

    //内存
    private MemoryLruCache mLruCache;

    //磁盘
    private DiskBitmapCache mDiskBitmapCache;

    public DoubleLruCache(Context mContext) {
        mLruCache = MemoryLruCache.getInstance();
        mDiskBitmapCache = DiskBitmapCache.getInstance(mContext);
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        mLruCache.put(request, bitmap);
        mDiskBitmapCache.put(request, bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        Bitmap bitmap = mLruCache.get(bitmapRequest);
        if (bitmap == null){
            bitmap = mDiskBitmapCache.get(bitmapRequest);
            mLruCache.put(bitmapRequest,bitmap);
        }
        return bitmap;
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
        mLruCache.remove(bitmapRequest);
        mDiskBitmapCache.remove(bitmapRequest);
    }
}
