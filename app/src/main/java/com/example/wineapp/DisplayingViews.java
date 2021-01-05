package com.example.wineapp;

import android.widget.ImageView;
import android.widget.TextView;

public class DisplayingViews {
    private ImageView[] imageView;
    private ImageView[] ratingImage;
    private int ImageViewId[];

    public DisplayingViews(int num){//コンストラクタ
        imageView = new ImageView[num];
        ratingImage = new ImageView[num];
    }

    public ImageView[] getImageView() {
        return imageView;
    }

    public ImageView[] getRatingImage() {
        return ratingImage;
    }

    /*
    public int[] getImageViewId() {
        return ImageViewId;
    }

    public void setImageView(ImageView[] imageView) {
        this.imageView = imageView;
    }

    public void setTextIndexView(TextView[] textIndexView) {
        this.textIndexView = textIndexView;
    }

    public void setImageViewId(int[] imageViewId) {
        ImageViewId = imageViewId;
    }
    */

}
