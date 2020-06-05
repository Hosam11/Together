package com.example.together.data.api;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.ListTask;

import java.util.ArrayList;

public class UserRepo {

    private ApiProvider provider;
    private ToDoListApiProvider toDoListApiProvider;

    public UserRepo() {
        provider = new ApiProvider();
        toDoListApiProvider = new ToDoListApiProvider();
    }


//    public MutableLiveData<GeneralResponse> updateUserProfile(int id, String token,User user) {
//        return provider.updateUserProfile(id,token,user) ;
//    }

//    public MutableLiveData<GeneralResponse> updateUserInterests(int id,String token, UserInterests interests) {
//        return provider.updateUserInterests(id,token,interests) ;
//    }
//
//
//
//
//    public MutableLiveData<ArrayList<UserGroup>> getAllUserGroups(int userId, String token){
//
//        return  provider.getAllUserGroups(userId,token);
//    }
//    public  MutableLiveData<ArrayList<Interests>> getAllInterests(){
//
//        return provider.getAllInterests();
//    }
//    public  MutableLiveData<GroupDetails> getSpecificGroupDetails(int groupId,String token){
//        return  provider.getSpecificGroupDetails(groupId,token);
//    }
//   public MutableLiveData<GeneralResponse> removeMemberFromGroup(int groupId,int id,int adminId,String token){
//        return  provider.removeMemberFromGroup(groupId, id, adminId,token);
//   }
//



//
//    // :LEAVE GROUP
//    public MutableLiveData<GeneralResponse> leaveGroup(int groupId,int id,String token) {
//    return provider.leaveGroup(groupId, id, token);
//    }
//
//    // :Logout
//    public MutableLiveData<GeneralResponse> logout(int id){
//        return provider.logout(id);
//
//
//    }


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

