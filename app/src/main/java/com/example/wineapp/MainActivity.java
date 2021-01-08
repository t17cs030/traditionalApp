package com.example.wineapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private int xPoint, yPoint; //画面スクロール用

    private DisplayingViews displayingViews;//画面に張り付ける画像群クラス

    private ZeroPoint zeroPoint;//画面の原点を保持するクラス

    private WineData wineData = new WineData();//ワインのインデックスや緯度経度リストのクラス
    private GrapeData grapeData = new GrapeData();//ワインに用いられているブドウのリストのクラス

    private ViewsPoint viewsPoint = new ViewsPoint();//各ビューの座標を保存するクラス

    private ArrayList<Integer> myWineListIndex = new ArrayList<>();//Myワインに登録されているのワインインデックスを保存する
    private int myWineListLength = 0;//Myワインリストの長さ

    private int centerIndex = 0;//中央のワインのインデックス(初期値は0)
    private double magnification=6.0;//拡大率
    private double pic_magnification=1.2;

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
            R.drawable.wine_40,
            R.drawable.wine_41,
            R.drawable.wine_42,
            R.drawable.wine_43,
            R.drawable.wine_44,
            R.drawable.wine_45,
            R.drawable.wine_46,
            R.drawable.wine_47,
            R.drawable.wine_48,
            R.drawable.wine_49,
            R.drawable.wine_50,
            R.drawable.wine_51,
            R.drawable.wine_52,
            R.drawable.wine_53,
            R.drawable.wine_54,
            R.drawable.wine_55,
            R.drawable.wine_56,
            R.drawable.wine_57,
            R.drawable.wine_58,
            R.drawable.wine_59,
            R.drawable.wine_60,
            R.drawable.wine_61,
            R.drawable.wine_62,
            R.drawable.wine_63,
            R.drawable.wine_64,
            R.drawable.wine_65,
            R.drawable.wine_66,
            R.drawable.wine_67,
            R.drawable.wine_68,
            R.drawable.wine_69,
            R.drawable.wine_70,
            R.drawable.wine_71,
            R.drawable.wine_72,
            R.drawable.wine_73,
            R.drawable.wine_74,
            R.drawable.wine_75,
            R.drawable.wine_76,
            R.drawable.wine_77,
            R.drawable.wine_78,
            R.drawable.wine_79,
            R.drawable.wine_80,
            R.drawable.wine_81,
            R.drawable.wine_82,
            R.drawable.wine_83,
            R.drawable.wine_84,
            R.drawable.wine_85,
            R.drawable.wine_86,
            R.drawable.wine_87,
            R.drawable.wine_88,
            R.drawable.wine_89,
            R.drawable.wine_90,
            R.drawable.wine_91,
            R.drawable.wine_92,
            R.drawable.wine_93,
            R.drawable.wine_94,
            R.drawable.wine_95,
            R.drawable.wine_96,
            R.drawable.wine_97,
            R.drawable.wine_98
    };//画像のIDを配列に格納//最終的にDisplayViewsに移動予定

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readCSV();//最初にデータを読み込む
        readGrape();
        try {
            readMyWineList();
            readEvalList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent me = getIntent();
        int center = me.getIntExtra("CENTER_WINE", -1);
        if(center == -1)
            centerIndex = randCenter();
        else
            centerIndex = center;

        //ボトムナビゲーションビューの初期値の設定
        BottomNavigationView navi;
        navi = (BottomNavigationView) findViewById(R.id.navigation);
        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.wineMap_navi:
                        return true;
                    case R.id.search_navi:
                        Intent intent_Search = new Intent(getApplication(), SearchActivity.class);
                        intent_Search.putExtra("WINE_DATA", wineData);
                        intent_Search.putExtra("GRAPE_DATA", grapeData);
                        intent_Search.putExtra("CENTER_WINE", centerIndex);
                        startActivity(intent_Search);
                        return true;
                    case R.id.myWine_navi:
                        Intent intent_myWine = new Intent(getApplication(), MyWineActivity.class);
                        intent_myWine.putExtra("WINE_DATA", wineData);
                        intent_myWine.putExtra("GRAPE_DATA", grapeData);
                        intent_myWine.putExtra("CENTER_WINE", centerIndex);
                        startActivity(intent_myWine);
                        return true;
                    case R.id.label_navi:
                        Intent intent_label = new Intent(getApplication(), LabelActivity.class);
                        intent_label.putExtra("WINE_DATA", wineData);
                        intent_label.putExtra("GRAPE_DATA", grapeData);
                        intent_label.putExtra("CENTER_WINE", centerIndex);
                        startActivity(intent_label);
                        return true;
                    case R.id.winery_navi:
                        Intent intent_winery = new Intent(getApplication(), WineryActivity.class);
                        intent_winery.putExtra("WINE_DATA", wineData);
                        intent_winery.putExtra("GRAPE_DATA", grapeData);
                        intent_winery.putExtra("CENTER_WINE", centerIndex);
                        startActivity(intent_winery);
                        return true;

                }
                return false;
            }
        });

        //拡大ボタン
        findViewById(R.id.expansion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magnification+1 <= 8){
                    if(magnification >= 6){
                        magnification += 1;
                        pic_magnification += 0.4;
                    }
                    else {
                        magnification += 1;
                        pic_magnification += 0.2;
                    }
                }
                scaleImage(displayingViews.getImageView(), displayingViews.getRatingImage());
            }
        });

        //縮小ボタン
        findViewById(R.id.reduction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magnification-1 >= 4){
                    if(magnification <= 6){
                        magnification -= 1;
                        pic_magnification -= 0.2;
                    }
                    else {
                        magnification -= 1;
                        pic_magnification -= 0.4;
                    }
                }
                scaleImage(displayingViews.getImageView(), displayingViews.getRatingImage());
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        //タッチした場所の絶対座標を取得
        int newXPoint = (int)motionEvent.getRawX();
        int newYPoint = (int)motionEvent.getRawY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE://ドラッグイベントが発生した際の処理
                roleImage(newXPoint, newYPoint, displayingViews.getImageView(), displayingViews.getRatingImage());
                break;
            case MotionEvent.ACTION_CANCEL://タッチダウンとタッチアップが同時に発生した際の処理
                break;
            case MotionEvent.ACTION_DOWN://タッチダウンが発生した際の処理
                break;
            case MotionEvent.ACTION_UP://タッチアップが発生した際の処理
                reDraw(displayingViews.getImageView(), displayingViews.getRatingImage());
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
        FrameLayout usingLayout = (FrameLayout) findViewById(R.id.layout);

        //描画可能域の中心をゼロとする
        int point[] = new int[2];
        usingLayout.getLocationOnScreen(point);
        BottomNavigationView navi = findViewById(R.id.navigation);
        zeroPoint = new ZeroPoint((int)( (point[0]+usingLayout.getWidth())/2 ), (int)( (point[1]+usingLayout.getHeight()-navi.getHeight())/2) ) ;

        drawPicture();
    }

    public void calPointOnSphere(){//前回選んだワインを中心にした時の各ワインの座標を計算する(起動時のとき)
        ArrayList<Double> Ido = wineData.getWineIdoList();
        ArrayList<Double> Kedo = wineData.getWineKedoList();

        int picIndexNum = wineData.getWineIndexList().indexOf(centerIndex);//選んだワインのインデックス番号を取得
        double phi0 = Ido.get(picIndexNum);
        double theta0 = Kedo.get(picIndexNum);

        double picWineX = Math.sin(phi0)*Math.cos(theta0);
        double picWineY = Math.sin(phi0)*Math.sin(theta0);
        double picWineZ = Math.cos(phi0);

        double rotationX;
        if(picWineZ >= 0){
            rotationX = -Math.asin(picWineY);
        }
        else{
            rotationX = -Math.PI + Math.asin(picWineY);
        }
        double rotationY = Math.asin(picWineX);


        //各ワインの座標を求める
        for(int i=0; i<wineData.getWineNum(); i++){

            double wineID = wineData.getWineIndexList().get(i);
            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

            double phi = Ido.get(thisWineIndex);
            double theta = Kedo.get(thisWineIndex);

            double x = Math.sin(phi)*Math.cos(theta);
            double y = Math.sin(phi)*Math.sin(theta);
            double z = Math.cos(phi);

            double afterX = x;
            double afterY = y*Math.cos(rotationX) + z*Math.sin(rotationX);
            double afterZ = -y*Math.sin(rotationX) + z*Math.cos(rotationX);

            double X = afterX*Math.cos(rotationY) - afterZ*Math.sin(rotationY);
            double Y = afterY;
            double Z = afterX*Math.sin(rotationY) + afterZ*Math.cos(rotationY);

            viewsPoint.addxPoint(X);
            viewsPoint.addyPoint(Y);
            viewsPoint.addzPints(Z);
        }
    }

    public void roleImage(int newX, int newY, ImageView[] imageView, ImageView[] ratingImage){//スクロール時の座標計算
        int xZero = zeroPoint.getxZeroPoint();
        int yZero = zeroPoint.getyZeroPoint();
        //x方向増加量
        Double xMoved = -( ((double)newY - yPoint)/yZero );
        //y方向増加量
        Double yMoved = -( ((double)newX - xPoint)/xZero );


        double movedPhi = -Math.asin(xMoved);
        double movedTheta = Math.asin(yMoved);

        ArrayList<Double> newXPoints = new ArrayList<>();
        ArrayList<Double> newYPoints = new ArrayList<>();
        ArrayList<Double> newZPoints = new ArrayList<>();

        for(int i=0; i<wineData.getWineNum(); i++) {

            double wineID = wineData.getWineIndexList().get(i);
            int thisWineIndex = wineData.getWineIndexList().indexOf((int) wineID);

            double beforeX = viewsPoint.getxPoints().get(thisWineIndex);
            double beforeY = viewsPoint.getyPoints().get(thisWineIndex);
            double beforeZ = viewsPoint.getzPoints().get(thisWineIndex);

            double afterX = beforeX;
            double afterY = beforeY * Math.cos(movedPhi) + beforeZ * Math.sin(movedPhi);
            double afterZ = -beforeY * Math.sin(movedPhi) + beforeZ * Math.cos(movedPhi);

            double X = afterX * Math.cos(movedTheta) - afterZ * Math.sin(movedTheta);
            double Y = afterY;
            double Z = afterX * Math.sin(movedTheta) + afterZ * Math.cos(movedTheta);

            newXPoints.add(X);
            newYPoints.add(Y);
            newZPoints.add(Z);

            double dx = X*xZero*magnification/3 + xZero;
            double dy = Y*xZero*magnification/3 + yZero;
            double imgW = dx + pic_magnification*80;
            double imgH = dy + pic_magnification*200;
            imageView[i].layout((int)dx, (int)dy, (int)imgW, (int)imgH);


            double Rdx = X*xZero*magnification/3 + xZero - 50*pic_magnification;
            double Rdy = Y*xZero*magnification/3 + yZero;
            double RimgW = Rdx + pic_magnification*50;
            double RimgH = Rdy + pic_magnification*200;
            ratingImage[i].layout((int)Rdx, (int)Rdy, (int)RimgW, (int)RimgH);

            if(Z < 0){
                imageView[i].setVisibility(View.INVISIBLE);
                ratingImage[i].setVisibility(View.INVISIBLE);
            }
            else{
                imageView[i].setVisibility(View.VISIBLE);
                ratingImage[i].setVisibility(View.VISIBLE);
            }


        }

        viewsPoint.setxPoints(newXPoints);
        viewsPoint.setyPoints(newYPoints);
        viewsPoint.setzPoints(newZPoints);
    }

    public void scaleImage(ImageView[] imageView, ImageView[] ratingImage){//拡大縮小時の座標計算
        int xZero = zeroPoint.getxZeroPoint();
        int yZero = zeroPoint.getyZeroPoint();

        for(int i=0; i<wineData.getWineNum(); i++) {

            double wineID = wineData.getWineIndexList().get(i);
            int thisWineIndex = wineData.getWineIndexList().indexOf((int) wineID);

            double beforeX = viewsPoint.getxPoints().get(thisWineIndex);
            double beforeY = viewsPoint.getyPoints().get(thisWineIndex);
            double beforeZ = viewsPoint.getzPoints().get(thisWineIndex);

            double dx = beforeX*xZero*magnification/3 + xZero;
            double dy = beforeY*xZero*magnification/3 + yZero;
            double imgW = dx + pic_magnification*80;
            double imgH = dy + pic_magnification*200;
            imageView[i].layout((int)dx, (int)dy, (int)imgW, (int)imgH);


            double Rdx = beforeX*xZero*magnification/3 + xZero - 50*pic_magnification;
            double Rdy = beforeY*xZero*magnification/3 + yZero;
            double RimgW = Rdx + pic_magnification*50;
            double RimgH = Rdy + pic_magnification*200;
            ratingImage[i].layout((int)Rdx, (int)Rdy, (int)RimgW, (int)RimgH);

            if(beforeZ < 0){
                imageView[i].setVisibility(View.INVISIBLE);
                ratingImage[i].setVisibility(View.INVISIBLE);
            }
            else{
                imageView[i].setVisibility(View.VISIBLE);
                ratingImage[i].setVisibility(View.VISIBLE);
            }


        }
    }

    //球状で動かす用
    public void setPictureOnSphere(ImageView[] imageView, ImageView[] ratingImage){

        FrameLayout usingLayout = (FrameLayout) findViewById(R.id.layout);

        //画像を設定
        for(int i=0;i<wineData.getWineNum();i++) {
            imageView[i] = new ImageView(this);
            ratingImage[i] = new ImageView(this);


            double wineID = wineData.getWineIndexList().get(i);
            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

            int xZero = zeroPoint.getxZeroPoint();
            int yZero = zeroPoint.getyZeroPoint();

            ArrayList<Double> xPoints = viewsPoint.getxPoints();
            ArrayList<Double> yPoints = viewsPoint.getyPoints();
            ArrayList<Double> zPoints = viewsPoint.getzPoints();

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int) (pic_magnification * 80), (int) (pic_magnification * 200));
            lp.leftMargin = (int) (xZero + xPoints.get(i)*xZero*magnification/3);
            lp.topMargin = (int) (yZero + yPoints.get(i)*xZero*magnification/3);

            FrameLayout.LayoutParams Rlp = new FrameLayout.LayoutParams((int)(pic_magnification*50), (int)(pic_magnification*200));
            Rlp.leftMargin = (int)(xZero + xPoints.get(i)*xZero*magnification/3 - 50*pic_magnification);//最後の50はラベルの左側に座標を指定するため
            Rlp.topMargin = (int) (yZero + yPoints.get(i)*xZero*magnification/3);

            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
            imageView[i].setImageBitmap(bitmap1);
            imageView[i].setScaleType(ImageView.ScaleType.FIT_XY);
            usingLayout.addView(imageView[i], lp);

            if(wineData.getWineEvalList().size() != 0) {
                if (wineData.getWineEvalList().get(thisWineIndex) != 0) {
                    int drawImage = 0;
                    if (wineData.getWineEvalList().get(thisWineIndex) == 1) {
                        drawImage = R.drawable.rate_01;
                    } else if (wineData.getWineEvalList().get(thisWineIndex) == 2) {
                        drawImage = R.drawable.rate_02;
                    } else if (wineData.getWineEvalList().get(thisWineIndex) == 3) {
                        drawImage = R.drawable.rate_03;
                    } else if (wineData.getWineEvalList().get(thisWineIndex) == 4) {
                        drawImage = R.drawable.rate_04;
                    } else if (wineData.getWineEvalList().get(thisWineIndex) == 5) {
                        drawImage = R.drawable.rate_05;
                    }

                    Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), drawImage);

                    ratingImage[i].setImageBitmap(bitmap2);
                    ratingImage[i].setScaleType(ImageView.ScaleType.FIT_XY);
                    usingLayout.addView(ratingImage[i], Rlp);

                }
            }

            if(zPoints.get(thisWineIndex) < 0){
                imageView[i].setVisibility(View.INVISIBLE);
                ratingImage[i].setVisibility(View.INVISIBLE);
            }
            else{
                imageView[i].setVisibility(View.VISIBLE);
                ratingImage[i].setVisibility(View.VISIBLE);
            }
        }
    }

    public void setListener(final ImageView[] imageView){

        //画像にリスナーを設定
        for(int i=0;i<wineData.getWineNum();i++) {
            final int thisWineNum = i;
            //final RelativeLayout usingLayout = findViewById(R.id.layout);
            final FrameLayout usingLayout = (FrameLayout) findViewById(R.id.layout);


            imageView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //画像が押された時のやつ
                    ImageView winePicture = findViewById(R.id.wine_info);
                    double wineID = wineData.getWineIndexList().get(thisWineNum);
                    final int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);
                    winePicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]));

                    centerIndex = (int)wineID;
                    keepCenter();
                    //TextView textView = findViewById(R.id.text_view);
                    //textView.setText("中心のワイン" + centerIndex);


                    //reDraw(displayingViews.getImageView(), displayingViews.getRatingImage());

                    TextView wine_name = findViewById(R.id.wine_name);
                    String str_wine
                            = "ワイン名: " + wineData.getWineNameList().get(thisWineIndex) + "\n"
                            + "ワイナリー名: " + wineData.getWineryNameList().get(thisWineIndex);
                    wine_name.setText(str_wine);

                    //簡易情報の乗っているレイアウトを表示
                    RelativeLayout wine_info_layout = findViewById(R.id.for_wine_info);
                    wine_info_layout.setVisibility(View.VISIBLE);

                    TextView to_wine_list = findViewById(R.id.to_wine_list);
                    to_wine_list.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //ワインリストへ登録ボタンが押されたときの処理(仮)
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle(wineData.getWineNameList().get(thisWineIndex));
                            if( addWineList(wineData.getWineIndexList().get(thisWineIndex)) ) {
                                builder.setMessage("Myワインリストへ登録しました");
                            }
                            else
                                builder.setMessage("既に登録されています");
                            builder.show();
                        }
                    });

                    //簡易情報をクリックした際のアクション
                    wine_info_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ImageView winePicture = findViewById(R.id.wine_detail_info);
                            double wineID = wineData.getWineIndexList().get(thisWineIndex);
                            final int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);
                            winePicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]));

                            TextView wine_name = findViewById(R.id.wine_detail_name);

                            String str_wine
                                    = "ワイン名: " + wineData.getWineNameList().get(thisWineIndex) + "\n"
                                    + "ワイナリー名: " + wineData.getWineryNameList().get(thisWineIndex) + "\n"
                                    + "色: "  + wineData.getWineColorList().get(thisWineIndex) + "\n"
                                    + "タイプ: " + wineData.getWineTypeList().get(thisWineIndex) + "\n"
                                    + "容量: " + wineData.getWineCapacityList().get(thisWineIndex) + "\n"
                                    + "ぶどう品種: " + wineData.getWineGrapeList().get(thisWineIndex) + "\n"
                                    + "産地: " + wineData.getWineOriginList().get(thisWineIndex) + "\n"
                                    + "収穫年: " + wineData.getWineHarvestList().get(thisWineIndex) + "\n"
                                    + "価格: " + wineData.getWinePriceList().get(thisWineIndex);

                            wine_name.setText(str_wine);

                            TextView wine_explanation = findViewById(R.id.wine_detail_explanation);
                            String str_wine_exp =
                                    wineData.getWineNameList().get(thisWineIndex) + ": " + "\n\n"
                                    + wineData.getWineExplanationList().get(thisWineIndex);
                            wine_explanation.setText(str_wine_exp);

                            RelativeLayout wine_detail_info_layout = findViewById(R.id.for_wine_detail_info);
                            wine_detail_info_layout.setVisibility(View.VISIBLE);
                            wine_detail_info_layout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //詳細情報がクリックされたときは何もしない
                                }
                            });

                            TextView to_wine_list_2 = findViewById(R.id.to_wine_list_2);
                            to_wine_list_2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //ワインリストへ登録ボタンが押されたときの処理(仮)
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setTitle(wineData.getWineNameList().get(thisWineIndex));
                                    if( addWineList(wineData.getWineIndexList().get(thisWineIndex)) ) {
                                        builder.setMessage("Myワインリストへ登録しました");
                                    }
                                    else
                                        builder.setMessage("既に登録されています");
                                    builder.show();
                                }
                            });

                            TextView to_want_drink = findViewById(R.id.to_want_drink);
                            to_want_drink.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //飲みたいボタンが押された時の処理
                                }
                            });

                            for (int i = 0; i < wineData.getWineNum(); i++) {
                                imageView[i].setClickable(false);
                            }
                        }
                    });

                    usingLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //簡易情報と詳細情報の乗っているレイアウトを非表示
                            RelativeLayout wine_info_layout = findViewById(R.id.for_wine_info);
                            wine_info_layout.setVisibility(View.INVISIBLE);
                            RelativeLayout wine_detail_info_layout = findViewById(R.id.for_wine_detail_info);
                            wine_detail_info_layout.setVisibility(View.INVISIBLE);

                            usingLayout.setClickable(false);
                            for (int i = 0; i < wineData.getWineNum(); i++) {
                                imageView[i].setClickable(true);
                            }
                        }
                    });




                    //クリックしたワインを表示(デバック用)
                    //TextView textView = findViewById(R.id.text_view);
                    //String str = "画像をクリックしました" + wineData.getWineIndexList().get(thisWineNum);
                    //textView.setText(str);
                }
            });
        }
    }

    public void drawPicture(){//画像の再描画


        //画像の初期設定
        displayingViews = new DisplayingViews(wineData.getWineNum());
        //座標の計算
        calPointOnSphere();
        //画像の張り付け
        setPictureOnSphere(displayingViews.getImageView(), displayingViews.getRatingImage());
        setListener(displayingViews.getImageView());

    }

    public void deleteView(ImageView[] imageView, ImageView[] ratingImage){//画面上のビューと座標を削除する
        viewsPoint.deletePoint();
        FrameLayout usingLayout = (FrameLayout) findViewById(R.id.layout);
        for(int i=0; i<wineData.getWineNum(); i++) {
            usingLayout.removeView(imageView[i]);
            usingLayout.removeView(ratingImage[i]);
        }
    }

    public void reDraw(ImageView[] imageView, ImageView[] ratingImage){
        FrameLayout usingLayout = (FrameLayout) findViewById(R.id.layout);
        for(int i=0; i<wineData.getWineNum(); i++) {
            usingLayout.removeView(imageView[i]);
            usingLayout.removeView(ratingImage[i]);
        }
        //画像の初期設定
        displayingViews = new DisplayingViews(wineData.getWineNum());
        //画像の張り付け
        setPictureOnSphere(displayingViews.getImageView(), displayingViews.getRatingImage());
        setListener(displayingViews.getImageView());
    }

    public int randCenter(){
        Random random = new Random();
        int randomValue = random.nextInt(wineData.getWineNum());
        int centerIndex = wineData.getWineIndexList().get(randomValue);
        return centerIndex;
    }

    private boolean addWineList(int wineIndex){
        File file = new File(this.getFilesDir(), "myWineList.txt");
        String filename = "myWineList.txt";
        FileOutputStream outputStream;
        int before_myWineListLength = myWineListLength;

        try {
            if( !(myWineListIndex.contains(Integer.parseInt(String.valueOf(wineIndex)))) ){
                myWineListIndex.add(Integer.parseInt(String.valueOf(wineIndex)));
                myWineListLength++;
            }
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);//上書きモード
            for(int i=0; i<myWineListLength; i++){
                outputStream.write((String.valueOf(myWineListIndex.get(i)) + "\n").getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (before_myWineListLength != myWineListLength);

    }

    private void keepCenter(){//中心のワインを保存できるようにする関数
        File file = new File(this.getFilesDir(), "centerIndex.txt");
        String filename = "centerIndex.txt";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);//上書きモード
            outputStream.write(centerIndex);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readMyWineList() throws IOException {//MyWineリストのCSVを読み込む関数
        File file = new File(this.getFilesDir(), "myWineList.txt");
        FileReader filereader = new FileReader(file);
        BufferedReader br = new BufferedReader(filereader);

        String str = br.readLine();
        while(str != null){
            if( !(myWineListIndex.contains(Integer.parseInt(str))) ){
                myWineListIndex.add(Integer.parseInt(str));
            }
            str = br.readLine();
        }
        myWineListLength = myWineListIndex.size();

        br.close();

    }

    public void readEvalList() throws IOException {//評価リストのCSVを読み込む関数
        File file = new File(this.getFilesDir(), "evalList.txt");
        FileReader filereader = new FileReader(file);
        BufferedReader br = new BufferedReader(filereader);

        String str = br.readLine();
        for(int i=0; i<wineData.getWineNum(); i++){
            if(str == null)
                wineData.addWineEvalList("0");
            else
                wineData.addWineEvalList(str);
            str = br.readLine();
        }
        br.close();
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
                wineData.addWineColorList(stringTokenizer.nextToken());
                wineData.addWineTypeList(stringTokenizer.nextToken());
                wineData.addWineTasteList(stringTokenizer.nextToken());
                wineData.addWinePriceList(stringTokenizer.nextToken());
                wineData.addWinePriceNumList(stringTokenizer.nextToken());
                wineData.addWineCapacityList(stringTokenizer.nextToken());
                wineData.addWineGrapeList(stringTokenizer.nextToken());
                wineData.addWineOriginList(stringTokenizer.nextToken());
                wineData.addWineHarvestList(stringTokenizer.nextToken());
                wineData.addWineNameList(stringTokenizer.nextToken());
                wineData.addWineFuriganaList(stringTokenizer.nextToken());
                wineData.addWineryIDList(stringTokenizer.nextToken());
                wineData.addWineryNameList(stringTokenizer.nextToken());
                wineData.addWineExplanationList(stringTokenizer.nextToken());
            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        wineData.setWineNum(wineData.getWineIndexList().size());
    }

    public void readGrape() {//CSVファイルを読み込む関数
        try {
            InputStream inputStream =
                    getResources().getAssets().open("grape.csv");

            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream);

            BufferedReader bufferReader =
                    new BufferedReader(inputStreamReader);

            String line = "";

            while ((line = bufferReader.readLine()) != null) {
                StringTokenizer stringTokenizer =
                        new StringTokenizer(line, ",");

                grapeData.addWineIndexList(stringTokenizer.nextToken());
                grapeData.addMBA(stringTokenizer.nextToken());
                grapeData.addSS(stringTokenizer.nextToken());
                grapeData.addKosyu(stringTokenizer.nextToken());
                grapeData.addKS(stringTokenizer.nextToken());
                grapeData.addMerlot(stringTokenizer.nextToken());
                grapeData.addPV(stringTokenizer.nextToken());
                grapeData.addBQ(stringTokenizer.nextToken());
                grapeData.addKF(stringTokenizer.nextToken());
                grapeData.addKN(stringTokenizer.nextToken());
                grapeData.addSB(stringTokenizer.nextToken());
                grapeData.addDelaware(stringTokenizer.nextToken());
                grapeData.addTana(stringTokenizer.nextToken());
                grapeData.addTempranillo(stringTokenizer.nextToken());
                grapeData.addSyrah(stringTokenizer.nextToken());
                grapeData.addMourvale(stringTokenizer.nextToken());
                grapeData.addCarmenere(stringTokenizer.nextToken());
                grapeData.addChardonnay(stringTokenizer.nextToken());

            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        /*

    public void calPoint(int index){//選んだワインを中心にした時の各ワインの座標を計算する

        ArrayList<Integer> Index = wineData.getWineIndexList();
        ArrayList<Double> Ido = wineData.getWineIdoList();
        ArrayList<Double> Kedo = wineData.getWineKedoList();

        //選んだワインの緯度と経度を求める
        int picIndexNum = wineData.getWineIndexList().indexOf(centerIndex);//選んだワインのインデックス番号を取得
        picIndexNum = 2;
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

    //球状で動かす用
    public void calPoint2(int index){//選んだワインを中心にした時の各ワインの座標を計算する

        ArrayList<Integer> Index = wineData.getWineIndexList();
        ArrayList<Double> Ido = wineData.getWineIdoList();
        ArrayList<Double> Kedo = wineData.getWineKedoList();

        //選んだワインの緯度と経度を求める
        //int picIndexNum = wineData.getWineIndexList().indexOf(centerIndex);//選んだワインのインデックス番号を取得
        int picIndexNum = 2;
        double phi0 = Ido.get(picIndexNum);
        double theta0 = Kedo.get(picIndexNum);
        //double phi1 = Math.toRadians(90-(Math.toDegrees(phi0)));

        //各ワインの座標を求める
        for(int i=0; i<wineData.getWineNum(); i++){
            double phi = Ido.get(i);
            double theta = Kedo.get(i);

            double x = Math.sin(phi)*Math.cos(theta);
            double y = Math.sin(phi)*Math.sin(theta);
            double z = Math.cos(phi);
            viewsPoint.addxPoint(x);
            viewsPoint.addyPoint(y);
            viewsPoint.addzPints(z);
        }

        TextView text = findViewById(R.id.text_view);
        String str = "";
        for(int k=0; k<viewsPoint.getxPoints().size(); k++){
            str += "x=" + viewsPoint.getxPoints().get(k) + "y=" + viewsPoint.getyPoints().get(k) + "z=" + viewsPoint.getzPoints().get(k)+ "\n";
        }
        text.setText(str);
    }

        public void setPicture(ImageView[] imageView, ImageView[] ratingImage){

        FrameLayout usingLayout = (FrameLayout) findViewById(R.id.layout);

        //画像を設定
        for(int i=0;i<wineData.getWineNum();i++) {
            imageView[i] = new ImageView(this);
            ratingImage[i] = new ImageView(this);

            double wineID = wineData.getWineIndexList().get(i);
            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

            int xZero = zeroPoint.getxZeroPoint();
            int yZero = zeroPoint.getyZeroPoint();

            ArrayList<Double> xPoints = viewsPoint.getxPoints();
            ArrayList<Double> yPoints = viewsPoint.getyPoints();

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int)(pic_magnification*80), (int)(pic_magnification*200));
            lp.leftMargin = (int)(xZero + xPoints.get(i)*xZero/3*magnification);
            lp.topMargin = (int) (yZero + yPoints.get(i)*xZero/3*magnification);

            FrameLayout.LayoutParams Rlp = new FrameLayout.LayoutParams((int)(pic_magnification*50), (int)(pic_magnification*200));
            Rlp.leftMargin = (int)(xZero + xPoints.get(i)*xZero/3*magnification - 50);
            Rlp.topMargin = (int) (yZero + yPoints.get(i)*xZero/3*magnification);

            //imageView[i].measure(80,200);
            //ratingImage[i].measure(10,200);

            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
            imageView[i].setImageBitmap(bitmap1);
            imageView[i].setScaleType(ImageView.ScaleType.FIT_XY);
            usingLayout.addView(imageView[i], lp);


            if(wineData.getWineEvalList().size() != 0) {
                if (wineData.getWineEvalList().get(thisWineIndex) != 0) {
                    int drawImage = 0;
                    if (wineData.getWineEvalList().get(thisWineIndex) == 1) {
                        drawImage = R.drawable.rate_01;
                    } else if (wineData.getWineEvalList().get(thisWineIndex) == 2) {
                        drawImage = R.drawable.rate_02;
                    } else if (wineData.getWineEvalList().get(thisWineIndex) == 3) {
                        drawImage = R.drawable.rate_03;
                    } else if (wineData.getWineEvalList().get(thisWineIndex) == 4) {
                        drawImage = R.drawable.rate_04;
                    } else if (wineData.getWineEvalList().get(thisWineIndex) == 5) {
                        drawImage = R.drawable.rate_05;
                    }

                    Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), drawImage);

                    ratingImage[i].setImageBitmap(bitmap2);
                    ratingImage[i].setScaleType(ImageView.ScaleType.FIT_XY);

                    usingLayout.addView(ratingImage[i], Rlp);
                }
            }
        }
    }

        /*
    public void reCalPoint(int newX, int newY){//スクロール時の座標計算
        int xZero = zeroPoint.getxZeroPoint();
        int yZero = zeroPoint.getyZeroPoint();
        //x方向増加量
        Double xMoved = -( ((double)newY - yPoint)/yZero );
        //y方向増加量
        Double yMoved = -( ((double)newX - xPoint)/xZero );


        double movedPhi = -Math.asin(xMoved);
        double movedTheta = Math.asin(yMoved);

        ArrayList<Double> newXPoints = new ArrayList<>();
        ArrayList<Double> newYPoints = new ArrayList<>();
        ArrayList<Double> newZPoints = new ArrayList<>();

        for(int i=0; i<wineData.getWineNum(); i++) {

            double wineID = wineData.getWineIndexList().get(i);
            int thisWineIndex = wineData.getWineIndexList().indexOf((int) wineID);

            double beforeX = viewsPoint.getxPoints().get(thisWineIndex);
            double beforeY = viewsPoint.getyPoints().get(thisWineIndex);
            double beforeZ = viewsPoint.getzPoints().get(thisWineIndex);

            double afterX = beforeX;
            double afterY = beforeY * Math.cos(movedPhi) + beforeZ * Math.sin(movedPhi);
            double afterZ = -beforeY * Math.sin(movedPhi) + beforeZ * Math.cos(movedPhi);

            double X = afterX * Math.cos(movedTheta) - afterZ * Math.sin(movedTheta);
            double Y = afterY;
            double Z = afterX * Math.sin(movedTheta) + afterZ * Math.cos(movedTheta);

            newXPoints.add(X);
            newYPoints.add(Y);
            newZPoints.add(Z);

            double moveX = X - beforeX;
            double moveY = Y - beforeY;
            double moveZ = Z - beforeZ;
        }

        viewsPoint.deletePoint();
        viewsPoint.setxPoints(newXPoints);
        viewsPoint.setyPoints(newYPoints);
        viewsPoint.setzPoints(newZPoints);


        TextView text = findViewById(R.id.text_view);
        String str = "";
        for(int k=0; k<viewsPoint.getxPoints().size(); k++){
            str += "x=" + viewsPoint.getxPoints().get(k) + "y=" + viewsPoint.getyPoints().get(k) + "z=" + viewsPoint.getzPoints().get(k)+ "\n";
        }
        text.setText(str);


    }



     */
    /*

    //球状で動かす用
    public void setPicture2(ImageView[] imageView, ImageView[] ratingImage){

        FrameLayout usingLayout = (FrameLayout) findViewById(R.id.layout);

        //画像を設定
        for(int i=0;i<wineData.getWineNum();i++) {
            imageView[i] = new ImageView(this);
            ratingImage[i] = new ImageView(this);


            double wineID = wineData.getWineIndexList().get(i);
            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

            int xZero = zeroPoint.getxZeroPoint();
            int yZero = zeroPoint.getyZeroPoint();

            ArrayList<Double> xPoints = viewsPoint.getxPoints();
            ArrayList<Double> yPoints = viewsPoint.getyPoints();
            ArrayList<Double> zPoints = viewsPoint.getzPoints();

            if(zPoints.get(i) >= 0) {

                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int) (pic_magnification * 80), (int) (pic_magnification * 200));
                lp.leftMargin = (int) (xZero + xPoints.get(i)*xZero*magnification/3);
                lp.topMargin = (int) (yZero + yPoints.get(i)*xZero*magnification/3);

                FrameLayout.LayoutParams Rlp = new FrameLayout.LayoutParams((int)(pic_magnification*50), (int)(pic_magnification*200));
                Rlp.leftMargin = (int)(xZero + xPoints.get(i)*xZero*magnification/3 - 50*pic_magnification);//最後の50はラベルの左側に座標を指定するため
                Rlp.topMargin = (int) (yZero + yPoints.get(i)*xZero*magnification/3);

                //imageView[i].measure(80, 200);

                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                imageView[i].setImageBitmap(bitmap1);
                imageView[i].setScaleType(ImageView.ScaleType.FIT_XY);
                usingLayout.addView(imageView[i], lp);

                if(wineData.getWineEvalList().size() != 0) {
                    if (wineData.getWineEvalList().get(thisWineIndex) != 0) {
                        int drawImage = 0;
                        if (wineData.getWineEvalList().get(thisWineIndex) == 1) {
                            drawImage = R.drawable.rate_01;
                        } else if (wineData.getWineEvalList().get(thisWineIndex) == 2) {
                            drawImage = R.drawable.rate_02;
                        } else if (wineData.getWineEvalList().get(thisWineIndex) == 3) {
                            drawImage = R.drawable.rate_03;
                        } else if (wineData.getWineEvalList().get(thisWineIndex) == 4) {
                            drawImage = R.drawable.rate_04;
                        } else if (wineData.getWineEvalList().get(thisWineIndex) == 5) {
                            drawImage = R.drawable.rate_05;
                        }

                        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), drawImage);

                        ratingImage[i].setImageBitmap(bitmap2);
                        ratingImage[i].setScaleType(ImageView.ScaleType.FIT_XY);
                        usingLayout.addView(ratingImage[i], Rlp);

                    }
                }
            }
        }
    }
    */

/*
    public void slideImage(ImageView[] imageView, ImageView[] ratingImage, int newX, int newY){//画像をスライドする
        //int distance = 10000;
        //x方向増加量
        int addingX = newX - xPoint;
        //y方向増加量
        int addingY = newY - yPoint;
        for(int i=0; i<wineData.getWineNum(); i++) {
            //if(viewsPoint.getzPoints().get(i) >= 0) {

            double dx = imageView[i].getLeft() + addingX;
            double dy = imageView[i].getTop() + addingY;
            double imgW = dx + imageView[i].getWidth();
            double imgH = dy + imageView[i].getHeight();
            imageView[i].layout((int)dx, (int)dy, (int)imgW, (int)imgH);


            int Rdx = ratingImage[i].getLeft() + addingX;
            int Rdy = ratingImage[i].getTop() + addingY;
            int RimgW = Rdx + ratingImage[i].getWidth();
            int RimgH = Rdy + ratingImage[i].getHeight();
            ratingImage[i].layout(Rdx, Rdy, RimgW, RimgH);

        }
    }

    public void slideImage2(ImageView[] imageView, ImageView[] ratingImage, int newX, int newY){//画像をスライドする
        //x方向増加量
        int addingX = newX - xPoint;
        //y方向増加量
        int addingY = newY - yPoint;


        for(int i=0; i<wineData.getWineNum(); i++) {
            int dx = imageView[i].getLeft() + addingX;
            int dy = imageView[i].getTop() + addingY;
            int imgW = dx + imageView[i].getWidth();
            int imgH = dy + imageView[i].getHeight();
            imageView[i].layout(dx, dy, imgW, imgH);


            int Rdx = ratingImage[i].getLeft() + addingX;
            int Rdy = ratingImage[i].getTop() + addingY;
            int RimgW = Rdx + ratingImage[i].getWidth();
            int RimgH = Rdy + ratingImage[i].getHeight();
            ratingImage[i].layout(Rdx, Rdy, RimgW, RimgH);
        }
    }


    public void slideViews(ImageView[] imageView, ImageView[] ratingImage, int newX, int newY){//画面に表示されているものをスライドする
        slideImage(imageView, ratingImage, newX, newY);
    }

     */

}

