package com.example.wineapp;

import android.app.AlertDialog;
import android.content.Context;
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

    private ViewsPoint viewsPoint = new ViewsPoint();//各ビューの座標を保存するクラス

    private ArrayList<Integer> myWineListIndex = new ArrayList<>();
    private int myWineListLength = 0;

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
        try {
            readMyWineList();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                intent.putExtra("WINE_DATA", wineData);
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });
        //MYワインリスト画面遷移
        findViewById(R.id.myWine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MyWineActivity.class);
                intent.putExtra("WINE_DATA", wineData);
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });
        //ラベル画面遷移
        findViewById(R.id.label).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), LabelActivity.class);
                intent.putExtra("WINE_DATA", wineData);
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });
        //ワイナリー画面遷移
        findViewById(R.id.winery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), WineryActivity.class);
                intent.putExtra("WINE_DATA", wineData);
                intent.putExtra("CENTER_WINE", centerIndex);
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
                wineData.addWineColorList(stringTokenizer.nextToken());
                wineData.addWineTypeList(stringTokenizer.nextToken());
                wineData.addWineTasteList(stringTokenizer.nextToken());
                wineData.addWinePriceList(stringTokenizer.nextToken());
                wineData.addWineCapacityList(stringTokenizer.nextToken());
                wineData.addWineNameList(stringTokenizer.nextToken());
                wineData.addWineFuriganaList(stringTokenizer.nextToken());
                wineData.addWineryNameList(stringTokenizer.nextToken());
                wineData.addWineExplanationList(stringTokenizer.nextToken());
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

            double wineID = wineData.getWineIndexList().get(i);
            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);
            imageView[i].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]));
            RelativeLayout.LayoutParams lp;

            Intent me = getIntent();
            boolean[] deleteFlag = me.getBooleanArrayExtra("DELETE_FLAG");
            if(deleteFlag != null && deleteFlag[thisWineIndex]){
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
                    double wineID = wineData.getWineIndexList().get(thisWineNum);
                    int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);
                    winePicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]));

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
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle(wineData.getWineNameList().get(thisWineNum));
                            if( addWineList(wineData.getWineIndexList().get(thisWineNum)) ) {
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
                            double wineID = wineData.getWineIndexList().get(thisWineNum);
                            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);
                            winePicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]));

                            TextView wine_name = findViewById(R.id.wine_detail_name);

                            String color;
                            if(wineData.getWineColorList().get(thisWineNum) == 1)
                                color = "白";
                            else if(wineData.getWineColorList().get(thisWineNum) == 2)
                                color = "ロゼ";
                            else
                                color = "赤";

                            String type;
                            if(wineData.getWineTypeList().get(thisWineNum) == 1)
                                type = "スティル";
                            else
                                type = "スパークリング";

                            String str_wine
                                    = "ワイン名: " + wineData.getWineNameList().get(thisWineNum) + "\n\n"
                                    + "ワインの色: "  + color + "\n"
                                    + "ワインタイプ: " + type + "\n"
                                    + "価格: " + wineData.getWinePriceList().get(thisWineNum) + "円" + " "
                                    + "容量: " + wineData.getWineCapacityList().get(thisWineNum) + "mL" + "\n"
                                    + "ワイナリー名: " + wineData.getWineryNameList().get(thisWineNum);
                            wine_name.setText(str_wine);

                            TextView wine_explanation = findViewById(R.id.wine_detail_explanation);
                            String str_wine_exp =
                                    wineData.getWineNameList().get(thisWineNum) + ": " + "\n\n"
                                    + wineData.getWineExplanationList().get(thisWineNum);
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
                                    builder.setTitle(wineData.getWineNameList().get(thisWineNum));
                                    if( addWineList(wineData.getWineIndexList().get(thisWineNum)) ) {
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

}

