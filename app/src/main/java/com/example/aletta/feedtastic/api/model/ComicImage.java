package com.example.aletta.feedtastic.api.model;

public class ComicImage {

private String path;
private String extension;

    public String getImagPathFul() {
        return path.concat(".").concat(extension);
    }
}
