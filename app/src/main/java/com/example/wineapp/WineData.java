package com.example.wineapp;

import android.content.Intent;

import java.util.ArrayList;

public class WineData {

    private static final long serialVersionUID = 1L;

    private int wineNum;//ワインデータの数

    private ArrayList<Integer> wineIndexList;
    private ArrayList<Double> wineIdoList;
    private ArrayList<Double> wineKedoList;
    private ArrayList<Integer> wineColorList;
    private ArrayList<Integer> wineTypeList;
    private ArrayList<Integer> wineTasteList;
    private ArrayList<Integer> winePriceList;
    private ArrayList<Integer> wineCapacityList;
    private ArrayList<String> wineNameList;
    private ArrayList<String> wineFuriganaList;
    private ArrayList<String> wineryNameList;
    private ArrayList<String> wineExplanationList;

    public WineData(){
        wineNum = 0;
        wineIndexList = new ArrayList<>();
        wineIdoList = new ArrayList<>();
        wineKedoList = new ArrayList<>();
        wineColorList = new ArrayList<>();
        wineTypeList = new ArrayList<>();
        wineTasteList = new ArrayList<>();
        winePriceList = new ArrayList<>();
        wineCapacityList = new ArrayList<>();
        wineNameList = new ArrayList<>();
        wineFuriganaList = new ArrayList<>();
        wineryNameList = new ArrayList<>();
        wineExplanationList = new ArrayList<>();
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



    public ArrayList<Integer> getWineColorList() {
        return wineColorList;
    }

    public ArrayList<Integer> getWineTypeList() {
        return wineTypeList;
    }

    public ArrayList<Integer> getWineTasteList() {
        return wineTasteList;
    }

    public ArrayList<Integer> getWinePriceList() {
        return winePriceList;
    }

    public ArrayList<Integer> getWineCapacityList() {
        return wineCapacityList;
    }

    public ArrayList<String> getWineNameList() {
        return wineNameList;
    }

    public ArrayList<String> getWineFuriganaList() {
        return wineFuriganaList;
    }

    public ArrayList<String> getWineryNameList() {
        return wineryNameList;
    }

    public ArrayList<String> getWineExplanationList() {
        return wineExplanationList;
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

    public void addWineColorList(String str){
        wineColorList.add(Integer.parseInt(str));
    }

    public void addWineTypeList(String str){
        wineTypeList.add(Integer.parseInt(str));
    }

    public void addWineTasteList(String str){
        wineTasteList.add(Integer.parseInt(str));
    }

    public void addWinePriceList(String str){
        winePriceList.add(Integer.parseInt(str));
    }

    public void addWineCapacityList(String str){
        wineCapacityList.add(Integer.parseInt(str));
    }

    public void addWineNameList(String str){
        wineNameList.add(str);
    }

    public void addWineFuriganaList(String str){
        wineFuriganaList.add(str);
    }

    public void addWineryNameList(String str){
        wineryNameList.add(str);
    }

    public void addWineExplanationList(String str){
        wineExplanationList.add(str);
    }
}
