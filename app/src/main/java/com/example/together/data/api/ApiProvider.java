package com.example.together.data.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.model.Group;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserLogin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.together.data.Urls.SIGN_UP_STRING;
import static com.example.together.utils.HelperClass.TAG;

public class ApiProvider {

    LoginResponse loginResponse;
    private APIInterface apiInterface;

    ApiProvider() {
        Log.i(TAG, "ApiProvider -- cons() ");
        // logs request and response information.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SIGN_UP_STRING)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(APIInterface.class);
    }

    /**
     * send userdata to database through calling api sing up
     * @param user user body that will send to db
     * @return response contain whether sign up failed or success
     */
    MutableLiveData<String> signUp(User user) {
        MutableLiveData<String> resSignUp = new MutableLiveData<>();
//        Log.i(TAG, "ApiProvider -- signUp() user >> " + user.toString());

        Call<GeneralResponse> signUpCall = apiInterface.signUp(user);

        signUpCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "ApiProvider -- signUp() enqueue()  body >> " +
                        res.body());
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
                Log.i(TAG, "ApiProvider -- login() onResponse() resBody >> "
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
     *
     * @param id
     * @return
     */
    MutableLiveData<User> fetchUserData(int id) {
        MutableLiveData<User> userData = new MutableLiveData<>();

        Call<User> userCall = apiInterface.fetchUserData(id);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i(TAG, "ApiProvider -- onResponse: fetchUserData() body >> " + response.body());

                userData.setValue( response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                call.cancel();

            }
        });

        return userData;

    }

    /**
     *
     * @param group group contains details of group as body in request api
     * @return {@link GeneralResponse} response that contain whether call success of failed
     * or if something wrong happened
     */
    MutableLiveData<GeneralResponse> createGroup(Group group) {
        MutableLiveData<GeneralResponse> addGroupRes = new MutableLiveData<>();
        Log.i(TAG, "ApiProvider -- createGroup() " + group);
        Call<GeneralResponse> addGroupCall = apiInterface.createGroup(group);
        addGroupCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> res) {
                Log.i(TAG, "ApiProvider -- signUp() enqueue()  body >> " +
                        res.body());
                addGroupRes.setValue(res.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response =  t.getMessage() ;
                addGroupRes.setValue(generalRes);
                t.printStackTrace();
            }
        });

        return addGroupRes;
    }

}
