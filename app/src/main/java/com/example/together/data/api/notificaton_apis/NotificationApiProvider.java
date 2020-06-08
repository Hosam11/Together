package com.example.together.data.api.notificaton_apis;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.api.APIInterface;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.Notification;
import com.example.together.data.model.User;
import com.example.together.utils.HelperClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

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

class NotificationApiProvider {

    private LoginResponse loginResponse;
    private NotificationAPIInterface apiInterface;

    /**
     * declare {@link HttpLoggingInterceptor} , {@link OkHttpClient} {@link Retrofit}
     * and instantiate the {@link #apiInterface} to calling the apis
     */
    NotificationApiProvider() {
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

        apiInterface = retrofit.create(NotificationAPIInterface.class);
    }

    MutableLiveData<NotificationResponse> getUserNotification(int id, int page, String token) {
        MutableLiveData<NotificationResponse> notificationsResponse = new MutableLiveData<>();

        Call<NotificationResponse> notificationsCall = apiInterface.getUserNotification(id, page,
                BEARER_HEADER + token);
        notificationsCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                notificationsResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

                notificationsResponse.setValue(null);
                call.cancel();
            }


        });


        return notificationsResponse;


    }


    MutableLiveData<GeneralResponse> enableNotification(int userId, String token){

        MutableLiveData<GeneralResponse> enableRes=new MutableLiveData<>();
        Call<GeneralResponse> enableCall=apiInterface.enableNotification(userId, HelperClass.BEARER_HEADER+token);
        enableCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                enableRes.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
enableRes.setValue(null);
                call.cancel();

            }
        });


        return enableRes;
    }
    MutableLiveData<GeneralResponse> disableNotification(int userId, String token){

        MutableLiveData<GeneralResponse> disableRes=new MutableLiveData<>();
        Call<GeneralResponse> disableCall=apiInterface.disableNotification(userId, BEARER_HEADER+ token);
        disableCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                disableRes.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                disableRes.setValue(null);
                call.cancel();


            }
        });

return  disableRes;

    }


    MutableLiveData<GeneralResponse> deleteNotification (int id, String token){
        MutableLiveData<GeneralResponse> deleteRes=new MutableLiveData<>();
        Call<GeneralResponse> deleteCall=apiInterface.deleteNotification(id, HelperClass.BEARER_HEADER+token);
        deleteCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                deleteRes.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                deleteRes.setValue(null);
                call.cancel();


            }
        });

return deleteRes;


    }





}
