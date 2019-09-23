package com.bing.mypermission;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bing.permission.PermissionManager;
import com.bing.permission.annotation.IPermission;

public class MainActivity extends PermissionBaseActivity {
    private static final int CMAERA_REQUEST_CODE = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void camera(View view) {
        takeCamera();
    }

    @IPermission(CMAERA_REQUEST_CODE)
    public void takeCamera() {
        if (PermissionManager.hasPermissions(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, "相机权限已经申请了", Toast.LENGTH_SHORT).show();
        } else {
            PermissionManager.requestPermissions(this, CMAERA_REQUEST_CODE, Manifest.permission.CAMERA);
        }
    }
}
