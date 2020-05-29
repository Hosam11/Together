package com.example.together.profile;

import android.graphics.Bitmap;

import java.io.Serializable;

public class UserPojo implements Serializable {
    String name="ahmed";
    String Address="Alex";
    int image;
    String email="m@m.com";
    String DateOfBirth="17/12/2020";
    String interests="aaa-AAA-xAaax-abc-hkgjghaaA-jhkjhk-xxx";
    String pass="12345678";
    String gender="female";

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getImage() {
        return image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
