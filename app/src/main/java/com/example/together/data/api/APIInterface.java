package com.example.together.data.api;

import com.example.together.data.model.Group;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIInterface {
    //    http://127.0.0.1:8000/api/

    @POST("signup")
    Call<GeneralResponse> signUp(@Body User user);

    @POST("login")
    Call<LoginResponse> login(@Body UserLogin userLogin);

    @GET("show?")
    Call<User> fetchUserData(@Query("id") Integer id);

    @POST("createGroup")
    Call<GeneralResponse> createGroup(@Body Group group);



}
