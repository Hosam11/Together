package com.example.together.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListTask {
    @SerializedName("group_id")
    private int groupID;
    @SerializedName("current_user_id")
    private int userId;
    @SerializedName("name")
    private String title;
    private String description;
    private String status;
    private int id;
    private int position;

    public ListTask(int groupID, int userId, String title, String description, String status,int id) {
        this.groupID = groupID;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.id =id;
    }
    public ListTask(int groupID, int userId, String title, String description,int position, String status) {
        this.groupID = groupID;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.id =id;
        this.position=position;
    }
    public ListTask(int groupID, int userId, String title, String description, String status) {
        this.groupID = groupID;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
