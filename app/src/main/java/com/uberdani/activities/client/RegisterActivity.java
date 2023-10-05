package com.uberdani.activities.client;

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
import com.uberdani.R;
import com.uberdani.activities.driver.MapDriverActivity;
import com.uberdani.activities.driver.RegisterDriverActivity;
import com.uberdani.includes.MyToolBar;
import com.uberdani.models.Client;
import com.uberdani.providers.AuthProvider;
import com.uberdani.providers.ClientProvider;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    AuthProvider mAuthProvider;
    ClientProvider mClientProvider;
    AlertDialog mDialog;
    TextInputEditText mtextInputName, mtextInputEmail, mtextInputPassword;
    Button mbtnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MyToolBar.show(this, "Registro de Usuario", true);

        mAuthProvider = new AuthProvider();
        mClientProvider = new ClientProvider();


        mDialog = new SpotsDialog.Builder(RegisterActivity.this).setTitle("Registrando").setMessage("Aguarde por favor.").create();

        mtextInputName = findViewById(R.id.textInputName);
        mtextInputEmail = findViewById(R.id.textInputEmail);
        mtextInputPassword = findViewById(R.id.textInputPassword);
        mbtnRegister = findViewById(R.id.btnRegister);

        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickRegister();
            }
        });
    }


    private void clickRegister() {
        final String name = mtextInputName.getText().toString();
        final String email = mtextInputEmail.getText().toString();
        final String password = mtextInputPassword.getText().toString();

        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if(password.length() >= 6){
                register(name,email,password);
                mDialog.show();
            }
            else{
                Toast.makeText(this, "La contraseña debe tener almenos 8 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Llene todos los campos por favor", Toast.LENGTH_SHORT).show();
        }
    }
    void register(final String name, String email, String password){
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if(task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Client client = new Client(id, name,email);
                    create(client);
                }
                else{
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar al usuario :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void create(Client client){
        mClientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registro de Cliente exitoso! Bienvenido usuario Cliente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MapClientActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterActivity.this, "Algo salió mal :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*private void saveUser(String id, String name, String email) {
        String selectedUser = mPref.getString("user","");
        User user = new User();
        user.setEmail(email);
        user.setName(name);

        if(selectedUser.equals("driver")){
            mDatabase.child("Users").child("Drivers").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro de Conductor exitoso! ya puede iniciar sesión", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Algo salió mal :(", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else if(selectedUser.equals("client")){
            mDatabase.child("Users").child("Clients").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro de Cliente exitoso! ya puede iniciar sesión", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Algo salió mal :(", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }*/
}