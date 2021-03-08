package com.example.apptesi;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GPS extends AppCompatActivity implements LocationListener, OnMapReadyCallback  {
    protected LocationManager locationManager;
    LatLng myCoordinate = null;
    static  GoogleMap gmap;
    static String android_id;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_ANDROID_ID = 3;
    static UserLocation user;
    static DataBase db;
    static Unical unical;
    private Toolbar myToolbar;
    String area;
   static Context context;
    static Intent intentLocationService;
   public static LocationService locationService;
    TelephonyManager telephonyManager;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu); // inflate your menu resource

        if (menu != null) {
            MenuItem serv = menu.findItem(R.id.settings);
            serv.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    startActivity(new Intent(GPS.this,SettingsActivity.class));
                    return true;
                    }
            });

            MenuItem privacy = menu.findItem(R.id.privacy);
            privacy.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    startActivity( new Intent( GPS.this, PrivacyActivity.class));
                    return false;
                }
            });

        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE)
                != PackageManager.PERMISSION_GRANTED ){

                    ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.FOREGROUND_SERVICE},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        }

        myToolbar  = findViewById(R.id.toolbarApp);
        setSupportActionBar(myToolbar);
        user = new UserLocation();
         telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        idPhone();



    }


    @Override
    public void onLocationChanged(Location location) {
       LatLng newCoordinate = new LatLng(location.getLatitude(), location.getLongitude());
        if (myCoordinate == null) {
            myCoordinate = newCoordinate;
            reset();
        } else if (myCoordinate != null && (myCoordinate.latitude != newCoordinate.latitude &&
                myCoordinate.longitude != newCoordinate.longitude)) {

            myCoordinate = newCoordinate;
            reset();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Location","disable");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirm Location");
        alertDialog.setMessage("Your Location is enabled, please enjoy");
        alertDialog.setNegativeButton("Back to maps", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();        }

    @Override
    public void onProviderEnabled(String provider) {

        Log.d("Location","enable");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirm Location");
        alertDialog.setMessage("Your Location is enabled, please enjoy");
        alertDialog.setNegativeButton("Back to maps", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
        }


     @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Location","statusChanged");
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();

                } else if (grantResults.length > 0
                        && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();

                }
            }
            case MY_PERMISSIONS_REQUEST_ACCESS_ANDROID_ID: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    android_id= telephonyManager.getImei();


                } else if (grantResults.length > 0
                        && grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();

                }
            }
            case 2: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length > 0
                        && grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        LatLng coordinates = new LatLng(39.362385, 16.226529);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));

        unical = new Unical();
        unical.drawAreaUnical();
        db = new DataBase(this);
       // startLocationBackground();
        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        //settings.setMyLocationButtonEnabled(false);
        locationService= new LocationService();
        intentLocationService= new Intent(getApplicationContext(),LocationService.class);
        //startService(intentLocationService);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void idPhone(){
        //permessi
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_ACCESS_ANDROID_ID);
        }else {
            android_id= telephonyManager.getImei();
        }
    }


    public void reset(){

        user.setLatitude(myCoordinate.latitude);
        user.setLongitude(myCoordinate.longitude);
        user.setArea(unical.findMyArea(myCoordinate));

        if(unical.isInTheArea(myCoordinate)) {
           db.userRef.child(android_id).setValue(user);
        }else{
            db.userRef.child(android_id).removeValue();
        }

        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoordinate, 15));

    }



    public void stopLocation(){
        //stopService(intentLocationService);
    }



}


