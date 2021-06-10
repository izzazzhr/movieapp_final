package com.example.movieapp_final.data.api.repository.callback;

import com.example.movieapp_final.data.models.TvShow;

import java.util.List;

public interface OnTvShowCallback {
    void onSuccess(int page, List<TvShow> tvShowList);

    void onFailure(String message);
}
