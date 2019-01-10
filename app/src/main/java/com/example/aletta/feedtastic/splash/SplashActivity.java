package com.example.aletta.feedtastic.splash;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.aletta.feedtastic.R;
import com.example.aletta.feedtastic.RegistrationActivity;
import com.example.aletta.feedtastic.models.User;
import com.example.aletta.feedtastic.util.FeedRepository;
import com.example.aletta.feedtastic.util.SharedPrefManager;

import static com.example.aletta.feedtastic.util.Consants.USER;
import static com.example.aletta.feedtastic.util.NavigationUtils.NAV_FROM_SPLASH;
import static com.example.aletta.feedtastic.util.NavigationUtils.NAV_TO_FEED;
import static com.example.aletta.feedtastic.util.NavigationUtils.NAV_TO_REG;

public class SplashActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                FeedRepository.getInstance().getMycomicsFromFireBase();
                if (SharedPrefManager.getInstance().getData(USER, User.class) != null) {
                    intent.putExtra(NAV_FROM_SPLASH, NAV_TO_FEED);
                } else {
                    intent.putExtra(NAV_FROM_SPLASH, NAV_TO_REG);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        };
        countDownTimer.start();
    }
}
