package com.example.myapplication.permisson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

public class PermissionAspectActivity extends Activity {


    private final static String permissionsTag = "permissions";
    private final static String requestCodeTag = "requestCode";

    private static IPermissionCallback mCallback;

    /**
     * 启动当前这个Activity
     */
    public static void startActivity(Context context, String[] permissions, int requestCode, IPermissionCallback callback) {
        Log.d("PermissionAspectTag", "context is : " + context.getClass().getSimpleName());
        if (context == null) return;
        mCallback = callback;
        //启动当前这个Activity并且取消切换动画
        Intent intent = new Intent(context, PermissionAspectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);//开启新的任务栈并且清除栈顶...为何要清除栈顶
        intent.putExtra(permissionsTag, permissions);
        intent.putExtra(requestCodeTag, requestCode);

        context.startActivity(intent);//利用context启动activity

        if (context instanceof Activity) {//并且，如果是activity启动的，那么还要屏蔽掉activity切换动画
            ((Activity) context).overridePendingTransition(0, 0);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String[] permissions = intent.getStringArrayExtra(permissionsTag);
        int requestCode = intent.getIntExtra(requestCodeTag, 0);

        if (PermissionUtil.hasSelfPermissions(this, permissions)) {
            mCallback.granted(requestCode);
            finish();
            overridePendingTransition(0, 0);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        // 现在拿到了权限的申请结果，那么如何处理，我这个Activity只是为了申请，然后把结果告诉外界，所以结果的处理只能是外界传进来
        boolean granted = PermissionUtil.verifyPermissions(grantResults);
        if (granted) {//如果用户给了权限
            mCallback.granted(requestCode);
        } else {
            if (PermissionUtil.shouldShowRequestPermissionRationale(this, permissions)) { // 判断是否还能继续申请
                mCallback.denied(requestCode);
            } else { // 不能再次申请，用户已经完全拒绝
                mCallback.deniedForever(requestCode);
            }
        }
        this.finish();
        overridePendingTransition(0, 0);

    }
}