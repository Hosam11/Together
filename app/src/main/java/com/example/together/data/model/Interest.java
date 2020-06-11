package com.example.together.data.model;

import com.google.gson.annotations.SerializedName;

public class Interest {

    private int image;
    private int id;
    private String name;
    @SerializedName("img")
    private String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public Interest(String name, int image) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
