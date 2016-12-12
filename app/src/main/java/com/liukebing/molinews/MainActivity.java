package com.liukebing.molinews;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.liukebing.molinews.adapter.MainTabAdapter;
import com.liukebing.molinews.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;



/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //顶部标题选项布局
    private TabLayout tab_layout;

    private ViewPager vp_main;

    //存放标题的数组
    String[] titles;

    //存放Fragment的集合
    List<TabFragment> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //记得要初始化Fresco，不然会
        //Error inflating class com.facebook.drawee.view.SimpleDraweeView
        Fresco.initialize(this);

        initView();
        initData();
        initListener();
    }

    private void initListener() {

        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                //不加这句，点击tab会报空指针
                vp_main.setCurrentItem(position);

                lists.get(position).setPosition(position);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void initData() {

        titles = getResources().getStringArray(R.array.title);
        lists = new ArrayList<>();

        System.out.println("标题的长度 "+titles.length);

        TabFragment fragment;
        for (int i=0;i<titles.length;i++){
            fragment = new TabFragment();
            lists.add(fragment);
        }

        vp_main.setAdapter(new MainTabAdapter(getSupportFragmentManager(),lists,titles));

        //一句代码就把ViewPager与TabLayout关联了
        tab_layout.setupWithViewPager(vp_main);
    }

    private void initView() {

        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        vp_main = (ViewPager) findViewById(R.id.vp_main);
    }
}
