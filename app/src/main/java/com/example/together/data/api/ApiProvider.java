package com.example.together.data.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.JoinGroupResponse;
import com.example.together.data.model.ListTask;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserLogin;
import com.example.together.utils.HelperClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.together.data.Urls.API_URL;
import static com.example.together.utils.HelperClass.BEARER_HEADER;
import static com.example.together.utils.HelperClass.TAG;

class ApiProvider {

    private LoginResponse loginResponse;
    private APIInterface apiInterface;

    /**
     * declare {@link HttpLoggingInterceptor} , {@link OkHttpClient} {@link Retrofit}
     * and instantiate the {@link #apiInterface} to calling the apis
     */
    ApiProvider() {
        Log.i(TAG, "ApiProvider -- cons() ");
        // logs request and response information.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(APIInterface.class);
    }

    /**
     * send userdata to database through calling api sing up
     *
     * @param user user body that will send to db
     * @return response contain whether sign up failed or success
     */
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

    /**
     * take user data Login and call login api if user exist response will be null
     * and id and token will be filled with data
     * otherwise response will contain response error whether the email or password not valid
     * then store result of call to liveData of {@link LoginResponse}
     * to activity can observe on it
     *
     * @param userLogin will carry email and password for user
     */
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

    /**
     * get user data
     *
     * @param id of the user you want to retrieve data for it
     * @return {@link User} object that carry data
     */
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

    }

    /**
     * @param group group contains details of group as body in request api
     * @return {@link GeneralResponse} response that contain whether call success of failed
     * or if something wrong happened
     */
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
    }

    /**
     * when user want request join to specific group
     *
     * @param gpId   group id that uer want to enter it
     * @param userId user that make the request
     * @return {@link GeneralResponse} that tell user whether Request sent successfully or something else happened
     */
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

    /**
     * show all joim request for a group
     *
     * @param groupId id for group that have request
     * @return list of all requests {@link JoinGroupResponse}
     */
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

    /**
     * adding user to  group - the admin of group only can call this function
     *
     * @param gpID    groupID that user want to enter it
     * @param userID  the user who want to enter
     * @param adminID current user that wil accept request of user
     * @param header  token that sended in the header of request
     * @return {@link GeneralResponse} that tell us whether request fails of success
     */
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

    /**
     * update group info
     *
     * @param gpID    group id that you want to edit it
     * @param adminID admin will be edith the group
     * @param group   {@link Group} object that carry data
     * @param token   used in header of request as authoritarian
     * @return {@link GeneralResponse} response whether that update request  fail or success
     */
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


    /**
     * accept join to exists group
     * @param reqID request id for request for joining the group
     * @param adminID id for admin tha will accept the request
     * @param token used in header of request as authoritarian
     * @return {@link GeneralResponse} response whether that request fail or success
     */
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

    /**
     * reject join to exists group
     * @param reqID reqID request id for request for joining the group
     * @param token used in header of request as authoritarian
     * @return {@link GeneralResponse} response whether that request fail or success
     */
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
    
    // FixMe ---------------------

    
    /**
     * @param userId update the user data without interests
     * @param user update the user data without interests
     * @return {@link GeneralResponse} response that contain whether call success of failed
     * or if something wrong happened
     */

    MutableLiveData<GeneralResponse> updateUserProfile (int userId, String header,User user){

        MutableLiveData<GeneralResponse> updateResponse = new MutableLiveData<>();
        Call<GeneralResponse> updateProfileCall=apiInterface.updateUserProfile(userId,HelperClass.BEARER_HEADER+header,user);
        updateProfileCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                updateResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                updateResponse.setValue(generalRes);
                t.printStackTrace();
                call.cancel();

            }
        });
        return updateResponse;


    }

    /**
     * @param userId update the user's interests
     * @param interests update the user's interests
     * @return {@link GeneralResponse} response that contain whether call success of failed
     * or if something wrong happened
     */

    MutableLiveData<GeneralResponse> updateUserInterests (int userId,String header, UserInterests interests){

        MutableLiveData<GeneralResponse> updateInterestsResponse = new MutableLiveData<>();
        Call<GeneralResponse> updateInterestCall=apiInterface.updateUserInterests(userId,header,interests);
        updateInterestCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                Log.i(TAG, "ApiProvider -- updateInterests() enqueue()  body >> " +
                        response.body());
                updateInterestsResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                updateInterestsResponse.setValue(generalRes);
                t.printStackTrace();
                call.cancel();

            }
        });
        return updateInterestsResponse;





    }
    
    /**
     * when user want request join to specific group
     *
     * @param gpId   group id that uer want to enter it
     * @param userId user that make the request
     * @return {@link GeneralResponse} that tell user whether Request sent successfully or something else happened
     */
    MutableLiveData<GeneralResponse> requestJoinGroup(int gpId, int userId) {
        MutableLiveData<GeneralResponse> resJoinGroup = new MutableLiveData<>();
        Call<GeneralResponse> joinGp = apiInterface.requestJoinGroup(gpId, userId);
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

    /**
     * show all joim request for a group
     *
     * @param groupId id for group that have request
     * @return list of all requests {@link JoinGroupResponse}
     */
    MutableLiveData<List<JoinGroupResponse>> getAllResponsesForGroup(int groupId) {
        MutableLiveData<List<JoinGroupResponse>> groupResList = new MutableLiveData<>();

        Call<List<JoinGroupResponse>> getGroupResponses = apiInterface.getAllResponsesForGroup(groupId);
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

    MutableLiveData<GeneralResponse> addGroupMember(int gpID, int userID, int adminID) {
        MutableLiveData<GeneralResponse> addMemberRes = new MutableLiveData<>();

        Call<GeneralResponse> addMemberCall = apiInterface.addGroupMember(gpID, userID, adminID);
        addMemberCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "ApiProvider -- requestJoinGroup() enqueue()  body >> " +
                        res.body());
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


    MutableLiveData<ArrayList<UserGroup>> getAllUserGroups(int userId, String header){
        MutableLiveData<ArrayList<UserGroup>> groupsRes=new MutableLiveData<>();

        Call<ArrayList<UserGroup>> getAllUserGroupsCall=apiInterface.getAllUserGroups(userId,HelperClass.BEARER_HEADER+header);

        getAllUserGroupsCall.enqueue(new Callback<ArrayList<UserGroup>>() {
            @Override
            public void onResponse(Call<ArrayList<UserGroup>> call, Response<ArrayList<UserGroup>> response) {
                groupsRes.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<UserGroup>> call, Throwable t) {

                t.printStackTrace();
                call.cancel();

            }
        });

        return  groupsRes;

    }
    MutableLiveData<ArrayList<Interests>> getAllInterests(String header){
        MutableLiveData<ArrayList<Interests>> allInterests=new MutableLiveData<>();
        Call<ArrayList<Interests>> getAllInterestsCall=apiInterface.getAllInterests(HelperClass.BEARER_HEADER+header);
        getAllInterestsCall.enqueue(new Callback<ArrayList<Interests>>() {
            @Override
            public void onResponse(Call<ArrayList<Interests>> call, Response<ArrayList<Interests>> response) {
                allInterests.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Interests>> call, Throwable t) {
                t.printStackTrace();
                call.cancel();

            }
        });



        return allInterests;

    }

    MutableLiveData<GroupDetails> getSpecificGroupDetails (int groupId,String header){
        MutableLiveData<GroupDetails> groupMutableLiveData=new MutableLiveData<>();
        Call<GroupDetails> groupCall=apiInterface.getSpecificGroupDetails(groupId,HelperClass.BEARER_HEADER+header);
        groupCall.enqueue(new Callback<GroupDetails>() {
            @Override
            public void onResponse(Call<GroupDetails> call, Response<GroupDetails> response) {
                groupMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GroupDetails> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });


        return groupMutableLiveData;
    }

    MutableLiveData<GeneralResponse> removeMemberFromGroup(int groupId,int id,int adminId,String header){
        MutableLiveData<GeneralResponse> removeMemberResponse=new MutableLiveData<>();

        Call<GeneralResponse> removeMemberCall=apiInterface.removeMemberFromGroup(groupId,id,adminId,HelperClass.BEARER_HEADER+header);
        removeMemberCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                removeMemberResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                removeMemberResponse.setValue(generalRes);
                t.printStackTrace();
                call.cancel();

            }
        });
        return  removeMemberResponse;



    }


}
