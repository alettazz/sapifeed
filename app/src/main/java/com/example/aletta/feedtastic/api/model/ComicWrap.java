package com.example.aletta.feedtastic.api.model;

import java.util.ArrayList;

public class ComicWrap {
    private String offset;
    private String limit;
    private String total;
    private ArrayList<ComicData> results;

    public ComicWrap() {

    }
    public String getOffset() {
        return offset;
    }

    public String getLimit() {
        return limit;
    }

    public String getTotal() {
        return total;
    }

    public ArrayList<ComicData> getResults() {
        return results;
    }


}
