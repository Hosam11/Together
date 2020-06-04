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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
                                           @Path("id") int userId,
                                           @Header("Authorization") String header);

    @GET("requests/{groupId}")
    Call<List<JoinGroupResponse>> getAllResponsesForGroup(@Path("groupId") int groupId,
                                                          @Header("Authorization") String header);

    @GET("add/{groupId}/{userID}?")
    //add/5/6?current_user_id=5
    Call<GeneralResponse> addGroupMember(@Path("groupId") int gpID,
                                         @Path("userID") int userID,
                                         @Query("current_user_id") int adminID);
    @POST("add")
    Call<GeneralResponse> addTask (@Body ListTask task,
                                   @Header("Authorization")String header);

    @GET("progress/{id}")
    Call<GeneralResponse> moveToProgressList(@Path("id") int id,
                                             @Header("Authorization")String header);
    @GET("done/{id}")
    Call<GeneralResponse> moveToDoneList(@Path("id") int id,
                                         @Header("Authorization")String header);

    @GET("do/{id}")
    Call<GeneralResponse> moveToDoList(@Path("id") int id,
                                         @Header("Authorization")String header);

    @GET("todo/{groupId}")
    Call<ArrayList<ListTask>> getToDoListTasks (@Path("groupId") int groupId,
                                                @Header("Authorization")String header);



    @GET("progresses/{groupId}")
    Call<ArrayList<ListTask>> getInProgressTasks (@Path("groupId") int groupId,
                                                @Header("Authorization")String header);

    @GET("dones/{groupId}")
    Call<ArrayList<ListTask>> getDoneTasks (@Path("groupId") int groupId,
                                                  @Header("Authorization")String header);
    @GET("deleteTask/{id}")
    Call<GeneralResponse> deleteTask(@Path("id") int id,
                                       @Header("Authorization")String header);
    @POST("updateTask/{id}")
    Call<GeneralResponse> editTask (@Path("id") int id,
                                    @Body ListTask task,
                                   @Header("Authorization")String header);

    Call<GeneralResponse> addGroupMember(@Path("groupId") int gpID,
                                         @Path("userID") int userID,
                                          @Query("current_user_id") int adminID,
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
    Call<ArrayList<Interests>> getAllInterests();

    @GET("show/{groupid}")
    Call<GroupDetails> getSpecificGroupDetails(@Path("groupid") int groupid,
                                               @Header("Authorization") String header);

    @GET("remove/{groupId}/{id}?")
    Call<GeneralResponse> removeMemberFromGroup(@Path("groupId") int groupId,
                                                @Path("id") int id,
                                                @Query("current_user_id") int adminId,

                                                @Header("Authorization") String header);



                                        
    @POST("updateGroup/{groupId}?")
    Call<GeneralResponse> updateGroupInfo(@Path("groupId") int groupId,
                                          @Query("current_user_id") int adminID,
                                          @Body Group group,
                                          @Header("Authorization") String token);

    @GET("accept/{requesID}?")
    Call<GeneralResponse> acceptJoinReqGroup(@Path("requesID") int reqID,
                                             @Query("current_user_id") int adminID,
                                             @Header("Authorization") String token);


    @GET("reject/{requesID}")
    Call<GeneralResponse> rejectJoinReqGroup(@Path("requesID") int reqID,
                                             @Header("Authorization") String token);


/////////////////////////

    @GET("leave/{groupid}/{id}")
    Call<GeneralResponse> leaveGroup(@Path("groupid") int groupId,
                                     @Path("id") int id,
                                     @Header("Authorization") String token
                                     );

    ///////

    @GET("logout/{id}")
    Call<GeneralResponse> logout(@Path("id") int id);


}




