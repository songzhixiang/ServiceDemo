package com.example.servicedemo.weight.tree;

/**
 * @author andysong
 * @data 2019-05-28
 * @discription xxx
 */
public class Tree {
    //绘制树干（贝塞尔曲线）

    private enum Step{
        BRANCHES_GROWING,
        BLOOMS_GROWING,
        MOVING_SNAPSHOT,
        BLOOMS_FALLING
    }


    private Step  mStep = Step.BRANCHES_GROWING;
    //绘制花瓣（心形曲线）
    //整体平移
    //花瓣掉落
}
