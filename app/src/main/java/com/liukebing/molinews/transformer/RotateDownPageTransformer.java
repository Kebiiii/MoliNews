package com.liukebing.molinews.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 自定义ViewPager切换特效
 * Created by HLQ on 2016/08/28.
 */
public class RotateDownPageTransformer implements ViewPager.PageTransformer{

    //旋转角度常量
    private static final float MAX_ROTATE = 20f;

    //以顺时针为正方向
    //A页旋转角度：0到-20；
    //B页旋转角度：20到0
    @Override
    public void transformPage(View view, float position) {

        //即时旋转角度
        float mRotate;

        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)

            view.setRotation(0);

            //A页面（0到-1）
        } else if (position <= 0) { // [-1,0]

            //B页面即时旋转角度（0到-20）
            mRotate = position * MAX_ROTATE;

            //设置旋转中心轴为中点
            view.setPivotX(pageWidth/2);
            view.setPivotY(view.getMeasuredHeight());

            //设置旋转角度
            view.setRotation(mRotate);

            //B页面（1到0）
        } else if (position <= 1) { // (0,1]

            //B页面即时旋转角度（20到0）
            mRotate = position * MAX_ROTATE;

            //设置旋转中心轴为中点
            view.setPivotX(pageWidth/2);
            view.setPivotY(view.getMeasuredHeight());

            //设置旋转角度
            view.setRotation(mRotate);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setRotation(0);
        }
    }
}
