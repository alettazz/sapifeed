package com.example.aletta.feedtastic.feed.model;

import android.net.Uri;

public class MyComicData {
    private String description;
    private String id;
    private boolean liked;
    private String ownCategory;
    private String title;
    private Uri img;

    public MyComicData() {
    }

    public MyComicData(String id,String title, String description, String ownCategory) {
        this.description = description;
        this.id = id;
        this.ownCategory = ownCategory;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public boolean getLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getOwnCategory() {
        return ownCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setUploadedImage(Uri upladedImage) {
        this.img = upladedImage;
    }

    public Uri getImg() {
        return img;
    }
}
