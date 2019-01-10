package com.example.aletta.feedtastic;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.aletta.feedtastic.feed.fragments.FeedFragment;
import com.example.aletta.feedtastic.registration.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aletta.feedtastic.feed.model.ComicUtil.GENERAL;
import static com.example.aletta.feedtastic.feed.model.ComicUtil.OWN;
import static com.example.aletta.feedtastic.util.NavigationUtils.NAV_FROM_REG;
import static com.example.aletta.feedtastic.util.NavigationUtils.NAV_TO_FEED;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.textMessage)
    TextView mTextMessage;
    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ft.replace(R.id.container, ProfileFragment.newInstance());
                    ft.addToBackStack("a");
                    ft.commit();
                    return true;
                case R.id.navigation_dashboard:
                    ft.replace(R.id.container, FeedFragment.newInstance(GENERAL));
                    ft.addToBackStack("a");
                    ft.commit();
                    return true;
                case R.id.navigation_notifications:
                    ft.replace(R.id.container, FeedFragment.newInstance(OWN));
                    ft.addToBackStack("a");
                    ft.commit();
                    return true;
            }
            return false;
        }
    };
    private String navTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getIntentExtras();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    private void getIntentExtras() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (getIntent() != null) {
            if (getIntent().hasExtra(NAV_FROM_REG)) {
                navTo = getIntent().getStringExtra(NAV_FROM_REG);
            }
        }

        if (navTo != null && navTo.equals(NAV_TO_FEED)) {
            ft.replace(R.id.container, FeedFragment.newInstance(GENERAL));
            bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
            ft.addToBackStack("a");
            ft.commit();
        } else {
            ft.replace(R.id.container, ProfileFragment.newInstance());
            ft.addToBackStack("a");
            ft.commit();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

    }
}
