package com.uberdani.activities.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.uberdani.R;
import com.uberdani.providers.AuthProvider;
import com.uberdani.providers.GeofireProvider;
import com.uberdani.providers.TokenProvider;

import java.util.ArrayList;
import java.util.List;

public class MapClientBookingActivity extends AppCompatActivity implements OnMapReadyCallback {
    AuthProvider mAuthProvider;

    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;

    private GeofireProvider mGeofireProvider;
    private TokenProvider mTokenProvider;

    private final static int LOCATION_REQUEST_CODE = 1;
    private final static int SETTINGS_REQUEST_CODE = 2;

    private Marker mMarker;
    private LatLng mCurrentLatLng;
    private List<Marker> mDriversMarkers = new ArrayList<>();

    private boolean mIsFirstTime = true;

    private PlacesClient mPlaces;

    private String mOrigin;
    private LatLng mOriginLatLng;

    private String mDestination;
    private LatLng mDestinationLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_client_booking);

        mAuthProvider = new AuthProvider();

        mGeofireProvider = new GeofireProvider("drivers_working");
        mTokenProvider = new TokenProvider();

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
            //Places.initialize(getApplicationContext(), "AIzaSyB7K3BPID2hFvGeIVNef6m1fKKbBX8hVS4");
        }

        mPlaces = Places.createClient(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }
}