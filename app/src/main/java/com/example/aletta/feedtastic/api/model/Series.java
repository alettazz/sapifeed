package com.example.aletta.feedtastic.api.model;

import com.google.gson.annotations.SerializedName;

public class Series {

    @SerializedName("url")
    private String resourceURI;

    public Series() {
    }

    public Series(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getResourceURI() {
        return resourceURI;
    }
}
