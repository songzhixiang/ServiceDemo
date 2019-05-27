package com.example.servicedemo.glide;

import android.graphics.Bitmap;

/**
 * @author andysong
 * @data 2019-05-24
 * @discription 加载回调
 */
public interface RequestListener {


    boolean onSuccess(Bitmap bitmap);

    boolean onFail();
}
