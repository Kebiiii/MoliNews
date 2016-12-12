package com.liukebing.molinews.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.liukebing.molinews.entity.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 获取网络数据工具类
 * Created by HLQ on 2016/09/02.
 */
public class JSONUtils {

    private List<Data>[] list;

    CallbackListener listener;

    public JSONUtils(){}

    public JSONUtils(CallbackListener listener){
        this.listener = listener;
    }

    public void getResultList(final String url){

        new Thread(new Runnable() {
            @Override
            public void run() {

                //实例化一个Client
                OkHttpClient client = new OkHttpClient();

                //建立请求
                final Request request = new Request.Builder().url(url).build();

                //获得响应结果
                Call call = client.newCall(request);

                //建立加入请求队列
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if(response.isSuccessful()){

                            //获取结果
                            String result = response.body().string();

                            //Log.i("HLQ2",result);

                            Message msg = Message.obtain();
                            msg.obj = result;
                            msg.what = 100;
                            handler.sendMessage(msg);

                        }else{
                            throw new IOException(""+response);
                        }
                    }
                });
            }
        }).start();

    }

    private void getResult(String result) {

        String jsonString = result.substring(result.indexOf("[[{"),result.indexOf("}]]")+3);

        try {

            JSONArray dataList = new JSONArray(jsonString);

            //不同的i代表不同的List<Data>，代表不同的新闻栏目
            list = new ArrayList[dataList.length()];

            for(int i=0;i<dataList.length();i++){

                JSONArray array = dataList.getJSONArray(i);

                //获取array数组里面的对象
                JSONObject jsonObject;
                Data data;
                list[i] = new ArrayList<>();

                for (int j = 0; j < array.length(); j++) {

                    jsonObject = (JSONObject) array.get(j);

                    data = new Data();
                    data.setThumbnail(jsonObject.getString("thumbnail"));
                    data.setTitle(jsonObject.getString("title"));
                    data.setUrl(jsonObject.getString("url"));
                    list[i].add(data);

//                    Log.i("JSONUtils",jsonObject.getString("thumbnail")+"\n");
//                    Log.i("JSONUtils",jsonObject.getString("title")+"\n");
//                    Log.i("JSONUtils",jsonObject.getString("url")+"\n");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        listener.update(list);
        Log.d("JSONUtils", "list.length=" + list.length);
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 100:
                    String result = (String) msg.obj;
                    getResult(result);
                    break;
            }
        }
    };

    /**
     * 关注数据加载的父类接口
     */
    public interface CallbackListener{
        void update(List<Data>[] list);
    }
}
