package com.example.movieapp_final.data.api;

import com.example.movieapp_final.data.models.CreditModel;
import com.example.movieapp_final.data.models.TrailerResponse;
import com.example.movieapp_final.data.models.TvShow;
import com.example.movieapp_final.data.models.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("tv/{sort_by}")
    Call<TvShowResponse> getResults(
            @Path("sort_by") String sortBy,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/{id}")
    Call<TvShow> getDetail(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

    @GET("search/tv")
    Call<TvShowResponse> search(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/{id}/videos")
    Call<TrailerResponse> getTvTrailer(
            @Path("id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("tv/{tv_id}/credits")
    Call<CreditModel> getCast(
            @Path("tv_id") int id,
            @Query("api_key") String apiKey
    );







}