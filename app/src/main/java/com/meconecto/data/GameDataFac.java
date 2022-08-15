package com.meconecto.data;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameDataFac {
    public static UserGameData emptyGame(){
        return new UserGameData();
    }

    public static void setUserGameData(String uid,UserGameData configData){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("usuarios").child(uid).setValue(configData);
    }

    public static void cargaDataUsuario(String idUsuario,ValueEventListener postListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usuarios/"+idUsuario);
// [START post_value_event_listener]
        myRef.addValueEventListener(postListener);
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("meconecto/usuarios/"+);*/
    }
}
