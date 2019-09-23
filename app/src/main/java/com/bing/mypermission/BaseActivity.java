package com.bing.mypermission;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bing.permission.PermissionManager;
import com.bing.permission.listener.PermissionCallBack;

import java.util.List;


public abstract class BaseActivity extends AppCompatActivity implements PermissionCallBack {

    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "通过", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //用户的允许或者拒绝的监听回调
        PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

}
