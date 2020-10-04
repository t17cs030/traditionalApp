package com.example.wineapp;

import android.graphics.Bitmap;

public class MyWineListMultipleItem {
    private Bitmap mThumbnail = null;
    private String mTitle = null;
    private Boolean mCheck = null;

    /**
     * 空のコンストラクタ
     */
    public MyWineListMultipleItem() {};

    /**
     * コンストラクタ
     * @param thumbnail サムネイル画像
     * @param title タイトル
     * @param check チェック
     */
    public MyWineListMultipleItem(Bitmap thumbnail, String title, Boolean check) {
        mThumbnail = thumbnail;
        mTitle = title;
        mCheck = check;
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
     * チェックボックスを設定
     * @param check タイトル
     */
    public void setCheck(Boolean check) {
        mCheck = check;
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
     * チェックボックスを取得
     * @return チェック
     */
    public Boolean getCheck() {
        return mCheck;
    }
}
