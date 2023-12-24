package com.example.myapplication.util;

import android.util.Log;

/**
 * 获取手机当前位置
 */
public class TrackUtil {

    public void startTrack() {
        Log.d("TrackUtil", "开始监听位置");
    }

    public void stopTrack() {
        Log.d("TrackUtil", "停止监听位置");
    }

    public String getCurrentLocation() {
        return "当前位置:[经度，纬度]";
    }
}
