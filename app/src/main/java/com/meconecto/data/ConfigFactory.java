package com.meconecto.data;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ConfigFactory {

    public static void loadConfiguration(ValueEventListener postListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("meconecto/configuracion");
        myRef.addValueEventListener(postListener);
    }

    public static AppConfiguration buildConfiguration(Map<String,Object> config){
        AppConfiguration ap = new AppConfiguration();
        Long version = (Long)config.get("version");
        ap.setVersion(version);
        Map<String,Object> cont = (Map<String,Object>)config.get("contenido");
        ap.setCategorias(cont);
        return ap;
    }


}
