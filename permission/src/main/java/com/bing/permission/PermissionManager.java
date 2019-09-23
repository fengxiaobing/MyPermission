package com.bing.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bing.permission.annotation.IPermission;
import com.bing.permission.helper.PermissionHelper;
import com.bing.permission.listener.PermissionCallBack;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PermissionManager {
    //检查所请求的权限是否被授予
    public static boolean hasPermissions(Activity activity,String... perms){
        if(activity == null){
            throw new IllegalArgumentException("activity 不能为空");
        }
        //如果低于6.0 无需做权限判断
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }
        for (String perm : perms) {
            //任意一个权限被拒绝  就返回false
            if(ContextCompat.checkSelfPermission(activity,perm)!= PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
    /**
     * 向用户申请权限
     * @param activity
     * @param requestCode
     * @param perms
     */
    public static void requestPermissions(@NonNull Activity activity, int requestCode, @NonNull String... perms){
        //发起请求之前 先检查
        if(hasPermissions(activity,perms)){
            //如何全部通过
            notifyHasPermissions(activity,requestCode,perms);
            return;
        }
        //权限申请（Helper）
        PermissionHelper helper = PermissionHelper.newInstance(activity);
        helper.requestPermission(requestCode,perms);
    }

    private static void notifyHasPermissions(Activity activity, int requestCode, String[] perms) {
        //将授予通过的权限组转参告知处理权限结果方法
         int[] grantResults = new int[perms.length];
        for (int i = 0; i < perms.length; i++) {
            grantResults[i] = PackageManager.PERMISSION_GRANTED;//全部通过
        }
        onRequestPermissionsResult(requestCode,perms,grantResults,activity);
    }
//处理权限请求回调结果方法 如果授予或者拒绝任何权限，将通过PermissionCallBack回调接收结果
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, Activity activity) {
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        //分类
        for (int i = 0; i < permissions.length; i++) {
            if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                String perm = permissions[i];
                granted.add(perm);
            }else {
                String perm = permissions[i];
                denied.add(perm);
            }
        }
        //做回调 Activity
        if(!granted.isEmpty()){
            if(activity instanceof PermissionCallBack){
                ((PermissionCallBack)activity).onPermissionGranted(requestCode,granted);
            }
        }
        if(!denied.isEmpty()){
            if(activity instanceof PermissionCallBack){
                ((PermissionCallBack)activity).onPermissionDenied(requestCode,denied);
            }
        }
        //全部通过了
        if(!granted.isEmpty() && denied.isEmpty()){
            refletAnnotationMethod(activity,requestCode);
        }

    }
///跳转到指定Activity当中有IPermission注释的并且请求参数码正确的方法
    private static void refletAnnotationMethod(Activity activity, int requestCode) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            //如果方法有IPermission注释
            if(method.isAnnotationPresent(IPermission.class)){
                //获取注释
                IPermission annotation = method.getAnnotation(IPermission.class);
                //如果注解的值等于请求码的值（两次匹配，避免冲突）
                if(annotation.value() == requestCode){
                    //严格检验
                    //方法返回值必须是void
                    Type returnType = method.getGenericReturnType();
                    if(!"void".equals(returnType.toString())){
                        throw new RuntimeException(method.getName()+"方法返回值必须是void");
                    }
                    //方法参数校验
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if(parameterTypes.length>0){
                        throw new RuntimeException(method.getName()+"方法无参数");
                    }
                    try {
                        if(!method.isAccessible()){
                            method.setAccessible(true);
                        }
                        method.invoke(activity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static boolean somePermissionPermanentlyDenited(Activity activity, List<String> denitedPerms) {
        return PermissionHelper.newInstance(activity).somePermissionPermanentlyDenited(denitedPerms);
    }
}
