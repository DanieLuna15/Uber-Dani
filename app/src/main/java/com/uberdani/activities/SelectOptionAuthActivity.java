package com.uberdani.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uberdani.R;
import com.uberdani.activities.client.RegisterActivity;
import com.uberdani.activities.driver.RegisterDriverActivity;
import com.uberdani.includes.MyToolBar;

public class SelectOptionAuthActivity extends AppCompatActivity {
    Button mbtnGoToLogin, mbtnGoToRegister;
    SharedPreferences mPref;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        MyToolBar.show(this, "Seleccione una opción", true);

        mPref = getApplicationContext().getSharedPreferences("typeuser",MODE_PRIVATE);

        mbtnGoToLogin = findViewById(R.id.btnGoToLogin);
        mbtnGoToRegister = findViewById(R.id.btnGoToRegister);
        mbtnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
        mbtnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });
    }

    private void goToLogin() {
        Intent intent = new Intent(SelectOptionAuthActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    private void goToRegister() {
        String typeUser = mPref.getString("user", "");
        if(typeUser.equals("client")){
            Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterDriverActivity.class);
            startActivity(intent);
        }

    }
}