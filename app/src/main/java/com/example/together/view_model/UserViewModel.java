package com.example.together.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.together.data.api.UserRepo;
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

public class UserViewModel extends ViewModel {

    private MutableLiveData<LoginResponse> loginRes;
    private MutableLiveData<User> userData;
    private MutableLiveData<GeneralResponse> createGroupRes;
    private MutableLiveData<GeneralResponse> joinGroup;
    private MutableLiveData<List<JoinGroupResponse>> listRequestJoinForGroup;
    private MutableLiveData<GeneralResponse> addMember;
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







    private MutableLiveData<ArrayList<UserGroup>> userGroups;
    private  MutableLiveData<ArrayList<Interests>> allInterestsList;

    private MutableLiveData<GeneralResponse> updateGroupRes;

    private MutableLiveData<GeneralResponse> resAcceptJoin;
    private MutableLiveData<GeneralResponse> resRejectJoin;

    private UserRepo userRepo = new UserRepo();


    /**
     * @param user: user that will added to database
     * @return the response message that contain whether the sing up fail or success
     */
    public MutableLiveData<String> signUp(User user) {
        return userRepo.signUp(user);
    }

    public MutableLiveData<LoginResponse> login(UserLogin userLogin) {
        loginRes = userRepo.login(userLogin);
        return loginRes;
    }

    public MutableLiveData<User> fetchUserData(int id, String token) {
        userData = userRepo.fetchUserData(id, token);
        return userData;
    }

    public MutableLiveData<GeneralResponse> updateUserProfile(int id, String token,User user) {
       return userRepo.updateUserProfile(id, token,user);

    }
    public MutableLiveData<GeneralResponse> updateUserInterests(int id,String token, UserInterests interests) {
        return userRepo.updateUserInterests(id,token,interests);

    }

    public MutableLiveData<GeneralResponse> createGroup(Group group, String token) {
        createGroupRes = userRepo.createGroup(group, token);
        return createGroupRes;
    }

    public MutableLiveData<GeneralResponse> requestJoinGroup(int gpId, int userID, String token) {
        joinGroup = userRepo.joinGroup(gpId, userID, token);
        return joinGroup;
    }

    public MutableLiveData<List<JoinGroupResponse>> getAllRequestJoinForGroup(int gpId, String token) {
        listRequestJoinForGroup = userRepo.getAllResponsesForGroup(gpId, token);
        return listRequestJoinForGroup;
    }

    public MutableLiveData<GeneralResponse> addGroupMember(int gpID, int userID,
                                                           int adminID, String token) {
        addMember = userRepo.addGroupMember(gpID, userID, adminID, token);
        return addMember;
    }
    public  MutableLiveData<ArrayList<UserGroup>> getAllUserGroups(int userId, String token){
        userGroups=userRepo.getAllUserGroups(userId,token);
        return  userGroups;


    }
    public  MutableLiveData<ArrayList<Interests>> getAllInterests(String token){
        allInterestsList=userRepo.getAllInterests(token);
        return  allInterestsList;
    }

    public MutableLiveData<GroupDetails> getSpecificGroupDetails(int groupId,String token){

        return userRepo.getSpecificGroupDetails(groupId,token);
    }
    public MutableLiveData<GeneralResponse> removeMemberFromGroup(int groupId,int id,int adminId,String header) {

    return  userRepo.removeMemberFromGroup(groupId, id,adminId ,header);
    }

    public MutableLiveData<GeneralResponse> updateGroupInfo(int gpID, int adminID,
                                                            Group group, String token) {
        updateGroupRes = userRepo.updateGroupInfo(gpID, adminID, group, token);
        return updateGroupRes;
    }

    public MutableLiveData<GeneralResponse> acceptJoinReqGroup(int reqID, int adminID, String token) {
        resAcceptJoin = userRepo.acceptJoinReqGroup(reqID, adminID, token);
        return resAcceptJoin;
    }

    public MutableLiveData<GeneralResponse> rejectJoinReqGroup(int reqID, String token) {
        resRejectJoin = userRepo.rejectJoinReqGroup(reqID, token);
        return resRejectJoin;
    }

        public void clearCreateGroupRes() {
        createGroupRes = null;
    }

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
