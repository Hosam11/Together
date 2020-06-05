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

    // admin of group
    @SerializedName("id")
    private int adminID;

    // nullable
    @SerializedName("address")
    private String location;

    @SerializedName("max_member_number")
    private int maxMembers;

    private int duration;

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

    @SerializedName("photo")
    private String image;

    public Group(String name, String image, String description) {
        this.groupName = name;
        this.image = image;
        this.groupDesc = description;
    }

    public Group(
            int userID, String location, String img,
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
        this.image = img;
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


//    public void setUserID(int userID) {
//        this.userID = userID;
//    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public void setCurrentMembers(int currentMembers) {
        this.currentMembers = currentMembers;}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

//    public int getUserID() {
//        return userID;
//    }




    public int getMaxMembers() {
        return maxMembers;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevelRequired() {
        return levelRequired;
    }

    public void setLevelRequired(String levelRequired) {
        this.levelRequired = levelRequired;
    }

    public String getInterest() {
        return interest;
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
}


