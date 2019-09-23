package com.bing.mypermission;

import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.bing.permission.PermissionManager;
import com.bing.permission.dialog.AppSettingDialog;

import java.util.List;

public class PermissionBaseActivity extends BaseActivity {

    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "权限申请拒绝", Toast.LENGTH_SHORT).show();
        //当用户点击拒绝并且有可能勾选“不在提示”
        if(PermissionManager.somePermissionPermanentlyDenited(this,perms)){
            //显示一个对话框告知用户是否跳转到设置页面
            new AppSettingDialog.Builder(this)
                    .setListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).build().show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //从设置回来

    }
}
