package com.example.aletta.feedtastic.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class SharedPrefManager {
    // TODO: CHANGE THIS TO SOMETHING MEANINGFUL
    private static final String SETTINGS_NAME = "default_settings";
    private static SharedPrefManager sSharedPrefs;
    private SharedPreferences mPref;
    private static SharedPreferences.Editor mEditor;
    private boolean mBulkUpdate = false;
    private static Context context;

    /**
     * Enum representing your setting names or key for your setting.
     */
    public enum Key {

        SAMPLE_STR,
        SAMPLE_INT
    }

    private SharedPrefManager() {
        if (context != null) {

        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
            mEditor = mPref.edit();
            mEditor.commit();

        }
    }


    public static SharedPrefManager getInstance() {
        if (sSharedPrefs == null) {
            sSharedPrefs = new SharedPrefManager();
        }
        return sSharedPrefs;
    }

    public static void init(Context con) {
        context = con;
    }

    public void setData(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }

        if (key.equals("") || key == null) {
            throw new IllegalArgumentException("key is empty or null");
        }
        Gson gson = new Gson();
        String json = gson.toJson(object);
        mEditor.putString(key, json);
        mEditor.commit();

    }

    public <T> T getData(String key, Class<T> a) {

        String gson = mPref.getString(key, null);
        if (gson == null) {
            return null;
        } else try {
            return new Gson().fromJson(gson, a);
        } catch (Exception e) {
            throw new IllegalArgumentException("Object storaged with key " + key + " is instanceof other class");
        }
    }

    public void put(Key key, String val) {
        doEdit();
        mEditor.putString(key.name(), val);
        doCommit();
    }

    public void put(Key key, int val) {
        doEdit();
        mEditor.putInt(key.name(), val);
        doCommit();
    }

    public void put(Key key, boolean val) {
        doEdit();
        mEditor.putBoolean(key.name(), val);
        doCommit();
    }

    public void put(Key key, float val) {
        doEdit();
        mEditor.putFloat(key.name(), val);
        doCommit();
    }


    public void put(Key key, double val) {
        doEdit();
        mEditor.putString(key.name(), String.valueOf(val));
        doCommit();
    }

    public void put(Key key, long val) {
        doEdit();
        mEditor.putLong(key.name(), val);
        doCommit();
    }

    public String getString(Key key, String defaultValue) {
        return mPref.getString(key.name(), defaultValue);
    }

    public String getString(Key key) {
        return mPref.getString(key.name(), null);
    }

    public int getInt(Key key) {
        return mPref.getInt(key.name(), 0);
    }

    public int getInt(Key key, int defaultValue) {
        return mPref.getInt(key.name(), defaultValue);
    }

    public long getLong(Key key) {
        return mPref.getLong(key.name(), 0);
    }

    public long getLong(Key key, long defaultValue) {
        return mPref.getLong(key.name(), defaultValue);
    }

    public float getFloat(Key key) {
        return mPref.getFloat(key.name(), 0);
    }

    public float getFloat(Key key, float defaultValue) {
        return mPref.getFloat(key.name(), defaultValue);
    }


    public double getDouble(Key key) {
        return getDouble(key, 0);
    }


    public double getDouble(Key key, double defaultValue) {
        try {
            return Double.valueOf(mPref.getString(key.name(), String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public boolean getBoolean(Key key, boolean defaultValue) {
        return mPref.getBoolean(key.name(), defaultValue);
    }

    public boolean getBoolean(Key key) {
        return mPref.getBoolean(key.name(), false);
    }


    public void remove(Key... keys) {
        doEdit();
        for (Key key : keys) {
            mEditor.remove(key.name());
        }
        doCommit();
    }

    public void clear() {
        doEdit();
        mEditor.clear();
        doCommit();
    }

    public void edit() {
        mBulkUpdate = true;
        mEditor = mPref.edit();
    }

    public void commit() {
        mBulkUpdate = false;
        mEditor.commit();
        mEditor = null;
    }

    private void doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }
}