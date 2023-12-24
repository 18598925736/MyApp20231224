package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyCrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private Context mContext;

    private static volatile MyCrashHandler instance;

    private int tipType = 0;

    private MyCrashHandler() {
        // 私有构造函数，防止外部实例化
    }

    public static MyCrashHandler getInstance() {
        if (instance == null) {
            synchronized (MyCrashHandler.class) {
                if (instance == null) {
                    instance = new MyCrashHandler();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.mContext = context;
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {

        if (tipType == 0) {
            startCrashActivity(t, e);
        } else {
            handleUncaughtException(e);
        }
    }

    /**
     * 这个可行
     *
     * @param t
     */
    private void handleUncaughtException(Throwable t) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "应用发生了异常" + t.toString(), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        // 延时一段时间后，退出应用
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 退出应用
        System.exit(1);
    }

    private void startCrashActivity(@NonNull Thread t, @NonNull Throwable e) {
        Intent intent = new Intent(mContext, ExceptionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

        if (mDefaultExceptionHandler != null) {
            mDefaultExceptionHandler.uncaughtException(t, e);
        } else {
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

}
