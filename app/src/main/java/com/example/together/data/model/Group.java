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
    private int userID;

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

    @SerializedName("interest_id")
    private int interestId;

    public Group(
            int userID, String location,
            int maxMembers, int duration,
            String groupName, String groupDesc,
            String status, String levelRequired, int interestId
    ) {
        this.userID = userID;
        this.location = location;
        this.maxMembers = maxMembers;
        this.duration = duration;
        this.groupName = groupName;
        this.groupDesc = groupDesc;
        this.status = status;
        this.levelRequired = levelRequired;
        this.interestId = interestId;
    }

    @NonNull
    @Override
    public String toString() {
        return (
                "\nname: " + groupName +
                        "\ndesc: " + groupDesc +
                        "\nuserID: " + userID +
                        "\nLocation: " + location +
                        "\nmax Numbers: " + maxMembers +
                        "\ndurtaion: " + duration +
                        "\nstatlus: " + status +
                        "\nlevel: " + levelRequired +
                        "\ninterest id: " + interestId
        );
    }
}


