package com.example.together.data.model;

import androidx.annotation.NonNull;

public class LoginResponse {

    private String token;
    private int id;
    private String response;

    private boolean isSuccess;
    private boolean isConFailed;

    public boolean isConFailed() {
        return isConFailed;
    }

    public void setConFailed(boolean conFailed) {
        isConFailed = conFailed;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getToken() {
        return token;
    }

    public String getResponse() {
        return response;
    }

    public int getId() {
        return id;
    }


    @NonNull
    @Override
    public String toString() {
        return ("a LoginResponse $$ token:  " + token +
                "\nid: " + id +
                "\nres: " + response +
                "\nisSuccess: " + isSuccess);
    }
}
