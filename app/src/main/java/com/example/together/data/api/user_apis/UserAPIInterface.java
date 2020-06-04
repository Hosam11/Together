package com.example.together.data.api.user_apis;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPIInterface {

    @POST("signup")
    Call<LoginResponse> signUp(@Body User user);

    @POST("signin")
    Call<LoginResponse> login(@Body UserLogin userLogin);

    @GET("show?")
    Call<User> fetchUserData(@Query("id") int id, @Header("Authorization") String header);
}
