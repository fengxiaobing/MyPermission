package com.bing.permission.helper;

import android.app.Activity;

import androidx.core.app.ActivityCompat;

public class ActivityPermissionHelper extends PermissionHelper{
    public ActivityPermissionHelper(Activity activity) {
        super(activity);
    }

    @Override
    public void requestPermission(int requestCode, String[] perms) {
        ActivityCompat.requestPermissions(getHost(),perms,requestCode);
    }

    @Override
    protected boolean shouldShowRequestPermissionRationale(String denitedPerm) {
        return ActivityCompat.shouldShowRequestPermissionRationale(getHost(),denitedPerm);
    }
}
