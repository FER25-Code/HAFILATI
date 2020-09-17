package com.map.fer.t_bus.Bus;

public class MyBus {


    String Linename, StartTime, ArrivalTime, Bus ,mark;
    int nbrBus , idline;
    double Far ;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
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

    String station;
    String bus;
    String arrival_time ,departure_time;
    double latitude,longitude;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getIdline() {
        return idline;
    }

    public void setIdline(int idline) {
        this.idline = idline;
    }

    public double getFar() {
        return Far;
    }

    public void setFar(double far) {
        Far = far;
    }

    public String getBus() {
        return Bus;
    }

    public void setBus(String bus) {
        Bus = bus;
    }


    public int getNbrBus() {
        return nbrBus;
    }

    public void setNbrBus(int nbrBus) {
        this.nbrBus = nbrBus;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }


    public String getLinename() {
        return Linename;
    }

    public void setLinename(String linename) {
        Linename = linename;
    }

    public MyBus() {

    }

}
