package com.example.servicedemo.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author andysong
 * @data 2019-05-24
 * @discription 创建一个阻塞队列
 */
public class BitmapDispatcher extends Thread {

    private static Handler mHandler = new Handler(Looper.myLooper());

    private LinkedBlockingDeque<BitmapRequest> mBitmapRequests;

    public BitmapDispatcher(LinkedBlockingDeque<BitmapRequest> bitmapRequests) {
        mBitmapRequests = bitmapRequests;
    }


    @Override
    public void run() {
        super.run();
        while (!isInterrupted())
        {
            try {
                BitmapRequest bitmapRequest = mBitmapRequests.take();
                //设置展位图片
                showLoadingImage(bitmapRequest);
                //加载图片
                Bitmap bitmap = findBitMap(bitmapRequest);
                //显示图片
                showImageView(bitmapRequest,bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void showImageView(final BitmapRequest bitmapRequest, final Bitmap bitmap) {
        if (null!=bitmap&& bitmapRequest.getImageView()!=null&&bitmapRequest.getUrlMd5().equals(bitmapRequest.getImageView().getTag())){
            final ImageView imageView = bitmapRequest.getImageView();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                    if (bitmapRequest.getRequestListener()!=null){
                        RequestListener requestListener = bitmapRequest.getRequestListener();
                        requestListener.onSuccess(bitmap);
                    }
                }
            });

        }
    }

    private Bitmap findBitMap(BitmapRequest bitmapRequest) {

        return downloadImageView(bitmapRequest.getUrl());

    }

    private Bitmap downloadImageView(String url) {

        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null;

        try {
            URL mUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return bitmap;
        }
    }

    private void showLoadingImage(BitmapRequest bitmapRequest) {
        if (null!=bitmapRequest.getImageView()&&bitmapRequest.getResID()>0){
            final int resID = bitmapRequest.getResID();
            final ImageView imageView = bitmapRequest.getImageView();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resID);
                }
            });
        }
    }


}
