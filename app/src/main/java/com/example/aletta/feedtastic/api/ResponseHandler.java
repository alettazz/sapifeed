package com.example.aletta.feedtastic.api;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponseHandler {

    public static <T> void handleCall(Call<T> call, @NonNull final IDataProvider.DataListener<T> responseListener) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {

                if (response.errorBody() != null) {
                    responseListener.onError(response.errorBody().toString());
                    return;
                }

                if (response.body() == null) {
                    responseListener.onError("");
                    return;
                }

                if (response.isSuccessful()) {
                    responseListener.onSuccess(response.body());
                } else {
                    responseListener.onError("");
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, Throwable t) {
                responseListener.onError("");
            }
        });
    }

}