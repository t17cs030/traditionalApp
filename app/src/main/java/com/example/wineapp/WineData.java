package com.example.wineapp;

import java.util.ArrayList;

public class WineData {
    private int wineNum;//ワインデータの数

    private ArrayList<Double> wineIndexList;
    private ArrayList<Double> wineIdoList;
    private ArrayList<Double> wineKedoList;

    public WineData(){
        wineNum = 0;
        wineIndexList = new ArrayList<>();
        wineIdoList = new ArrayList<>();
        wineKedoList = new ArrayList<>();
    }

    public ArrayList<Double> getWineIndexList() {
        return wineIndexList;
    }

    public ArrayList<Double> getWineIdoList() {
        return wineIdoList;
    }

    public ArrayList<Double> getWineKedoList() {
        return wineKedoList;
    }

    public int getWineNum() {
        return wineNum;
    }

    public void setWineNum(int wineNum) {
        this.wineNum = wineNum;
    }

    public void addWineIndexList(String str){
        wineIndexList.add(Double.parseDouble(str));
    }

    public void addWineIdoList(String str){
        wineIdoList.add(Double.parseDouble(str));
    }

    public void addWineKedoList(String str){
        wineKedoList.add(Double.parseDouble(str));
    }
}
