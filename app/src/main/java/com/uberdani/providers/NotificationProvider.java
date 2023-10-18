package com.uberdani.providers;

import com.uberdani.models.FCMBody;
import com.uberdani.models.FCMResponse;
import com.uberdani.retrofit.IFCMApi;
import com.uberdani.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Retrofit;

public class NotificationProvider {
    private String url = "https://fcm.googleapis.com";
    //https://fcm.googleapis.com/v1/projects/uberdani/messages:send

    public NotificationProvider(){

    }
    public Call<FCMResponse> sendNotification(FCMBody body){
        return RetrofitClient.getClientObject(url).create(IFCMApi.class).send(body);
    }
}
