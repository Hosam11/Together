package com.example.together.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    /*
    // sign up
    {
        "name": "ali",
        "email": "sd",
        "password": "asdsad",
        "BirthDate": "20",
        "gender": "Fasdf",
        "address": "asdsd"
    }
     */
    public String response;

    public String name;
    private String email;
    private String password;
    @SerializedName("BirthDate")
    private String birthDate;
    private String address;
    private String gender;

    private List<String> interests;

    @SerializedName("groups")
    private List<GroupReturned> groups;

    public User(
            String name, String email,
            String password, String birthDate,
            String address, String gender
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.address = address;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public List<GroupReturned> getGroups() {
        return groups;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getInterests() {
        return interests;
    }

    @NonNull
    @Override
    public String toString() {
        return (
                "\nname: " + name +
                        "\nemail: " + email +
                        "\npass: " + password +
                        "\nBirthDate: " + birthDate +
                        "\ngender: " + gender +
                        "\naddress: " + address
        );
    }


    public class GroupReturned implements Serializable {
        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }

}
