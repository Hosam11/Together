package com.example.together.data.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.GroupOfTasks;
import com.example.together.data.model.ListTask;
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
import static com.example.together.utils.HelperClass.TAG;

public class ToDoListApiProvider {
    private APIInterface apiInterface;

    ToDoListApiProvider() {
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

    MutableLiveData<GeneralResponse> addTask(ListTask task , String token){
        MutableLiveData<GeneralResponse> addTaskResp = new MutableLiveData<>();
        Call<GeneralResponse> addTaskCall = apiInterface.addTask(task, HelperClass.BEARER_HEADER+token);
        addTaskCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                Log.i(TAG, "ApiProvider -- AddTask enqueue()  body >> " +
                        response.body());
                addTaskResp.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                addTaskResp.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });
        return addTaskResp;
    }

    MutableLiveData<ArrayList<ListTask>> getToDoListTasks (int groupId , String token){
        MutableLiveData<ArrayList<ListTask>> toDoListTasks = new MutableLiveData<>();
        Call<ArrayList<ListTask>> addTaskCall = apiInterface.getToDoListTasks(groupId, HelperClass.BEARER_HEADER+token);
        addTaskCall.enqueue(new Callback<ArrayList<ListTask>>() {
            @Override
            public void onResponse(Call<ArrayList<ListTask>> call, Response<ArrayList<ListTask>> response) {
                if(toDoListTasks!=null) {
                    toDoListTasks.setValue(response.body());
                    Log.i(TAG, "ToDoListApiProvider -- getToDoListTasks enqueue()  body >> " +
                            toDoListTasks.getValue());
                    for (ListTask list : response.body()) {
                        Log.i(TAG, "ToDoListApiProvider -- getToDoListTasks enqueue()  body >> " +
                                list.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ListTask>> call, Throwable t) {
              toDoListTasks.setValue(null);
            }
        });

        return toDoListTasks;
    }

    MutableLiveData<GeneralResponse> moveToProgressList (int id,String token){
        MutableLiveData<GeneralResponse> res = new MutableLiveData<>();
        Call<GeneralResponse>moveToProgressListCall = apiInterface.moveToProgressList(id,HelperClass.BEARER_HEADER+token);
        moveToProgressListCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                res.setValue(response.body());
                Log.i(TAG, "ToDolistapiprovider -- moveToProgressListCall enqueue()  body >> " +
                        response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                res.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });
        return res;
    }

    MutableLiveData<ArrayList<ListTask>> getInProgressTasks (int groupId , String token){
        MutableLiveData<ArrayList<ListTask>> InProgressTasks = new MutableLiveData<>();
        Call<ArrayList<ListTask>> inProgressTasksCall = apiInterface.getInProgressTasks(groupId, HelperClass.BEARER_HEADER+token);
        inProgressTasksCall.enqueue(new Callback<ArrayList<ListTask>>() {
            @Override
            public void onResponse(Call<ArrayList<ListTask>> call, Response<ArrayList<ListTask>> response) {

                InProgressTasks.setValue(response.body());
                Log.i(TAG, "ToDoListApiProvider -- getToDoListTasks enqueue()  body >> " +
                        InProgressTasks.getValue());
            }

            @Override
            public void onFailure(Call<ArrayList<ListTask>> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

        return InProgressTasks;
    }

    MutableLiveData<ArrayList<ListTask>> getDoneTasks (int groupId , String token){
        MutableLiveData<ArrayList<ListTask>> doneTasks = new MutableLiveData<>();
        Call<ArrayList<ListTask>> doneTasksCall = apiInterface.getDoneTasks(groupId, HelperClass.BEARER_HEADER+token);
        doneTasksCall.enqueue(new Callback<ArrayList<ListTask>>() {
            @Override
            public void onResponse(Call<ArrayList<ListTask>> call, Response<ArrayList<ListTask>> response) {

                doneTasks.setValue(response.body());
                Log.i(TAG, "ToDoListApiProvider -- getToDoListTasks enqueue()  body >> " +
                        doneTasks.getValue());
            }

            @Override
            public void onFailure(Call<ArrayList<ListTask>> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

        return doneTasks;
    }


    MutableLiveData<GeneralResponse> moveToDoneList (int id,String token){
        MutableLiveData<GeneralResponse> res = new MutableLiveData<>();
        Call<GeneralResponse>moveToDoneCall = apiInterface.moveToDoneList(id,HelperClass.BEARER_HEADER+token);
        moveToDoneCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                res.setValue(response.body());
                Log.i(TAG, "ToDolistapiprovider -- moveToProgressListCall enqueue()  body >> " +
                        response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t){
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                res.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });
        return res;
    }

    MutableLiveData<GeneralResponse> moveToDoList (int id,String token){
        MutableLiveData<GeneralResponse> res = new MutableLiveData<>();
        Call<GeneralResponse>moveToDoListCall = apiInterface.moveToDoList(id,HelperClass.BEARER_HEADER+token);
        moveToDoListCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                res.setValue(response.body());
                Log.i(TAG, "ToDolistapiprovider -- moveToProgressListCall enqueue()  body >> " +
                        response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                res.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });
        return res;
    }

    MutableLiveData<GeneralResponse> deleteTask (int id,String token){
        MutableLiveData<GeneralResponse> res = new MutableLiveData<>();
        Call<GeneralResponse>deleteTaskCall = apiInterface.deleteTask(id,HelperClass.BEARER_HEADER+token);
        deleteTaskCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                res.setValue(response.body());
                Log.i(TAG, "ToDolistapiprovider -- moveToProgressListCall enqueue()  body >> " +
                        response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                res.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });
        return res;
    }

    MutableLiveData<GeneralResponse> editTask(int id,ListTask task , String token){
        MutableLiveData<GeneralResponse> editTaskResp = new MutableLiveData<>();
        Call<GeneralResponse> editTaskCall = apiInterface.editTask(id,task, HelperClass.BEARER_HEADER+token);
        editTaskCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                Log.i(TAG, "ApiProvider -- AddTask enqueue()  body >> " +
                        response.body());
                editTaskResp.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                editTaskResp.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });
        return editTaskResp;
    }
    MutableLiveData<GeneralResponse> sendPositionArrangment(ArrayList<ListTask> tasks , String token){
        MutableLiveData<GeneralResponse> sendPositionArrangmentResp = new MutableLiveData<>();
        GroupOfTasks groupOfTasks= new GroupOfTasks();
        groupOfTasks.tasks=tasks;
        Call<GeneralResponse> sendPositionArrangmentCall = apiInterface.sendPositionArrangment(groupOfTasks, HelperClass.BEARER_HEADER+token);
        sendPositionArrangmentCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                Log.i(TAG, "ApiProvider -- AddTask enqueue()  body >> " +
                        response.body());
                sendPositionArrangmentResp.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                GeneralResponse generalRes = new GeneralResponse();
                generalRes.response = t.getMessage();
                sendPositionArrangmentResp.setValue(generalRes);
                t.printStackTrace();
                call.cancel();
            }
        });
        return sendPositionArrangmentResp;
    }

}
