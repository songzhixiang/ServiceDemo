package com.example.servicedemo.glide.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CloseUtils;
import com.example.servicedemo.glide.BitmapRequest;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author andysong
 * @data 2019-05-27
 * @discription xxx
 */
public class DiskBitmapCache implements BitmapCache {

    private DiskLruCache mDiskLruCache;

    private static volatile DiskBitmapCache instance;

    private static final byte[] lock = new byte[0];

    private String imageCachePath = "Image";

    private int MB = 1024*1024;

    private int maxDiskSize = 50*MB;


    private DiskBitmapCache(Context mContext){
        File cacheFile = getImageCacheFile(mContext,imageCachePath);
        if (!cacheFile.exists()){
            cacheFile.mkdirs();
        }
        try {
            mDiskLruCache = DiskLruCache.open(cacheFile, AppUtils.getAppVersionCode(),1,maxDiskSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getImageCacheFile(Context mContext, String imageCachePath) {
        String path;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            path = mContext.getExternalCacheDir().getPath();
        }else {
            path = mContext.getCacheDir().getPath();
        }

        return new File(path+File.separator+imageCachePath);
    }

    public static DiskBitmapCache getInstance(Context mContext){
        if (instance == null){
            synchronized (lock){
                if (instance ==  null)
                {
                    instance = new DiskBitmapCache(mContext);
                }
            }
        }
        return instance;
    }


    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        DiskLruCache.Editor editor;
        OutputStream outputStream = null;
        try {
            editor=  mDiskLruCache.edit(request.getUrlMd5());
            outputStream = editor.newOutputStream(0);
            if (presetBitmap2Disk(outputStream,bitmap)){
                editor.commit();
            }else {
                editor.abort();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseUtils.closeIOQuietly(outputStream);
        }
    }

    private boolean presetBitmap2Disk(OutputStream outputStream, Bitmap bitmap) {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bufferedOutputStream);
            bufferedOutputStream.flush();
            return true;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            CloseUtils.closeIOQuietly(bufferedOutputStream);
        }
        return false;
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        InputStream stream = null;
        DiskLruCache.Snapshot snapshot  = null;
        try {
            snapshot = mDiskLruCache.get(bitmapRequest.getUrlMd5());
            if (snapshot!=null){
                stream = snapshot.getInputStream(0);

                return BitmapFactory.decodeStream(stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseUtils.closeIOQuietly(stream);
        }
        return null;
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
        try {
            mDiskLruCache.remove(bitmapRequest.getUrlMd5());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
