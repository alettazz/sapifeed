package com.example.aletta.feedtastic.models;

import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private boolean isLogin;
    private int version;
    private String email;
    private String firstname;
    private String secondname;
    private String reported;
    private String hearted;
    private String nickname;
    private String phonenumber;

    private ArrayList<String> hobbiesList;

    public User() {
    }

    public User(String username, String phonenumber, boolean isLogin, int version) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.isLogin = isLogin;
        this.version = version;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public String getReported() {
        return reported;
    }

    public String getHearted() {
        return hearted;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
}