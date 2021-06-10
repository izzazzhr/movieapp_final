package com.example.movieapp_final.data.api.repository;

import com.example.movieapp_final.data.models.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApiInterface {
    @GET("tv")
    Call<TvShowResponse> getSearchResult(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );
}
