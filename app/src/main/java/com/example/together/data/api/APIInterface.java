package com.example.together.data.api;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.GroupOfTasks;
import com.example.together.data.model.ListTask;
import com.example.together.data.model.User;

import java.util.ArrayList;

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




    @POST("dragAdrop")
    Call<GeneralResponse> sendPositionArrangment (@Body GroupOfTasks tasks,
                                   @Header("Authorization")String header);



}




