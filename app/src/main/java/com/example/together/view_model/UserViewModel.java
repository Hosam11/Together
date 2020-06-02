package com.example.together.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.together.data.api.UserRepo;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.GroupDetails;
import com.example.together.data.model.Interests;
import com.example.together.data.model.JoinGroupResponse;
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
    private MutableLiveData<ArrayList<UserGroup>> userGroups;
    private  MutableLiveData<ArrayList<Interests>> allInterestsList;

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

    public MutableLiveData<GeneralResponse> requestJoinGroup(int gpId, int userID) {
        joinGroup = userRepo.joinGroup(gpId, userID);
        return joinGroup;
    }

    public MutableLiveData<List<JoinGroupResponse>> getAllRequestJoinForGroup(int gpId) {
        listRequestJoinForGroup = userRepo.getAllResponsesForGroup(gpId);
        return listRequestJoinForGroup;
    }

    public MutableLiveData<GeneralResponse> addGroupMember(int gpID, int userID, int adminID) {
        addMember = userRepo.addGroupMember(gpID, userID, adminID);
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


        public void clearCreateGroupRes() {
        createGroupRes = null;
    }

}
