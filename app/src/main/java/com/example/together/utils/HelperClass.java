package com.example.together.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;

public class HelperClass {
    public static final String TAG = "logs_info";
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";

    // Shared Prefernces consts
    public static final String USER_DATA = "user_data";
    public static final String PASSED_USER = "pass_user";
    public static final String PASSED_USER_OBJ = "pass_user";
    public static final String NO_USER = "invalid user data";
    public static final String ID = "id";
    public static final String TOKEN = "token";
    public static final String TOKEN_DEF = "token_def";


    // backend consts
    public static final String SING_UP_SUCCESS = "Signed up Successfully";
    public static final String PAID = "Paid";
    public static final String FREE = "Free";
    public static final String CREATE_GROUP_SUCCESS = "Group Created Successfully ";
    public static final String ERROR_MISSING_FILEDS = "Please fill all fields";
    public static final String ERROR_INTERESTS = "Please select interest";
    public static final String BEARER_HEADER = "Bearer ";




    public static void showAlert(String msg, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle("Alert");

        builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }



    /**
     * @param context: context that called the method
     * @return true if there is a internet connection otherwise false
     */
    public static boolean checkInternetState(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
