package com.example.together.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.together.data.model.Group;
import com.example.together.data.model.User;
import com.example.together.data.model.UserGroup;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;
import static com.example.together.utils.HelperClass.ID;
import static com.example.together.utils.HelperClass.NO_GROUP_DEFULT;
import static com.example.together.utils.HelperClass.NO_USER;
import static com.example.together.utils.HelperClass.PASSED_GROUP_FILE;
import static com.example.together.utils.HelperClass.PASSED_GROUP_OBJ;
import static com.example.together.utils.HelperClass.PASSED_USER;
import static com.example.together.utils.HelperClass.PASSED_USER_OBJ;
import static com.example.together.utils.HelperClass.TOKEN;
import static com.example.together.utils.HelperClass.TOKEN_DEF;
import static com.example.together.utils.HelperClass.USER_DATA;

public class Storage {

    Context context;
    SharedPreferences sharedPreferences;

    /**
     * this version of construct use to store most used user data id and token
     * start with assgin object to sharPrefs
     * @param context
     */
    public Storage(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(USER_DATA,
                MODE_PRIVATE);
    }

    /**
     * this construct used in store and other thing in sharedPrefs
     */
    public Storage() {

    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN, TOKEN_DEF);
    }

    public int getId() {
        return sharedPreferences.getInt(ID, 0);
    }

    public void saveUserData(String token, int id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, token);
        editor.putInt(ID, id);
        editor.apply();
    }

    public void savePassedUser(User user, Context context) {
        sharedPreferences = context.getSharedPreferences(PASSED_USER, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user); // myObject - instance of MyObject
        prefsEditor.putString(PASSED_USER_OBJ, json);
        prefsEditor.apply();
    }

    public User getPassUser(Context context) {
        sharedPreferences = context.getSharedPreferences(PASSED_USER, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PASSED_USER_OBJ, NO_USER);
        return gson.fromJson(json, User.class);
    }

    public void saveGroupObject(UserGroup group, Context context) {
        sharedPreferences = context.getSharedPreferences(PASSED_GROUP_FILE, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(group); // myObject - instance of MyObject
        prefsEditor.putString(PASSED_GROUP_OBJ, json);
        prefsEditor.apply();
    }

    public UserGroup getGroupUser(Context context) {
        sharedPreferences = context.getSharedPreferences(PASSED_GROUP_FILE, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PASSED_GROUP_OBJ, NO_GROUP_DEFULT);
        return gson.fromJson(json, UserGroup.class);
    }

    public  void clearStorage(){
        SharedPreferences pref = context.getSharedPreferences(USER_DATA, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();

    }
}



