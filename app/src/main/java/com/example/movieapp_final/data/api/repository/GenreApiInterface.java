package com.example.movieapp_final.data.api.repository;

import com.example.movieapp_final.data.models.GenreResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GenreApiInterface {

    @GET("tv/list")
    Call<GenreResponse> getGenre(
            @Query("api_key")
                    String apiKey
    );
}
