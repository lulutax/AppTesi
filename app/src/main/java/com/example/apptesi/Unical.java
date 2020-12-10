package com.example.apptesi;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;

public class Unical {
    private PolygonOptions polygonOptions;
    private ArrayList<LatLng> coordinateAreaUnical;
    public GoogleMap gmap;

    public Unical(GPS gps) {

        gmap = gps.gmap;
    }

    public void drawAreaUnical() {
         polygonOptions = new PolygonOptions()
                .add(new LatLng(39.368771, 16.224308),
                        new LatLng(39.365013, 16.223652),
                        new LatLng(39.364225, 16.224296),
                        new LatLng(39.363595, 16.220691),
                        new LatLng(39.362434, 16.219758),
                        new LatLng(39.361546, 16.220595),
                        new LatLng(39.360899, 16.225337),
                        new LatLng(39.358974, 16.221689),
                        new LatLng(39.355490, 16.220895),
                        new LatLng(39.354279, 16.222826),
                        new LatLng(39.353897, 16.226302),
                        new LatLng(39.355108, 16.226366),
                        new LatLng(39.355970, 16.228169),
                        new LatLng(39.355057, 16.230744),
                        new LatLng(39.355588, 16.232804),
                        new LatLng(39.358701, 16.232800),
                        new LatLng(39.358112, 16.229463),
                        new LatLng(39.357191, 16.229699),
                        new LatLng(39.356876, 16.228025),
                        new LatLng(39.364913, 16.227214),
                        new LatLng(39.366804, 16.227021),
                        new LatLng(39.368771, 16.224308));
        Polygon polygon = gmap.addPolygon(polygonOptions);
        polygon.setStrokeColor(Color.RED);
        coordinateAreaUnical= (ArrayList<LatLng>) polygon.getPoints();
    }


    public boolean isInTheArea(LatLng pos){


        if (pos == null){
            return false;
        }
        return PolyUtil.containsLocation(pos,coordinateAreaUnical,true);
    }

}