package com.example.together.data.api.group_apis;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.JoinGroupResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupAPIInterface {

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


    Call<GeneralResponse> addGroupMember(@Path("groupId") int gpID,
                                         @Path("userID") int userID,
                                         @Query("current_user_id") int adminID,
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

}
