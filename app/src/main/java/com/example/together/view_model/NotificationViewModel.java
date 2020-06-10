package com.example.together.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.together.data.api.group_apis.GroupRepo;
import com.example.together.data.api.notificaton_apis.NotificationRepo;
import com.example.together.data.api.notificaton_apis.NotificationResponse;
import com.example.together.data.model.GeneralResponse;

public class NotificationViewModel extends ViewModel {

    private NotificationRepo notificationRepo ;

    public NotificationViewModel() {
        notificationRepo = new NotificationRepo();
    }


    public MutableLiveData<NotificationResponse> getUserNotification(int id, int page, String token) {

        return  notificationRepo.getUserNotification(id, page, token);

    }
   public MutableLiveData<GeneralResponse> enableNotification(int userId, String token){

        return  notificationRepo.enableNotification(userId, token);
    }
   public MutableLiveData<GeneralResponse> disableNotification(int userId, String token){

        return  notificationRepo.disableNotification(userId, token);

    }
    public MutableLiveData<GeneralResponse> deleteNotification (int id, String token) {
    return notificationRepo.deleteNotification(id,token);
    }


    }
