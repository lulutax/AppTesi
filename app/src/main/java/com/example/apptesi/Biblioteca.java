package com.example.apptesi;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class Biblioteca extends AreeUnical{


    ArrayList<LatLng> coordinate;
    PolygonOptions polygonOptions;
    GoogleMap gmap;
    MarkerOptions markerOptions;
    LatLng coordinateMarker;

    Biblioteca(){
        coordinate= new ArrayList<>();
        coordinateMarker = new LatLng(39.3617, 16.2249);
        gmap=GPS.gmap;
        markerOptions= new MarkerOptions()
                .position(coordinateMarker)
                .draggable(true).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.biblioteca));
        //Areea -->inserisco le coordinate
        polygonOptions = new PolygonOptions()
                .add(new LatLng(39.3619, 16.2248),
                        new LatLng(39.3611, 16.2249),
                        new LatLng(39.3612, 16.2258),
                        new LatLng(39.3620, 16.2257),
                        new LatLng(39.3619, 16.2248));
        Polygon polygon = gmap.addPolygon(polygonOptions);
        polygon.setVisible(false);
        //  polygon.setStrokeColor(Color.RED);
        coordinate= (ArrayList<LatLng>) polygon.getPoints();
    }


    @Override
    public void drawArea() {
        Polygon polygon = gmap.addPolygon(polygonOptions); //cancellare poi e inserire un oggetto singolo
        polygon.setStrokeColor(Color.RED);
    }

    @Override
    public String getName() {
        return "Biblioteca";
    }

    @Override
    public ArrayList<LatLng> getCoordinate() {
        return coordinate;
    }



    @Override
    public void setMarker(int p) {
        markerOptions.title(getName()+" ci sono "+ p +"persone");
        gmap.addMarker(markerOptions);



    }

    public LatLng getCoordinateMarker(){

        return new LatLng(39.3617, 16.2249);
    }

}
