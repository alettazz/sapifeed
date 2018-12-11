package com.example.aletta.feedtastic.base;

import android.app.Application;

import com.example.aletta.feedtastic.ResourceProvider;
import com.example.aletta.feedtastic.util.SharedPrefManager;


public  class BaseApplication extends Application {

    private static Application context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ResourceProvider resourceProvider = new ResourceProvider();
        resourceProvider.init(context);
        SharedPrefManager.init(context);
    }

    public static Application getContext() {
        return context;
    }
}
