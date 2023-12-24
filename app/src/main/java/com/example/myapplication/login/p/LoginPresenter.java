package com.example.myapplication.login.p;

import com.example.myapplication.base.BasePresenter;
import com.example.myapplication.base.i.IView;

public class LoginPresenter extends BasePresenter {

    IView iView;

    public LoginPresenter(IView iView) {
        this.iView = iView;
    }

    public void loginAction() {

        threadPoolExecutor.execute(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            iView.refreshUI();
        });
    }
}
