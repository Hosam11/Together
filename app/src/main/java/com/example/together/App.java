package com.example.together;

import android.app.Application;
import com.pusher.pushnotifications.PushNotifications;
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PushNotifications.start(getApplicationContext(), "ed3b05e0-b714-487d-a137-8daea3dfdecd");
        PushNotifications.addDeviceInterest("debug-together");
    }
}
