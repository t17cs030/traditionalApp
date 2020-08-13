package com.example.wineapp;

import java.util.ArrayList;

public class ViewsPoint {
    private ArrayList<Double> xPoints;
    private ArrayList<Double> yPoints;

    public ViewsPoint(){
        xPoints = new ArrayList<>();
        yPoints = new ArrayList<>();
    }

    public ArrayList<Double> getxPoints() {
        return xPoints;
    }

    public ArrayList<Double> getyPoints() {
        return yPoints;
    }

    public void addxPoint(Double num){
        xPoints.add(num);
    }

    public void addyPoint(Double num){
        yPoints.add(num);
    }

    public void deletePoint(){
        xPoints.clear();
        yPoints.clear();
    }
}
