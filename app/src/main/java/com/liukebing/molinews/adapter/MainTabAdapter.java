package com.liukebing.molinews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.liukebing.molinews.fragment.TabFragment;

import java.util.List;


/**
 * 顶部标题选项的适配器
 * Created by HLQ on 2016/08/31.
 */
public class MainTabAdapter extends FragmentPagerAdapter{

    private List<TabFragment> lists;
    private String[] titles;

    //注意这里FragmentPagerAdapter类的构造函数只有一个FragmentManager参数，要先调用super的manager再自定义
    //构造方法才不会报错
    public MainTabAdapter(FragmentManager manager, List<TabFragment> lists,String[] titles){

        super(manager);
        this.lists = lists;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return lists.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
