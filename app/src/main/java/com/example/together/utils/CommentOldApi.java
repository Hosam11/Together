package com.example.together.utils;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.JoinGroupResponse;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;

public class CommentOldApi {

    /*  *//**
     * @param group group contains details of group as body in request api
     * @return {@link GeneralResponse} response that contain whether call success of failed
     * or if something wrong happened
     *//*
    MutableLiveData<GeneralResponse> createGroup(Group group, String header) {
        MutableLiveData<GeneralResponse> addGroupRes = new MutableLiveData<>();
        Log.i(TAG, "ApiProvider -- createGroup() " + group);

        Call<GeneralResponse> addGroupCall = apiInterface.createGroup(group,
                BEARER_HEADER + header);
        addGroupCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "ApiProvider -- createGroup() enqueue()  body >> " +
                        res.body());
                addGroupRes.setValue(res.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                addGroupRes.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });

        return addGroupRes;
    }*/

    /*  *//**
     * when user want request join to specific group
     *
     * @param gpId   group id that uer want to enter it
     * @param userId user that make the request
     * @return {@link GeneralResponse} that tell user whether Request sent successfully or something else happened
     *//*
    MutableLiveData<GeneralResponse> requestJoinGroup(int gpId, int userId, String header) {
        MutableLiveData<GeneralResponse> resJoinGroup = new MutableLiveData<>();
        Call<GeneralResponse> joinGp = apiInterface.requestJoinGroup(gpId, userId,
                BEARER_HEADER + header);
        joinGp.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "ApiProvider -- requestJoinGroup() enqueue()  body >> " +
                        res.body());
                resJoinGroup.setValue(res.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                resJoinGroup.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });
        return resJoinGroup;
    }

    *//**
     * show all joim request for a group
     *
     * @param groupId id for group that have request
     * @return list of all requests {@link JoinGroupResponse}
     *//*
    MutableLiveData<List<JoinGroupResponse>> getAllResponsesForGroup(int groupId, String header) {
        MutableLiveData<List<JoinGroupResponse>> groupResList = new MutableLiveData<>();

        Call<List<JoinGroupResponse>> getGroupResponses = apiInterface.getAllResponsesForGroup(groupId,
                BEARER_HEADER + header);
        getGroupResponses.enqueue(new Callback<List<JoinGroupResponse>>() {
            @Override
            public void onResponse(Call<List<JoinGroupResponse>> call,
                                   Response<List<JoinGroupResponse>> res) {

                Log.i(TAG, "ApiProvider  -- getAllResponsesForGroup() enqueue() a reqSize >> "
                        + res.body().size());
                groupResList.setValue(res.body());
            }

            @Override
            public void onFailure(Call<List<JoinGroupResponse>> call, Throwable t) {
                t.printStackTrace();
                Log.i(TAG, "onFailure: " + t.getMessage());
                call.cancel();
            }
        });

        return groupResList;
    }

    *//**
     * adding user to  group - the admin of group only can call this function
     *
     * @param gpID    groupID that user want to enter it
     * @param userID  the user who want to enter
     * @param adminID current user that wil accept request of user
     * @param header  token that sended in the header of request
     * @return {@link GeneralResponse} that tell us whether request fails of success
     *//*
    MutableLiveData<GeneralResponse> addGroupMember(int gpID, int userID, int adminID, String header) {
        MutableLiveData<GeneralResponse> addMemberRes = new MutableLiveData<>();

        Call<GeneralResponse> addMemberCall = apiInterface.addGroupMember(gpID, userID,
                adminID, BEARER_HEADER + header);
        addMemberCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "ApiProvider -- requestJoinGroup() enqueue()  body >> " +
                        res.body().response);
                addMemberRes.setValue(res.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                addMemberRes.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });

        return addMemberRes;
    }

    *//**
     * update group info
     *
     * @param gpID    group id that you want to edit it
     * @param adminID admin will be edith the group
     * @param group   {@link Group} object that carry data
     * @param token   used in header of request as authoritarian
     * @return {@link GeneralResponse} response whether that update request  fail or success
     *//*
    MutableLiveData<GeneralResponse> updateGroupInfo(int gpID, int adminID, Group group, String token) {
        MutableLiveData<GeneralResponse> resUpdateGroup = new MutableLiveData<>();
        Call<GeneralResponse> callUpdateGroup = apiInterface.updateGroupInfo(gpID, adminID,
                group, BEARER_HEADER + token);

        callUpdateGroup.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "ApiProvider  -- addGroupMember() enqueue() a body >> "
                        + res.body());
                resUpdateGroup.setValue(res.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                resUpdateGroup.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });

        return resUpdateGroup;
    }


    *//**
     * accept join to exists group
     * @param reqID request id for request for joining the group
     * @param adminID id for admin tha will accept the request
     * @param token used in header of request as authoritarian
     * @return {@link GeneralResponse} response whether that request fail or success
     *//*
    MutableLiveData<GeneralResponse> acceptJoinReqGroup(int reqID, int adminID, String token) {
        MutableLiveData<GeneralResponse> resUJoinReqGroup = new MutableLiveData<>();
        Call<GeneralResponse> callAcceptJoinReqGorup = apiInterface.acceptJoinReqGroup(reqID, adminID,
                BEARER_HEADER + token);
        callAcceptJoinReqGorup.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "ApiProvider  -- addGroupMember() enqueue() a body >> "
                        + res.body());
                resUJoinReqGroup.setValue(res.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                resUJoinReqGroup.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });


        return resUJoinReqGroup;
    }

    *//**
     * reject join to exists group
     * @param reqID reqID request id for request for joining the group
     * @param token used in header of request as authoritarian
     * @return {@link GeneralResponse} response whether that request fail or success
     *//*
    MutableLiveData<GeneralResponse> rejectJoinReqGroup(int reqID, String token){
        MutableLiveData<GeneralResponse> rejectUJoinReqGroup = new MutableLiveData<>();
        Call<GeneralResponse> callRejectJoinReqGorup = apiInterface.rejectJoinReqGroup(reqID,
                BEARER_HEADER + token);
        callRejectJoinReqGorup.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "ApiProvider  -- addGroupMember() enqueue() a body >> "
                        + res.body());
                rejectUJoinReqGroup.setValue(res.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                rejectUJoinReqGroup.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });


        return rejectUJoinReqGroup;
    }
    */

/*
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
                                         @Header("Authorization") String header);*/

    /*   *//**
     * send userdata to database through calling api sing up
     *
     * @param user user body that will send to db
     * @return response contain whether sign up failed or success
     *//*
    MutableLiveData<String> signUp(User user) {

        MutableLiveData<String> resSignUp = new MutableLiveData<>();

        Call<GeneralResponse> signUpCall = apiInterface.signUp(user);

        signUpCall.enqueue(new Callback<GeneralResponse>() {

            @Override
            public void onResponse(Call<GeneralResponse> call,
                                   Response<GeneralResponse> res) {
                Log.i(TAG, "ApiProvider -- signUp() enqueue()  body >> " +
                        res.body().response);

                resSignUp.setValue(res.body().response);
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Log.i(TAG, "ApiProvider -- onFailure: " + t.getMessage());
                t.printStackTrace();
                call.cancel();
            }
        });
        return resSignUp;
    }

    *//**
     * take user data Login and call login api if user exist response will be null
     * and id and token will be filled with data
     * otherwise response will contain response error whether the email or password not valid
     * then store result of call to liveData of {@link LoginResponse}
     * to activity can observe on it
     *
     * @param userLogin will carry email and password for user
     *//*
    MutableLiveData<LoginResponse> login(UserLogin userLogin) {
        Log.i(TAG, "ApiProvider -- login() ");
        MutableLiveData<LoginResponse> resLoginLiveData = new MutableLiveData<>();

        Call<LoginResponse> loginCall = apiInterface.login(userLogin);

        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> res) {
                Log.i(TAG, "ApiProvider -- login() enqueue() onResponse() resBody >> "
                        + res.body());

                loginResponse = res.body();
                loginResponse.setSuccess(res.body().getResponse() == null);
                resLoginLiveData.setValue(loginResponse);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // here server is down
                loginResponse = new LoginResponse();
                loginResponse.setConFailed(true);
                resLoginLiveData.setValue(loginResponse);
                t.printStackTrace();
                call.cancel();
            }
        });

        return resLoginLiveData;

    }

    *//**
     * get user data
     *
     * @param id of the user you want to retrieve data for it
     * @return {@link User} object that carry data
     *//*
    MutableLiveData<User> fetchUserData(int id, String token) {
        MutableLiveData<User> userData = new MutableLiveData<>();

        Call<User> userCall = apiInterface.fetchUserData(id,
                BEARER_HEADER + token);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i(TAG, "ApiProvider -- onResponse: fetchUserData() body >> "
                        + response.body());

                userData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onFailure: errMsg " + t.getMessage());
                call.cancel();

            }
        });

        return userData;

    }*/

    /*    @POST("signup")
    Call<GeneralResponse> signUp(@Body User user);

    @POST("signin")
    Call<LoginResponse> login(@Body UserLogin userLogin);

    @GET("show?")
    Call<User> fetchUserData(@Query("id") int id, @Header("Authorization") String header);*/

    /*    public MutableLiveData<GeneralResponse> createGroup(Group group, String token) {
        return provider.createGroup(group,token);
    }

    public MutableLiveData<GeneralResponse> joinGroup(int gpId, int userID,  String token) {
        return provider.requestJoinGroup(gpId, userID, token);
    }

    public MutableLiveData<List<JoinGroupResponse>> getAllResponsesForGroup(int gpId, String token) {
        return provider.getAllResponsesForGroup(gpId,token);
    }

    public MutableLiveData<GeneralResponse> addGroupMember(int gpID, int userID, int adminID, String token) {
        return provider.addGroupMember(gpID, userID, adminID,token);
    }

    public MutableLiveData<GeneralResponse> updateGroupInfo(int gpID, int adminID, Group group, String token) {
        return provider.updateGroupInfo(gpID, adminID, group, token);
    }

   public MutableLiveData<GeneralResponse> rejectJoinReqGroup(int reqID, String token) {
        return provider.rejectJoinReqGroup(reqID,  token);
    }

    public MutableLiveData<GeneralResponse> acceptJoinReqGroup(int reqID, int adminID, String token) {

        return provider.acceptJoinReqGroup(reqID, adminID, token);
    }*/


    /* *//**
     * @param user: user that will added to database
     * @return the response message that contain whether the sing up fail or success
     *//*
    public MutableLiveData<String> signUp(User user) {

        return userRepo.signUp(user);
    }

    public MutableLiveData<LoginResponse> login(UserLogin userLogin) {
        loginRes = userRepo.login(userLogin);
        return loginRes;
    }

    public MutableLiveData<User> fetchUserData(int id, String token) {
        userData = userRepo.fetchUserData(id, token);
        return userData;
    }*/
}
