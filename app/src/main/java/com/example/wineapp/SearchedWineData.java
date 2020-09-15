package com.example.wineapp;

import java.util.ArrayList;

public class SearchedWineData {
    private int wineNum;

    private ArrayList<Integer> wineIndexList;

    private ArrayList<String> wineNameList;
    private ArrayList<Integer> wineColorList;
    private ArrayList<Integer> wineTasteList;
    private ArrayList<Integer> winePriceList;


    public SearchedWineData(){
        wineNum=0;
        wineIndexList = new ArrayList<>();
        wineNameList = new ArrayList<>();
        wineColorList = new ArrayList<>();
        wineTasteList = new ArrayList<>();
        winePriceList = new ArrayList<>();
    }


    public ArrayList<Integer> getWineIndexList() {
        return wineIndexList;
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

    public int getWineNum(){
        return wineNum;
    }



    public void setWineIndexList(ArrayList<Integer> wineIndexList) {
        this.wineIndexList = wineIndexList;
    }

    public void setWineNameList(ArrayList<String> wineNameList) {
        this.wineNameList = wineNameList;
    }

    public void setWineColorList(ArrayList<Integer> wineColorList) {
        this.wineColorList = wineColorList;
    }

    public void setWineTasteList(ArrayList<Integer> wineTasteList) {
        this.wineTasteList = wineTasteList;
    }

    public void setWinePriceList(ArrayList<Integer> winePriceList) {
        this.winePriceList = winePriceList;
    }


    public void setWineNum(int wineNum) {
        this.wineNum = wineNum;
    }
}
