package com.example.aletta.feedtastic.api;
import com.example.aletta.feedtastic.api.response.ComicResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApiService {

    @GET("/v1/public/comics")
    Call<ComicResponse> getComics(@Query("limit") String limit, @Query("ts") String ts, @Query("apikey") String apiKey, @Query("hash") String hash);

}
