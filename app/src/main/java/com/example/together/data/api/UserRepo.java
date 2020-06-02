package com.example.together.data.api;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.JoinGroupResponse;
import com.example.together.data.model.ListTask;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
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

    public MutableLiveData<String> signUp(User user) {
        return provider.signUp(user);
    }

    public MutableLiveData<LoginResponse> login(UserLogin userLogin) {
        return provider.login(userLogin);
    }

    public MutableLiveData<User> fetchUserData(int id, String token) {
        return provider.fetchUserData(id,token) ;
    }

    public MutableLiveData<GeneralResponse> createGroup(Group group, String token) {
        return provider.createGroup(group,token);
    }

    public MutableLiveData<GeneralResponse> joinGroup(int gpId, int userID) {
        return provider.requestJoinGroup(gpId, userID);
    }

    public MutableLiveData<List<JoinGroupResponse>> getAllResponsesForGroup(int gpId) {
        return provider.getAllResponsesForGroup(gpId);
    }

    public MutableLiveData<GeneralResponse> addGroupMember(int gpID, int userID, int adminID) {
        return provider.addGroupMember(gpID, userID, adminID);
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

}
