package com.liukebing.molinews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class NewsDetailActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        webView = (WebView) findViewById(R.id.webView);

        if(webView!=null) {
            //自适应屏幕
            WebSettings settings = webView.getSettings();
            settings.setUseWideViewPort(true);

//            int position = getIntent().getIntExtra("position",0);
//
//            String url = getUrlByPosition(position);

            String url = getIntent().getStringExtra("url");

            webView.loadUrl(url);
        }

    }

    private String getUrlByPosition(int position) {

        String oldUrl = getIntent().getStringExtra("url");

        String number = getNumberFromOldUrl(oldUrl);

        //拼接成手机网页版
        StringBuilder url = new StringBuilder();

        switch (position){
            //要闻
            case 0:
                return url.append("http://ient.ifeng.com/").append(number).append("/news.shtml").toString();
            //财经
            case 1:
                return url.append("http://ient.ifeng.com/").append(number).append("/news.shtml").toString();
            //娱乐
            case 2:
                return url.append("http://ient.ifeng.com/").append(number).append("/news.shtml").toString();
            //体育
            case 3:
                return url.append("http://isports.ifeng.com/").append(number).append("/news.shtml").toString();
            //军事
            case 4:
                return url.append("http://ient.ifeng.com/").append(number).append("/news.shtml").toString();
            //科技
            case 5:
                return url.append("http://ient.ifeng.com/").append(number).append("/news.shtml").toString();
            //历史
            case 6:
                return url.append("http://ihistory.ifeng.com/").append(number).append("/news.shtml").toString();
            //凤凰号
            case 7:
                //url.append("http://ient.ifeng.com/").append(number).append("/news.shtml");
                return url.append(oldUrl).toString();
        }
        return null;
    }

    private String getNumberFromOldUrl(String oldUrl) {

        //截取所需字符串
        String oldUrl2 = oldUrl.substring(oldUrl.lastIndexOf("/")+1,oldUrl.lastIndexOf("."));
        //包含"_"，去掉"_0"
        if(oldUrl2.contains("_")){
            return oldUrl2.substring(0,oldUrl.lastIndexOf("_"));
        }
        return oldUrl2;
    }
}
