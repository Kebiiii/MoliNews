package com.liukebing.molinews.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.liukebing.molinews.R;


/**
 * 自定进度条对话框
 * 继承自AlertDialog
 * Created by HLQ on 2016/09/01.
 */
public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Context context) {

        //设置样式主题
        super(context, R.style.CustomProgressDialog);

        //通过setContentView引入自定义layout
        setContentView(R.layout.dialog_progress_custom);

        //显示ProgressBar在屏幕中央
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    /**
     * 设置消息提示的方法
     * @param message 传入的消息
     */
    public void setMessage(String message){
        TextView tv_loading_image_view = (TextView) this.findViewById(R.id.tv_loading_image_view);
        tv_loading_image_view.setText(message);
    }
}
