package com.example.together.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.together.data.api.UserRepo;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Interest;
import com.example.together.data.model.ListTask;

import java.util.ArrayList;

public class UserViewModel extends ViewModel {


    private MutableLiveData<GeneralResponse> addTaskRes;
    private MutableLiveData<ArrayList<ListTask>> toDoListTasks;
    private MutableLiveData<ArrayList<ListTask>> inProgresTasks;
    private MutableLiveData<ArrayList<ListTask>> doneTasks;

    private MutableLiveData<GeneralResponse> addToProgressList;
    private MutableLiveData<GeneralResponse> addToDoneList;
    private MutableLiveData<GeneralResponse> addToDoList;
    private MutableLiveData<GeneralResponse> deleteTaskResp;
    private MutableLiveData<GeneralResponse> editTaskResp;
    private MutableLiveData<GeneralResponse> sendPositionArrangmentResp;

    private UserRepo userRepo = new UserRepo();


    public MutableLiveData<GeneralResponse> addTask(ListTask task, String token){
        addTaskRes = userRepo.addTask(task,token);
        return addTaskRes;
    }
    public MutableLiveData<ArrayList<ListTask>> getToDoListTasks(int groupId, String token){
        toDoListTasks = userRepo.getToDoListTasks(groupId,token);
        return toDoListTasks;
    }
    public MutableLiveData<GeneralResponse>moveToProgressList(int id,String token){
        addToProgressList=userRepo.moveToProgressList(id,token);
        return addToProgressList;
    }
    public MutableLiveData<ArrayList<ListTask>> getInProgressTasks(int groupId, String token){
        inProgresTasks = userRepo.getInProgressTasks(groupId,token);
        return inProgresTasks;
    }
    public MutableLiveData<ArrayList<ListTask>> getDoneTasks(int groupId, String token){
        doneTasks = userRepo.getDoneTasks(groupId,token);
        return doneTasks;
    }

    public MutableLiveData<GeneralResponse>moveToDoneList(int id,String token){
        addToDoneList=userRepo.moveToDoneList(id,token);
        return addToDoneList;
    }
    public MutableLiveData<GeneralResponse>moveToDoList(int id,String token){
        addToDoList=userRepo.moveToDoList(id,token);
        return addToDoList;
    }
    public MutableLiveData<GeneralResponse>deleteTask(int id,String token){
        deleteTaskResp=userRepo.deleteTask(id,token);
        return deleteTaskResp;
    }
    public MutableLiveData<GeneralResponse> editTask(int id,ListTask task, String token){
        editTaskResp = userRepo.editTask(id,task,token);
        return editTaskResp;
    }
    public MutableLiveData<GeneralResponse> sendPositionArrangment(ArrayList<ListTask> tasks, String token){
        sendPositionArrangmentResp = userRepo.sendPositionArrangment(tasks,token);
        return sendPositionArrangmentResp;
    }
}
