package com.example.aletta.feedtastic.api;
import android.support.annotation.NonNull;
import com.example.aletta.feedtastic.api.response.ComicResponse;

public interface IDataProvider {

    void getComic(String limit, String timestmp, String apikey, String hash, @NonNull DataListener<ComicResponse> contractsDataListener);


    interface DataListener<T> {

        void onSuccess(T t);

        void onError(String errorMessage);
    }
}
