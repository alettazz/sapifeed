package com.example.aletta.feedtastic.api;

import android.support.annotation.NonNull;

import com.example.aletta.feedtastic.api.response.ComicResponse;

public class RemoteDataProvider implements IDataProvider {

    private final IApiService apiService = RetrofitClient.getInstance().getApiService();

    private RemoteDataProvider() {
    }

    public static RemoteDataProvider instance() {
        return RemoteDataProviderInstanceHolder.instance;
    }

    @Override
    public void getComic(String limit, String timestmp, String apiKey, String hash, @NonNull DataListener<ComicResponse> comicListener) {

        ResponseHandler.handleCall(apiService.getComics(limit, timestmp, apiKey, hash), comicListener);

    }

    private static class RemoteDataProviderInstanceHolder {
        static final RemoteDataProvider instance = new RemoteDataProvider();
    }
}

