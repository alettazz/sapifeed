package com.example.aletta.feedtastic;

import android.content.Context;

public class ResourceProvider {

    private static ResourceProvider instance;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public static ResourceProvider getInstance(){
        return instance;
    }

    public void init(Context context){
        this.context = context;
        instance = this;
    }

    public String getString(int stringId){
        return context.getString(stringId);
    }

}
