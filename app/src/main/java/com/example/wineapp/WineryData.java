package com.example.wineapp;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

public class WineryData implements Serializable {
    private ArrayList<Integer> wineryID;
    private ArrayList<String> wineryName;
    private ArrayList<String> wineryAddress;
    private ArrayList<LatLng> wineryLatLng;

    private int wineryNum;

    public WineryData(){
        wineryID = new ArrayList<>();
        wineryName = new ArrayList<>();
        wineryAddress = new ArrayList<>();
        wineryLatLng = new ArrayList<>();
        wineryNum=0;
    }

    public void setWineryData(WineryData wineryData) {
        setWineryID(wineryData.getWineryID());
        setWineryName(wineryData.getWineryName());
        setWineryAddress(wineryData.getWineryAddress());
        setWineryLatLng(wineryData.getWineryLatLng());

        setWineryNum(wineryData.getWineryNum());
    }

    public ArrayList<Integer> getWineryID() {
        return wineryID;
    }
    public ArrayList<String> getWineryName() {
        return wineryName;
    }
    public ArrayList<String> getWineryAddress() {
        return wineryAddress;
    }
    public ArrayList<LatLng> getWineryLatLng() {
        return wineryLatLng;
    }

    public int getWineryNum() {
        return wineryNum;
    }





    public void setWineryID(ArrayList<Integer> wineryID) {
        this.wineryID = wineryID;
    }

    public void setWineryName(ArrayList<String> wineryName) {
        this.wineryName = wineryName;
    }

    public void setWineryAddress(ArrayList<String> wineryAddress) {
        this.wineryAddress = wineryAddress;
    }

    public void setWineryLatLng(ArrayList<LatLng> wineryLatLng) {
        this.wineryLatLng = wineryLatLng;
    }

    public void setWineryNum(int wineryNum) {
        this.wineryNum = wineryNum;
    }





    public void addWineryID(String str){
        wineryID.add(Integer.parseInt(str));
    }
    public void addWineryName(String str){
        wineryName.add(str);
    }
    public void addWineryAddress(String str){
        wineryAddress.add(str);
    }
    public void addWineryLatLng(String str1, String str2){
        wineryLatLng.add(new LatLng(Double.parseDouble(str1), Double.parseDouble(str2)));
    }
}
