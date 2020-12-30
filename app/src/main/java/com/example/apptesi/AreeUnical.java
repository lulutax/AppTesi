package com.example.apptesi;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public abstract class AreeUnical {
    public abstract void drawArea();
    public abstract String getName();
    public abstract ArrayList<LatLng> getCoordinate();

}
