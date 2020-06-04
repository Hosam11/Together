package com.example.together.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class GeneralResponse {

    @SerializedName("response")
    public String response;



    @NonNull
    @Override
    public String toString() {
        return ("response >> " + response);
    }
}
