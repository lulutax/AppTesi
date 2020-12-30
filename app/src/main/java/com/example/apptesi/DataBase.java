package com.example.apptesi;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.List;

public class DataBase {

    DatabaseReference dbRef;

    boolean isInTheDb = false;
    long numPerson=0;


    DataBase(){

        dbRef = FirebaseDatabase.getInstance().getReference();



    }


    public void  headMap(Context context ){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<LatLng>temp = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //UserLocation userHeat = (UserLocation) postSnapshot.getValue();
                    double latitudP = Double.valueOf(postSnapshot.child("latitude").getValue().toString());
                    double longituP = Double.valueOf(postSnapshot.child("longitude").getValue().toString());
                    LatLng l = new LatLng(latitudP, longituP);
                    temp.add(l);


                }
                HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                        .data(temp).build();
                TileOverlay overlay = GPS.gmap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
                overlay.clearTileCache();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    });

}

            public boolean existIntheDb (final String android_id){
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(android_id)){
                            isInTheDb = true;
                        } else {
                            isInTheDb = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                return isInTheDb;
            }

            public void setValue (String area,String android_id, UserLocation user){
                dbRef.child(area).child(android_id).setValue(user);
            }

            public void removeValue (String area,String android_id){
                dbRef.child(android_id).removeValue();

            }


            public void  getNumberOfPerson (final Context context) {

                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        numPerson = dataSnapshot.getChildrenCount();

                        Log.d("db", String.valueOf(numPerson));
                        Toast.makeText(context, "ci sono" + numPerson + " persone all'unical!", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

        }