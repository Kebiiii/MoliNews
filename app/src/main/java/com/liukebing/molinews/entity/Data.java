package com.liukebing.molinews.entity;

/**
 * 新闻图片和标题实体类
 * Created by HLQ on 2016/09/02.
 */
public class Data {

    //缩略图链接
    private String thumbnail;
    //标题
    private String title;
    //新闻详情链接
    private String url;

    public Data() {
    }

    public Data(String thumbnail, String title, String url) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Data{" +
                "thumbnail='" + thumbnail + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
