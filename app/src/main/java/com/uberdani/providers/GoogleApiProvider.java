package com.uberdani.providers;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.uberdani.R;
import com.uberdani.retrofit.IGoogleApi;
import com.uberdani.retrofit.RetrofitClient;

import java.util.Date;

import retrofit2.Call;

public class GoogleApiProvider {
    private Context context;

    public GoogleApiProvider(Context context) {
        this.context = context;
    }
    public Call<String> getDirections(LatLng originLatLng,LatLng destinationLatLng ){
        String baseurl = "https://maps.googleapis.com";

        String query = "/maps/api/directions/json?mode=driving&transit_routing_preferences=less_driving&"
                + "origin=" + originLatLng.latitude + "," + originLatLng.longitude + "&"
                + "destination=-16.527678,-68.1083769&"
                + "departure_time=" + (new Date().getTime()+(60*60*1000)) + "&"
                + "traffic_model=best_guess&"
                + "key=" + context.getResources().getString(R.string.google_maps_key);
        String complete="https://maps.googleapis.com/maps/api/directions/json?mode=driving&transit_routing_preferences=less_driving&origin=-16.5242587,-68.1119803&destination=-16.527678,-68.1083769&departure_time=1919973532&traffic_model=best_guess&key=AIzaSyB7K3BPID2hFvGeIVNef6m1fKKbBX8hVS4";
        /*String query = "/maps/api/directions/json?mode=driving&transit_routing_preferences=less_driving&"
                + "origin=" + originLatLng.latitude + "," + originLatLng.longitude + "&"
                + "destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + "&"
                + "departure_time=" + (new Date().getTime() + (60*60*1000)) + "&"
                + "traffic_model=best_guess&"
                + "key=" + context.getResources().getString(R.string.google_maps_key);*/
        return RetrofitClient.getClient(baseurl).create(IGoogleApi.class).getDirections(baseurl + query);
    }
}
