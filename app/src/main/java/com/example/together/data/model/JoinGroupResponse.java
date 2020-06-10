package com.example.together.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class JoinGroupResponse {
    private int id;
    private String content;
    private String photo;

    public JoinGroupResponse(int id, String content, String photo) {
        this.id = id;
        this.content = content;
        this.photo = photo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return ("\nid: " + id +
                "\nphoto: " + photo +
                "\nreqContent: " + content);
    }
}


