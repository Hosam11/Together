package com.example.together.data.api.notificaton_apis;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.model.GeneralResponse;


public class NotificationRepo {

    private NotificationApiProvider provider;

    public NotificationRepo() {
        provider = new NotificationApiProvider();

    }

   public MutableLiveData<NotificationResponse> getUserNotification(int id, int page, String token) {

        return  provider.getUserNotification(id, page, token);

   }

   public MutableLiveData<GeneralResponse> enableNotification(int userId, String token){

  return  provider.enableNotification(userId, token);
    }
  public   MutableLiveData<GeneralResponse> disableNotification(int userId, String token){

       return  provider.disableNotification(userId, token);

    }

   public MutableLiveData<GeneralResponse> deleteNotification (int id, String token){
        return  provider.deleteNotification(id, token);

   }




    }

