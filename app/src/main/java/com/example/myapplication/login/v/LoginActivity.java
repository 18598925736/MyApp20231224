package com.example.myapplication.login.v;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.base.i.IView;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.login.p.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements IView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;

            TextView tv = findViewById(R.id.btnLogin);
            tv.setText(versionName + " - " + versionCode);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void refreshUI() {
        Log.d("BasePresenter", "refreshUI at :" + System.currentTimeMillis());
    }
}