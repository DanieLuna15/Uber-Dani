package com.uberdani.activities.driver;

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
import com.uberdani.activities.client.RegisterActivity;
import com.uberdani.includes.MyToolBar;
import com.uberdani.models.Client;
import com.uberdani.models.Driver;
import com.uberdani.providers.AuthProvider;
import com.uberdani.providers.ClientProvider;
import com.uberdani.providers.DriverProvider;

import dmax.dialog.SpotsDialog;

public class RegisterDriverActivity extends AppCompatActivity {

    AuthProvider mAuthProvider;
    DriverProvider mDriverProvider;
    AlertDialog mDialog;
    TextInputEditText mtextInputName, mtextInputEmail, mtextInputPassword, mtextInputVehicleBrand, mtextInputVehiclePlate;
    Button mbtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        MyToolBar.show(this, "Registro de Conductor", true);

        mAuthProvider = new AuthProvider();
        mDriverProvider = new DriverProvider();

        mDialog = new SpotsDialog.Builder(RegisterDriverActivity.this).setTitle("Registrando").setMessage("Aguarde por favor.").create();

        mtextInputName = findViewById(R.id.textInputName);
        mtextInputEmail = findViewById(R.id.textInputEmail);
        mtextInputPassword = findViewById(R.id.textInputPassword);
        mtextInputVehicleBrand = findViewById(R.id.textInputVehicleBrand);
        mtextInputVehiclePlate = findViewById(R.id.textInputVehiclePlate);
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
        final String vehiclebrand = mtextInputVehicleBrand.getText().toString();
        final String vehicleplate = mtextInputVehiclePlate.getText().toString();
        final String password = mtextInputPassword.getText().toString();

        if(!name.isEmpty() && !email.isEmpty() && !vehiclebrand.isEmpty() && !vehicleplate.isEmpty() && !password.isEmpty()){
            if(password.length() >= 8){
                register(name,email,password,vehiclebrand,vehicleplate);
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
    void register(final String name, String email, String password, final String vehiclebrand, final String vehicleplate){
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if(task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Driver driver = new Driver(id, name, email, vehiclebrand, vehicleplate );
                    create(driver);
                }
                else{
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo registrar al usuario :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void create(Driver driver){
        mDriverProvider.create(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterDriverActivity.this, "Registro de Conductor exitoso! Bienvenido usuario Conductor", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterDriverActivity.this, MapDriverActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterDriverActivity.this, "Algo salió mal :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}