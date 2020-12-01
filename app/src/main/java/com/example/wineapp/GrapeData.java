package com.example.wineapp;

import java.io.Serializable;
import java.util.ArrayList;

public class GrapeData implements Serializable {


    private static final long serialVersionUID = 1L;

    private ArrayList<Integer> wineIndexList;
    private ArrayList<Integer> MBA;
    private ArrayList<Integer> SS;
    private ArrayList<Integer> Kosyu;
    private ArrayList<Integer> KS;
    private ArrayList<Integer> Merlot;
    private ArrayList<Integer> PV;
    private ArrayList<Integer> BQ;
    private ArrayList<Integer> KF;
    private ArrayList<Integer> KN;
    private ArrayList<Integer> SB;
    private ArrayList<Integer> Delaware;
    private ArrayList<Integer> Tana;
    private ArrayList<Integer> Tempranillo;
    private ArrayList<Integer> Syrah;
    private ArrayList<Integer> Mourvale;
    private ArrayList<Integer> Carmenere;
    private ArrayList<Integer> Chardonnay;

    public GrapeData(){
        wineIndexList = new ArrayList<>();
        MBA = new ArrayList<>();
        SS = new ArrayList<>();
        Kosyu = new ArrayList<>();
        KS = new ArrayList<>();
        Merlot = new ArrayList<>();
        PV = new ArrayList<>();
        BQ = new ArrayList<>();
        KF = new ArrayList<>();
        KN = new ArrayList<>();
        SB = new ArrayList<>();
        Delaware = new ArrayList<>();
        Tana = new ArrayList<>();
        Tempranillo = new ArrayList<>();
        Syrah = new ArrayList<>();
        Mourvale = new ArrayList<>();
        Carmenere = new ArrayList<>();
        Chardonnay = new ArrayList<>();
    }

    public void setGrapeData(GrapeData grapeData) {
        setWineIndexList(grapeData.getWineIndexList());
        setMBA(grapeData.getMBA());
        setSS(grapeData.getSS());
        setKosyu(grapeData.getKosyu());
        setKS(grapeData.getKS());
        setMerlot(grapeData.getMerlot());
        setPV(grapeData.getPV());
        setBQ(grapeData.getBQ());
        setKF(grapeData.getKF());
        setKN(grapeData.getKN());
        setSB(grapeData.getSB());
        setDelaware(grapeData.getDelaware());
        setTana(grapeData.getTana());
        setTempranillo(grapeData.getTempranillo());
        setSyrah(grapeData.getSyrah());
        setMourvale(grapeData.getMourvale());
        setCarmenere(grapeData.getCarmenere());
        setChardonnay(grapeData.getChardonnay());
    }

    public ArrayList<Integer> getWineIndexList() {
        return wineIndexList;
    }
    public ArrayList<Integer> getMBA() {
        return MBA;
    }
    public ArrayList<Integer> getSS() {
        return SS;
    }
    public ArrayList<Integer> getKosyu() {
        return Kosyu;
    }
    public ArrayList<Integer> getKS() {
        return KS;
    }
    public ArrayList<Integer> getMerlot() {
        return Merlot;
    }
    public ArrayList<Integer> getPV() {
        return PV;
    }
    public ArrayList<Integer> getBQ() {
        return BQ;
    }
    public ArrayList<Integer> getKF() {
        return KF;
    }
    public ArrayList<Integer> getKN() {
        return KN;
    }
    public ArrayList<Integer> getSB() {
        return SB;
    }
    public ArrayList<Integer> getDelaware() {
        return Delaware;
    }
    public ArrayList<Integer> getTana() {
        return Tana;
    }
    public ArrayList<Integer> getTempranillo() {
        return Tempranillo;
    }
    public ArrayList<Integer> getSyrah() {
        return Syrah;
    }
    public ArrayList<Integer> getMourvale() {
        return Mourvale;
    }
    public ArrayList<Integer> getCarmenere() {
        return Carmenere;
    }
    public ArrayList<Integer> getChardonnay() {
        return Chardonnay;
    }


    public void setWineIndexList(ArrayList<Integer> wineIndexList) {
        this.wineIndexList = wineIndexList;
    }
    public void setMBA(ArrayList<Integer> MBA) {
        this.MBA = MBA;
    }
    public void setSS(ArrayList<Integer> SS) {
        this.SS = SS;
    }
    public void setKosyu(ArrayList<Integer> kosyu) {
        Kosyu = kosyu;
    }
    public void setKS(ArrayList<Integer> KS) {
        this.KS = KS;
    }
    public void setMerlot(ArrayList<Integer> merlot) {
        Merlot = merlot;
    }
    public void setPV(ArrayList<Integer> PV) {
        this.PV = PV;
    }
    public void setBQ(ArrayList<Integer> BQ) {
        this.BQ = BQ;
    }
    public void setKF(ArrayList<Integer> KF) {
        this.KF = KF;
    }
    public void setKN(ArrayList<Integer> KN) {
        this.KN = KN;
    }
    public void setSB(ArrayList<Integer> SB) {
        this.SB = SB;
    }
    public void setDelaware(ArrayList<Integer> delaware) {
        Delaware = delaware;
    }
    public void setTana(ArrayList<Integer> tana) {
        Tana = tana;
    }
    public void setTempranillo(ArrayList<Integer> tempranillo) {
        Tempranillo = tempranillo;
    }
    public void setSyrah(ArrayList<Integer> syrah) {
        Syrah = syrah;
    }
    public void setMourvale(ArrayList<Integer> mourvale) {
        Mourvale = mourvale;
    }
    public void setCarmenere(ArrayList<Integer> carmenere) {
        Carmenere = carmenere;
    }
    public void setChardonnay(ArrayList<Integer> chardonnay) {
        Chardonnay = chardonnay;
    }


    public void addWineIndexList(String str){
        wineIndexList.add(Integer.parseInt(str));
    }
    public void addMBA(String str){
        MBA.add(Integer.parseInt(str));
    }
    public void addSS(String str){
        SS.add(Integer.parseInt(str));
    }
    public void addKosyu(String str){
        Kosyu.add(Integer.parseInt(str));
    }
    public void addKS(String str){
        KS.add(Integer.parseInt(str));
    }
    public void addMerlot(String str){
        Merlot.add(Integer.parseInt(str));
    }
    public void addPV(String str){
        PV.add(Integer.parseInt(str));
    }
    public void addBQ(String str){
        BQ.add(Integer.parseInt(str));
    }
    public void addKF(String str){
        KF.add(Integer.parseInt(str));
    }
    public void addKN(String str){
        KN.add(Integer.parseInt(str));
    }
    public void addSB(String str){
        SB.add(Integer.parseInt(str));
    }
    public void addDelaware(String str){
        Delaware.add(Integer.parseInt(str));
    }
    public void addTana(String str){
        Tana.add(Integer.parseInt(str));
    }
    public void addTempranillo(String str){
        Tempranillo.add(Integer.parseInt(str));
    }
    public void addSyrah(String str){
        Syrah.add(Integer.parseInt(str));
    }
    public void addMourvale(String str){
        Mourvale.add(Integer.parseInt(str));
    }
    public void addCarmenere(String str){
        Carmenere.add(Integer.parseInt(str));
    }
    public void addChardonnay(String str){
        Chardonnay.add(Integer.parseInt(str));
    }
}
