package com.example.myapplication.base;

import android.util.Log;

import com.example.myapplication.base.i.IPresenter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class BasePresenter implements IPresenter {

    private static final String TAG = BasePresenter.class.getSimpleName();


    protected ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
    }


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate 创建线程持");
        threadPoolExecutor = new ThreadPoolExecutor(
                1,
                1,
                1,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(3),
                (runnable, threadPoolExecutor) -> Log.d(TAG, "任务排队中，且队列已满，无法继续插入等待队列"));
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy P层action全部取消");
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdownNow();
        }
    }
}
