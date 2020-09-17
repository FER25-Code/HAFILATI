package com.map.rsd.busapplication.Station;

public class MyStation {

    double latitude ,longitude ;
    String nameStation ;

    public MyStation() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNameStation() {
        return nameStation;
    }

    public void setNameStation(String nameStation) {
        this.nameStation = nameStation;
    }
}
