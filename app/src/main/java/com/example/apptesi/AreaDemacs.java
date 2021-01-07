package com.example.apptesi;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class AreaDemacs extends AreeUnical {
    ArrayList<LatLng> coordinate;
    PolygonOptions polygonOptions;
    GoogleMap gmap;

    AreaDemacs(){
        coordinate= new ArrayList<>();

        gmap=GPS.gmap;

        //Areea Demacs-->inserisco le coordinate
        polygonOptions = new PolygonOptions()
                .add(new LatLng(39.362948, 16.226032),
                        new LatLng(39.363018, 16.226657),
                        new LatLng(39.363539, 16.226593),
                        new LatLng(39.363547, 16.225923),
                        new LatLng(39.362948, 16.226032));
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
        return "demacs";
    }

    @Override
    public ArrayList<LatLng> getCoordinate() {
        return coordinate;
    }


    public LatLng getCoordinateMarker(){

        return new LatLng(39.362948, 16.226032);
    }

}
