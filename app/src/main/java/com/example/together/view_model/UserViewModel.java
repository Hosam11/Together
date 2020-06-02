package com.example.together.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.together.data.api.UserRepo;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.JoinGroupResponse;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserLogin;

import java.util.List;

public class UserViewModel extends ViewModel {

    private MutableLiveData<LoginResponse> loginRes;
    private MutableLiveData<User> userData;
    private MutableLiveData<GeneralResponse> createGroupRes;
    private MutableLiveData<GeneralResponse> joinGroup;
    private MutableLiveData<List<JoinGroupResponse>> listRequestJoinForGroup;
    private MutableLiveData<GeneralResponse> addMember;
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

}
