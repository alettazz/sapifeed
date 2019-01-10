package com.example.aletta.feedtastic.feed.model;

public class AdsBuilder {
    private String adId;
    private String adTitle;
    private String adDate;
    private String adImgUrl;
    private String adVisNr;
    private String adIsFav;

    public AdsBuilder setAdId(String adId) {
        this.adId = adId;
        return this;
    }

    public AdsBuilder setAdTitle(String adTitle) {
        this.adTitle = adTitle;
        return this;
    }

    public AdsBuilder setAdDate(String adDate) {
        this.adDate = adDate;
        return this;
    }

    public AdsBuilder setAdImgUrl(String adImgUrl) {
        this.adImgUrl = adImgUrl;
        return this;
    }

    public AdsBuilder setAdVisNr(String adVisNr) {
        this.adVisNr = adVisNr;
        return this;
    }

    public AdsBuilder setAdIsFav(String adIsFav) {
        this.adIsFav = adIsFav;
        return this;
    }

    public Ads createAds() {
        return new Ads(adId, adTitle, adDate, adImgUrl, adVisNr, adIsFav);
    }
}