package com.map.fer.t_bus.Line;

public class MyLine {

    String name,Color,line;
    int NbrLine ;

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }


    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getNbrLine() {
        return NbrLine;
    }

    public void setNbrLine(int nbrLine) {
        NbrLine = nbrLine;
    }

    public MyLine( ) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyLine(String name , String color, int nbrLine) {
  name =name;
        Color = color;
        NbrLine = nbrLine;
    }
}
