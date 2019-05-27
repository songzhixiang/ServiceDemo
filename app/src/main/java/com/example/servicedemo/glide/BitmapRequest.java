package com.example.servicedemo.glide;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.blankj.utilcode.util.EncryptUtils;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * @author andysong
 * @data 2019-05-24
 * @discription 图片请求对象封装类
 */
public class BitmapRequest {
    //请求路径
    private String url;

    //上下文
    private Context mContext;

    //需要加载图片的控件
    private WeakReference<ImageView> mImageView;

    //站位图片
    private int resID;


    //监听回调对象
    private RequestListener mRequestListener;


    //图片的标识
    private String urlMd5;


    public BitmapRequest(Context context) {
        mContext = context;
    }


    public BitmapRequest load(String url){
        this.url = url;
        this.urlMd5 = EncryptUtils.encryptMD5ToString(url);
        return this;
    }

    public BitmapRequest loading(int resID){
        this.resID = resID;
        return this;
    }


    public BitmapRequest listenter(RequestListener listener){
        this.mRequestListener = listener;
        return this;
    }


    public void into(ImageView imageView){
        imageView.setTag(this.urlMd5);
        this.mImageView = new WeakReference<>(imageView);
        RequestManager.getInstance().addBitmapRequest(this);
    }


    public String getUrl() {
        return url;
    }

    public Context getContext() {
        return mContext;
    }

    public ImageView getImageView() {
        return mImageView.get();
    }

    public int getResID() {
        return resID;
    }

    public RequestListener getRequestListener() {
        return mRequestListener;
    }

    public String getUrlMd5() {
        return urlMd5;
    }
}
