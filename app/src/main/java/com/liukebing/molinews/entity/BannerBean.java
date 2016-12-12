package com.liukebing.molinews.entity;

/**
 * 轮播图的实体类
 * Created by HLQ on 2016/09/14.
 */
public class BannerBean {

    //缩略图链接
    private String[] thumbnails;
    //标题
    private String[] titles;
    //新闻详情链接
    private String[] urls;

    public BannerBean(){}

    public String[] getThumbnail() {
        return thumbnails;
    }

    public void setThumbnail(String[] thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String[] getTitle() {
        return titles;
    }

    public void setTitle(String[] titles) {
        this.titles = titles;
    }

    public String[] getUrl() {
        return urls;
    }

    public void setUrl(String[] url) {
        this.urls = urls;
    }
}
