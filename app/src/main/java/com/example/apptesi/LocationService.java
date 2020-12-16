package com.example.apptesi;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class LocationService extends  Service {


    private double latitude,longitude;

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            if (locationResult != null && locationResult.getLastLocation() != null) {
                latitude = locationResult.getLastLocation().getLatitude();
                longitude = locationResult.getLastLocation().getLongitude();
                LatLng newCoordinate = new LatLng(latitude, longitude);

                Log.d("prova", latitude + " - " + longitude);

            }


        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("not Yet IMPLEMENTED");
    }


    public void startLocationService() {

        Log.d("LOCATION_UPDATE","startLocationService");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"Location_notification_chanel");

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("LocationService");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText("Running");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);



        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(60000);
        locationRequest.setFastestInterval(60000); // update location every minute
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(getApplicationContext()).requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        startForeground(175,builder.build());

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startLocationService();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("prova","stop");
        LocationServices.getFusedLocationProviderClient(getApplicationContext()).removeLocationUpdates(locationCallback);
        stopForeground(true);

        stopSelf();
    }



}
