package com.meconecto.data;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameDataFac {
    public static UserGameData emptyGame(){
        return new UserGameData();
    }

    public static void setUserGameData(FirebaseDatabase database,String uid,UserGameData configData){
        DatabaseReference mDatabase = database.getReference();
        mDatabase.child("usuarios").child(uid).setValue(configData);
    }

    public static void cargaDataUsuario(FirebaseDatabase database,String idUsuario,ValueEventListener postListener){
        Log.i("meconecto:","buscara el usuario:");
        Log.i("meconecto:",idUsuario);
        if(idUsuario!="") {
            DatabaseReference myRef = database.getReference("usuarios/" + idUsuario);
// [START post_value_event_listener]
            myRef.addValueEventListener(postListener);
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("meconecto/usuarios/"+);*/
        }
    }
}
