package com.example.together.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.together.R;

import java.io.ByteArrayOutputStream;

public class HelperClass {
    public static final String TAG = "logs_info";
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static final String JOIN_GROUP = "Join Group";
    public static final String PENDING = "Pending...";



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
    public static final String SUCCESS ="sucess";
    public static final String deleteTaskSuccess ="This task deleted successfully";
    public static final String updatedTaskSuccess = "Updated successfully";

    public static final String checkYourCon= "Please check your internet connection";
    public static final String SERVER_DOWN = "Failed connect to host!";

    public static  final String USER_NOT_MEMBER = "Not member ";
    public static final String USER_WAITING_JOIN_GROUP
            = "Not member , This user waiting for admin of group to accept his request of join";
    public static final String USER_IN_GROUP = "Member of this group";

    // user send req and accepte it
    // "Member of this group and"

    public static void showAlert(String msg, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle("Alert");

        builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public static void showAlert(String description,String msg, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View alertView = inflater.inflate(R.layout.custom_alert,null);
        builder.setView(alertView);
        TextView alertDescription = alertView.findViewById(R.id.alert_description_edit_text);
        TextView alertMessage = alertView.findViewById(R.id.alert_message_edit_text);
        alertDescription.setText(description);
        alertMessage.setText(msg);
        TextView okBtn = alertView.findViewById(R.id.ok_button);
        AlertDialog alertDialog = builder.create();
        okBtn.setOnClickListener((v)->{alertDialog.cancel();});

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
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void selectImage(Activity activity, int cameraReqCode, int cameraReq, int  galleryReq) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose Photo");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, cameraReqCode);
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    activity.startActivityForResult(cameraIntent, cameraReq);
                }
            } else if (options[item].equals("Choose from Gallery")) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activity.startActivityForResult(intent, galleryReq);
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}