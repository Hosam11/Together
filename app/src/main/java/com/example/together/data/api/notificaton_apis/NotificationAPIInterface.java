package com.example.together.data.api.notificaton_apis;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.GroupOfTasks;
import com.example.together.data.model.ListTask;
import com.example.together.data.model.Notification;
import com.example.together.data.model.User;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface NotificationAPIInterface {

   // http://127.0.0.1:8000/api/
  //  http://192.168.1.4:8000/api/users/1/notifications?page=1

    @GET("users/{userId}/notifications?")
    Call<NotificationResponse> getUserNotification(@Path("userId") int userId,
                                                   @Query("page") int page,
                                                   @Header("Authorization") String header);
    @PUT("users/{userId}/enable")
    Call<GeneralResponse> enableNotification(@Path("userId") int userId,
                                                   @Header("Authorization") String header);


    @PUT("users/{userId}/disable")
    Call<GeneralResponse> disableNotification(@Path("userId") int userId,
                                                  @Header("Authorization") String header);

    @DELETE("notifications/{id}")
    Call<GeneralResponse> deleteNotification(@Path("id") int id,
                                              @Header("Authorization") String header);


}




