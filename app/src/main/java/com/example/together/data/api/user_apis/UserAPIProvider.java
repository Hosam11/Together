package com.example.together.data.api.user_apis;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.api.APIInterface;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.LoginResponse;
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

import static com.example.together.data.Urls.API_URL;
import static com.example.together.utils.HelperClass.BEARER_HEADER;
import static com.example.together.utils.HelperClass.TAG;

public class UserAPIProvider {

    private LoginResponse loginResponse;
    private UserAPIInterface userInterface;

    public UserAPIProvider() {
        Log.i(TAG, "UserUserAPIProvider -- cons() ");
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

        userInterface = retrofit.create(UserAPIInterface.class);
    }

    /**
     * send userdata to database through calling api sing up
     *
     * @param user user body that will send to db
     * @return response contain whether sign up failed or success
     */
    MutableLiveData<LoginResponse> signUp(User user) {

        MutableLiveData<LoginResponse> resSignUp = new MutableLiveData<>();

        Call<LoginResponse> signUpCall = userInterface.signUp(user);

        signUpCall.enqueue(new Callback<LoginResponse>() {


            @Override
            public void onResponse(Call<LoginResponse> call,
                                   Response<LoginResponse> res) {
                Log.i(TAG, "UserAPIProvider -- signUp() enqueue()  body >> " +
                        res.body());

                loginResponse = res.body();
                // coz if response is mean will object will carry token and id only
                loginResponse.setSuccess(res.body().getResponse() == null);
                resSignUp.setValue(loginResponse);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i(TAG, "UserAPIProvider -- onFailure: " + t.getMessage());
                // here server is down
                loginResponse = new LoginResponse();
                loginResponse.setConFailed(true);
                resSignUp.setValue(loginResponse);
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
        Log.i(TAG, "UserAPIProvider -- login() ");
        MutableLiveData<LoginResponse> resLoginLiveData = new MutableLiveData<>();

        Call<LoginResponse> loginCall = userInterface.login(userLogin);

        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> res) {
                Log.i(TAG, "UserAPIProvider -- login() enqueue() onResponse() resBody >> "
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

        Call<User> userCall = userInterface.fetchUserData(id,
                BEARER_HEADER + token);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i(TAG, "UserAPIProvider -- onResponse: fetchUserData() body >> "
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
}
