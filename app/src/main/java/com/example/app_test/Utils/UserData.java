package com.example.app_test.Utils;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;

public class UserData {
    static private UserData instance;
    private HashMap<String, String> user_data;

    DatabaseReference reference;
    FirebaseUser curr_user;

    private UserData(){
        this.reference = FirebaseDatabase.getInstance().getReference();
        this.curr_user = FirebaseAuth.getInstance().getCurrentUser();
        user_data = new HashMap<>();

        reference.child("Users").child(curr_user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user_data = (HashMap<String, String>) snapshot.getValue();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
    }
    public static UserData getInstance(){
        if (instance == null){
            instance = new UserData();
        }
        return instance;
    }
    public static void init(){ UserData.getInstance(); }
    public HashMap<String, String> getData(){
        return user_data;
    }
}
