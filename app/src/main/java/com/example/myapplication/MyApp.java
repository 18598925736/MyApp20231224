package com.example.myapplication;

import android.app.Application;
import android.util.Log;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        MyCrashHandler.getInstance().init(this); adfsafda


        FutureTask<String> task = new FutureTask<>(this::getString);

        new Thread(task).start();

        try {
            task.get(2000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // 任务执行超时
            Log.d("FutureTask","任务执行超时");
        }

    }


    private String getString() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

}
