package com.example.apptesi;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class Cafe extends AreeUnical {


    ArrayList<LatLng> coordinate;
    PolygonOptions polygonOptions;
    GoogleMap gmap;
    MarkerOptions markerOptions;
    LatLng coordinateMarker;

    Cafe(){
        coordinate= new ArrayList<>();
        coordinateMarker = new LatLng(39.3617, 16.2264);


        gmap=GPS.gmap;
        markerOptions= new MarkerOptions()
                .position(coordinateMarker)
                .draggable(true).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.demacs));
        polygonOptions = new PolygonOptions()
                .add(new LatLng(39.3618, 16.2263),
                        new LatLng(39.3618, 16.2264),
                        new LatLng(39.3617, 16.2264),
                        new LatLng(39.3617, 16.2263),
                        new LatLng(39.3618, 16.2263));
        Polygon polygon = gmap.addPolygon(polygonOptions);
        polygon.setStrokeColor(Color.RED);
        coordinate= (ArrayList<LatLng>) polygon.getPoints();
    }

    @Override
    public void drawArea() {
        Polygon polygon = gmap.addPolygon(polygonOptions); //cancellare poi e inserire un oggetto singolo
        polygon.setStrokeColor(Color.RED);
    }

    @Override
    public String getName() {
        return "cafe";
    }

    @Override
    public ArrayList<LatLng> getCoordinate() {
        return coordinate;
    }

    @Override
    public LatLng getCoordinateMarker() {
        return coordinateMarker;
    }

    @Override
    public void setMarker(int p) {
        markerOptions.title(getName()+" ci sono "+ p +"persone");
        gmap.addMarker(markerOptions);
    }
}
