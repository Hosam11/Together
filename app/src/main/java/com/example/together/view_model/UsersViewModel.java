package com.example.together.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.together.data.api.user_apis.UsersRepo;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserLogin;

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


}
