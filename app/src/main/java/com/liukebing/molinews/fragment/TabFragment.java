package com.liukebing.molinews.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liukebing.molinews.NewsDetailActivity;
import com.liukebing.molinews.R;
import com.liukebing.molinews.adapter.FirstPageAdapter;
import com.liukebing.molinews.entity.BannerBean;
import com.liukebing.molinews.entity.Data;
import com.liukebing.molinews.utils.JSONUtils;
import com.liukebing.molinews.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 当Fragment没有布局文件时，TabLayout滑动十分卡顿，那是因为
 * tabLayout不知道要创建几个Fragment，所以卡顿
 * 有了布局创建的Fragment确定，就不会卡顿了
 */
public class TabFragment extends Fragment implements JSONUtils.CallbackListener,SwipeRefreshLayout.OnRefreshListener{

    private static final long DELAY = 500;
    private ProgressDialogUtils utils;

    private BannerBean bannerBean;

    private List<Data>[] list;

    private RecyclerView recyclerView;

    private FirstPageAdapter adapter;
    
    private JSONUtils jsonUtils;

    //轮播图+item数据总条数
    private int NUM = 12;

    //item的集合（不包括轮播图）
    private List<Data> itemList;

    //下拉刷新控件
    private SwipeRefreshLayout refresh_layout;

    //最后一个可见的item的下标
    private int lastVisibleItemPosition;

    //当前选项卡的位置
    private int position = 0;

    public TabFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //第2个参数表示存放该Fragment的容器为ViewPager
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_tab_layout, (ViewGroup) getActivity().findViewById(R.id.vp_main),false);

        utils = new ProgressDialogUtils();

        utils.showProgressDialog(getActivity(),null);

        getData();

        //new JSONUtils().getResultList("http://news.ifeng.com");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        //设置刷新控件交替变换的颜色
        refresh_layout.setColorSchemeResources(android.R.color.holo_red_light,android.R.color.holo_blue_light,android.R.color.holo_green_light);
        //设置监听事件
        refresh_layout.setOnRefreshListener(this);

        //getContext() or getActivity()？
        //因为现在TabFragment继承FragmentActivity，依赖在Activity里，所以getContext也是返回Activity
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        bannerBean = new BannerBean();
        itemList = new ArrayList<>();

        adapter = new FirstPageAdapter(getActivity(),itemList,bannerBean);

        recyclerView.setAdapter(adapter);

        addOnScrollListener();

        //设置点击事件
        adapter.setOnItemClickListener(new FirstPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("url",itemList.get(position-1).getUrl());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),"下次注意咯，长按删除哦~",Toast.LENGTH_SHORT).show();
                adapter.removeItem(position);
            }
        });

        return view;
    }

    private void addOnScrollListener() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItemPosition+1==adapter.getItemCount()){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(100);
                        }
                    },DELAY);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取最后一个可见item位置
                LinearLayoutManager manager = (LinearLayoutManager)recyclerView.getLayoutManager();
                lastVisibleItemPosition = manager.findLastVisibleItemPosition();
            }
        });
    }


    /**
     * 回调的更新数据的方法
     * @param list 数据集合
     */
    @Override
    public void update(List<Data>[] list) {

        utils.dismissProgressDialog();

        this.list = list;

        initData();
    }

    private void initData() {

        String[] thumbnails = new String[4];
        String[] titles = new String[4];
        String[] urls = new String[4];

        if(itemList!=null&&itemList.size()>0){
            //清空原来的缓存
            itemList.clear();
        }

        //获取4条轮播图数据
        for(int i=0;i<4;i++) {
            Log.e("TabFragment", "position"+position);
            if (position == 7) {
                position=6;
            }
            thumbnails[i] = list[position].get(i).getThumbnail();
            titles[i] = list[position].get(i).getTitle();
            urls[i] = list[position].get(i).getUrl();
        }

        bannerBean.setThumbnail(thumbnails);
        bannerBean.setTitle(titles);
        bannerBean.setUrl(urls);

        //获取8条item数据
        for(int i=4;i<NUM;i++){

            itemList.add(list[position].get(i));
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取网络数据
     */
    public void getData() {

        jsonUtils = new JSONUtils(this);
        jsonUtils.getResultList("http://news.ifeng.com");
    }

    @Override
    public void onRefresh() {

        handler.sendEmptyMessage(200);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    NUM += 4;

                    if(NUM<=100) {
                        initData();
                    }
                    break;
                case 200:
                    //新增4条数据
                    NUM += 4;

                    if(NUM<=100) {
                        initData();
                    }
                    //停止刷新
                    refresh_layout.setRefreshing(false);
                    break;
            }
        }
    };

    public void setPosition(int position) {
        this.position = position;

        if(list!=null) {
            initData();
        }
    }
}
