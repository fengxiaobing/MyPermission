package com.bing.permission.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class AppSettingDialog implements DialogInterface.OnClickListener{
    private static final int setting_code = 333;

    private Activity activity;
    private String title;
    private String message;
    private String posttiveButton;
    private String negativeButton;
    private DialogInterface.OnClickListener listener;
    private int requestCode;

    public AppSettingDialog(Builder builder) {
        this.activity = builder.activity;
        this.title = builder.title;
        this.message = builder.message;
        this.posttiveButton = builder.posttiveButton;
        this.negativeButton = builder.negativeButton;
        this.listener = builder.listener;
        this.requestCode = builder.requestCode;
    }
    public void show(){
        if(listener != null){
            showDialog();
        }else {
            throw new IllegalArgumentException("对话框监听不能为空");
        }
    }


    //显示对话框
    private void showDialog() {
        new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(posttiveButton,this)
                .setNegativeButton(negativeButton,listener)
                .create().show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",activity.getPackageName(),null);
        intent.setData(uri);
        activity.startActivityForResult(intent,requestCode);
    }

    public static class Builder{
        private Activity activity;
        private String title;
        private String message;
        private String posttiveButton;
        private String negativeButton;
        private DialogInterface.OnClickListener listener;
        private int requestCode;

        public Builder(Activity activity) {
            this.activity = activity;
        }
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }
        public Builder setListener(DialogInterface.OnClickListener listener) {
            this.listener = listener;
            return this;
        }
        public Builder setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }
        public AppSettingDialog build(){
            this.title = "需要的权限";
            this.message = message==null?"打开设置，启动权限":message;
            this.posttiveButton = activity.getString(android.R.string.ok);
            this.negativeButton = activity.getString(android.R.string.cancel);
            return new AppSettingDialog(this);
        }
    }
}
