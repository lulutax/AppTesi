package com.example.apptesi;

import android.graphics.Color;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class Martensson extends AreeUnical {
    ArrayList<LatLng> coordinate;
    PolygonOptions polygonOptions;
    GoogleMap gmap;
    MarkerOptions markerOptions;
    LatLng coordinateMarker;

    Martensson(){
        coordinate= new ArrayList<>();
        coordinateMarker = new LatLng(39.362008, 16.223631);


        gmap=GPS.gmap;
        markerOptions= new MarkerOptions()
                .position(coordinateMarker)
                .draggable(true).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.mensacentrale));
        //Martensson-->inserisco le coordinate
        polygonOptions = new PolygonOptions()
                .add(new LatLng(39.362937, 16.224484),
                        new LatLng(39.362796, 16.223189),
                        new LatLng(39.361519, 16.223382),
                        new LatLng(39.361846, 16.224675),
                        new LatLng(39.362937, 16.224484));
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
        return "martensson";
    }

    @Override
    public ArrayList<LatLng> getCoordinate() {
        return coordinate;
    }


    public LatLng getCoordinateMarker(){

        return coordinateMarker;
    }



    @Override
    public void setMarker(int p) {
        markerOptions.title(getName()+" ci sono "+ p +"persone");
        gmap.addMarker(markerOptions);



    }

}