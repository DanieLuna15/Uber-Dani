package com.uberdani.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uberdani.models.Client;
import com.uberdani.models.Driver;

import java.util.HashMap;
import java.util.Map;

public class DriverProvider {
    DatabaseReference mDatabase;

    public DriverProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");
    }
    public Task<Void> create(Driver driver){
        Map<String, Object> map= new HashMap<>();
        map.put("name", driver.getName());
        map.put("id", driver.getId());
        map.put("email", driver.getEmail());
        map.put("vehiclebrand", driver.getVehicleBrand());
        map.put("vehicleplate", driver.getVehiclePlate());
        return mDatabase.child(driver.getId()).setValue(map);
    }

    public Task<Void> update(Driver driver){
        Map<String, Object> map= new HashMap<>();
        map.put("name", driver.getName());
        map.put("image", driver.getImage());
        map.put("vehiclebrand", driver.getVehicleBrand());
        map.put("vehicleplate", driver.getVehiclePlate());
        return mDatabase.child(driver.getId()).updateChildren(map);
    }
    public DatabaseReference getDriver(String idDriver){
        return mDatabase.child(idDriver);
    }
}
