package com.example.myapplication.login.v;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.login.p.LoginPresenter;
import com.example.myapplication.base.i.IView;
import com.example.myapplication.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements IView {

    ActivityLoginBinding binding;

    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginPresenter = new LoginPresenter(this);
        getLifecycle().addObserver(loginPresenter);
        binding.btnLogin.setOnClickListener(view -> {
            Log.d("BasePresenter", "btnLogin click at: " + System.currentTimeMillis());
            loginPresenter.loginAction();
        });

    }

    @Override
    public void refreshUI() {
        Log.d("BasePresenter", "refreshUI at :" + System.currentTimeMillis());
    }
}