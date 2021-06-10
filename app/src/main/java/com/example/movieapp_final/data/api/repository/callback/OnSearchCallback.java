package com.example.movieapp_final.data.api.repository.callback;

import com.example.movieapp_final.data.models.TvShow;

import java.util.List;

public interface OnSearchCallback {
    void onSuccess(List<TvShow> tvShowList, String msg, int page);

    void onFailure(String msg);
}
