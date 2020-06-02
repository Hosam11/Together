package com.example.together.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Group {
    // POST - Create Group
    //http://127.0.0.1:8000/api/createGroup
    /*{
        // optional
        // "photo"
        // "address"
            "id":1, // user id
            "max_member_number": 5,
            "duration": 3,
            "name": "android",
            "description": "afs adssf asfa ",
            "current_number_of_members": 0, // alway 0
            "status":"free", // paid
            "level":"" ,// 3,
            // from interest table
            "interest_id": 2

    }*/
    // TODO 1- missing interests of other

    @SerializedName("id")
    private int adminID;

    // nullable
    @SerializedName("address")
    private String location;

    @SerializedName("max_member_number")
    private int maxMembers;

    private int duration;

    public String getLocation() {
        return location;
    }

    public int getDuration() {
        return duration;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public int getCurrentMembers() {
        return currentMembers;
    }

    public String getStatus() {
        return status;
    }

    public String getLevelRequired() {
        return levelRequired;
    }

    public String getInterest() {
        return interest;
    }

    @SerializedName("name")
    private String groupName;

    @SerializedName("description")
    private String groupDesc;

    @SerializedName("current_number_of_members")
    private int currentMembers = 0;

    private String status; // free - paid

    @SerializedName("level")
    private String levelRequired;

    @SerializedName("interest")
    private String interest;

    public Group(
            int userID, String location,
            int maxMembers, int duration,
            String groupName, String groupDesc,
            String status, String levelRequired, String interest
    ) {
        this.adminID = userID;
        this.location = location;
        this.maxMembers = maxMembers;
        this.duration = duration;
        this.groupName = groupName;
        this.groupDesc = groupDesc;
        this.status = status;
        this.levelRequired = levelRequired;
        this.interest = interest;
    }

    public int getAdminID() {
        return adminID;
    }

    public Group(int adminID,
                 String location, int duration, String groupName,
                 String groupDesc, int currentMembers, String status,
                 String levelRequired, String interest
    ) {
        this.adminID = adminID;
        this.location = location;
        this.duration = duration;
        this.groupName = groupName;
        this.groupDesc = groupDesc;
        this.currentMembers = currentMembers;
        this.status = status;
        this.levelRequired = levelRequired;
        this.interest = interest;
    }

    @NonNull
    @Override
    public String toString() {
        return (
                "\nname: " + groupName +
                        "\ndesc: " + groupDesc +
                        "\nadminID: " + adminID +
                        "\nLocation: " + location +
                        "\nmax Numbers: " + maxMembers +
                        "\ndurtaion: " + duration +
                        "\nstatlus: " + status +
                        "\nlevel: " + levelRequired +
                        "\ninterest : " + interest
        );
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }
}


