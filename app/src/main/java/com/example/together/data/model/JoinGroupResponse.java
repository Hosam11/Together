package com.example.together.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class JoinGroupResponse {

    /*
        "id": 1,
        "request_content": "this user asks to join this group",
        "user_id": 3,
        "group_id": 1,
     */
    private int id;

    @SerializedName("user_id")
    private int userID;

    @SerializedName("group_id")
    private int groupID;

    @SerializedName("request_content")
    private String reqContent;


    public int getUserID() {
        return userID;
    }

    public int getGroupID() {
        return groupID;
    }

    @NonNull
    @Override
    public String toString() {
        return ("\nid: " + id +
                "\nuserID: " + userID +
                "\ngroupID: " + groupID +
                "\nreqContent: " + reqContent);
    }
}


