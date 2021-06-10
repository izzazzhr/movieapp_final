package com.example.movieapp_final.data.api.repository.callback;

import com.example.movieapp_final.data.models.Trailer;
import com.example.movieapp_final.data.models.TvShow;

import java.util.List;

public interface OnTrailerCallback {
        void onSuccess(int id, List<Trailer> trailerList);

        void onFailure(String message);

}
