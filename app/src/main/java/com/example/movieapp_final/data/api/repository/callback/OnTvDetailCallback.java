package com.example.movieapp_final.data.api.repository.callback;


import com.example.movieapp_final.data.models.TvShow;

public interface OnTvDetailCallback {
    void onSuccess(TvShow tvShow, String message);

    void onFailure(String message);
}
