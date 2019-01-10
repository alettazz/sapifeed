package com.example.aletta.feedtastic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aletta.feedtastic.registration.VerifyPhoneActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aletta.feedtastic.util.NavigationUtils.NAV_FROM_REG;
import static com.example.aletta.feedtastic.util.NavigationUtils.NAV_FROM_SPLASH;
import static com.example.aletta.feedtastic.util.NavigationUtils.NAV_TO_FEED;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.editTextMobile)
    EditText editTextMobile;
    @BindView(R.id.buttonContinue)
    Button buttonContinue;
    private String navigateTo;

    @Override
    protected void onStart() {
        super.onStart();

        if (getIntent().hasExtra(NAV_FROM_SPLASH)) {
            navigateTo = getIntent().getStringExtra(NAV_FROM_SPLASH);
        }

        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        if (navigateTo.equals(NAV_TO_FEED)) {
            intent.putExtra(NAV_FROM_REG, NAV_TO_FEED);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("phonenumber", editTextMobile.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            }
        });

    }
}
