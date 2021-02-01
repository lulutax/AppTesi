package com.example.apptesi;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.app.NotificationManager.*;

public class LocationService extends Service {
    private static final String CHANNEL_ID = "Location_notification_chanel";
    private double latitude, longitude;
    static LatLng myCoordinate;
    int importance=0;
    long interval=300000;
    DatabaseReference userRef;
    static boolean active= false;
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            if (locationResult != null && locationResult.getLastLocation() != null) {
                latitude = locationResult.getLastLocation().getLatitude();
                longitude = locationResult.getLastLocation().getLongitude();
                myCoordinate = new LatLng(latitude, longitude);
                reset();
                Log.d("LOCATION_UPDATE", myCoordinate.latitude + " " + myCoordinate.longitude);


            }
        }
    };


    public LocationService(){
        userRef = FirebaseDatabase.getInstance().getReference().child("user");

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("not Yet IMPLEMENTED");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startLocationService() {



            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d("prova","versioneSbagliata");

                CharSequence name = getString(R.string.channel_name);
                String description = getString(R.string.channel_description);
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);


}


        Log.d("LOCATION_UPDATE", "startLocationService");
        Intent resultIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("LocationService");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText("Running");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);



        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(interval);
        locationRequest.setFastestInterval(interval); // update location every minute
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



        LocationServices.getFusedLocationProviderClient(getApplicationContext()).requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        startForeground(175,builder.build());

    }

    public void setInterval(int value) {
        if(value == 5){
            interval = 300000;

        }else if(value==10)
       {
           interval = 600000;

       }
        else if(value== 15){
            interval = 900000;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        active= true;
        startLocationService();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public boolean stopService(Intent name) {
        Log.d("LOCATION_UPDATE","stopService");
        active= false;

        return super.stopService(name);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationServices.getFusedLocationProviderClient(getApplicationContext()).removeLocationUpdates(locationCallback);
        stopForeground(true);

        stopSelf();
    }
    public void reset(){
        GPS.user.setLatitude(myCoordinate.latitude);
        GPS.user.setLongitude(myCoordinate.longitude);
        GPS.user.setArea(GPS.unical.findMyArea(myCoordinate));

        if(GPS.unical.isInTheArea(myCoordinate)) {
            userRef.child(GPS.android_id).setValue( GPS.user);
        }else{
            userRef.child(GPS.android_id).removeValue();
            Log.d("ISIN","non è all'interno dell unical");
        }

    }




}
