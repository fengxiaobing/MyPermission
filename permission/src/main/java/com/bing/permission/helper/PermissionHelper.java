package com.bing.permission.helper;

import android.app.Activity;
import android.os.Build;

import java.util.List;

//抽象辅助类
public abstract class PermissionHelper {
    private Activity activity;

    public PermissionHelper(Activity activity) {
        this.activity = activity;
    }

    public Activity getHost() {
        return activity;
    }

    public static PermissionHelper newInstance(Activity activity) {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return new  LowApiPermissionHelper(activity);
        }
        return new ActivityPermissionHelper(activity);
    }
//用户申请权限
    public abstract void requestPermission(int requestCode, String[] perms);
//检查被拒绝的权限组中是否有勾选不在提示 如果有任一权限勾选就返回true，反之返回false
    public boolean somePermissionPermanentlyDenited(List<String> denitedPerms){
        for (String denitedPerm : denitedPerms) {
            if(!shouldShowRequestPermissionRationale(denitedPerm)){
                return true;
            }
        }
        return false;
    }

    /**
     * 第一次打开app时候  false
     * 上次申请点击了拒绝  没有勾选不在提示  true
     * 上次申请点击了拒绝   勾选了不再提示 false
     * @param denitedPerm
     * @return  点击了拒绝  没有勾选不在提示 true    勾选了不在提示  false
     */
    protected abstract boolean shouldShowRequestPermissionRationale(String denitedPerm);
}
