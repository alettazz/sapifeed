package com.example.aletta.feedtastic.api.response;

import com.example.aletta.feedtastic.api.model.ComicWrap;
import com.google.gson.annotations.SerializedName;

public class ComicResponse {
    private ComicWrap data;

    public ComicWrap getData() {
        return data;
    }

}
