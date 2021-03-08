package com.example.apptesi;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;

public class Unical {
     PolygonOptions polygonOptions;
     boolean result;
     ArrayList<LatLng> coordinateAreaUnical;
     public GoogleMap gmap;
     ArrayList<AreeUnical> listAree;

     public Unical() {
         coordinateAreaUnical = new ArrayList<>();
         listAree = new ArrayList<>();
         gmap = GPS.gmap;

        //Le coordinate dell'area dell'Unical
        polygonOptions = new PolygonOptions()
                .add(new LatLng(39.368771, 16.224308), new LatLng(39.365013, 16.223652),
                        new LatLng(39.364225, 16.224296), new LatLng(39.363595, 16.220691),
                        new LatLng(39.362434, 16.219758), new LatLng(39.361546, 16.220595),
                        new LatLng(39.360899, 16.225337), new LatLng(39.358974, 16.221689),
                        new LatLng(39.355490, 16.220895), new LatLng(39.354279, 16.222826),
                        new LatLng(39.353897, 16.226302), new LatLng(39.355108, 16.226366),
                        new LatLng(39.355970, 16.228169), new LatLng(39.355057, 16.230744),
                        new LatLng(39.355588, 16.232804), new LatLng(39.358701, 16.232800),
                        new LatLng(39.358112, 16.229463), new LatLng(39.357191, 16.229699),
                        new LatLng(39.356876, 16.228025), new LatLng(39.364913, 16.227214),
                        new LatLng(39.366804, 16.227021), new LatLng(39.368771, 16.224308));
        Polygon polygon = gmap.addPolygon(polygonOptions);
        polygon.setStrokeColor(Color.RED);



        coordinateAreaUnical= (ArrayList<LatLng>) polygon.getPoints();

        //aggiungo le aree nel listArray servono per poter effettuare controlli sulle aree nel db
        listAree.add(new AreaDemacs());
        listAree.add(new Martensson());
        listAree.add(new Bar());
        listAree.add(new Biblioteca());

    }

    public void drawAreaUnical() {

         Polygon polygon = gmap.addPolygon(polygonOptions);
        polygon.setStrokeColor(Color.RED);

    }


     public boolean isInTheArea(LatLng pos){

        if (pos == null){
            return false;
        }
        return PolyUtil.containsLocation(pos,coordinateAreaUnical,true);
    }


     public String findMyArea(LatLng pos){
         result = false;
         if (pos == null){
            return null;
         }

        for(int i =0; i<listAree.size();i++){
            result = PolyUtil.containsLocation(pos,listAree.get(i).getCoordinate(),true) ;
            if(result== true){
                return listAree.get(i).getName();
            }
        }
        return "dadefinire";
    }

        public ArrayList<AreeUnical> getListAree() {
        return listAree;
    }
}