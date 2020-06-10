package com.example.together.data.api.user_apis;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.Interest;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserInterests;
import com.example.together.data.model.UserLogin;

import java.util.ArrayList;

public class UsersRepo {

    private UserAPIProvider userAPIProvider;

    public UsersRepo() {
        userAPIProvider = new UserAPIProvider();
    }

    public MutableLiveData<LoginResponse> signUp(User user) {
        return userAPIProvider.signUp(user);
    }

    public MutableLiveData<LoginResponse> login(UserLogin userLogin) {
        return userAPIProvider.login(userLogin);
    }

    public MutableLiveData<User> fetchUserData(int id, String token) {
        return userAPIProvider.fetchUserData(id,token) ;
    }

    //TODO : MOVED
    public MutableLiveData<GeneralResponse> updateUserProfile(int id, String token, User user) {
        return userAPIProvider.updateUserProfile(id,token,user) ;
    }


    public MutableLiveData<GeneralResponse> updateUserInterests(int id,String token, UserInterests interests) {
        return userAPIProvider.updateUserInterests(id,token,interests) ;
    }




    public MutableLiveData<ArrayList<Group>> getAllUserGroups(int userId, String token){

        return  userAPIProvider.getAllUserGroups(userId,token);
    }
    public  MutableLiveData<ArrayList<Interest>> getAllInterests(){

        return userAPIProvider.getAllInterests();
    }
    public  MutableLiveData<Group> getSpecificGroupDetails(int groupId, String token){
        return  userAPIProvider.getSpecificGroupDetails(groupId,token);
    }
    public MutableLiveData<GeneralResponse> removeMemberFromGroup(int groupId,int id,int adminId,String token){
        return  userAPIProvider.removeMemberFromGroup(groupId, id, adminId,token);
    }


    // :LEAVE GROUP
    public MutableLiveData<GeneralResponse> leaveGroup(int groupId,int id,String token) {
        return userAPIProvider.leaveGroup(groupId, id, token);
    }

    // :Logout

    public MutableLiveData<GeneralResponse> logout(int id, String token){
        return userAPIProvider.logout(id, token);

    }




}
