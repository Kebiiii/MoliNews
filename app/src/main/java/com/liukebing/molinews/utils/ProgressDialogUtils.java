package com.liukebing.molinews.utils;

import android.support.v4.app.FragmentActivity;

import com.liukebing.molinews.view.CustomProgressDialog;


/**
 * 弹窗工具类
 * Created by HLQ on 2016/09/01.
 */
public class ProgressDialogUtils {

    CustomProgressDialog dialog;

    /**
     * 弹出弹窗
     * @param message 消息字符串
     */
    public void showProgressDialog(FragmentActivity activity,String message){

        dialog = new CustomProgressDialog(activity);

        //如果message为null，默认显示正在加载
        if(message==null){
            message = "Loading";
        }
        dialog.setMessage(message);

        dialog.setCancelable(true);

        //如果Activity运行并且弹窗未显示才显示弹窗
        dialog.show();
    }

    /**
     * 关闭弹窗的方法
     */
    public void dismissProgressDialog(){

        dialog.dismiss();
    }
}
