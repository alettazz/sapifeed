package com.example.aletta.feedtastic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.aletta.feedtastic.registration.RegistrationFragment;
import com.example.aletta.feedtastic.util.SharedPrefManager;

import com.example.aletta.feedtastic.models.User;

import static com.example.aletta.feedtastic.login.LoginStatus.LOGGED;
import static com.example.aletta.feedtastic.login.LoginStatus.LOGGED_OUT;
import static com.example.aletta.feedtastic.util.Consants.USER;

public class MainActivity extends AppCompatActivity {


    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkUserLogged()) {
        }

        initFragmentNavigation();


        User user = new User("ali", "1234", false, 1);
        SharedPrefManager.getInstance().setData("USER", user);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mTextMessage.setText(SharedPrefManager.getInstance().getData("USER", User.class).getUsername());


    }

    private void initFragmentNavigation() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, RegistrationFragment.newInstance(SharedPrefManager.getInstance().getData(USER, User.class) .getUsername(),"b"));
        ft.commit();
    }

    private boolean checkUserLogged() {
        if (SharedPrefManager.getInstance().getData(USER, User.class) != null) {
            return LOGGED;
        } else {
            return LOGGED_OUT;
        }

    }

}
