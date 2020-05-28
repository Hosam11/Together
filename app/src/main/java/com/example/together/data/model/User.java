package com.example.together.data.model;

import androidx.annotation.NonNull;

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
    private String email;
    private String password;
    // TODO will be String later in DB and name brith data
    private int age;
    private String address;
    private String gender;
    public String test;

    public String response;

    public User(String name, String email, String password, int age, String address, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.address = address;
        this.gender = gender;
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


}
