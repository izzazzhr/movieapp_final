package com.example.movieapp_final.data.api.repository;

import com.example.movieapp_final.Const;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchApiClient {
    private static Retrofit retrofit;
    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(Const.BASE_URL_SEARCH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
