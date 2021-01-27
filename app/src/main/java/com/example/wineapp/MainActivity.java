package com.example.wineapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private WineData wineData = new WineData();
    private GrapeData grapeData = new GrapeData();

    private ArrayList<Integer> myWineListIndex = new ArrayList<>();
    private int myWineListLength = 0;

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
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wine);

        Intent me = getIntent();

        readCSV();//最初にデータを読み込む
        readGrape();

        final boolean [] deleteFlag = new boolean[wineData.getWineNum()];
        Arrays.fill(deleteFlag, true);

        //チェックボックスのアクション
        color_check_box();
        setButtonClick(deleteFlag);
        updateMyWineList(deleteFlag);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            // タップしたアイテムの取得//元々あったもの
            ListView thisListView = (ListView)parent;
            MyWineListItem thisItem = (MyWineListItem)thisListView.getItemAtPosition(position);  // SampleListItemにキャスト

            //リストが押された時
            ImageView winePicture = findViewById(R.id.my_wine_info);

            //final double wineID = myWineListIndex.get(position);
            final double wineID = thisItem.getWineID();
            final int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

            winePicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]));

            TextView my_wine_name = findViewById(R.id.my_wine_name);

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

            my_wine_name.setText(str_wine);

            TextView my_wine_explanation = findViewById(R.id.my_wine_explanation);
            String str_wine_exp = wineData.getWineExplanationList().get(thisWineIndex);
            my_wine_explanation.setText(str_wine_exp);

            //ワイン情報の乗っているレイアウトを表示
            RelativeLayout my_wine_info_layout = findViewById(R.id.for_my_wine_info);
            my_wine_info_layout.setVisibility(View.VISIBLE);

            RelativeLayout close_my_wine_info = findViewById(R.id.for_close_wine_info);
            close_my_wine_info.setVisibility(View.VISIBLE);

            my_wine_info_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //詳細情報がクリックされたときは何もしない
                }
            });

            close_my_wine_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ワイン情報閉じる用レイアウトがクリックされたときの処理
                    RelativeLayout my_wine_info_layout = findViewById(R.id.for_my_wine_info);
                    my_wine_info_layout.setVisibility(View.INVISIBLE);

                    RelativeLayout close_my_wine_info = findViewById(R.id.for_close_wine_info);
                    close_my_wine_info.setVisibility(View.INVISIBLE);
                }
            });


            ListView listView = findViewById(R.id.my_wine_list);
            listView.setClickable(false);


            //ここからレコメンデーションシステム
            Double thisWineIdo = wineData.getWineIdoList().get(thisWineIndex);
            Double thisWineKedo = wineData.getWineKedoList().get(thisWineIndex);

            double thisWineX = Math.sin(thisWineIdo) * Math.cos(thisWineKedo);
            double thisWineY = Math.sin(thisWineIdo) * Math.sin(thisWineKedo);
            double thisWineZ = Math.cos(thisWineIdo);

            double [] distance = new double[wineData.getWineNum()];

            for(int i=0; i<wineData.getWineNum(); i++){
                double wineID_ = wineData.getWineIndexList().get(i);
                int thisWineIndex_ = wineData.getWineIndexList().indexOf((int) wineID_);

                Double Ido = wineData.getWineIdoList().get(thisWineIndex_);
                Double Kedo = wineData.getWineKedoList().get(thisWineIndex_);

                double X = Math.sin(Ido) * Math.cos(Kedo);
                double Y = Math.sin(Ido) * Math.sin(Kedo);
                double Z = Math.cos(Ido);

                distance[i] = (Math.cbrt( (X-thisWineX)*(X-thisWineX) + (Y-thisWineY)*(Y-thisWineY) + (Z-thisWineZ)*(Z-thisWineZ) ));
            }

            ArrayList<Double> before_distance = new ArrayList<>();
            for(int i=0; i<wineData.getWineNum(); i++){
                before_distance.add(distance[i]);
            }
            Arrays.sort(distance);

            final int no1 = before_distance.indexOf(distance[1]);
            final int no2 = before_distance.indexOf(distance[2]);
            final int no3 = before_distance.indexOf(distance[3]);

            ImageView recommendation1 = findViewById(R.id.recommendation1);
            ImageView recommendation2 = findViewById(R.id.recommendation2);
            ImageView recommendation3 = findViewById(R.id.recommendation3);

            recommendation1.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[no1]));
            recommendation2.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[no2]));
            recommendation3.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[no3]));

            recommendation1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickAction(no1,thisWineIndex);
                }
            });
            recommendation2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickAction(no2,thisWineIndex);
                }
            });
            recommendation3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickAction(no3,thisWineIndex);
                }
            });
            Button returnBefore = findViewById(R.id.return_before_wine);
            returnBefore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickAction(-1, -1);
                }
            });

            //ここまでレコメンデーションシステム

        }
    };

    private void clickAction(int no, int beforeWineIndex){

        if(no != -1) {
            final int thisWineIndex = no;
            final int beforeIndex = beforeWineIndex;
            ImageView winePicture = findViewById(R.id.my_wine_info);
            winePicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]));

            TextView my_wine_name = findViewById(R.id.my_wine_name);

            String str_wine
                    = "ワイン名: " + wineData.getWineNameList().get(thisWineIndex) + "\n"
                    + "ワイナリー名: " + wineData.getWineryNameList().get(thisWineIndex) + "\n"
                    + "色: " + wineData.getWineColorList().get(thisWineIndex) + "\n"
                    + "タイプ: " + wineData.getWineTypeList().get(thisWineIndex) + "\n"
                    + "容量: " + wineData.getWineCapacityList().get(thisWineIndex) + "\n"
                    + "ぶどう品種: " + wineData.getWineGrapeList().get(thisWineIndex) + "\n"
                    + "産地: " + wineData.getWineOriginList().get(thisWineIndex) + "\n"
                    + "収穫年: " + wineData.getWineHarvestList().get(thisWineIndex) + "\n"
                    + "価格: " + wineData.getWinePriceList().get(thisWineIndex);

            my_wine_name.setText(str_wine);

            TextView my_wine_explanation = findViewById(R.id.my_wine_explanation);
            String str_wine_exp = wineData.getWineExplanationList().get(thisWineIndex);
            my_wine_explanation.setText(str_wine_exp);

            //ここからレコメンデーションシステム
            Double thisWineIdo = wineData.getWineIdoList().get(thisWineIndex);
            Double thisWineKedo = wineData.getWineKedoList().get(thisWineIndex);

            double thisWineX = Math.sin(thisWineIdo) * Math.cos(thisWineKedo);
            double thisWineY = Math.sin(thisWineIdo) * Math.sin(thisWineKedo);
            double thisWineZ = Math.cos(thisWineIdo);

            double[] distance = new double[wineData.getWineNum()];

            for (int i = 0; i < wineData.getWineNum(); i++) {
                double wineID_ = wineData.getWineIndexList().get(i);
                int thisWineIndex_ = wineData.getWineIndexList().indexOf((int) wineID_);

                Double Ido = wineData.getWineIdoList().get(thisWineIndex_);
                Double Kedo = wineData.getWineKedoList().get(thisWineIndex_);

                double X = Math.sin(Ido) * Math.cos(Kedo);
                double Y = Math.sin(Ido) * Math.sin(Kedo);
                double Z = Math.cos(Ido);

                distance[i] = (Math.cbrt((X - thisWineX) * (X - thisWineX) + (Y - thisWineY) * (Y - thisWineY) + (Z - thisWineZ) * (Z - thisWineZ)));
            }

            ArrayList<Double> before_distance = new ArrayList<>();
            for (int i = 0; i < wineData.getWineNum(); i++) {
                before_distance.add(distance[i]);
            }
            Arrays.sort(distance);

            final int no1 = before_distance.indexOf(distance[1]);
            final int no2 = before_distance.indexOf(distance[2]);
            final int no3 = before_distance.indexOf(distance[3]);

            ImageView recommendation1 = findViewById(R.id.recommendation1);
            ImageView recommendation2 = findViewById(R.id.recommendation2);
            ImageView recommendation3 = findViewById(R.id.recommendation3);

            recommendation1.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[no1]));
            recommendation2.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[no2]));
            recommendation3.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[no3]));

            recommendation1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickAction(no1, thisWineIndex);
                }
            });
            recommendation2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickAction(no2, thisWineIndex);
                }
            });
            recommendation3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickAction(no3, thisWineIndex);
                }
            });
            if (beforeIndex != -1) {
                Button returnBefore = findViewById(R.id.return_before_wine);
                returnBefore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickAction(beforeIndex, -1);
                    }
                });
            }
        }

        //ここまでレコメンデーションシステム

    }

    private void updateMyWineList(boolean[] deleteFlag){
        // レイアウトからリストビューを取得
        ListView listView = (ListView)findViewById(R.id.my_wine_list);
        // リストビューに表示する要素を設定
        ArrayList<MyWineListItem> listItems = new ArrayList<>();
        for (int i = 0; i < wineData.getWineNum(); i++) {
            double wineID = wineData.getWineIndexList().get(i);
            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

            if (!deleteFlag[thisWineIndex]) {

                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                    MyWineListItem item = new MyWineListItem(bmp,
                            wineData.getWineNameList().get(thisWineIndex),
                            wineData.getWineFuriganaList().get(thisWineIndex),
                            wineData.getWineryNameList().get(thisWineIndex),
                            wineData.getWineIndexList().get(thisWineIndex),
                            wineData.getWineColorList().get(thisWineIndex),
                            wineData.getWinePriceNumList().get(thisWineIndex));
                    listItems.add(item);
            }
        }

        if(listItems.size() == 0) {
            for (int i = 0; i < wineData.getWineNum(); i++) {
                double wineID = wineData.getWineIndexList().get(i);
                int thisWineIndex = wineData.getWineIndexList().indexOf((int) wineID);
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                MyWineListItem item = new MyWineListItem(bmp,
                        wineData.getWineNameList().get(thisWineIndex),
                        wineData.getWineFuriganaList().get(thisWineIndex),
                        wineData.getWineryNameList().get(thisWineIndex),
                        wineData.getWineIndexList().get(thisWineIndex),
                        wineData.getWineColorList().get(thisWineIndex),
                        wineData.getWinePriceNumList().get(thisWineIndex));
                listItems.add(item);
            }
        }
        // 出力結果をリストビューに表示
        MyWineListAdapter adapter = new MyWineListAdapter(this, R.layout.my_wine_list_item, listItems);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(onItemClickListener);  // タップ時のイベントを追加
    }


    public void setButtonClick(final boolean [] deleteFlag){

        findViewById(R.id.open_grape).setOnClickListener(new View.OnClickListener() {//ブドウ品種で検索
            @Override
            public void onClick(View view) {
                findViewById(R.id.for_search_grape).setVisibility(View.VISIBLE);
                findViewById(R.id.for_close_search_grape).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.for_search_grape).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ブドウの品種で検索ウィンドウが押されたとき
            }
        });
        findViewById(R.id.for_close_search_grape).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.for_search_grape).setVisibility(View.INVISIBLE);
                findViewById(R.id.for_close_search_grape).setVisibility(View.INVISIBLE);
                TextView grape = findViewById(R.id.text_selected_grape);
                grape.setText(selectedGrapes());
            }
        });
        findViewById(R.id.grape_enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.for_search_grape).setVisibility(View.INVISIBLE);
                findViewById(R.id.for_close_search_grape).setVisibility(View.INVISIBLE);
                TextView grape = findViewById(R.id.text_selected_grape);
                grape.setText(selectedGrapes());
            }
        });

        //検索ボタンが押されたとき
        findViewById(R.id.search_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.bar_list).setVisibility(View.INVISIBLE);
                findViewById(R.id.bar_search).setVisibility(View.VISIBLE);
                findViewById(R.id.search_list_menu).setVisibility(View.VISIBLE);
                
                //検索のエンターが押されたとき
                searchMyWine(deleteFlag);
                //タブ上の戻るボタンが押されたとき
                findViewById(R.id.return_search_mode).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.bar_list).setVisibility(View.VISIBLE);
                        findViewById(R.id.bar_search).setVisibility(View.INVISIBLE);
                        findViewById(R.id.search_list_menu).setVisibility(View.INVISIBLE);
                        updateMyWineList(deleteFlag);
                    }
                });
            }
        });

        //ソートボタンが押されたとき
        findViewById(R.id.sort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.bar_list).setVisibility(View.INVISIBLE);
                findViewById(R.id.bar_sort).setVisibility(View.VISIBLE);
                findViewById(R.id.sort_my_wine).setVisibility(View.VISIBLE);
                //名前で並び変え昇順ボタンが押されたとき
                findViewById(R.id.sort_name_up).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<MyWineListItem> listItems = new ArrayList<>();
                        ListView listView = (ListView) findViewById(R.id.my_wine_list);
                        for (int i = 0; i < wineData.getWineNum(); i++) {
                            double wineID = wineData.getWineIndexList().get(i);
                            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                            if(!deleteFlag[thisWineIndex]) {

                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                                MyWineListItem item = new MyWineListItem(bmp,
                                        wineData.getWineNameList().get(thisWineIndex),
                                        wineData.getWineFuriganaList().get(thisWineIndex),
                                        wineData.getWineryNameList().get(thisWineIndex),
                                        wineData.getWineIndexList().get(thisWineIndex),
                                        wineData.getWineColorList().get(thisWineIndex),
                                        wineData.getWinePriceNumList().get(thisWineIndex));

                                listItems.add(item);
                            }
                        }
                        MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                        Collections.sort(listItems, new NameComparator());
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);


                        findViewById(R.id.sort_my_wine).setVisibility(View.INVISIBLE);
                    }
                });
                //名前で並び変え降順ボタンが押されたとき
                findViewById(R.id.sort_name_down).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<MyWineListItem> listItems = new ArrayList<>();
                        ListView listView = (ListView) findViewById(R.id.my_wine_list);
                        for (int i = 0; i < wineData.getWineNum(); i++) {
                            double wineID = wineData.getWineIndexList().get(i);
                            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                            if(!deleteFlag[thisWineIndex]) {

                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                                MyWineListItem item = new MyWineListItem(bmp,
                                        wineData.getWineNameList().get(thisWineIndex),
                                        wineData.getWineFuriganaList().get(thisWineIndex),
                                        wineData.getWineryNameList().get(thisWineIndex),
                                        wineData.getWineIndexList().get(thisWineIndex),
                                        wineData.getWineColorList().get(thisWineIndex),
                                        wineData.getWinePriceNumList().get(thisWineIndex));

                                listItems.add(item);
                            }
                        }
                        MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                        Collections.sort(listItems, new NameComparatorDown());
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);


                        findViewById(R.id.sort_my_wine).setVisibility(View.INVISIBLE);
                    }
                });

                //ワイナリー名で並び変え昇順ボタンが押されたとき
                findViewById(R.id.sort_winery_up).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<MyWineListItem> listItems = new ArrayList<>();
                        ListView listView = (ListView) findViewById(R.id.my_wine_list);
                        for (int i = 0; i < wineData.getWineNum(); i++) {
                            double wineID = wineData.getWineIndexList().get(i);
                            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                            if(!deleteFlag[thisWineIndex]) {

                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                                MyWineListItem item = new MyWineListItem(bmp,
                                        wineData.getWineNameList().get(thisWineIndex),
                                        wineData.getWineFuriganaList().get(thisWineIndex),
                                        wineData.getWineryNameList().get(thisWineIndex),
                                        wineData.getWineIndexList().get(thisWineIndex),
                                        wineData.getWineColorList().get(thisWineIndex),
                                        wineData.getWinePriceNumList().get(thisWineIndex));

                                listItems.add(item);
                            }
                        }
                        MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                        Collections.sort(listItems, new WineryComparator());
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);


                        findViewById(R.id.sort_my_wine).setVisibility(View.INVISIBLE);
                    }
                });
                //ワイナリー名で並び変え降順ボタンが押されたとき
                findViewById(R.id.sort_winery_down).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<MyWineListItem> listItems = new ArrayList<>();
                        ListView listView = (ListView) findViewById(R.id.my_wine_list);
                        for (int i = 0; i < wineData.getWineNum(); i++) {
                            double wineID = wineData.getWineIndexList().get(i);
                            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                            if(!deleteFlag[thisWineIndex]) {

                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                                MyWineListItem item = new MyWineListItem(bmp,
                                        wineData.getWineNameList().get(thisWineIndex),
                                        wineData.getWineFuriganaList().get(thisWineIndex),
                                        wineData.getWineryNameList().get(thisWineIndex),
                                        wineData.getWineIndexList().get(thisWineIndex),
                                        wineData.getWineColorList().get(thisWineIndex),
                                        wineData.getWinePriceNumList().get(thisWineIndex));

                                listItems.add(item);
                            }
                        }
                        MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                        Collections.sort(listItems, new WineryComparatorDown());
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);


                        findViewById(R.id.sort_my_wine).setVisibility(View.INVISIBLE);
                    }
                });
                //ワインIDで並び変え昇順ボタンが押されたとき
                findViewById(R.id.sort_wineID_up).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<MyWineListItem> listItems = new ArrayList<>();
                        ListView listView = (ListView) findViewById(R.id.my_wine_list);
                        for (int i = 0; i < wineData.getWineNum(); i++) {
                            double wineID = wineData.getWineIndexList().get(i);
                            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                            if(!deleteFlag[thisWineIndex]) {

                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                                MyWineListItem item = new MyWineListItem(bmp,
                                        wineData.getWineNameList().get(thisWineIndex),
                                        wineData.getWineFuriganaList().get(thisWineIndex),
                                        wineData.getWineryNameList().get(thisWineIndex),
                                        wineData.getWineIndexList().get(thisWineIndex),
                                        wineData.getWineColorList().get(thisWineIndex),
                                        wineData.getWinePriceNumList().get(thisWineIndex));

                                listItems.add(item);
                            }
                        }
                        MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                        Collections.sort(listItems, new WineIDComparator());
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);


                        findViewById(R.id.sort_my_wine).setVisibility(View.INVISIBLE);
                    }
                });
                //ワインIDで並び変えボタン降順が押されたとき
                findViewById(R.id.sort_wineID_down).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<MyWineListItem> listItems = new ArrayList<>();
                        ListView listView = (ListView) findViewById(R.id.my_wine_list);
                        for (int i = 0; i < wineData.getWineNum(); i++) {
                            double wineID = wineData.getWineIndexList().get(i);
                            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                            if(!deleteFlag[thisWineIndex]) {

                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                                MyWineListItem item = new MyWineListItem(bmp,
                                        wineData.getWineNameList().get(thisWineIndex),
                                        wineData.getWineFuriganaList().get(thisWineIndex),
                                        wineData.getWineryNameList().get(thisWineIndex),
                                        wineData.getWineIndexList().get(thisWineIndex),
                                        wineData.getWineColorList().get(thisWineIndex),
                                        wineData.getWinePriceNumList().get(thisWineIndex));

                                listItems.add(item);
                            }
                        }
                        MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                        Collections.sort(listItems, new WineIDComparatorDown());
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);


                        findViewById(R.id.sort_my_wine).setVisibility(View.INVISIBLE);
                    }
                });
                //ワインの色で並び変え昇順ボタンが押されたとき
                findViewById(R.id.sort_wineColor_up).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<MyWineListItem> listItems = new ArrayList<>();
                        ListView listView = (ListView) findViewById(R.id.my_wine_list);
                        for (int i = 0; i < wineData.getWineNum(); i++) {
                            double wineID = wineData.getWineIndexList().get(i);
                            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                            if(!deleteFlag[thisWineIndex]) {

                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                                MyWineListItem item = new MyWineListItem(bmp,
                                        wineData.getWineNameList().get(thisWineIndex),
                                        wineData.getWineFuriganaList().get(thisWineIndex),
                                        wineData.getWineryNameList().get(thisWineIndex),
                                        wineData.getWineIndexList().get(thisWineIndex),
                                        wineData.getWineColorList().get(thisWineIndex),
                                        wineData.getWinePriceNumList().get(thisWineIndex));

                                listItems.add(item);
                            }
                        }
                        MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                        Collections.sort(listItems, new WineColorComparator());
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);


                        findViewById(R.id.sort_my_wine).setVisibility(View.INVISIBLE);
                    }
                });
                //ワインの色で並び変え降順ボタンが押されたとき
                findViewById(R.id.sort_wineColor_down).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<MyWineListItem> listItems = new ArrayList<>();
                        ListView listView = (ListView) findViewById(R.id.my_wine_list);
                        for (int i = 0; i < wineData.getWineNum(); i++) {
                            double wineID = wineData.getWineIndexList().get(i);
                            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                            if(!deleteFlag[thisWineIndex]) {

                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                                MyWineListItem item = new MyWineListItem(bmp,
                                        wineData.getWineNameList().get(thisWineIndex),
                                        wineData.getWineFuriganaList().get(thisWineIndex),
                                        wineData.getWineryNameList().get(thisWineIndex),
                                        wineData.getWineIndexList().get(thisWineIndex),
                                        wineData.getWineColorList().get(thisWineIndex),
                                        wineData.getWinePriceNumList().get(thisWineIndex));

                                listItems.add(item);
                            }
                        }
                        MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                        Collections.sort(listItems, new WineColorComparatorDown());
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);


                        findViewById(R.id.sort_my_wine).setVisibility(View.INVISIBLE);
                    }
                });
                //ワインの価格で並び変え昇順ボタンが押されたとき
                findViewById(R.id.sort_winePrice_up).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<MyWineListItem> listItems = new ArrayList<>();
                        ListView listView = (ListView) findViewById(R.id.my_wine_list);
                        for (int i = 0; i < wineData.getWineNum(); i++) {
                            double wineID = wineData.getWineIndexList().get(i);
                            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                            if(!deleteFlag[thisWineIndex]) {

                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                                MyWineListItem item = new MyWineListItem(bmp,
                                        wineData.getWineNameList().get(thisWineIndex),
                                        wineData.getWineFuriganaList().get(thisWineIndex),
                                        wineData.getWineryNameList().get(thisWineIndex),
                                        wineData.getWineIndexList().get(thisWineIndex),
                                        wineData.getWineColorList().get(thisWineIndex),
                                        wineData.getWinePriceNumList().get(thisWineIndex));

                                listItems.add(item);
                            }
                        }
                        MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                        Collections.sort(listItems, new WinePriceComparator());
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);


                        findViewById(R.id.sort_my_wine).setVisibility(View.INVISIBLE);
                    }
                });
                //ワインの価格で並び変え降順ボタンが押されたとき
                findViewById(R.id.sort_winePrice_down).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<MyWineListItem> listItems = new ArrayList<>();
                        ListView listView = (ListView) findViewById(R.id.my_wine_list);
                        for (int i = 0; i < wineData.getWineNum(); i++) {
                            double wineID = wineData.getWineIndexList().get(i);
                            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                            if(!deleteFlag[thisWineIndex]) {

                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                                MyWineListItem item = new MyWineListItem(bmp,
                                        wineData.getWineNameList().get(thisWineIndex),
                                        wineData.getWineFuriganaList().get(thisWineIndex),
                                        wineData.getWineryNameList().get(thisWineIndex),
                                        wineData.getWineIndexList().get(thisWineIndex),
                                        wineData.getWineColorList().get(thisWineIndex),
                                        wineData.getWinePriceNumList().get(thisWineIndex));

                                listItems.add(item);
                            }
                        }
                        MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                        Collections.sort(listItems, new WinePriceComparatorDown());
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);


                        findViewById(R.id.sort_my_wine).setVisibility(View.INVISIBLE);
                    }
                });



                findViewById(R.id.return_sort_mode).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.bar_list).setVisibility(View.VISIBLE);
                        findViewById(R.id.bar_sort).setVisibility(View.INVISIBLE);
                        findViewById(R.id.sort_my_wine).setVisibility(View.INVISIBLE);
                        updateMyWineList(deleteFlag);
                    }
                });
            }
        });
    }

    public void searchMyWine(final boolean [] deleteFlag){
        //検索のエンターが押されたとき
        findViewById(R.id.enter_my_wine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Arrays.fill(deleteFlag, true);

                //色のチェックボックス
                final CheckBox color_Red = findViewById(R.id.my_wine_color_Red);
                final CheckBox color_Rose = findViewById(R.id.my_wine_color_Rose);
                final CheckBox color_White = findViewById(R.id.my_wine_color_White);
                final CheckBox color_Orange = findViewById(R.id.my_wine_color_Orange);
                final CheckBox color_Sparkling = findViewById(R.id.my_wine_color_Sparkling);
                final CheckBox color_All = findViewById(R.id.my_wine_color_All);

                boolean color_search = false;
                boolean taste_red_search = false;
                boolean taste_white_search = false;
                boolean price_search = false;
                boolean grape_search = false;
                boolean free_search = false;


                //赤ワインの味わい
                Spinner spinner_taste_red_bottom = (Spinner) findViewById(R.id.spinner_red_bottom);
                Spinner spinner_taste_red_top = (Spinner) findViewById(R.id.spinner_red_top);
                String selected_red_bottom = (String) spinner_taste_red_bottom.getSelectedItem();
                String selected_red_top = (String) spinner_taste_red_top.getSelectedItem();

                //白ワインの味わい
                Spinner spinner_taste_white_bottom = (Spinner) findViewById(R.id.spinner_white_bottom);
                Spinner spinner_taste_white_top = (Spinner) findViewById(R.id.spinner_white_top);
                String selected_white_bottom = (String) spinner_taste_white_bottom.getSelectedItem();
                String selected_white_top = (String) spinner_taste_white_top.getSelectedItem();

                //価格
                Spinner spinner_price_bottom = (Spinner) findViewById(R.id.spinner_price_bottom);
                Spinner spinner_price_top = (Spinner) findViewById(R.id.spinner_price_top);
                String selected_price_bottom = (String) spinner_price_bottom.getSelectedItem();
                String selected_price_top = (String) spinner_price_top.getSelectedItem();


                int taste_Red_B = 0;
                if(selected_red_bottom.equals("フルボディ"))
                    taste_Red_B = 5;
                else if(selected_red_bottom.equals("ミディアムボディ") )
                    taste_Red_B = 3;
                else if(selected_red_bottom.equals("ライトボディ") )
                    taste_Red_B = 1;

                int taste_Red_T = 0;
                if(selected_red_top.equals("フルボディ"))
                    taste_Red_T = 5;
                else if(selected_red_top.equals("ミディアムボディ"))
                    taste_Red_T = 3;
                else if(selected_red_top.equals("ライトボディ"))
                    taste_Red_T = 1;

                int taste_white_B = 0;
                if(selected_white_bottom.equals("辛口"))
                    taste_white_B = 5;
                else if(selected_white_bottom.equals("普通"))
                    taste_white_B = 3;
                else if(selected_white_bottom.equals("甘口"))
                    taste_white_B = 1;

                int taste_white_T = 0;
                if(selected_white_top.equals("辛口"))
                    taste_white_T = 5;
                else if(selected_white_top.equals("普通"))
                    taste_white_T = 3;
                else if(selected_white_top.equals("甘口"))
                    taste_white_T = 1;

                //価格下
                int bottom_price = -1;
                if(selected_price_bottom.equals("0"))
                    bottom_price = 0;
                else if(selected_price_bottom.equals("2000"))
                    bottom_price = 2000;
                else if(selected_price_bottom.equals("3000"))
                    bottom_price = 3000;
                else if(selected_price_bottom.equals("4000"))
                    bottom_price = 4000;
                else if(selected_price_bottom.equals("5000"))
                    bottom_price = 5000;
                else if(selected_price_bottom.equals("20000"))
                    bottom_price = 20000;

                //価格上
                int top_price = -1;
                if(selected_price_top.equals("0"))
                    top_price = 0;
                else if(selected_price_top.equals("2000"))
                    top_price = 2000;
                else if(selected_price_top.equals("3000"))
                    top_price = 3000;
                else if(selected_price_top.equals("4000"))
                    top_price = 4000;
                else if(selected_price_top.equals("5000"))
                    top_price = 5000;
                else if(selected_price_top.equals("20000"))
                    top_price = 20000;

                //文字入力の自由検索
                final SearchView search = findViewById(R.id.free_search);
                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });

                if( !(color_All.isChecked())){
                    color_search = true;
                }
                if(taste_Red_B != 0 && taste_Red_T != 0 && (taste_Red_B <= taste_Red_T) ){
                    taste_red_search = true;
                }
                if(taste_white_B != 0 && taste_white_T != 0 && (taste_white_B <= taste_white_T) ){
                    taste_white_search = true;
                }
                if( (bottom_price != -1) && (top_price != -1) && (bottom_price <= top_price) ){
                    price_search = true;
                }
                if(search.getQuery().toString().trim().length() != 0){
                    free_search = true;
                }
                CheckBox MBA = findViewById(R.id.check_MBA);
                CheckBox SS = findViewById(R.id.check_SS);
                CheckBox Kosyu = findViewById(R.id.check_Kosyu);
                CheckBox KS = findViewById(R.id.check_KS);
                CheckBox Merlot = findViewById(R.id.check_Merlot);
                CheckBox PV = findViewById(R.id.check_PV);
                CheckBox BQ = findViewById(R.id.check_BQ);
                CheckBox KF = findViewById(R.id.check_KF);
                CheckBox KN = findViewById(R.id.check_KaiN);
                CheckBox SB = findViewById(R.id.check_SB);
                CheckBox Delaware = findViewById(R.id.check_Delaware);
                CheckBox Tana = findViewById(R.id.check_Tana);
                CheckBox Tempranillo = findViewById(R.id.check_Tempranillo);
                CheckBox Syrah = findViewById(R.id.check_Syrah);
                CheckBox Mourvedre = findViewById(R.id.check_Mourvedre);
                CheckBox Carmenere = findViewById(R.id.check_Carm);
                CheckBox Chardonnay = findViewById(R.id.check_Chardonnay);
                if( MBA.isChecked() || SS.isChecked() || Kosyu.isChecked() || KS.isChecked() || Merlot.isChecked()
                        || PV.isChecked() || BQ.isChecked() || KF.isChecked() || KN.isChecked() || SB.isChecked()
                        || Delaware.isChecked() || Tana.isChecked() || Tempranillo.isChecked() || Syrah.isChecked()
                        || Mourvedre.isChecked() || Carmenere.isChecked() || Chardonnay.isChecked())
                    grape_search = true;


                for(int i=0; i<wineData.getWineNum(); i++){

                    double wineID = wineData.getWineIndexList().get(i);
                    int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);


                    //色についての検索
                    if(! (color_All.isChecked()) ) {
                        if(wineData.getWineColorList().get(i).equals("白ワイン") && color_White.isChecked()){
                            deleteFlag[thisWineIndex] = false;
                        }
                        else if(wineData.getWineColorList().get(i).equals("ロゼワイン") && color_Rose.isChecked()){
                            deleteFlag[thisWineIndex] = false;
                        }
                        else if(wineData.getWineColorList().get(i).equals("赤ワイン") && color_Red.isChecked()){
                            deleteFlag[thisWineIndex] = false;
                        }
                        else if(wineData.getWineColorList().get(i).equals("オレンジワイン") && color_Orange.isChecked()){
                            deleteFlag[thisWineIndex] = false;
                        }
                        else if(wineData.getWineColorList().get(i).equals("スパークリングワイン") && color_Sparkling.isChecked()){
                            deleteFlag[thisWineIndex] = false;
                        }
                    }
                    //味わい赤についての検索
                    if(taste_Red_B != 0 && taste_Red_T != 0 && (taste_Red_B <= taste_Red_T) ){
                        if(color_search) {
                            if ( !((wineData.getWineColorList().get(i).equals("赤ワイン")) && (wineData.getWineTasteList().get(i) >= taste_Red_B) && (taste_Red_T >= wineData.getWineTasteList().get(i)))) {
                                if(deleteFlag[thisWineIndex] == false){
                                    deleteFlag[thisWineIndex] = true;
                                }
                            }
                        }
                        else{
                            if ( ((wineData.getWineColorList().get(i).equals("赤ワイン")) && (wineData.getWineTasteList().get(i) >= taste_Red_B) && (taste_Red_T >= wineData.getWineTasteList().get(i)))) {
                                deleteFlag[thisWineIndex] = false;
                            }
                        }
                    }
                    //味わい白についての検索
                    if(taste_white_B != 0 && taste_white_T != 0 && (taste_white_B <= taste_white_T) ){
                        if(color_search || taste_red_search) {
                            if ( !(!(wineData.getWineColorList().get(i).equals("赤ワイン")) && (wineData.getWineTasteList().get(i) >= taste_white_B) && (taste_white_T >= wineData.getWineTasteList().get(i)))) {
                                if(deleteFlag[thisWineIndex] == false){
                                    deleteFlag[thisWineIndex] = true;
                                }
                            }
                        }
                        else{
                            if ( (!(wineData.getWineColorList().get(i).equals("赤ワイン")) && (wineData.getWineTasteList().get(i) >= taste_white_B) && (taste_white_T >= wineData.getWineTasteList().get(i)))) {
                                deleteFlag[thisWineIndex] = false;
                            }
                        }
                    }
                    //価格についての検索
                    if( (bottom_price != -1) && (top_price != -1) && (bottom_price <= top_price) ){
                        if(color_search || taste_red_search || taste_white_search) {
                            if ( !((wineData.getWinePriceNumList().get(i) >= bottom_price) && (top_price >= wineData.getWinePriceNumList().get(i))) ){
                                if(deleteFlag[thisWineIndex] == false){
                                    deleteFlag[thisWineIndex] = true;
                                }
                            }
                        }
                        else{
                            if ( ((wineData.getWinePriceNumList().get(i) >= bottom_price) && (top_price >= wineData.getWinePriceNumList().get(i))) ){
                                deleteFlag[thisWineIndex] = false;
                            }
                        }
                    }
                    //自由検索についての検索
                    if(search.getQuery().toString().trim().length() != 0){
                        if(color_search || taste_red_search || taste_white_search || price_search) {
                            if( !(wineData.getWineNameList().get(i).contains(search.getQuery().toString()))){
                                if(deleteFlag[thisWineIndex] == false){
                                    deleteFlag[thisWineIndex] = true;
                                }
                            }
                        }
                        else{
                            if( (wineData.getWineNameList().get(i).contains(search.getQuery().toString()))){
                                deleteFlag[thisWineIndex] = false;
                            }
                        }
                    }
                    //ブドウの品種についての検索
                    boolean other_search = false;
                    if(color_search || taste_red_search || taste_white_search || price_search || free_search){
                        other_search = true;
                    }
                    int thisWineIndex_for_grape = grapeData.getWineIndexList().indexOf((int)wineID);
                    searchByGrape(thisWineIndex, thisWineIndex_for_grape, deleteFlag, other_search);
                }

                boolean check = false;
                for(int j=0; j<wineData.getWineNum(); j++){
                    if(deleteFlag[j] == false)
                        check = true;
                }


                if(check){//検索結果に当てはまるものがある場合
                    // レイアウトからリストビューを取得
                    ListView listView = (ListView) findViewById(R.id.my_wine_list);
                    // リストビューに表示する要素を設定
                    ArrayList<MyWineListItem> listItems = new ArrayList<>();
                    for (int i = 0; i < wineData.getWineNum(); i++) {
                        double wineID = wineData.getWineIndexList().get(i);
                        int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                        if(!deleteFlag[thisWineIndex]) {
                            Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                            MyWineListItem item = new MyWineListItem(bmp,
                                    wineData.getWineNameList().get(thisWineIndex),
                                    wineData.getWineFuriganaList().get(thisWineIndex),
                                    wineData.getWineryNameList().get(thisWineIndex),
                                    wineData.getWineIndexList().get(thisWineIndex),
                                    wineData.getWineColorList().get(thisWineIndex),
                                    wineData.getWinePriceNumList().get(thisWineIndex));
                            listItems.add(item);
                        }
                    }
                    // 出力結果をリストビューに表示
                    MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                    listView.setAdapter(adapter);


                    listView.setOnItemClickListener(onItemClickListener);  // タップ時のイベントを追加
                }
                else{//検索結果に当てはまるものがない場合
// レイアウトからリストビューを取得
                    ListView listView = (ListView) findViewById(R.id.my_wine_list);
                    // リストビューに表示する要素を設定
                    ArrayList<MyWineListItem> listItems = new ArrayList<>();
                    for (int i = 0; i < wineData.getWineNum(); i++) {
                        double wineID = wineData.getWineIndexList().get(i);
                        int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);
                        Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                        MyWineListItem item = new MyWineListItem(bmp,
                                wineData.getWineNameList().get(thisWineIndex),
                                wineData.getWineFuriganaList().get(thisWineIndex),
                                wineData.getWineryNameList().get(thisWineIndex),
                                wineData.getWineIndexList().get(thisWineIndex),
                                wineData.getWineColorList().get(thisWineIndex),
                                wineData.getWinePriceNumList().get(thisWineIndex));
                        listItems.add(item);
                    }
                    // 出力結果をリストビューに表示
                    MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                    listView.setAdapter(adapter);


                    listView.setOnItemClickListener(onItemClickListener);  // タップ時のイベントを追加
                }
                findViewById(R.id.search_list_menu).setVisibility(View.INVISIBLE);
            }
        });
    }

    public void color_check_box(){
        //色のチェックボックス
        final CheckBox color_Red = findViewById(R.id.my_wine_color_Red);
        final CheckBox color_Rose = findViewById(R.id.my_wine_color_Rose);
        final CheckBox color_White = findViewById(R.id.my_wine_color_White);
        final CheckBox color_Orange = findViewById(R.id.my_wine_color_Orange);
        final CheckBox color_Sparkling = findViewById(R.id.my_wine_color_Sparkling);
        final CheckBox color_All = findViewById(R.id.my_wine_color_All);

        color_Red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(color_Red.isChecked() && color_Rose.isChecked() && color_White.isChecked() && color_Orange.isChecked() && color_Sparkling.isChecked()){
                    color_All.setChecked(true);
                    color_Red.setChecked(false);
                    color_Rose.setChecked(false);
                    color_White.setChecked(false);
                    color_Orange.setChecked(false);
                    color_Sparkling.setChecked(false);
                }
                else if(!color_Red.isChecked() && !color_Rose.isChecked() && !color_White.isChecked() && !color_Orange.isChecked() && !color_Sparkling.isChecked()){
                    color_All.setChecked(true);
                }
                else if(color_All.isChecked()) {
                    color_All.setChecked(false);
                }
            }
        });
        color_Rose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(color_Red.isChecked() && color_Rose.isChecked() && color_White.isChecked() && color_Orange.isChecked() && color_Sparkling.isChecked()){
                    color_All.setChecked(true);
                    color_Red.setChecked(false);
                    color_Rose.setChecked(false);
                    color_White.setChecked(false);
                    color_Orange.setChecked(false);
                    color_Sparkling.setChecked(false);
                }
                else if(!color_Red.isChecked() && !color_Rose.isChecked() && !color_White.isChecked() && !color_Orange.isChecked() && !color_Sparkling.isChecked()){
                    color_All.setChecked(true);
                }
                else if(color_All.isChecked()) {
                    color_All.setChecked(false);
                }
            }
        });
        color_White.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(color_Red.isChecked() && color_Rose.isChecked() && color_White.isChecked() && color_Orange.isChecked() && color_Sparkling.isChecked()){
                    color_All.setChecked(true);
                    color_Red.setChecked(false);
                    color_Rose.setChecked(false);
                    color_White.setChecked(false);
                    color_Orange.setChecked(false);
                    color_Sparkling.setChecked(false);
                }
                else if(!color_Red.isChecked() && !color_Rose.isChecked() && !color_White.isChecked() && !color_Orange.isChecked() && !color_Sparkling.isChecked()){
                    color_All.setChecked(true);
                }
                else if(color_All.isChecked()) {
                    color_All.setChecked(false);
                }
            }
        });
        color_Orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(color_Red.isChecked() && color_Rose.isChecked() && color_White.isChecked() && color_Orange.isChecked() && color_Sparkling.isChecked()){
                    color_All.setChecked(true);
                    color_Red.setChecked(false);
                    color_Rose.setChecked(false);
                    color_White.setChecked(false);
                    color_Orange.setChecked(false);
                    color_Sparkling.setChecked(false);
                }
                else if(!color_Red.isChecked() && !color_Rose.isChecked() && !color_White.isChecked() && !color_Orange.isChecked() && !color_Sparkling.isChecked()){
                    color_All.setChecked(true);
                }
                else if(color_All.isChecked()) {
                    color_All.setChecked(false);
                }
            }
        });
        color_Sparkling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(color_Red.isChecked() && color_Rose.isChecked() && color_White.isChecked() && color_Orange.isChecked() && color_Sparkling.isChecked()){
                    color_All.setChecked(true);
                    color_Red.setChecked(false);
                    color_Rose.setChecked(false);
                    color_White.setChecked(false);
                    color_Orange.setChecked(false);
                    color_Sparkling.setChecked(false);
                }
                else if(!color_Red.isChecked() && !color_Rose.isChecked() && !color_White.isChecked() && !color_Orange.isChecked() && !color_Sparkling.isChecked()){
                    color_All.setChecked(true);
                }
                else if(color_All.isChecked()) {
                    color_All.setChecked(false);
                }
            }
        });
        color_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(color_All.isChecked()) {
                    color_All.setChecked(true);
                    color_Red.setChecked(false);
                    color_Rose.setChecked(false);
                    color_White.setChecked(false);
                    color_Orange.setChecked(false);
                    color_Sparkling.setChecked(false);
                }
                else if(color_All.isChecked()) {
                    color_All.setChecked(false);
                }
            }
        });
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

    public void searchByGrape(int wineIndex, int grapeIndex, boolean flag[], boolean search){
        //ブドウの品種のチェックボックス
        CheckBox MBA = findViewById(R.id.check_MBA);
        CheckBox SS = findViewById(R.id.check_SS);
        CheckBox Kosyu = findViewById(R.id.check_Kosyu);
        CheckBox KS = findViewById(R.id.check_KS);
        CheckBox Merlot = findViewById(R.id.check_Merlot);
        CheckBox PV = findViewById(R.id.check_PV);
        CheckBox BQ = findViewById(R.id.check_BQ);
        CheckBox KF = findViewById(R.id.check_KF);
        CheckBox KN = findViewById(R.id.check_KaiN);
        CheckBox SB = findViewById(R.id.check_SB);
        CheckBox Delaware = findViewById(R.id.check_Delaware);
        CheckBox Tana = findViewById(R.id.check_Tana);
        CheckBox Tempranillo = findViewById(R.id.check_Tempranillo);
        CheckBox Syrah = findViewById(R.id.check_Syrah);
        CheckBox Mourvedre = findViewById(R.id.check_Mourvedre);
        CheckBox Carmenere = findViewById(R.id.check_Carm);
        CheckBox Chardonnay = findViewById(R.id.check_Chardonnay);

        //ブドウの品種についての検索
        if(search) {
            if (MBA.isChecked() && grapeData.getMBA().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(MBA.isChecked() && grapeData.getMBA().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (SS.isChecked() && grapeData.getSS().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(SS.isChecked() && grapeData.getSS().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (Kosyu.isChecked() && grapeData.getKosyu().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(Kosyu.isChecked() && grapeData.getKosyu().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (KS.isChecked() && grapeData.getKS().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(KS.isChecked() && grapeData.getKS().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (Merlot.isChecked() && grapeData.getMerlot().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(Merlot.isChecked() && grapeData.getMerlot().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (PV.isChecked() && grapeData.getPV().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(PV.isChecked() && grapeData.getPV().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (BQ.isChecked() && grapeData.getBQ().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(BQ.isChecked() && grapeData.getBQ().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (KF.isChecked() && grapeData.getKF().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(KF.isChecked() && grapeData.getKF().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (KN.isChecked() && grapeData.getKN().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(KN.isChecked() && grapeData.getKN().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (SB.isChecked() && grapeData.getSB().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(SB.isChecked() && grapeData.getSB().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (Delaware.isChecked() && grapeData.getDelaware().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(Delaware.isChecked() && grapeData.getDelaware().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (Tana.isChecked() && grapeData.getTana().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(Tana.isChecked() && grapeData.getTana().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (Tempranillo.isChecked() && grapeData.getTempranillo().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(Tempranillo.isChecked() && grapeData.getTempranillo().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (Syrah.isChecked() && grapeData.getSyrah().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(Syrah.isChecked() && grapeData.getSyrah().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (Mourvedre.isChecked() && grapeData.getMourvale().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(Mourvedre.isChecked() && grapeData.getMourvale().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (Carmenere.isChecked() && grapeData.getCarmenere().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(Carmenere.isChecked() && grapeData.getCarmenere().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
        if(search) {
            if (Chardonnay.isChecked() && grapeData.getChardonnay().get(grapeIndex) != 1) {
                flag[wineIndex] = true;
            }
        }
        else{
            if(Chardonnay.isChecked() && grapeData.getChardonnay().get(grapeIndex) == 1){
                flag[wineIndex] = false;
            }
        }
    }

    public String selectedGrapes(){
        String selected = "";

        CheckBox MBA = findViewById(R.id.check_MBA);
        CheckBox SS = findViewById(R.id.check_SS);
        CheckBox Kosyu = findViewById(R.id.check_Kosyu);
        CheckBox KS = findViewById(R.id.check_KS);
        CheckBox Merlot = findViewById(R.id.check_Merlot);
        CheckBox PV = findViewById(R.id.check_PV);
        CheckBox BQ = findViewById(R.id.check_BQ);
        CheckBox KF = findViewById(R.id.check_KF);
        CheckBox KN = findViewById(R.id.check_KaiN);
        CheckBox SB = findViewById(R.id.check_SB);
        CheckBox Delaware = findViewById(R.id.check_Delaware);
        CheckBox Tana = findViewById(R.id.check_Tana);
        CheckBox Tempranillo = findViewById(R.id.check_Tempranillo);
        CheckBox Syrah = findViewById(R.id.check_Syrah);
        CheckBox Mourvedre = findViewById(R.id.check_Mourvedre);
        CheckBox Carmenere = findViewById(R.id.check_Carm);
        CheckBox Chardonnay = findViewById(R.id.check_Chardonnay);

        if(MBA.isChecked()){
            selected += "マスカット・ベーリーA, ";
        }
        if(SS.isChecked()){
            selected += "サンセミヨン, ";
        }
        if(Kosyu.isChecked()){
            selected += "甲州, ";
        }
        if(KS.isChecked()){
            selected += "カベルネ・ソーヴィニョン, ";
        }
        if(Merlot.isChecked()){
            selected += "メルロー, ";
        }
        if(PV.isChecked()){
            selected += "プティ・ヴェルド, ";
        }
        if(BQ.isChecked()){
            selected += "ブラック・クィーン, ";
        }
        if(KF.isChecked()){
            selected += "カベルネ・フラン, ";
        }
        if(KN.isChecked()){
            selected += "甲斐ノワール, ";
        }
        if(SB.isChecked()){
            selected += "ソーヴィニヨン・ブラン, ";
        }
        if(Delaware.isChecked()){
            selected += "デラウェア, ";
        }
        if(Tana.isChecked()){
            selected += "タナ, ";
        }
        if(Tempranillo.isChecked()){
            selected += "テンプラニーリョ, ";
        }
        if(Syrah.isChecked()){
            selected += "シラー, ";
        }
        if(Mourvedre.isChecked()){
            selected += "ムールヴェール, ";
        }
        if(Carmenere.isChecked()){
            selected += "カルメネール, ";
        }
        if(Chardonnay.isChecked()){
            selected += "シャルドネ, ";
        }

        if(selected.equals("")){
            selected += "なし";
        }
        return selected;
    }




}

