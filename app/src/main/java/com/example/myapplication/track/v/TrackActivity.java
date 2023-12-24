package com.example.myapplication.track.v;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.base.i.IView;
import com.example.myapplication.databinding.ActivityTrackBinding;
import com.example.myapplication.track.p.TrackPresenter;
import com.example.myapplication.util.TrackUtil;

public class TrackActivity extends AppCompatActivity implements IView {

    ActivityTrackBinding binding;

    TrackPresenter trackPresenter;

    TrackUtil trackUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        trackPresenter = new TrackPresenter(this);
        getLifecycle().addObserver(trackPresenter);

        binding.tvStartTrack.setOnClickListener(view -> trackPresenter.syncCurrentLocation());

    }

    @Override
    public void onResume() {
        super.onResume();
        trackUtil.startTrack();
    }

    @Override
    public void onPause() {
        super.onPause();
        trackUtil.stopTrack();
    }


    @Override
    public void refreshUI() {
        Log.d("BasePresenter", "refreshUI at :" + System.currentTimeMillis());
        binding.tvCurrentLocation.setText(trackUtil.getCurrentLocation());
    }
}