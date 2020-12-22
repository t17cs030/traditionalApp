package com.example.wineapp;

import android.graphics.Bitmap;

public class MyWineListItem {
    private Bitmap mThumbnail = null;
    private String mTitle = null;
    private String mFurigana = null;
    private String mWinery = null;
    private int mWineID = 0;
    private int mWineColor = 0;
    private int mWinePrice = 0;

    /**
     * 空のコンストラクタ
     */
    public MyWineListItem() {};

    /**
     * コンストラクタ
     * @param thumbnail サムネイル画像
     * @param title タイトル
     * @param furigana ふりがな
     */
    public MyWineListItem(Bitmap thumbnail, String title, String furigana, String winery, int wineID, int wineColor, int winePrice) {
        mThumbnail = thumbnail;
        mTitle = title;
        mFurigana = furigana;
        mWinery = winery;
        mWineID = wineID;
        mWineColor = wineColor;
        mWinePrice = winePrice;
    }

    /**
     * サムネイル画像を設定
     * @param thumbnail サムネイル画像
     */
    public void setThumbnail(Bitmap thumbnail) {
        mThumbnail = thumbnail;
    }

    /**
     * タイトルを設定
     * @param title タイトル
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * ふりがなを設定
     * @param furigana ふりがな
     */
    public void setFurigana(String furigana) {
        mFurigana = furigana;
    }

    public void setWinery(String winery){
        mWinery = winery;
    }

    public void setWineID(int wineID){
        mWineID = wineID;
    }

    public void setWineColor(int wineColor){
        mWineColor = wineColor;
    }

    public void setWinePrice(int winePrice){
        mWinePrice = winePrice;
    }


    /**
     * サムネイル画像を取得
     * @return サムネイル画像
     */
    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    /**
     * タイトルを取得
     * @return タイトル
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * ふりがなを取得
     * @return ふりがな
     */
    public String getFurigana() {
        return mFurigana;
    }

    public String getWinery(){
        return mWinery;
    }

    public int getWineID(){
        return mWineID;
    }

    public int getWineColor(){
        return mWineColor;
    }

    public  int getWinePrice(){
        return mWinePrice;
    }

}
