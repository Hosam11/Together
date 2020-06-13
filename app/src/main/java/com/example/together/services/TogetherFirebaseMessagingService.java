package com.example.together.services;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UsersViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

public class TogetherFirebaseMessagingService extends FirebaseMessagingService {
    UsersViewModel usersViewModel ;
    public TogetherFirebaseMessagingService() {
        usersViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(UsersViewModel.class);
    }

    @SuppressLint("WrongThread")
    @Override
    public void onNewToken(@NonNull String newToken) {
        super.onNewToken(newToken);
        //check connectivity
        if(HelperClass.checkInternetState(this)){
            Storage storage = new Storage(this);
            usersViewModel.updateDeviceToken(storage.getId(),storage.getToken(),newToken)
                    .observe((LifecycleOwner)this, generalResponse -> {
                        Log.i(HelperClass.TAG, "response .."+generalResponse);
                    });
        }

        /*
        //check connectivity
        if(HelperClass.checkInternetState(this)){
            //call api to update token here
            Storage storage = new Storage(this);
            //get token from fcm
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("Device-Token", "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();
                            Log.d("Device-Token", token);
                            usersViewModel.updateDeviceToken(storage.getId(),storage.getToken(),token)
                                    .observe((LifecycleOwner) TogetherFirebaseMessagingService.this, generalResponse -> {
                                Log.i(HelperClass.TAG, "response .."+generalResponse);
                            });
                        }
                    });

        } */


    }
}
