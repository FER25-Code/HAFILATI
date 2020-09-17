package com.map.fer.t_bus.Station;



public class MyStation {

    String station ,NameStation;
    String bus;
    String arrival_time ,departure_time;

    public String getNameStation() {
        return NameStation;
    }

    public void setNameStation(String nameStation) {
        NameStation = nameStation;
    }

    double latitude,longitude;
    String line_number , color_type;
    Double latitude1, longitude1,latitude2,longitude2;

    public String getLine_number() {
        return line_number;
    }

    public String getColor_type() {
        return color_type;
    }

    public void setColor_type(String color_type) {
        this.color_type = color_type;
    }

    public void setLine_number(String line_number) {
        this.line_number = line_number;
    }

    public Double getLatitude1() {
        return latitude1;
    }

    public void setLatitude1(Double latitude1) {
        this.latitude1 = latitude1;
    }

    public Double getLongitude1() {
        return longitude1;
    }

    public void setLongitude1(Double longitude1) {
        this.longitude1 = longitude1;
    }

    public Double getLatitude2() {
        return latitude2;
    }

    public void setLatitude2(Double latitude2) {
        this.latitude2 = latitude2;
    }

    public Double getLongitude2() {
        return longitude2;
    }

    public void setLongitude2(Double longitude2) {
        this.longitude2 = longitude2;
    }

    public MyStation(){};
    public MyStation(String bus, String arrival_time , String departure_time){
        this.bus=bus;
        this.arrival_time=arrival_time;
    this.departure_time=departure_time;
    }


    public  void setlatitude(double latitude) {

        this.latitude = latitude;
    }

    public  float getlongitude(){
        return (float) longitude;
    }
    public  void setlongitude(double longitude) {

        this.longitude = longitude;
    }

    public  float getlaltitude(){
        return (float) latitude;
    }


    public String getstation() {
        return station;
    }
    public String getBus() {return bus;}

    public void setstation(String station) {
        this.station = station;
    }
    public void setbus(String bus) {
        this.bus = bus;
    }


    public String getarrival_time() {
        return arrival_time;
    }
    public String getdeparture_time() {return departure_time;}

    public void setarrival_time(String arrival_time) {
        this.arrival_time= arrival_time;
    }
    public void setdeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

}
