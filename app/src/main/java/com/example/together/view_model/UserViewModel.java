package com.example.together.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.together.data.api.UserRepo;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserLogin;

public class UserViewModel extends ViewModel {

    private MutableLiveData<String> signUpRes;
    private MutableLiveData<LoginResponse> loginRes;
    private MutableLiveData<User> userData;
    private MutableLiveData<GeneralResponse> createGroupRes;

    private UserRepo userRepo = new UserRepo();


    /**
     * @param user: user that will added to database
     * @return the response message that contain whether the sing up fail or success
     */
    public MutableLiveData<String> signUp(User user) {
        signUpRes = userRepo.signUp(user);
        return signUpRes;
    }

    public MutableLiveData<LoginResponse> login(UserLogin userLogin) {
        loginRes = userRepo.login(userLogin);
        return loginRes;
    }

    public MutableLiveData<User> fetchUserData(int id) {
        userData = userRepo.fetchUserData(id);
        return userData;
    }

    public MutableLiveData<GeneralResponse> createGroup(Group group) {
        createGroupRes = userRepo.createGroup(group);
        return createGroupRes;
    }

    public void clearCreateGroupRes() {
        createGroupRes = null;
    }

}
