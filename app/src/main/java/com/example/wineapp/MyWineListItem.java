package com.example.wineapp;

import android.graphics.Bitmap;

public class MyWineListItem {
    private Bitmap mThumbnail = null;
    private String mTitle = null;
    private String mFurigana = null;

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
    public MyWineListItem(Bitmap thumbnail, String title, String furigana) {
        mThumbnail = thumbnail;
        mTitle = title;
        mFurigana = furigana;
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

}
