package com.example.together.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.together.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class HelperClass {
    public static final String TAG = "logs_info";
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static final String JOIN_GROUP = "Join Group";
    public static final String PENDING = "Pending...";
    public static final String ALERT = "Alert!";


    // Shared Prefernces consts
    public static final String USER_DATA = "user_data";
    public static final String PASSED_USER = "pass_user";

    public static final String PASSED_GROUP_FILE = "pass_group_file";
    public static final String PASSED_GROUP_OBJ = "pass_group_obj";
    public static final String NO_GROUP_DEFULT = "no_group";

    public static final String PASSED_USER_OBJ = "pass_user";
    public static final String NO_USER = "invalid user data";
    public static final String ID = "id";
    public static final String TOKEN = "token";
    public static final String TOKEN_DEF = "token_def";

    public static final String SAVED_USER_NAME = "user_name";
    public static final String SAVED_USER_NAME_FILE = "user_name_file";
    public static final String SAVED_USER_NAME_DEFAULT = "user_name_default";

    public static final String MSG_ID = "msg_id";
    public static final String MSG_DELETED_SUCCUESS =  "This messge deleted successfully";

    // Chat consts
    /*
    "name
    "message
    "user_id
    group_id
     */
    public static final String NAME  = "name";
    public static final String MESSAGE  = "message";
    public static final String USER_ID  = "user_id";
    public static final String GROUP_ID  = "group_id";
    public static final String IS_SEND  = "isSent";
    public static final String IS_STORED_MESSAGE  = "is_stored_msg";
    // backend consts
    public static final String SING_UP_SUCCESS = "Signed up Successfully";
    public static final String PAID = "Paid";
    public static final String FREE = "Free";
    public static final String CREATE_GROUP_SUCCESS = "Group Created Successfully";
    public static final String ERROR_MISSING_FILEDS = "Please fill all fields";
    public static final String ERROR_INTERESTS = "Please select interest";
    public static final String BEARER_HEADER = "Bearer ";

    public static final String TODO ="to do";
    public static final String ADD_TASK_RESPONSE_SUCCESS ="Task added Successfully";
    public static final String MOVING_SUCCESS ="Moved successfully";
    public static final String deleteTaskSuccess ="This task deleted successfully";

    public static final String updatedTaskSuccess = "Updated successfully";

    public static final String checkYourCon = "Please check your internet connection";
    public static final String SERVER_DOWN = "Failed connect to host!";

    public static final String USER_NOT_MEMBER = "Not member ";
    public static final String USER_WAITING_JOIN_GROUP
            = "Not member , This user waiting for admin of group to accept his request of join";
    public static final String USER_IN_GROUP = "Member of this group";
    public static final String GROUP_FULL = "This group is full";


    public static final String UPDATE_GROUP_SUCCESS = "updated successfully";
    public static final String GROUP_TITLE_EXITS= "This title already exist";

    public static final String INTEREST_FILE = "interest_file";
    public static final String INTEREST_OBJECT = "interest_object";
    public static final String INTEREST_DEFULT = "other";


    public static void showAlert(String description, String msg, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertView = inflater.inflate(R.layout.custom_alert, null);
        builder.setView(alertView);
        TextView alertDescription = alertView.findViewById(R.id.alert_description_edit_text);
        TextView alertMessage = alertView.findViewById(R.id.alert_message_edit_text);
        alertDescription.setText(description);
        alertMessage.setText(msg);
        TextView okBtn = alertView.findViewById(R.id.ok_button);
        AlertDialog alertDialog = builder.create();
        okBtn.setOnClickListener((v) -> {
            alertDialog.cancel();
        });

        alertDialog.show();

    }

    public static void newSelectImage(Activity activity) {
        Log.i(TAG, "HelperClass -- newSelectImage: ");
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1, 1)
                .start(activity);
    }

    /**
     * @param context: context that called the method
     * @return true if there is a internet connection otherwise false
     */
    public static boolean checkInternetState(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}