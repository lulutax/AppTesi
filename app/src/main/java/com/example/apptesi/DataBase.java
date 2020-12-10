package com.example.apptesi;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataBase {

    DatabaseReference dbRef,usRef;
    boolean isInTheDb = false;
    long numPerson=0;


    DataBase(){

        dbRef = FirebaseDatabase.getInstance().getReference();
        usRef = FirebaseDatabase.getInstance().getReference().child("UserLocation");
    }



    public boolean existIntheDb(String android_id){

        usRef.orderByChild("imei").equalTo(android_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                     isInTheDb = true;
                    Log.d("situazione","è nel db");
                } else {
                    isInTheDb = false;
                    Log.d("situazione","non è nel db");

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return isInTheDb;
    }

    public void setValue(String android_id, UserLocation user){
        usRef.child(android_id).setValue(user);
        Log.d("situazione","ho aggiornato le coordinate");
    }

    public void removeValue(String android_id){
        usRef.child(android_id).removeValue();

    }


    public long getNumberOfPerson(){
        usRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numPerson = dataSnapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return numPerson;
    }


    public void headMap() {

    }
}
