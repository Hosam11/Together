package com.example.together.data.api;

import android.util.Log;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.together.data.Urls.API_URL;
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
     * @param user   update the user data without interests
     * @return {@link GeneralResponse} response that contain whether call success of failed
     * or if something wrong happened
     */




}
