package com.example.servicedemo.weight.tree;

import android.graphics.Canvas;

import java.util.LinkedList;

/**
 * @author andysong
 * @data 2019-05-28
 * @discription 树干
 */
public class Branch {

    public static int branchColor = 0xFF775533;
    //控制点，二姐贝塞尔曲线，需要三个点
    private Point[] cp = new Point[3];

    private int currentLength;

    private float maxLength;
    private float radius;
    private float part;

    private float growX;
    private float growY;

    LinkedList<Branch> childList;


    Branch(int[] data){
        //{0,-1,217,490,252,60,182,10,30,100},
        cp[0] = new Point(data[2],data[3]);
        cp[1] = new Point(data[4],data[5]);
        cp[2] = new Point(data[6],data[7]);
        radius = data[8];

        maxLength = data[9];

        part = 1.0f/maxLength;



    }

    public boolean grow(Canvas canvas,float scaleFactor){
        if (currentLength<maxLength){
            bazier(part*currentLength);
            return true;
        }else {
            return false;
        }
    }

    private void bazier(float t) {
        float c0 = (1-t)*(1-t);
        float c1 = 2*t*(1-t);
        float c2 = t*t;

        growX = c0*cp[0].x+c1*cp[1].x+c2*cp[2].y;
        growY = c0*cp[0].x+c1*cp[1].x+c2*cp[2].y;

    }
}
