package com.example.together.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.together.data.api.user_apis.UsersRepo;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.Interests;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserInterests;
import com.example.together.data.model.UserLogin;

import java.util.ArrayList;

public class UsersViewModel extends ViewModel {
    private MutableLiveData<LoginResponse> loginRes;

    private MutableLiveData<User> userData;
    UsersRepo usersRepo;

    public UsersViewModel() {
        usersRepo = new UsersRepo();
    }

    /**
     * @param user: user that will added to database
     * @return the response message that contain whether the sing up fail or success
     */
    public MutableLiveData<LoginResponse> signUp(User user) {
        return usersRepo.signUp(user);
    }

    public MutableLiveData<LoginResponse> login(UserLogin userLogin) {
        loginRes = usersRepo.login(userLogin);
        return loginRes;
    }

    public MutableLiveData<User> fetchUserData(int id, String token) {
        userData = usersRepo.fetchUserData(id, token);
        return userData;
    }


    //TODO: MOVED
    public MutableLiveData<GeneralResponse> updateUserProfile(int id, String token, User user) {
        return usersRepo.updateUserProfile(id, token,user);

    }

    public MutableLiveData<GeneralResponse> updateUserInterests(int id,String token, UserInterests interests) {
        return usersRepo.updateUserInterests(id,token,interests);

    }


    public  MutableLiveData<ArrayList<Group>> getAllUserGroups(int userId, String token){

        return   usersRepo.getAllUserGroups(userId,token);


    }
    public  MutableLiveData<ArrayList<Interests>> getAllInterests(){
        return usersRepo.getAllInterests();

    }

    public MutableLiveData<Group> getSpecificGroupDetails(int groupId, String token){

        return usersRepo.getSpecificGroupDetails(groupId,token);
    }
    public MutableLiveData<GeneralResponse> removeMemberFromGroup(int groupId,int id,int adminId,String header) {

        return  usersRepo.removeMemberFromGroup(groupId, id,adminId ,header);
    }


    ///LEAVE GROUP
    public MutableLiveData<GeneralResponse> leaveGroup(int groupId,int id,String token) {

        return usersRepo.leaveGroup(groupId, id, token);

    }
    public MutableLiveData<GeneralResponse> logout(int id,String token){
        return usersRepo.logout(id,token);


    }




}
