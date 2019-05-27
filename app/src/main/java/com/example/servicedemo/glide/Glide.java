package com.example.servicedemo.glide;

import android.content.Context;

/**
 * @author andysong
 * @data 2019-05-27
 * @discription xxx
 */
public class Glide {

    public static BitmapRequest with(Context mContext){
        return new BitmapRequest(mContext);
    }
}
