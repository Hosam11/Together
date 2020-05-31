package com.example.together.data.api;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.JoinGroupResponse;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APIInterface {

    // http://127.0.0.1:8000/api/

    @POST("signup")
    Call<GeneralResponse> signUp(@Body User user);

    @POST("signin")
    Call<LoginResponse> login(@Body UserLogin userLogin);

    @GET("show?")
    Call<User> fetchUserData(@Query("id") int id, @Header("Authorization") String header);

    @POST("createGroup")
    Call<GeneralResponse> createGroup(@Body Group group,
                                      @Header("Authorization") String header);

    @POST("request/{groupId}/{id}")
    Call<GeneralResponse> requestJoinGroup(@Path("groupId") int groupId,
                                           @Path("id") int userId);

    @GET("requests/{groupId}")
    Call<List<JoinGroupResponse>> getAllResponsesForGroup(@Path("groupId") int groupId);

    @GET("add/{groupId}/{userID}/current_user_id?")
    Call<GeneralResponse> addGroupMember(@Path("groupId") int gpID,
                                         @Path("userID") int userID,
                                         @Query("current_user_id") int adminID);
}


