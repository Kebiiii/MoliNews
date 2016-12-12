package com.liukebing.molinews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * 闪屏页
 */
public class SplashActivity extends AppCompatActivity {

    private static final long DELAY_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //使用Handler的postDelayed方法实现延时跳转
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //根据存储在SharedPreferences的数据判断是否为第一次进入
                boolean isFirstRun = getSharedPreferences("config", Context.MODE_PRIVATE).getBoolean("first",true);
                if(isFirstRun){
                    startActivity(new Intent(SplashActivity.this, ViewPagerActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }

                finish();
            }
        },DELAY_TIME);
    }
}
