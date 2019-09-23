package com.bing.permission.helper;

import android.app.Activity;

public class LowApiPermissionHelper extends PermissionHelper {
    public LowApiPermissionHelper(Activity activity) {
        super(activity);
    }

    @Override
    public void requestPermission(int requestCode, String[] perms) {
        throw new IllegalArgumentException("不需要申请权限");
    }

    @Override
    protected boolean shouldShowRequestPermissionRationale(String denitedPerm) {
        return false;
    }
}
