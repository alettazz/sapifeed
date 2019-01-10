package com.example.aletta.feedtastic.feed.model;

public class Ads {
    private String adId;
    private String title;
    private String date;
    private String img_url;
    private String seen_nr;
    private String desc;
    private String adIsFav;

    public Ads() {
    }

    public Ads(String adTitle, String date, String adVisNr) {
        this.title = adTitle;
        this.date = date;
        this.seen_nr = adVisNr;
    }

    public Ads(String adId, String adTitle, String date, String adImgUrl, String adVisNr, String adIsFav) {
        this.adId = adId;
        this.title = adTitle;
        this.date = date;
        this.img_url = adImgUrl;
        this.seen_nr = adVisNr;
        this.adIsFav = adIsFav;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdId() {
        return adId;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getDesc() {
        return desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getAdImgUrl() {
        return img_url;
    }

    public String getSeen_nr() {
        return seen_nr;
    }

    public String getAdIsFav() {
        return adIsFav;
    }
}
