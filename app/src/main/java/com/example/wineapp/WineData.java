package com.example.wineapp;

import android.content.Intent;

import java.util.ArrayList;

public class WineData {

    private static final long serialVersionUID = 1L;

    private int wineNum;//ワインデータの数

    private ArrayList<Integer> wineIndexList;
    private ArrayList<Double> wineIdoList;
    private ArrayList<Double> wineKedoList;
    private ArrayList<String> wineNameList;
    private ArrayList<Integer> wineColorList;
    private ArrayList<Integer> wineTasteList;
    private ArrayList<Integer> winePriceList;

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

    public ArrayList<Integer> getWineIndexList() {
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

    public ArrayList<Integer> getWineColorList() {
        return wineColorList;
    }

    public ArrayList<Integer> getWineTasteList() {
        return wineTasteList;
    }

    public ArrayList<Integer> getWinePriceList() {
        return winePriceList;
    }


    public int getWineNum() {
        return wineNum;
    }

    public void setWineNum(int wineNum) {
        this.wineNum = wineNum;
    }

    public void addWineIndexList(String str){
        wineIndexList.add(Integer.parseInt(str));
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
        wineColorList.add(Integer.parseInt(str));
    }

    public void addWineTasteList(String str){
        wineTasteList.add(Integer.parseInt(str));
    }

    public void addWinePriceList(String str){
        winePriceList.add(Integer.parseInt(str));
    }
}
