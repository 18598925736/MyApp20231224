package com.example.myapplication.track.p;

import com.example.myapplication.base.BasePresenter;
import com.example.myapplication.base.i.IView;

public class TrackPresenter extends BasePresenter {

    IView iView;


    public TrackPresenter(IView iView) {
        this.iView = iView;
    }

    public void syncCurrentLocation() {
        threadPoolExecutor.execute(() -> {
            while (true) { // 模拟无限循环执行同步当前位置并显示到UI上
                iView.refreshUI();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

    }

}
