package com.example.servicedemo.weight.tree;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * @author andysong
 * @data 2019-05-28
 * @discription 快照
 */
public class SnapShot {
    Canvas mCanvas;
    Bitmap mBitmap;

    public SnapShot(Bitmap bitmap) {

        mBitmap = bitmap;
        mCanvas = new Canvas(bitmap);
    }
}
