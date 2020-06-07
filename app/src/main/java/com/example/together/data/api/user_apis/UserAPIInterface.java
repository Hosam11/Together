package com.example.together.data.api.user_apis;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.Interests;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserInterests;
import com.example.together.data.model.UserLogin;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPIInterface {

    @POST("signup")
    Call<LoginResponse> signUp(@Body User user);

    @POST("signin")
    Call<LoginResponse> login(@Body UserLogin userLogin);

    @GET("show?")
    Call<User> fetchUserData(@Query("id") int id, @Header("Authorization") String header);

    //TODO: Moved APIS

    @POST("update/{id}")
    Call<GeneralResponse> updateUserProfile(@Path("id") int id,
                                            @Header("Authorization") String header,
                                            @Body User user);


    @POST("updateInterests/{id}")
    Call<GeneralResponse> updateUserInterests(@Path("id") int id,
                                              @Header("Authorization") String header,
                                              @Body UserInterests interests);

    @GET("home/{id}")
    Call<ArrayList<Group>> getAllUserGroups(@Path("id") int id, @Header("Authorization") String header);

    @GET("interests")
    Call<ArrayList<Interests>> getAllInterests();

    @GET("show/{groupid}")
    Call<Group> getSpecificGroupDetails(@Path("groupid") int groupid,
                                               @Header("Authorization") String header);

    @GET("remove/{groupId}/{id}?")
    Call<GeneralResponse> removeMemberFromGroup(@Path("groupId") int groupId,
                                                @Path("id") int id,
                                                @Query("current_user_id") int adminId,

                                                @Header("Authorization") String header);




    @GET("leave/{groupid}/{id}")
    Call<GeneralResponse> leaveGroup(@Path("groupid") int groupId,
                                     @Path("id") int id,
                                     @Header("Authorization") String token
    );


    @GET("logout/{id}")
    Call<GeneralResponse> logout(@Path("id") int id);

}
