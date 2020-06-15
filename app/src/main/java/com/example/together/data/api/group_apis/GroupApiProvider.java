package com.example.together.data.api.group_apis;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.model.ChatResponse;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.JoinGroupResponse;
import com.example.together.data.model.MessageId;
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

public class GroupApiProvider {

    private GroupAPIInterface groupAPIInterface;

    /**
     * declare {@link HttpLoggingInterceptor} , {@link OkHttpClient} {@link Retrofit}
     * and instantiate the {@link #groupAPIInterface} to calling the apis
     */
    GroupApiProvider() {
        Log.i(TAG, "GroupApiProvider -- cons() ");
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

        groupAPIInterface = retrofit.create(GroupAPIInterface.class);
    }

    /**
     * @param group group contains details of group as body in request api
     * @return {@link GeneralResponse} response that contain whether call success of failed
     * or if something wrong happened
     */
    MutableLiveData<GeneralResponse> createGroup(Group group, String header) {

        if (group.getImage() != null) {
            Log.i(TAG, getClass().getSimpleName() + "createGroup: chars imgLeng is >>  " + group.getImage().length());
        }
        MutableLiveData<GeneralResponse> addGroupRes = new MutableLiveData<>();
        Log.i(TAG, "GroupApiProvider -- createGroup() " + group);

        Call<GeneralResponse> addGroupCall = groupAPIInterface.createGroup(group,
                BEARER_HEADER + header);
        addGroupCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {

                Log.i(TAG, "GroupApiProvider -- createGroup() enqueue()  body >> " +
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
     * @return {@link GeneralResponse} that tell user whether Request sent successfully or
     * group is full
     */
    MutableLiveData<GeneralResponse> requestJoinGroup(int gpId, int userId, String header) {
        MutableLiveData<GeneralResponse> resJoinGroup = new MutableLiveData<>();
        Call<GeneralResponse> joinGp = groupAPIInterface.requestJoinGroup(gpId, userId,
                BEARER_HEADER + header);
        joinGp.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "GroupApiProvider -- requestJoinGroup() enqueue()  body >> " +
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
     * show all join request for a group
     *
     * @param groupId id for group that have request
     * @return list of all requests {@link JoinGroupResponse}
     */
    MutableLiveData<List<JoinGroupResponse>> getAllResponsesForGroup(int groupId, String header) {
        MutableLiveData<List<JoinGroupResponse>> groupResList = new MutableLiveData<>();

        Call<List<JoinGroupResponse>> getGroupResponses = groupAPIInterface.getAllResponsesForGroup(groupId,
                BEARER_HEADER + header);
        getGroupResponses.enqueue(new Callback<List<JoinGroupResponse>>() {
            @Override
            public void onResponse(Call<List<JoinGroupResponse>> call,
                                   Response<List<JoinGroupResponse>> res) {

                Log.i("aaa", "GroupApiProvider  -- getAllResponsesForGroup() enqueue() a reqSize >> "
                        + res.body().size());
                groupResList.setValue(res.body());
            }

            @Override
            public void onFailure(Call<List<JoinGroupResponse>> call, Throwable t) {
                t.printStackTrace();
                Log.i("aaa", "onFailure: " + t.getMessage());
                call.cancel();
            }
        });

        return groupResList;
    }

    /**
     * adding user to group - the admin of group only can call this function
     *
     * @param gpID    groupID that user want to enter it
     * @param userID  the user who want to enter
     * @param adminID current user that wil accept request of user
     * @param header  token that sended in the header of request
     * @return {@link GeneralResponse} that tell us whether request fails of success
     */
    MutableLiveData<GeneralResponse> addGroupMember(int gpID, int userID, int adminID, String header) {
        MutableLiveData<GeneralResponse> addMemberRes = new MutableLiveData<>();

        Call<GeneralResponse> addMemberCall = groupAPIInterface.addGroupMember(gpID, userID,
                adminID, BEARER_HEADER + header);
        addMemberCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "GroupApiProvider -- requestJoinGroup() enqueue()  body >> " +
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
    MutableLiveData<GeneralResponse> updateGroupInfo(int gpID, int adminID,
                                                     Group group, String token) {
        MutableLiveData<GeneralResponse> resUpdateGroup = new MutableLiveData<>();
        Call<GeneralResponse> callUpdateGroup = groupAPIInterface.updateGroupInfo(gpID, adminID,
                group, BEARER_HEADER + token);

        Log.i(TAG, "GroupApiProvider  -- updateGroupInfo() a group >> " + group);

        callUpdateGroup.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "GroupApiProvider  -- updateGroupInfo() enqueue() a body >> "
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
     *
     * @param reqID   request id for request for joining the group
     * @param adminID id for admin tha will accept the request
     * @param token   used in header of request as authoritarian
     * @return {@link GeneralResponse} response whether that request fail or success
     */
    MutableLiveData<GeneralResponse> acceptJoinReqGroup(int reqID, int adminID, String token) {
        MutableLiveData<GeneralResponse> resUJoinReqGroup = new MutableLiveData<>();
        Call<GeneralResponse> callAcceptJoinReqGorup = groupAPIInterface.acceptJoinReqGroup(reqID, adminID,
                BEARER_HEADER + token);
        callAcceptJoinReqGorup.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "GroupApiProvider  -- addGroupMember() enqueue() a body >> "
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
     *
     * @param reqID reqID request id for request for joining the group
     * @param token used in header of request as authoritarian
     * @return {@link GeneralResponse} response whether that request fail or success
     */
    MutableLiveData<GeneralResponse> rejectJoinReqGroup(int reqID, String token) {
        MutableLiveData<GeneralResponse> rejectUJoinReqGroup = new MutableLiveData<>();

        Call<GeneralResponse> callRejectJoinReqGorup = groupAPIInterface.rejectJoinReqGroup(reqID,
                BEARER_HEADER + token);
        callRejectJoinReqGorup.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "GroupApiProvider  -- addGroupMember() enqueue() a body >> "
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

    /**
     * check whether user in the group or not or waitting from admin to accept invitation method
     *
     * @param gpID   group id that user make request on it
     * @param userID user that will make request with him
     * @param token  used in header of request as authoritarian
     * @return {@link GeneralResponse} reponse tell us status
     */
    MutableLiveData<GeneralResponse> userRequestJoinStatus(int gpID, int userID, String token) {
        MutableLiveData<GeneralResponse> reqJoinStatus = new MutableLiveData<>();

        Call<GeneralResponse> reqJoinCall = groupAPIInterface.userRequestJoinStatus(gpID, userID,
                BEARER_HEADER + token);
        reqJoinCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "GroupApiProvider  -- userRequestJoinStatus() enqueue() a body >> "
                        + res.body());
                reqJoinStatus.setValue(res.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                reqJoinStatus.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });
        return reqJoinStatus;
    }

    /**
     * get all Messages for chat of certian group
     *
     * @param gpID group id that you want to get chat of it
     * @return {@link ChatResponse} that carry details of each message
     */
    MutableLiveData<ChatResponse> getChatMessages(int gpID, String token) {
        MutableLiveData<ChatResponse> messagesRes = new MutableLiveData<>();

        Call<ChatResponse> chatCall = groupAPIInterface.getChatMessages(gpID,
                BEARER_HEADER + token);

        chatCall.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> res) {
                Log.i(TAG, "GroupApiProvider  -- getChatMessages() enqueue() a body.size() >> "
                        + res.body().getChatMsgList().size());
                messagesRes.setValue(res.body());
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                t.printStackTrace();
                ChatResponse chatResponse=new ChatResponse();
                chatResponse.setServerDown(true);
                messagesRes.setValue(chatResponse);
                Log.i(TAG, "GroupApiProvider --  getChatMessages() onFailure: " +
                        t.getMessage());
                call.cancel();

            }
        });
        return messagesRes;
    }

    MutableLiveData<GeneralResponse> deleteChatMsg(MessageId msgID, int adminID, String token) {
        MutableLiveData<GeneralResponse> deleteMsgData = new MutableLiveData<>();

        Call<GeneralResponse> callDeleteMsg = groupAPIInterface.deleteChatMsg(msgID, adminID,
                BEARER_HEADER + token);
        callDeleteMsg.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "GroupApiProvider  -- deleteChatMsg() enqueue() a body >> "
                        + res.body());
                deleteMsgData.setValue(res.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                deleteMsgData.setValue(generalRes);
                Log.i(TAG, "GroupApiProvider -- deleteChatMsg() onFailure: ");
                t.printStackTrace();
                call.cancel();
            }
        });

        return deleteMsgData;
    }
}

