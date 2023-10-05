package com.uberdani.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uberdani.R;

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

    private void goToSelectAuth() {
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }
}