package com.liukebing.molinews.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;
    private static final String TAG = "DepthPageTransformer";

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        //A页面到B页面
        //A页面position：0到-1
        //B页面position：1到0

        //Log.d(TAG,"View = "+view+", View Position "+position);

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

            //A页面（0到-1）
        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

            //B页面（1到0）
        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);    //B页面从透明到不透明，setAlpha()从小到大

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);    //X从左往右偏移

            // Scale the page down (between MIN_SCALE and 1)

            //scaleFactor（缩放）值从0.75到1
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}