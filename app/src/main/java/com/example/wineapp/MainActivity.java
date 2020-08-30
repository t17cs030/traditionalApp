package com.example.wineapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private int xPoint, yPoint; //画面スクロール用

    private DisplayingViews displayingViews;//画面に張り付ける画像群クラス

    private ZeroPoint zeroPoint;//画面の原点を保持するクラス

    private WineData wineData = new WineData();//ワインのインデックスや緯度経度リストのクラス

    private ViewsPoint viewsPoint = new ViewsPoint();//各ビューの座標を保存するクラス

    private double centerIndex = 0;//中央のワインのインデックス(初期値は0)
    private double magnification=1;//拡大率

    private int imageViewId[]={
            R.drawable.wine_01,
            R.drawable.wine_02,
            R.drawable.wine_03,
            R.drawable.wine_04,
            R.drawable.wine_05,
            R.drawable.wine_06,
            R.drawable.wine_07,
            R.drawable.wine_08,
            R.drawable.wine_09,
            R.drawable.wine_10,
            R.drawable.wine_11,
            R.drawable.wine_12,
            R.drawable.wine_13,
            R.drawable.wine_14,
            R.drawable.wine_15,
            R.drawable.wine_16,
            R.drawable.wine_17,
            R.drawable.wine_18,
            R.drawable.wine_19,
            R.drawable.wine_20,
            R.drawable.wine_21,
            R.drawable.wine_22,
            R.drawable.wine_23,
            R.drawable.wine_24,
            R.drawable.wine_25,
            R.drawable.wine_26,
            R.drawable.wine_27,
            R.drawable.wine_28,
            R.drawable.wine_29,
            R.drawable.wine_30,
            R.drawable.wine_31,
            R.drawable.wine_32,
            R.drawable.wine_33,
            R.drawable.wine_34,
            R.drawable.wine_35,
            R.drawable.wine_36,
            R.drawable.wine_37,
            R.drawable.wine_38,
            R.drawable.wine_39,
            R.drawable.wine_40
    };//画像のIDを配列に格納//最終的にDisplayViewsに移動予定

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readCSV();//最初にデータを読み込む
        centerIndex = randCenter();

        //拡大ボタン
        findViewById(R.id.expansion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magnification *=1.5;
                deleteView(displayingViews.getImageView(), displayingViews.getTextIndexView());
                drawPicture();
            }
        });

        //縮小ボタン
        findViewById(R.id.reduction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magnification /=1.5;
                deleteView(displayingViews.getImageView(), displayingViews.getTextIndexView());
                drawPicture();
            }
        });

        //検索画面遷移
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SearchActivity.class);
                startActivity(intent);
            }
        });
        //MYワインリスト画面遷移
        findViewById(R.id.myWine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MyWineActivity.class);
                startActivity(intent);
            }
        });
        //ラベル画面遷移
        findViewById(R.id.label).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
        //ワイナリー画面遷移
        findViewById(R.id.winery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), WineryActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        //タッチした場所の絶対座標を取得
        int newXPoint = (int)motionEvent.getRawX();
        int newYPoint = (int)motionEvent.getRawY();

        int distance=10000;//最も近い点を遠くに設定しておく
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE://ドラッグイベントが発生した際の処理
                slideViews(displayingViews.getImageView(), displayingViews.getTextIndexView(), newXPoint, newYPoint);//画面全体をスライド
                break;
            case MotionEvent.ACTION_CANCEL://タッチダウンとタッチアップが同時に発生した際の処理
                break;
            case MotionEvent.ACTION_DOWN://タッチダウンが発生した際の処理
                break;
            case MotionEvent.ACTION_UP://タッチアップが発生した際の処理
                deleteView(displayingViews.getImageView(), displayingViews.getTextIndexView());
                drawPicture();
                break;
        }
        //タッチした位置を画像の位置に更新する

        xPoint = newXPoint;
        yPoint = newYPoint;
        return false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {//画像を張り付けるのに使う関数
        super.onWindowFocusChanged(hasFocus);
        //画像を載せるレイアウトを決定
        RelativeLayout usingLayout = (RelativeLayout) findViewById(R.id.layout);

        //描画可能域の中心をゼロとする
        int point[] = new int[2];
        usingLayout.getLocationOnScreen(point);
        Button wineMap = findViewById(R.id.wineMap);
        zeroPoint = new ZeroPoint((int)( (point[0]+usingLayout.getWidth())/2 ), (int)( (point[1]+usingLayout.getHeight()-wineMap.getHeight())/2) ) ;

        drawPicture();

    }

    //ここから通常関数
    public void readCSV() {//CSVファイルを読み込む関数
        try {
            InputStream inputStream =
                    getResources().getAssets().open("wineList.csv");

            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream);

            BufferedReader bufferReader =
                    new BufferedReader(inputStreamReader);

            String line = "";

            while ((line = bufferReader.readLine()) != null) {
                StringTokenizer stringTokenizer =
                        new StringTokenizer(line, ",");

                wineData.addWineIndexList(stringTokenizer.nextToken());
                wineData.addWineIdoList(stringTokenizer.nextToken());
                wineData.addWineKedoList(stringTokenizer.nextToken());
            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        wineData.setWineNum(wineData.getWineIndexList().size());
    }

    public void calPoint(Double index){//選んだワインを中心にした時の各ワインの座標を計算する

        ArrayList<Double> Index = wineData.getWineIndexList();
        ArrayList<Double> Ido = wineData.getWineIdoList();
        ArrayList<Double> Kedo = wineData.getWineKedoList();

        //選んだワインの緯度と経度を求める
        int picIndexNum = Index.indexOf(centerIndex);//選んだワインのインデックス番号を取得
        double phi0 = Ido.get(picIndexNum);
        double theta0 = Kedo.get(picIndexNum);
        double phi1 = Math.toRadians(90-(Math.toDegrees(phi0)));

        //各ワインの座標を求める
        for(int i=0; i<wineData.getWineNum(); i++){
            double phi_before = Ido.get(i);
            double theta = Kedo.get(i);
            double phi = Math.toRadians(90-(Math.toDegrees(phi_before)));

            double c = Math.acos( Math.sin(phi1)*Math.sin(phi)+Math.cos(phi1)*Math.cos(phi)*Math.cos(theta-theta0) );
            double k = c/( Math.sin(c) );

            double x = k*Math.cos(phi)*Math.sin(theta-theta0);
            double y = k*( Math.cos(phi1)*Math.sin(phi) - Math.sin(phi1)*Math.cos(phi)*Math.cos(theta-theta0) );
            if(Double.isNaN(x)){
                x=0;
            }
            if(Double.isNaN(y)){
                y=0;
            }
            viewsPoint.addxPoint(x);
            viewsPoint.addyPoint(y);
        }
    }

    public void setPicture(ImageView[] imageView, TextView[] textIndexView){

        RelativeLayout usingLayout = (RelativeLayout) findViewById(R.id.layout);

        //画像を設定
        for(int i=0;i<wineData.getWineNum();i++) {
            imageView[i] = new ImageView(this);
            textIndexView[i] = new TextView(this);

            double wineIndex = wineData.getWineIndexList().get(i);
            imageView[i].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[(int)wineIndex-1]));
            textIndexView[i].setText(String.valueOf(wineData.getWineIndexList().get(i)));

            //RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)(200*magnification), (int)(200*magnification));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)(200), (int)(200));
            RelativeLayout.LayoutParams Ilp = new RelativeLayout.LayoutParams((int)(100*magnification), (int)(100*magnification));

            int xZero = zeroPoint.getxZeroPoint();
            int yZero = zeroPoint.getyZeroPoint();

            ArrayList<Double> xPoints = viewsPoint.getxPoints();
            ArrayList<Double> yPoints = viewsPoint.getyPoints();

            TextView textView = findViewById(R.id.text_view2);
            String str = "X=" + xZero + "y=" + yZero;
            textView.setText(str);

            lp.leftMargin = (int)(xZero + xPoints.get(i)*xZero/3*magnification);
            lp.topMargin = (int) (yZero + yPoints.get(i)*xZero/3*magnification);
            Ilp.leftMargin = (int)(xZero + xPoints.get(i)*xZero/3*magnification);
            Ilp.topMargin = (int) (yZero + yPoints.get(i)*xZero/3*magnification);

            usingLayout.addView(imageView[i], lp);
            //usingLayout.addView(textIndexView[i], Ilp);

            imageView[i].layout(0, 0, imageView[i].getWidth(), imageView[i].getHeight());
            //textIndexView[i].layout(0, 0, textIndexView[i].getWidth(), textIndexView[i].getHeight());
        }
    }

    public void drawPicture(){//画像の再描画
        

        //画像の初期設定
        displayingViews = new DisplayingViews(wineData.getWineNum());
        //座標の計算
        calPoint(centerIndex);
        //画像の張り付け
        setPicture(displayingViews.getImageView(), displayingViews.getTextIndexView());

        TextView textView = findViewById(R.id.text_view);
        String str = "現在の中央=" + centerIndex;
        textView.setText(str);

    }

    public void deleteView(ImageView[] imageView, TextView[] textIndexView){//画面上のビューと座標を削除する
        viewsPoint.deletePoint();
        RelativeLayout usingLayout = (RelativeLayout) findViewById(R.id.layout);
        for(int i=0; i<wineData.getWineNum(); i++) {
            usingLayout.removeView(imageView[i]);
            usingLayout.removeView(textIndexView[i]);
        }
    }


    public int getDistance(int dx, int dy){//画面の中心からの距離を計算する
        int xZero = zeroPoint.getxZeroPoint();
        int yZero = zeroPoint.getyZeroPoint();
        return (xZero-dx)*(xZero-dx)+(yZero-dy)*(yZero-dy);
    }

    public void slideImage(ImageView[] imageView, int newX, int newY){//画像をスライドする
        int distance = 10000;
        for(int i=0; i<wineData.getWineNum(); i++) {
            int dx = imageView[i].getLeft() + (newX - xPoint);
            int dy = imageView[i].getTop() + (newY - yPoint);
            int imgW = dx + imageView[i].getWidth();
            int imgH = dy + imageView[i].getHeight();

            imageView[i].layout(dx, dy, imgW, imgH);

            if(distance > getDistance(dx, dy)){
                centerIndex = wineData.getWineIndexList().get(i);
            }
        }
    }

    public void slideText(TextView[] textIndexView, int newX, int newY){//文字(インデックス)をスライドする
        for(int i=0; i<wineData.getWineNum(); i++) {
            int dx = textIndexView[i].getLeft() + (newX - xPoint);
            int dy = textIndexView[i].getTop() + (newY - yPoint);
            int textW = dx + textIndexView[i].getWidth();
            int textH = dy + textIndexView[i].getHeight();

            textIndexView[i].layout(dx, dy, textW, textH);
        }
    }

    public void slideViews(ImageView[] imageView, TextView[] textView, int newX, int newY){//画面に表示されているものをスライドする
        slideImage(imageView, newX, newY);
        slideText(textView, newX, newY);
    }

    public int randCenter(){
        Random random = new Random();
        int randomValue = random.nextInt(wineData.getWineNum());
        return 1+randomValue;
    }

}

