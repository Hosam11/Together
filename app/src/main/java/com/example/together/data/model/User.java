package com.example.together.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    /*
    // sign up
    {
        "name": "ali",
        "email": "sd",
        "password": "asdsad",
        "age": 20,
        "gender": "Fasdf",
        "address": "asdsd"
    }
     */

    /*
        "name": "update ali",
        "email": "sd",
        "gender": "update Fasdf",
        "age": 200,
        "address": null
     */

    public String name;
    public String response;
    private String email;
    private String password;
    // TODO will be String later in DB and name brith data
    private int age;
    private String address;
    private String gender;

    private List<String> interests;

    public List<GroupReturned> getGroups() {
        return groups;
    }

    @SerializedName("groups")
    private List<GroupReturned> groups;
    public List<String> getInterests() {
        return interests;
    }


    public User(String name, String email, String password,
                int age, String address, String gender,
                List<String> interests
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.address = address;
        this.gender = gender;
        this.interests = interests;
    }


    @NonNull
    @Override
    public String toString() {
        return (
                "\nname: " + name +
                        "\nemail: " + email +
                        "\npass: " + password +
                        "\nage: " + age +
                        "\ngender: " + gender +
                        "\naddress: " + address
        );
    }


     public  class GroupReturned {
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
