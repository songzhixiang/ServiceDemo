package com.example.servicedemo.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author andysong
 * @data 2019-05-28
 * @discription xxx
 */
public class TreeView extends View {
    public TreeView(Context context) {
        this(context,null);
    }

    public TreeView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public TreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xff000000);

    }
}
