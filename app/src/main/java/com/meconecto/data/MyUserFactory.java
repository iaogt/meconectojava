package com.meconecto.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MyUserFactory {

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener authStateListener;


    public MyUserFactory(FirebaseAuth.AuthStateListener authList){
        mAuth = FirebaseAuth.getInstance();
        authStateListener = authList;
    }

    public void authUserAnonymous(OnCompleteListener<AuthResult> doneList){
        mAuth.signInAnonymously()
                .addOnCompleteListener(doneList);
    }


    public String getCurrentUser() {
        String uid =null;
        FirebaseUser us = mAuth.getCurrentUser();
        if(us!=null){
            uid=us.getUid();
        }
        return uid;
    }


}
