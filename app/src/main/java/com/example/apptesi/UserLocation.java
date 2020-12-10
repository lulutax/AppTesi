package com.example.apptesi;

public class UserLocation {
    public String imei;
    public double latitude;
    public double longitude;

    public String getImei() {
        return imei;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
