package com.example.wineapp;

import java.util.ArrayList;

public class WineData {
    private int wineNum;//ワインデータの数

    private ArrayList<Double> wineIndexList;
    private ArrayList<Double> wineIdoList;
    private ArrayList<Double> wineKedoList;
    private ArrayList<String> wineNameList;
    private ArrayList<Double> wineColorList;
    private ArrayList<Double> wineTasteList;
    private ArrayList<Double> winePriceList;

    public WineData(){
        wineNum = 0;
        wineIndexList = new ArrayList<>();
        wineIdoList = new ArrayList<>();
        wineKedoList = new ArrayList<>();
        wineNameList = new ArrayList<>();
        wineColorList = new ArrayList<>();
        wineTasteList = new ArrayList<>();
        winePriceList = new ArrayList<>();


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

    public ArrayList<String> getWineNameList() {
        return wineNameList;
    }

    public ArrayList<Double> getWineColorList() {
        return wineColorList;
    }

    public ArrayList<Double> getWineTasteList() {
        return wineTasteList;
    }

    public ArrayList<Double> getWinePriceList() {
        return winePriceList;
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

    public void addWineNameList(String str){
        wineNameList.add(str);
    }

    public void addWineColorList(String str){
        wineColorList.add(Double.parseDouble(str));
    }

    public void addWineTasteList(String str){
        wineTasteList.add(Double.parseDouble(str));
    }

    public void addWinePriceList(String str){
        winePriceList.add(Double.parseDouble(str));
    }
}
