package com.example.together.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static com.example.together.utils.HelperClass.ID;
import static com.example.together.utils.HelperClass.TOKEN;
import static com.example.together.utils.HelperClass.TOKEN_DEF;
import static com.example.together.utils.HelperClass.USER_DATA;

public class Storage {

    Context context;
    SharedPreferences sharedPreferences;

    public Storage(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(USER_DATA,
                MODE_PRIVATE);
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


}
