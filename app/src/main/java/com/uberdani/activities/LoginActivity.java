package com.uberdani.activities;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uberdani.R;
import com.uberdani.activities.client.MapClientActivity;
import com.uberdani.activities.driver.MapDriverActivity;
import com.uberdani.activities.driver.RegisterDriverActivity;
import com.uberdani.includes.MyToolBar;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText mtextInputEmail, mtextInputPassword;
    Button mbtnLogin;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    SharedPreferences mPref;
    AlertDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyToolBar.show(this, "Login de Usuario", true);

        mtextInputEmail = findViewById(R.id.textInputEmail);
        mtextInputPassword = findViewById(R.id.textInputPassword);
        mbtnLogin = findViewById(R.id.btnLogin);

        mDialog = new SpotsDialog.Builder(LoginActivity.this).setTitle("Accediendo").setMessage("Aguarde por favor.").create();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mPref = getApplicationContext().getSharedPreferences("typeuser",MODE_PRIVATE);
        String selectedUser = mPref.getString("user","");
        //Toast.makeText(this, "El valor que seleccionó fue: " + selectedUser, Toast.LENGTH_SHORT).show();

        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String email = mtextInputEmail.getText().toString();
        String password = mtextInputPassword.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 8){
                mDialog.show();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String user = mPref.getString("user","");
                            if(user.equals("client")){
                                Toast.makeText(LoginActivity.this, "Bienvenido! Usuario Cliente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MapClientActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this, "Bienvenido! Usuario Conductor", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MapDriverActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, "La contraseña o el correo son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                        mDialog.dismiss();
                    }
                });
            }
            else{
                Toast.makeText(this, "la contraseña debe tener como mínimo 8 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "La contraseña y el email son obligatorios!", Toast.LENGTH_SHORT).show();
        }
    }
}