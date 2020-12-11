package com.example.apptesi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class GPS extends AppCompatActivity implements LocationListener, OnMapReadyCallback  {
    protected LocationManager locationManager;
    double lat, lon;
    LatLng myCoordinate = null;
    public  GoogleMap gmap;
    String android_id;
   // public ArrayList<LatLng> coordinateAreaUnical;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_ANDROID_ID = 3;
    public UserLocation user;
    boolean isIn;
   // Button stop;
    private Intent intentLocationService ;
    DataBase db;
    Unical unical;
private Toolbar myToolbar;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu); // inflate your menu resource

        if (menu != null) {
            MenuItem serv = menu.findItem(R.id.closeLocationService);
            serv.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.d("prova","funziona");
                    return true;
                    }
            });

        }
        return true;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ){

            ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        }

      myToolbar  = findViewById(R.id.toolbar);

        user = new UserLocation();
        idPhone();
        db = new DataBase();
        user.setImei(android_id);
        //stop= findViewById(R.id.stopButton);
      // stop.setOnClickListener(new View.OnClickListener() {
           /* @Override
            public void onClick(View v) {
                stopService(intentLocationService);
            }
        });*/

    //latLng = new LocationService();
    //LatLng prova = latLng.startLocation(this);

    }

   /* @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

            getMenuInflater().inflate(R.layout.menu_toolbar,menu);
            return true;

    }*/

    public void startLocationBackground(){

        if( ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.ACCESS_BACKGROUND_LOCATION},2);
        }
      // intentLocationService = new Intent(getApplicationContext(),LocationService.class);
      //  startService(intentLocationService);
     //   Log.d("LOCATION_UPDATE","start");


    }
    @Override
    public void onLocationChanged(Location location) {
       LatLng newCoordinate = new LatLng(location.getLatitude(), location.getLongitude());
        if (myCoordinate == null) {
            myCoordinate = newCoordinate;
            reset();
        } else if (myCoordinate != null && (myCoordinate.latitude != newCoordinate.latitude && myCoordinate.longitude != newCoordinate.longitude)) {

            myCoordinate = newCoordinate;
            reset();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
            Log.d("position","disable");
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
            Log.d("Latitude","enable");
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
            Log.d("situazione","statusChanged");
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

                    android_id = Settings.Secure.getString(this.getContentResolver(),
                            Settings.Secure.ANDROID_ID);

                } else if (grantResults.length > 0
                        && grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();

                }
            }
            case 2: {
                Log.d("background","permesso");
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
        LatLng coordinates = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions().position(coordinates).title("io sono qui"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));

        unical = new Unical(this);
        unical.drawAreaUnical();
        //startLocationBackground();

    }


    public void idPhone(){
        //permessi
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_ACCESS_ANDROID_ID);

        }else {
            android_id = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            Log.d("situazione","imei telefono "+android_id);
        }

    }


    public void reset(){

        user.setLatitude(myCoordinate.latitude);
        user.setLongitude(myCoordinate.longitude);

        //se l'utente era gia presente all'interno dell unical
        isIn= db.existIntheDb(android_id);

        if(unical.isInTheArea(myCoordinate)==true){
            Toast.makeText(this, "sei all'unical!", Toast.LENGTH_SHORT).show();

            //se è nel db aggiorno le coordinate
           if(isIn== true){
               db.setValue(android_id,user);

           }
            //altrimenti lo aggiungo
            else if(isIn == false){
               user.setLatitude(myCoordinate.latitude);
               user.setLongitude(myCoordinate.longitude);
               db.setValue(android_id,user);
               Log.d("situazione","ho inserito l'utente");
           }
            //verifico quante persone ci sono all'interno dell'unical
            long nPerson = db.getNumberOfPerson();
            Toast.makeText(this, "ci sono" +nPerson+" persone all'unical!", Toast.LENGTH_SHORT).show();
        }
        //lo rimuovo se le coordinate non sono all'interno dell'unical
        else{
            Toast.makeText(this, "sei fuori dall unical!", Toast.LENGTH_SHORT).show();
           db.removeValue(android_id);
        }
        gmap.clear();
        Log.d("unical","dev essere ultimo");
        unical.drawAreaUnical();
        gmap.addMarker(new MarkerOptions().position(myCoordinate).title("io sono qui"));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoordinate, 15));
        //addHeadMap();

    }

    //mappa di calore
 /*   private void addHeadMap() {

         final List<LatLng> list = new ArrayList<>();

        usRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // listas.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //UserLocation userHeat = (UserLocation) postSnapshot.getValue();
                    double latitudP= Double.valueOf(postSnapshot.child("latitude").getValue().toString());
                    double longituP= Double.valueOf(postSnapshot.child("longitude").getValue().toString());
                    LatLng l= new LatLng(latitudP,longituP);
                    list.add(l);


                }

                HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                        .data(list).build();
                TileOverlay overlay = gmap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
                //overlay.clearTileCache();

        }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }*/



}


