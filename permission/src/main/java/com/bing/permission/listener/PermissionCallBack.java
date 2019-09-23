package com.bing.permission.listener;

import java.util.List;

public interface PermissionCallBack {
    //授予权限通过返回
    void onPermissionGranted(int requestCode, List<String> perms);
    //授予权限被拒绝返回
    void onPermissionDenied(int requestCode, List<String> perms);
}
