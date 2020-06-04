package com.example.together.data.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

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
import com.example.together.utils.HelperClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
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

    MutableLiveData<GroupDetails> getSpecificGroupDetails (int groupId, String header){
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
