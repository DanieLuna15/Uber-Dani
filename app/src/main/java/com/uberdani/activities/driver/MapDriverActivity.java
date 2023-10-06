package com.uberdani.activities.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.uberdani.R;
import com.uberdani.activities.MainActivity;
import com.uberdani.activities.client.MapClientActivity;
import com.uberdani.includes.MyToolBar;
import com.uberdani.providers.AuthProvider;

public class MapDriverActivity extends AppCompatActivity implements OnMapReadyCallback {
    //Button mbtnLogout;
    AuthProvider mAuthProvider;

    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_driver);
        MyToolBar.show(this,"Conductor",false);

        //mbtnLogout = findViewById(R.id.btnLogout);
        mAuthProvider = new AuthProvider();

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        /*mbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuthProvider.logout();
                Intent intent = new Intent(MapDriverActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });*/
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.driver_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

     void logout() {
        mAuthProvider.logout();
        Intent intent = new Intent(MapDriverActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}