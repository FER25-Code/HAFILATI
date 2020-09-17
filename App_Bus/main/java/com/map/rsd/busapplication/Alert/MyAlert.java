package com.map.rsd.busapplication.Alert;

public class MyAlert {

    String name ,description;
    int level ,id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyAlert(String name, String description, int level) {
        this.name = name;
        this.description = description;
        this.level = level;
    }

    public MyAlert() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
