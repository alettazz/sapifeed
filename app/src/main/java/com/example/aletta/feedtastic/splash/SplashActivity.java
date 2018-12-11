package com.example.aletta.feedtastic.splash;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aletta.feedtastic.MainActivity;
import com.example.aletta.feedtastic.R;

public class SplashActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
            @Override
            public void onTick(long millisUntilFinished) {
            }
        };
        countDownTimer.start();
    }
}
