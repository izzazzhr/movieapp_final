package com.example.movieapp_final.data.api.repository.callback;

import com.example.movieapp_final.data.models.Cast;
import com.example.movieapp_final.data.models.CreditModel;

public interface OnCastCallback {
    void onSuccess(CreditModel creditModel, String message);

    void onFailure(String message);
}
