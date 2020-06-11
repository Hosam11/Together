package com.example.together.data.api.explore_apis;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.example.together.data.model.Group;
import com.example.together.data.model.Interest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
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

public class ExploreAPIProvider {
    private ExploreAPIInterface exploreAPIInterface;
    private static ExploreAPIProvider instance;
    private ExploreAPIProvider() {

        Log.i(TAG, "EXPLORE APIProvider ");
        // logs request and response information.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();
        //json converter
        Gson gson = new GsonBuilder().setLenient().create();
        //retrofit client instance building
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        exploreAPIInterface = retrofit.create(ExploreAPIInterface.class);
    }
    public static ExploreAPIProvider getInstance(){
        if(instance == null){
            instance = new ExploreAPIProvider();
        }
        return instance;
    }

    MutableLiveData<List<Group>> getInterestGroups(String header, int interestID) {
        MutableLiveData<List<Group>> groupList = new MutableLiveData<>();
        Call<HashMap<String,List<Group>>> getGroups = exploreAPIInterface.getInterestGroups(BEARER_HEADER + header,interestID);
        getGroups.enqueue(new Callback <HashMap<String,List<Group>>>() {
            @Override
            public void onResponse(Call<HashMap<String,List<Group>>> call,
                                   Response <HashMap<String,List<Group>>> res) {

                Log.i(TAG, "ExploreApiProvider  -- getInterestGroups() -- body"
                        + res.body());
                groupList.setValue(res.body().get("data"));
            }

            @Override
            public void onFailure(Call<HashMap<String,List<Group>>> call, Throwable t) {
                t.printStackTrace();
                Log.i(TAG, "onFailure: " + t.getMessage());
                call.cancel();
            }

        });

        return groupList;
    }

    MutableLiveData<List<Group>> search(String header, String q) {
        MutableLiveData<List<Group>> groupList = new MutableLiveData<>();
        Call<HashMap<String,List<Group>>> getGroups = exploreAPIInterface.search(BEARER_HEADER + header,q);
        getGroups.enqueue(new Callback <HashMap<String,List<Group>>>() {
            @Override
            public void onResponse(Call<HashMap<String,List<Group>>> call,
                                   Response <HashMap<String,List<Group>>> res) {

                Log.i(TAG, "search  -- response --"
                        + res.body());
                groupList.setValue(res.body().get("data"));
            }

            @Override
            public void onFailure(Call<HashMap<String,List<Group>>> call, Throwable t) {
                t.printStackTrace();
                Log.i(TAG, "onFailure: " + t.getMessage());
                call.cancel();
            }

        });

        return groupList;
    }

    MutableLiveData<List<Interest>> getInterests(String header) {
        MutableLiveData<List<Interest>> interestList = new MutableLiveData<>();
        Call<HashMap<String, List<Interest>>> getInterests = exploreAPIInterface.getInterests(BEARER_HEADER + header);
        getInterests.enqueue(new Callback <HashMap<String,List<Interest>>>() {

            @Override
            public void onResponse(Call <HashMap<String,List<Interest>>> call,
                                   Response<HashMap<String,List<Interest>>> res) {

                Log.i(TAG, "ExploreApiProvider  -- getInterests() response --"
                        + res.body());
                interestList.setValue(res.body().get("data"));
            }

            @Override
            public void onFailure(Call<HashMap<String,List<Interest>>> call, Throwable t) {
                t.printStackTrace();
                Log.i(TAG, "onFailure: " + t.getMessage());
                call.cancel();
            }

        });

        return interestList;
    }


}
