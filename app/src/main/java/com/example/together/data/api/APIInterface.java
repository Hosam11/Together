package com.example.together.data.api;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.GroupDetails;
import com.example.together.data.model.Interests;
import com.example.together.data.model.JoinGroupResponse;
import com.example.together.data.model.ListTask;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserGroup;
import com.example.together.data.model.UserInterests;
import com.example.together.data.model.UserLogin;

import java.util.ArrayList;
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



    @POST("add")
    Call<GeneralResponse> addTask(@Body ListTask task,
                                  @Header("Authorization") String header);

    @GET("progress/{id}")
    Call<GeneralResponse> moveToProgressList(@Path("id") int id,
                                             @Header("Authorization") String header);

    @GET("done/{id}")
    Call<GeneralResponse> moveToDoneList(@Path("id") int id,
                                         @Header("Authorization") String header);

    @GET("do/{id}")
    Call<GeneralResponse> moveToDoList(@Path("id") int id,
                                       @Header("Authorization") String header);

    @GET("todo/{groupId}")
    Call<ArrayList<ListTask>> getToDoListTasks(@Path("groupId") int groupId,
                                               @Header("Authorization") String header);


    @GET("progresses/{groupId}")
    Call<ArrayList<ListTask>> getInProgressTasks(@Path("groupId") int groupId,
                                                 @Header("Authorization") String header);

    @GET("dones/{groupId}")
    Call<ArrayList<ListTask>> getDoneTasks(@Path("groupId") int groupId,
                                           @Header("Authorization") String header);

    @GET("deleteTask/{id}")
    Call<GeneralResponse> deleteTask(@Path("id") int id,
                                     @Header("Authorization") String header);

    @POST("updateTask/{id}")
    Call<GeneralResponse> editTask(@Path("id") int id,
                                   @Body ListTask task,
                                   @Header("Authorization") String header);


    @POST("update/{id}")
    Call<GeneralResponse> updateUserProfile(@Path("id") int id,
                                            @Header("Authorization") String header,
                                            @Body User user);

    @POST("updateInterests/{id}")
    Call<GeneralResponse> updateUserInterests(@Path("id") int id,
                                              @Header("Authorization") String header,
                                              @Body UserInterests interests);


    @GET("home/{id}")
    Call<ArrayList<UserGroup>> getAllUserGroups(@Path("id") int id, @Header("Authorization") String header);

    @GET("interests")
    Call<ArrayList<Interests>> getAllInterests(@Header("Authorization") String header);

    @GET("show/{groupid}")
    Call<GroupDetails> getSpecificGroupDetails(@Path("groupid") int groupid,
                                               @Header("Authorization") String header);

    @GET("remove/{groupId}/{id}?")
    Call<GeneralResponse> removeMemberFromGroup(@Path("groupId") int groupId,
                                                @Path("id") int id,
                                                @Query("current_user_id") int adminId,

                                                @Header("Authorization") String header);






}




