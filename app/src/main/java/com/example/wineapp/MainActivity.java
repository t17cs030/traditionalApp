package com.example.wineapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private int xPoint, yPoint; //画面スクロール用

    private DisplayingViews displayingViews;//画面に張り付ける画像群クラス

    private ZeroPoint zeroPoint;//画面の原点を保持するクラス

    private WineData wineData = new WineData();//ワインのインデックスや緯度経度リストのクラス

    private ViewsPoint viewsPoint = new ViewsPoint();//各ビューの座標を保存するクラス

    private int centerIndex = 0;//中央のワインのインデックス(初期値は0)
    private double magnification=2;//拡大率

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
        Intent me = getIntent();
        int center = me.getIntExtra("CENTER_WINE", 0);
        if(center == 0)
            centerIndex = randCenter();
        else
            centerIndex = center;

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
                intent.putExtra("WINE_INDEX", wineData.getWineIndexList());
                intent.putExtra("WINE_NAME", wineData.getWineNameList());
                intent.putExtra("WINE_COLOR", wineData.getWineColorList());
                intent.putExtra("WINE_TASTE", wineData.getWineTasteList());
                intent.putExtra("WINE_PRICE", wineData.getWinePriceList());
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });
        //MYワインリスト画面遷移
        findViewById(R.id.myWine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MyWineActivity.class);
                intent.putExtra("WINE_INDEX", wineData.getWineIndexList());
                intent.putExtra("WINE_NAME", wineData.getWineNameList());
                intent.putExtra("WINE_COLOR", wineData.getWineColorList());
                intent.putExtra("WINE_TASTE", wineData.getWineTasteList());
                intent.putExtra("WINE_PRICE", wineData.getWinePriceList());
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });
        //ラベル画面遷移
        findViewById(R.id.label).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.putExtra("WINE_INDEX", wineData.getWineIndexList());
                intent.putExtra("WINE_NAME", wineData.getWineNameList());
                intent.putExtra("WINE_COLOR", wineData.getWineColorList());
                intent.putExtra("WINE_TASTE", wineData.getWineTasteList());
                intent.putExtra("WINE_PRICE", wineData.getWinePriceList());
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });
        //ワイナリー画面遷移
        findViewById(R.id.winery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //手抜き画面遷移APIを使わないver

                
                //Intent intent = new Intent(getApplication(), WineryActivity.class);
                //startActivity(intent);

                String winery_name = "SADOYA";
                Uri uri = Uri.parse("geo:0,0?q=" + winery_name);
                //Uri uri = Uri.parse("geo:35.681382,139.766084?z=16");

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.putExtra("WINE_INDEX", wineData.getWineIndexList());
                intent.putExtra("WINE_NAME", wineData.getWineNameList());
                intent.putExtra("WINE_COLOR", wineData.getWineColorList());
                intent.putExtra("WINE_TASTE", wineData.getWineTasteList());
                intent.putExtra("WINE_PRICE", wineData.getWinePriceList());
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

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

                wineData.addWineNameList(stringTokenizer.nextToken());

                wineData.addWineColorList(stringTokenizer.nextToken());
                wineData.addWineTasteList(stringTokenizer.nextToken());
                wineData.addWinePriceList(stringTokenizer.nextToken());

            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        wineData.setWineNum(wineData.getWineIndexList().size());
    }

    public void calPoint(int index){//選んだワインを中心にした時の各ワインの座標を計算する

        ArrayList<Integer> Index = wineData.getWineIndexList();
        ArrayList<Double> Ido = wineData.getWineIdoList();
        ArrayList<Double> Kedo = wineData.getWineKedoList();

        //選んだワインの緯度と経度を求める
        int picIndexNum = wineData.getWineIndexList().indexOf(centerIndex);//選んだワインのインデックス番号を取得
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

            double wineIndex = wineData.getWineIndexList().get(i);
            imageView[i].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[(int)wineIndex-1]));
            RelativeLayout.LayoutParams lp;

            Intent me = getIntent();
            boolean[] deleteFlag = me.getBooleanArrayExtra("DELETE_FLAG");
            if(deleteFlag != null && deleteFlag[wineData.getWineIndexList().get(i)-1]){
                lp = new RelativeLayout.LayoutParams((int)(50), (int)(50));
            }
            else {
                lp = new RelativeLayout.LayoutParams((int) (200), (int) (200));
            }

            int xZero = zeroPoint.getxZeroPoint();
            int yZero = zeroPoint.getyZeroPoint();

            ArrayList<Double> xPoints = viewsPoint.getxPoints();
            ArrayList<Double> yPoints = viewsPoint.getyPoints();

            TextView textView = findViewById(R.id.text_view2);
            String str = "X=" + xZero + "y=" + yZero;
            textView.setText(str);

            lp.leftMargin = (int)(xZero + xPoints.get(i)*xZero/3*magnification);
            lp.topMargin = (int) (yZero + yPoints.get(i)*xZero/3*magnification);

            usingLayout.addView(imageView[i], lp);

            imageView[i].layout(0, 0, imageView[i].getWidth(), imageView[i].getHeight());
        }
    }

    public void setListener(final ImageView[] imageView){

        //画像にリスナーを設定
        for(int i=0;i<wineData.getWineNum();i++) {
            final int thisWineNum = i;
            final RelativeLayout usingLayout = findViewById(R.id.layout);


            imageView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //画像が押された時のやつ
                    ImageView winePicture = findViewById(R.id.wine_info);
                    double wineIndex = wineData.getWineIndexList().get(thisWineNum);
                    winePicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[(int)wineIndex-1]));

                    TextView wine_name = findViewById(R.id.wine_name);
                    String str_wine = "ワイン名: " + wineData.getWineNameList().get(thisWineNum);
                    wine_name.setText(str_wine);

                    //簡易情報の乗っているレイアウトを表示
                    RelativeLayout wine_info_layout = findViewById(R.id.for_wine_info);
                    wine_info_layout.setVisibility(View.VISIBLE);

                    TextView to_wine_list = findViewById(R.id.to_wine_list);
                    to_wine_list.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //ワインリストへ登録ボタンが押されたときの処理(仮)
                            Intent intent = new Intent(getApplication(), MyWineActivity.class);
                            startActivity(intent);
                        }
                    });

                    //簡易情報をクリックした際のアクション
                    wine_info_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ImageView winePicture = findViewById(R.id.wine_detail_info);
                            double wineIndex = wineData.getWineIndexList().get(thisWineNum);
                            winePicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[(int) wineIndex - 1]));

                            TextView wine_name = findViewById(R.id.wine_detail_name);

                            String color;
                            if(wineData.getWineColorList().get(thisWineNum) == 1)
                                color = "白";
                            else if(wineData.getWineColorList().get(thisWineNum) == 2)
                                color = "ロゼ";
                            else
                                color = "赤";

                            String str_wine
                                    = "ワイン名: " + wineData.getWineNameList().get(thisWineNum) + "\n\n"
                                    + "ワインの色: "  + color + "\n\n"
                                    + "価格: " + wineData.getWinePriceList().get(thisWineNum) + "円";
                            wine_name.setText(str_wine);

                            TextView wine_explanation = findViewById(R.id.wine_detail_explanation);
                            String str_wine_exp =
                                    wineData.getWineNameList().get(thisWineNum) + "の説明";
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
                                    Intent intent = new Intent(getApplication(), MyWineActivity.class);
                                    startActivity(intent);
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
                    TextView textView = findViewById(R.id.text_view);
                    String str = "画像をクリックしました" + wineData.getWineIndexList().get(thisWineNum);
                    textView.setText(str);
                }
            });
        }
    }

    public void drawPicture(){//画像の再描画


        //画像の初期設定
        displayingViews = new DisplayingViews(wineData.getWineNum());
        //座標の計算
        calPoint(centerIndex);
        //画像の張り付け
        setPicture(displayingViews.getImageView(), displayingViews.getTextIndexView());
        setListener(displayingViews.getImageView());

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
                //ここで中心を変える
                centerIndex = wineData.getWineIndexList().get(i);
            }
        }
    }


    public void slideViews(ImageView[] imageView, TextView[] textView, int newX, int newY){//画面に表示されているものをスライドする
        slideImage(imageView, newX, newY);
    }

    public int randCenter(){
        Random random = new Random();
        int randomValue = random.nextInt(wineData.getWineNum());
        return 1+randomValue;
    }

}

