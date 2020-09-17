package com.map.rsd.busapplication.Event;

public class Event_Item {

    String name;
    String description;
    String start_date ,end_date;
    public Event_Item(){};
    public Event_Item( String name,String description,  String start_date ,String end_date){
        this.name=name;
        this.description=description;
        this.start_date=start_date;
        this.end_date=end_date;
    }

    public String getname() {
        return name;
    }
    public String getdescription() {return description;}

    public void setname(String name) {
        this.name = name;
    }
    public void setdescription(String description) {
        this.description = description;
    }


    public String getstart_date() {
        return start_date;
    }
    public String getend_date() {return end_date;}

    public void setstart_date(String start_date) {
        this.start_date= start_date;
    }
    public void setend_date(String end_date) {
        this.end_date = end_date;
    }




}
