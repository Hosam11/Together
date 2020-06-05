package com.example.together.data.api;

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

import java.util.ArrayList;
import java.util.List;

public class UserRepo {

    private ApiProvider provider;
    private ToDoListApiProvider toDoListApiProvider;

    public UserRepo() {
        provider = new ApiProvider();
        toDoListApiProvider = new ToDoListApiProvider();
    }



    public MutableLiveData<GeneralResponse> addTask(ListTask task, String token){
        return toDoListApiProvider.addTask(task,token);
    }
    public MutableLiveData<ArrayList<ListTask>> getToDoListTasks(int groupId, String token){
        return toDoListApiProvider.getToDoListTasks(groupId,token);
    }
    public MutableLiveData<GeneralResponse> moveToProgressList (int id,String token){
        return toDoListApiProvider.moveToProgressList(id,token);
    }
    public MutableLiveData<ArrayList<ListTask>> getInProgressTasks(int groupId, String token) {
        return toDoListApiProvider.getInProgressTasks(groupId, token);
    }
    public MutableLiveData<ArrayList<ListTask>> getDoneTasks(int groupId, String token) {
        return toDoListApiProvider.getDoneTasks(groupId, token);
    }
    public MutableLiveData<GeneralResponse> moveToDoneList (int id,String token) {
        return toDoListApiProvider.moveToDoneList(id, token);
    }
    public MutableLiveData<GeneralResponse> moveToDoList (int id,String token) {
        return toDoListApiProvider.moveToDoList(id, token);
    }
    public MutableLiveData<GeneralResponse> deleteTask (int id,String token) {
        return toDoListApiProvider.deleteTask(id, token);
    }
    public MutableLiveData<GeneralResponse> editTask(int id,ListTask task, String token){
        return toDoListApiProvider.editTask(id,task,token);
    }
    public MutableLiveData<GeneralResponse> sendPositionArrangment(ArrayList<ListTask> tasks, String token) {
        return toDoListApiProvider.sendPositionArrangment(tasks, token);
    }

}

