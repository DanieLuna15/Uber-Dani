package com.uberdani.retrofit;

import com.uberdani.models.FCMBody;
import com.uberdani.models.FCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {
    @Headers({
            "Content-type:application/json",
            "Authorization:key=AAAATJT48ks:APA91bGYzjGeHitKMa-SabGyShO_OkmT2yvQrMAAJjo8dG2KsS2LxqSH-4hvsZ8jft4PTS3bZwArMrDMh45ad-HNfmPcCyIhTjGLEwaATFekiJfhrEJ3A-BAIFJnsQQSq9Lg1Ymfowz7"
    })
    @POST("fcm/send")
    Call<FCMResponse> send(@Body FCMBody body);

}

/*
{
    "to": "fZ8decKxSAOsd-OB8sUfPL:APA91bHyIX8cOwM-hL4E9JCyJrQOKx_lfR6nMpYUjC3psck9RwXO1NWrXLklZcHzT8IQgsjs5d3FecSRVUlN4bT2ubOL6wB7ECvpXsUWCJMdMjdECD0j_AACdUsMW_FgDqbrOnlhhmSc",
    "priority":"high",
   "data": {
    "title": "hola",
    "body": "Viaje cancelado"
      }
}

{
    "multicast_id": 3134441550870872085,
    "success": 1,
    "failure": 0,
    "canonical_ids": 0,
    "results": [
        {
            "message_id": "0:1697652810053329%9d489fbff9fd7ecd"
        }
    ]
}
https://www.site24x7.com/tools/json-to-java.html
*/
