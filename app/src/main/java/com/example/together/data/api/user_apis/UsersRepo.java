package com.example.together.data.api.user_apis;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.api.ToDoListApiProvider;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
import com.example.together.data.model.UserLogin;

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

}
