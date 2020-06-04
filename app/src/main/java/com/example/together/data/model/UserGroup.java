package com.example.together.data.model;

import java.io.Serializable;

public class UserGroup implements Serializable {
    private int id;

    private String photo = null;
    private String address;
    private int max_member_number;
    private int duration;
    private String name;
    private String description;
    private int current_number_of_members;
    private String status;
    private String level;
    private String created_at = null;
    private String updated_at = null;
    private int interest_id;
    private int admin_id;
    Pivot PivotObject;


    // Getter Methods

    public int getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getAddress() {
        return address;
    }

    public int getMax_member_number() {
        return max_member_number;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCurrent_number_of_members() {
        return current_number_of_members;
    }

    public String getStatus() {
        return status;
    }

    public String getLevel() {
        return level;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getInterest_id() {
        return interest_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public Pivot getPivot() {
        return PivotObject;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMax_member_number(int max_member_number) {
        this.max_member_number = max_member_number;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCurrent_number_of_members(int current_number_of_members) {
        this.current_number_of_members = current_number_of_members;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setInterest_id(int interest_id) {
        this.interest_id = interest_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public void setPivot(Pivot pivotObject) {
        this.PivotObject = pivotObject;
    }
    public class Pivot {
        private int user_id;
        private int group_id;


        // Getter Methods

        public float getUser_id() {
            return user_id;
        }

        public float getGroup_id() {
            return group_id;
        }

        // Setter Methods

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }
    }
}
