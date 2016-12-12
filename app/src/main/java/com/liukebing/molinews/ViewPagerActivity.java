package com.liukebing.molinews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.liukebing.molinews.transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerActivity extends AppCompatActivity {

    //private static final String TAG = "ViewPagerActivity";
    ViewPager viewPager;

    //图片资源ID
    //注意：不能把图片放在drawable文件夹下，会OOM
    int[] imgResIds = {R.mipmap.pic1,R.mipmap.pic2,R.mipmap.pic3};

    //存放ImageView的集合
    List<ImageView> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_view_pager);

        setSharedPreferences();

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        //初始化数据
        initData();

        //设置滑动特效
        // （缩放过渡特效）
        //viewPager.setPageTransformer(true,new ZoomOutPageTransformer());

        //（深浅过渡特效）
        viewPager.setPageTransformer(true,new DepthPageTransformer());

        //（自定义旋转过渡特效）
        //viewPager.setPageTransformer(true,new RotateDownPageTransformer());

        //设置适配器
        viewPager.setAdapter(new PagerAdapter() {

            //返回count+1，用于跳转到MainActivity
            @Override
            public int getCount() {
                return imgResIds.length+1;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ImageView imageView;
                if(position<imgResIds.length) {
                    //当前position的ImageView
                    imageView = list.get(position);
                    container.addView(imageView);
                    return imageView;
                }

                //为容器添加子View（即为ImageView）
                //container.addView(imageView);

                //Log.d(TAG,"List Size is "+list.size());

                if(position==imgResIds.length){
                    startActivity(new Intent(ViewPagerActivity.this,MainActivity.class));
                    finish();
                }

                //返回当前position的ImageView
                return null;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

                //移除指定position的ImageView
                container.removeView(list.get(position));
            }

        });

    }

    /**
     * 保存数据在SharedPreferences
     */
    private void setSharedPreferences() {

        SharedPreferences preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("first",false);
        editor.apply();
    }

    /**
     * 初始化数据
     * 保证instantiateItem()方法中，List的Size一直为3
     */
    private void initData() {

        ImageView imageView;

        for (int imgResId : imgResIds) {

            //实例化ImageView对象
            imageView = new ImageView(ViewPagerActivity.this);

            //设置图片资源
            imageView.setImageResource(imgResId);

            //裁剪图片，确保全屏却不变形
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            //把ImageView添加到集合中
            list.add(imageView);
        }
    }
}
