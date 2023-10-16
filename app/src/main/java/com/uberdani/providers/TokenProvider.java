package com.uberdani.providers;

import static android.content.ContentValues.TAG;
import static android.provider.Settings.System.getString;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.cloudmessaging.CloudMessagingReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.internal.IdTokenListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.iid.FirebaseInstanceId;

import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessaging;
import com.uberdani.R;
import com.uberdani.activities.MainActivity;
import com.uberdani.models.Token;

public class TokenProvider {

    DatabaseReference mDatabase;

    public TokenProvider(DatabaseReference mDatabase) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tokens");
    }


    public void create() {

    }
}
