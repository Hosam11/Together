package com.example.together;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

public class TogetherFirebaseMessagingService extends FirebaseMessagingService {
    public TogetherFirebaseMessagingService() {
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        //check connectivity

        //call api to update token here .. make request and enqueue request , handle success and failure


    }
}
