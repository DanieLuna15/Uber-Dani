package com.uberdani.activities.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.uberdani.R;
import com.uberdani.models.FCMBody;
import com.uberdani.models.FCMResponse;
import com.uberdani.providers.GeofireProvider;
import com.uberdani.providers.NotificationProvider;
import com.uberdani.providers.TokenProvider;

import java.util.HashMap;
import java.util.Map;

import okhttp3.internal.cache.DiskLruCache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestDriverActivity extends AppCompatActivity {

    private LottieAnimationView mAnimation;
    private TextView mTextViewLookingFor;
    private Button mButtonCancelRequest;
    private GeofireProvider mGeofireProvider;

    private double mExtraOriginLat;
    private double mExtraOriginLng;
    private LatLng mOriginLatLng;

    private double mRadius = 0.1;
    private boolean mDriverFound = false;
    private String mIdDriverFound = "";
    private LatLng mDriverFoundLatLng;
    private NotificationProvider mNotificationProvider;
    private TokenProvider mTokenProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_driver);

        mAnimation = findViewById(R.id.animation);
        mTextViewLookingFor = findViewById(R.id.textViewLookingFor);
        mButtonCancelRequest = findViewById(R.id.btnCancelRequest);

        mAnimation.playAnimation();
        mExtraOriginLat = getIntent().getDoubleExtra("origin_lat",0);
        mExtraOriginLng = getIntent().getDoubleExtra("origin_lng",0);
        mOriginLatLng = new LatLng(mExtraOriginLat,mExtraOriginLng);
        mGeofireProvider = new GeofireProvider();
        mTokenProvider = new TokenProvider();
        mNotificationProvider = new NotificationProvider();
        getClosestDriver();
    }

    private void getClosestDriver(){
        mGeofireProvider.getActiveDrivers(mOriginLatLng, mRadius).addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                if (!mDriverFound) {
                    mDriverFound = true;
                    mIdDriverFound = key;
                    mDriverFoundLatLng = new LatLng(location.latitude, location.longitude);
                    mTextViewLookingFor.setText("CONDUCTOR ENCONTRADO!\nESPERANDO RESPUESTA...");
                    sendNotification();
                    Log.d("DRIVER", "ID: " + mIdDriverFound);
                }
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                //ACA INGRESA CUANDO TERMINA LA BUSQUEDA DEL CONDUCTOR EN UN RADIO DE 0.1 KM
                if(!mDriverFound){
                    mRadius = mRadius + 0.1f;
                    //no encontro ningun conductor
                    if(mRadius > 5){
                        mTextViewLookingFor.setText("NO SE PUDO ENCONTRAR UN CONDUCTOR...");
                        Toast.makeText(RequestDriverActivity.this, "No se encontró un conductor.", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        getClosestDriver();
                    }
                }

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    private void sendNotification() {
        mTokenProvider.getToken(mIdDriverFound).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String token = snapshot.child("token").getValue().toString();
                String token = snapshot.child("token").getValue().toString();
                Map<String, String> map = new HashMap<>();
                map.put("title", "SOLICITUD DE SERVICIO");
                map.put("body", "Un cliente esta solicitando un servicio");
                FCMBody fcmBody = new FCMBody(token,"high",map);
                mNotificationProvider.sendNotification(fcmBody).enqueue(new Callback<FCMResponse>() {
                    @Override
                    public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                        if(response.body()!=null){
                            if(response.body().getSuccess() == 1){
                                Toast.makeText(RequestDriverActivity.this, "Su notificación se ha enviado corrrectamente", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(RequestDriverActivity.this, "No se pudo enviar la notificación", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FCMResponse> call, Throwable t) {
                        Log.d("Error","Error " + t.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}