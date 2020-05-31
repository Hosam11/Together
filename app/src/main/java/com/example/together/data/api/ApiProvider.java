package com.example.together.data.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.JoinGroupResponse;
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
import static com.example.together.utils.HelperClass.TAG;

class ApiProvider {

    LoginResponse loginResponse;
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
                HelperClass.BEARER_HEADER + token);

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

        Call<GeneralResponse> addGroupCall = apiInterface.createGroup(group, HelperClass.BEARER_HEADER + header);
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
}
