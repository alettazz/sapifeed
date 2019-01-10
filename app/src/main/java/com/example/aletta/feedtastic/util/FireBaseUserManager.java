package com.example.aletta.feedtastic.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.aletta.feedtastic.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireBaseUserManager {

    private static final String TAG = FireBaseUserManager.class.getSimpleName();
    private static FireBaseUserManager instance;
    private static Context ctx;
    private static DatabaseReference myRef;
    private static FirebaseDatabase database;


    private FireBaseUserManager(Context context) {
        ctx = context;
    }

    public static void init(Context context) {
        ctx = context;
    }

    public static FireBaseUserManager getInstance() {
        if (instance == null) {
            instance = new FireBaseUserManager(ctx);
            database = FirebaseDatabase.getInstance();

        }
        return instance;
    }

    public static boolean checkUserLoggedIn() {

        return (FirebaseAuth.getInstance().getCurrentUser() != null);
    }

    public  void write(String phoneIdentifier) {
        // Write a message to the database
        myRef = database.getReference("users");

        if (phoneIdentifier != null) {

        myRef.child(phoneIdentifier).setValue(SharedPrefManager.getInstance().getData("USER",User.class));
        }
    }

    public  void read() {
        // Read from the database

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
