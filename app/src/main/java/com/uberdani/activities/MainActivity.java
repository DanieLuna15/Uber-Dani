package com.uberdani.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.uberdani.R;
import com.uberdani.activities.client.MapClientActivity;
import com.uberdani.activities.driver.MapDriverActivity;

public class MainActivity extends AppCompatActivity {
    Button mButtonIamClient;
    Button mButtonIamDriver;
    SharedPreferences mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ocultando ToolBar (metodo 1 funciona)
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        mPref = getApplicationContext().getSharedPreferences("typeuser",MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();

        mButtonIamClient = findViewById(R.id.btnIamClient);
        mButtonIamDriver = findViewById(R.id.btnIamDriver);

        mButtonIamClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user","client");
                editor.apply();
                goToSelectAuth();
            }
        });
        mButtonIamDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user","driver");
                editor.apply();
                goToSelectAuth();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            String user = mPref.getString("user", "");
            if(user.equals("client")){
                Toast.makeText(MainActivity.this, "Hola de nuevo! Usuario Cliente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MapClientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else{
                Toast.makeText(MainActivity.this, "Hola de nuevo! Usuario Conductor", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MapDriverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }

    }

    private void goToSelectAuth() {
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }
}