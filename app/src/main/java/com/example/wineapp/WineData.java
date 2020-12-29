package com.example.wineapp;

import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;

public class WineData implements Serializable {

    private static final long serialVersionUID = 1L;

    private int wineNum;//ワインデータの数

    private ArrayList<Integer> wineIndexList;
    private ArrayList<Double> wineIdoList;
    private ArrayList<Double> wineKedoList;
    private ArrayList<String> wineColorList;
    private ArrayList<String> wineTypeList;
    private ArrayList<Integer> wineTasteList;
    private ArrayList<String> winePriceList;
    private ArrayList<Integer> winePriceNumList;
    private ArrayList<String> wineCapacityList;
    private ArrayList<String> wineGrapeList;
    private ArrayList<String> wineOriginList;
    private ArrayList<String> wineHarvestList;
    private ArrayList<String> wineNameList;
    private ArrayList<String> wineFuriganaList;
    private ArrayList<Integer> wineryIDList;
    private ArrayList<String> wineryNameList;
    private ArrayList<String> wineExplanationList;

    private ArrayList<Integer> wineEvalList;


    public WineData(){
        wineNum = 0;
        wineIndexList = new ArrayList<>();
        wineIdoList = new ArrayList<>();
        wineKedoList = new ArrayList<>();
        wineColorList = new ArrayList<>();
        wineTypeList = new ArrayList<>();
        wineTasteList = new ArrayList<>();
        winePriceList = new ArrayList<>();
        winePriceNumList = new ArrayList<>();
        wineCapacityList = new ArrayList<>();
        wineGrapeList = new ArrayList<>();
        wineOriginList = new ArrayList<>();
        wineHarvestList = new ArrayList<>();
        wineNameList = new ArrayList<>();
        wineFuriganaList = new ArrayList<>();
        wineryIDList = new ArrayList<>();
        wineryNameList = new ArrayList<>();
        wineExplanationList = new ArrayList<>();

        wineEvalList = new ArrayList<>();
    }

    public void setWineData(WineData wineData){
        setWineIndexList(wineData.getWineIndexList());
        setWineIdoList(wineData.getWineIdoList());
        setWineKedoList(wineData.getWineKedoList());
        setWineColorList(wineData.getWineColorList());
        setWineTypeList(wineData.getWineTypeList());
        setWineTasteList(wineData.getWineTasteList());
        setWinePriceList(wineData.getWinePriceList());
        setWinePriceNumList(wineData.getWinePriceNumList());
        setWineCapacityList(wineData.getWineCapacityList());
        setWineGrapeList(wineData.getWineGrapeList());
        setWineOriginList(wineData.getWineOriginList());
        setWineHarvestList(wineData.getWineHarvestList());
        setWineNameList(wineData.getWineNameList());
        setWineFuriganaList(wineData.getWineFuriganaList());
        setWineryIDList(wineData.getWineryIDList());
        setWineryNameList(wineData.getWineryNameList());
        setWineExplanationList(wineData.getWineExplanationList());
        setWineNum(wineData.getWineNum());

        setWineEvalList(wineData.getWineEvalList());
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

    public ArrayList<String> getWineColorList() {
        return wineColorList;
    }

    public ArrayList<String> getWineTypeList() {
        return wineTypeList;
    }

    public ArrayList<Integer> getWineTasteList(){
        return wineTasteList;
    }

    public ArrayList<String> getWinePriceList() {
        return winePriceList;
    }

    public ArrayList<Integer> getWinePriceNumList() {
        return winePriceNumList;
    }

    public ArrayList<String> getWineCapacityList() {
        return wineCapacityList;
    }

    public ArrayList<String> getWineGrapeList() {
        return wineGrapeList;
    }

    public ArrayList<String> getWineOriginList() {
        return wineOriginList;
    }

    public ArrayList<String> getWineHarvestList() {
        return wineHarvestList;
    }

    public ArrayList<String> getWineNameList() {
        return wineNameList;
    }

    public ArrayList<String> getWineFuriganaList() {
        return wineFuriganaList;
    }

    public ArrayList<Integer> getWineryIDList(){
        return wineryIDList;
    }

    public ArrayList<String> getWineryNameList() {
        return wineryNameList;
    }

    public ArrayList<String> getWineExplanationList() {
        return wineExplanationList;
    }

    public ArrayList<Integer> getWineEvalList() {
        return wineEvalList;
    }


    public void setWineIndexList(ArrayList<Integer> wineIndexList) {
        this.wineIndexList = wineIndexList;
    }

    public void setWineIdoList(ArrayList<Double> wineIdoList) {
        this.wineIdoList = wineIdoList;
    }

    public void setWineKedoList(ArrayList<Double> wineKedoList) {
        this.wineKedoList = wineKedoList;
    }

    public void setWineColorList(ArrayList<String> wineColorList) {
        this.wineColorList = wineColorList;
    }

    public void setWineTypeList(ArrayList<String> wineTypeList) {
        this.wineTypeList = wineTypeList;
    }

    public void setWineTasteList(ArrayList<Integer> wineTasteList) {
        this.wineTasteList = wineTasteList;
    }

    public void setWinePriceList(ArrayList<String> winePriceList) {
        this.winePriceList = winePriceList;
    }

    public void setWinePriceNumList(ArrayList<Integer> winePriceNumList) {
        this.winePriceNumList = winePriceNumList;
    }

    public void setWineCapacityList(ArrayList<String> wineCapacityList) {
        this.wineCapacityList = wineCapacityList;
    }

    public void setWineGrapeList(ArrayList<String> wineGrapeList) {
        this.wineGrapeList = wineGrapeList;
    }

    public void setWineOriginList(ArrayList<String> wineOriginList) {
        this.wineOriginList = wineOriginList;
    }

    public void setWineHarvestList(ArrayList<String> wineHarvestList) {
        this.wineHarvestList = wineHarvestList;
    }

    public void setWineNameList(ArrayList<String> wineNameList) {
        this.wineNameList = wineNameList;
    }

    public void setWineFuriganaList(ArrayList<String> wineFuriganaList) {
        this.wineFuriganaList = wineFuriganaList;
    }

    public void setWineryIDList(ArrayList<Integer> wineryIDList) {
        this.wineryIDList = wineryIDList;
    }

    public void setWineryNameList(ArrayList<String> wineryNameList) {
        this.wineryNameList = wineryNameList;
    }

    public void setWineExplanationList(ArrayList<String> wineExplanationList) {
        this.wineExplanationList = wineExplanationList;
    }

    public void setWineEvalList(ArrayList<Integer> wineEvalList) {
        this.wineEvalList = wineEvalList;
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
        wineColorList.add(str);
    }

    public void addWineTypeList(String str){
        wineTypeList.add(str);
    }

    public void addWineTasteList(String str) {
        wineTasteList.add(Integer.parseInt(str));
    }

    public void addWinePriceList(String str){
        winePriceList.add(str);
    }

    public void addWinePriceNumList(String str) {
        winePriceNumList.add(Integer.parseInt(str));
    }

    public void addWineCapacityList(String str){
        wineCapacityList.add(str);
    }

    public void addWineGrapeList(String str){
        wineGrapeList.add(str);
    }

    public void addWineOriginList(String str){
        wineOriginList.add(str);
    }

    public void addWineHarvestList(String str){
        wineHarvestList.add(str);
    }

    public void addWineNameList(String str){
        wineNameList.add(str);
    }

    public void addWineFuriganaList(String str){
        wineFuriganaList.add(str);
    }

    public void addWineryIDList(String str){
        wineryIDList.add(Integer.parseInt(str));
    }

    public void addWineryNameList(String str){
        wineryNameList.add(str);
    }

    public void addWineExplanationList(String str){
        wineExplanationList.add(str);
    }

    public void addWineEvalList(String num) {
        wineEvalList.add(Integer.parseInt(num));
    }

    public void changeEvalValue(int index, int valueNum) {
        if (wineEvalList.size() == 0){
            for (int i = 0; i < wineNum; i++) {
                wineEvalList.add(0);
            }
        }
        wineEvalList.set(index, valueNum);
    }

}
