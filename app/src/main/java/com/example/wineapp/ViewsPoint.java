package com.example.wineapp;

import java.util.ArrayList;

public class ViewsPoint {
    private ArrayList<Double> xPoints;
    private ArrayList<Double> yPoints;
    private ArrayList<Double> zPoints;
    public ViewsPoint(){
        xPoints = new ArrayList<>();
        yPoints = new ArrayList<>();
        zPoints = new ArrayList<>();
    }

    public ArrayList<Double> getxPoints() {
        return xPoints;
    }

    public ArrayList<Double> getyPoints() {
        return yPoints;
    }

    public ArrayList<Double> getzPoints() {
        return zPoints;
    }

    public void addxPoint(Double num){
        xPoints.add(num);
    }

    public void addyPoint(Double num){
        yPoints.add(num);
    }

    public void addzPints(Double num){
        zPoints.add(num);
    }

    public void deletePoint(){
        xPoints.clear();
        yPoints.clear();
        zPoints.clear();
    }
}
