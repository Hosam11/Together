package com.example.together.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class HelperClass {
    public static final String TAG = "logs_info";

    public static final String MALE = "Male";
    public static final String FEMALE = "Female";

    public static final String SING_UP_SUCCESS = "Signed In Successfully";

    public static final String USER_DATA = "user_data";
    public static final String TOKEN = "token";
    public static final String TOKEN_DEF = "token_def";
    public static final String ID = "id";
    public static final String PAID = "Paid";
    public static final String FREE = "Free";
    public static final String CREATE_GROUP_SUCCESS = "successfully";
    public static final String ERROR_MISSING_FILEDS = "Please fill all fields";



    public static void showAlert(String msg, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle("Alert");

        builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}
