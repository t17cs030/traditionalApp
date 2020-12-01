package com.example.wineapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public class MyWineActivity extends AppCompatActivity
        implements View.OnClickListener

{  //クリックリスナーを実装
    private WineData wineData = new WineData();
    private GrapeData grapeData = new GrapeData();
    private  int centerIndex = 0;

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

        wineData.setWineData((WineData)me.getSerializableExtra("WINE_DATA"));
        grapeData.setGrapeData((GrapeData)me.getSerializableExtra("GRAPE_DATA"));
        centerIndex = me.getIntExtra("CENTER_WINE", 0);

        final boolean [] deleteFlag = new boolean[wineData.getWineNum()];
        Arrays.fill(deleteFlag, true);

        findViewById(R.id.wineMap).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);
//        findViewById(R.id.myWine).setOnClickListener(this);
        findViewById(R.id.label).setOnClickListener(this);
        findViewById(R.id.winery).setOnClickListener(this);

        //チェックボックスのアクション
        color_check_box();

        //類似度マップへボタンが押されたとき
        findViewById(R.id.to_wine_map_multiple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView listView = findViewById(R.id.my_wine_list_multiple);
                for(int i=0; i<myWineListLength; i++){
                    MyWineListMultipleItem item = (MyWineListMultipleItem)listView.getItemAtPosition(i);
                    if( item.getCheck() ) {
                        double wineID = myWineListIndex.get(i);
                        int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);
                        deleteFlag[thisWineIndex] = false;
                    }
                }
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.putExtra("DELETE_FLAG", deleteFlag);
                startActivity(intent);
            }
        });

        setButtonClick();

        updateMyWineList();
        updateMultipleMyWineList();
    }
    //ボタンが押された時の処理
    public void onClick (View view){
        Intent intent;
        if (view.getId() == R.id.search) {//検索画面へ
            intent = new Intent(this, SearchActivity.class);
        }
        else if (view.getId() == R.id.wineMap) {//ワインマップへ
            intent = new Intent(this, MainActivity.class);
        }
        else if (view.getId() == R.id.label) {//ラベル読み込みへ
            intent = new Intent(this, LabelActivity.class);
        }
        else if (view.getId() == R.id.winery) {//ワイナリーマップへ
            intent = new Intent(this, WineryActivity.class);
        }
        else{//MYワインを維持
            intent = new Intent(this, MyWineActivity.class);
        }
        intent.putExtra("WINE_DATA", wineData);
        intent.putExtra("GRAPE_DATA", grapeData);
        intent.putExtra("CENTER_WINE", centerIndex);
        startActivity(intent);
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

    /**
     * リストビューのタップイベント
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            // タップしたアイテムの取得//元々あったもの
            //ListView listView = (ListView)parent;
            //MyWineListItem item = (MyWineListItem)listView.getItemAtPosition(position);  // SampleListItemにキャスト

            //リストが押された時
            ImageView winePicture = findViewById(R.id.my_wine_info);

            final double wineID = myWineListIndex.get(position);
            final int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

            winePicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]));

            TextView my_wine_name = findViewById(R.id.my_wine_name);

            String color;
            if(wineData.getWineColorList().get(thisWineIndex) == 1)
                color = "白";
            else if(wineData.getWineColorList().get(thisWineIndex) == 3)
                color = "ロゼ";
            else if(wineData.getWineColorList().get(thisWineIndex) == 5)
                color = "赤";
            else
                color = "オレンジ";

            String str_wine
                    = "ワイン名: " + wineData.getWineNameList().get(thisWineIndex) + "\n\n"
                    + "ワインの色: "  + color + "\n\n"
                    + "価格: " + wineData.getWinePriceList().get(thisWineIndex) + "円"
                    ;
            my_wine_name.setText(str_wine);

            TextView my_wine_explanation = findViewById(R.id.my_wine_explanation);
            String str_wine_exp =
                    wineData.getWineNameList().get(thisWineIndex) + "の説明";
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

            TextView to_wine_map = findViewById(R.id.to_wine_map);
            to_wine_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //類似度マップへボタンが押されたときの処理(仮)
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.putExtra("WINE_DATA", wineData);
                    intent.putExtra("GRAPE_DATA", grapeData);
                    intent.putExtra("CENTER_WINE", (int)wineID);
                    startActivity(intent);
                }
            });

            TextView to_favorite = findViewById(R.id.to_favorite);
            to_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //お気に入りボタンが押された時の処理
                }
            });

            TextView to_delete = findViewById(R.id.to_delete);
            to_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //削除ボタンが押された時の処理
                    RelativeLayout close_delete = findViewById(R.id.for_close_delete);
                    close_delete.setVisibility(View.VISIBLE);
                    close_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //削除タブ表示中は何を押しても何もしない
                        }
                    });

                    RelativeLayout for_delete = findViewById(R.id.for_delete);
                    for_delete.setVisibility(View.VISIBLE);
                    for_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //削除しますかタブを押しても何もしない
                        }
                    });

                    TextView yes = findViewById(R.id.delete_yes);
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //はい　が押されたときの処理
                            myWineListIndex.remove(position);
                            myWineListLength--;
                            resetWineList();
                            updateMyWineList();
                            updateMultipleMyWineList();

                            RelativeLayout close_delete = findViewById(R.id.for_close_delete);
                            close_delete.setVisibility(View.INVISIBLE);

                            RelativeLayout for_delete = findViewById(R.id.for_delete);
                            for_delete.setVisibility(View.INVISIBLE);

                            RelativeLayout my_wine_info_layout = findViewById(R.id.for_my_wine_info);
                            my_wine_info_layout.setVisibility(View.INVISIBLE);

                            RelativeLayout close_my_wine_info = findViewById(R.id.for_close_wine_info);
                            close_my_wine_info.setVisibility(View.INVISIBLE);
                        }
                    });

                    TextView no = findViewById(R.id.delete_no);
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //いいえ　が押されたときの処理
                            RelativeLayout close_delete = findViewById(R.id.for_close_delete);
                            close_delete.setVisibility(View.INVISIBLE);

                            RelativeLayout for_delete = findViewById(R.id.for_delete);
                            for_delete.setVisibility(View.INVISIBLE);
                        }
                    });




                }
            });

            ListView listView = findViewById(R.id.my_wine_list);
            listView.setClickable(false);


            /*
            ダイアログで文字を表示
            AlertDialog.Builder builder = new AlertDialog.Builder(MyWineActivity.this);
            builder.setTitle("Tap No. " + String.valueOf(position));
            builder.setMessage(item.getTitle());
            builder.show();
            */
        }
    };




    private void resetWineList(){
        File file = new File(this.getFilesDir(), "myWineList.txt");
        String filename = "myWineList.txt";
        FileOutputStream outputStream;

        try {
            //outputStream = openFileOutput(filename, Context.MODE_APPEND);//追記モード
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);//上書きモード
            for(int i=0; i<myWineListLength; i++){
                outputStream.write((String.valueOf(myWineListIndex.get(i)) + "\n").getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateMyWineList(){
        try {
            readMyWineList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // レイアウトからリストビューを取得
        ListView listView = (ListView)findViewById(R.id.my_wine_list);
        // リストビューに表示する要素を設定
        ArrayList<MyWineListItem> listItems = new ArrayList<>();
        for (int i = 0; i < myWineListLength; i++) {
            double wineID = myWineListIndex.get(i);
            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

            Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
            MyWineListItem item = new MyWineListItem(bmp, wineData.getWineNameList().get(thisWineIndex), wineData.getWineFuriganaList().get(thisWineIndex));
            listItems.add(item);
        }
        // 出力結果をリストビューに表示
        MyWineListAdapter adapter = new MyWineListAdapter(this, R.layout.my_wine_list_item, listItems);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(onItemClickListener);  // タップ時のイベントを追加
    }


    private void updateMultipleMyWineList(){
        try {
            readMyWineList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // レイアウトからリストビューを取得
        ListView MultipleListView = (ListView)findViewById(R.id.my_wine_list_multiple);
        // リストビューに表示する要素を設定
        ArrayList<MyWineListMultipleItem> MultipleListMultipleItems = new ArrayList<>();
        for (int i = 0; i < myWineListLength; i++) {
            double wineID = myWineListIndex.get(i);
            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

            Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
            MyWineListMultipleItem item = new MyWineListMultipleItem(bmp, wineData.getWineNameList().get(thisWineIndex), false);
            MultipleListMultipleItems.add(item);
        }
        // 出力結果をリストビューに表示
        MyWineListMultipleAdapter adapter = new MyWineListMultipleAdapter(this, R.layout.my_wine_list_multiple_choice, MultipleListMultipleItems);
        MultipleListView.setAdapter(adapter);
    }

    public void setButtonClick(){
        //検索ボタンが押されたとき
        findViewById(R.id.search_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.bar_list).setVisibility(View.INVISIBLE);
                findViewById(R.id.bar_search).setVisibility(View.VISIBLE);
                findViewById(R.id.search_list_menu).setVisibility(View.VISIBLE);
                //検索のエンターが押されたとき
                searchMyWine();
                //タブ上の戻るボタンが押されたとき
                findViewById(R.id.return_search_mode).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.bar_list).setVisibility(View.VISIBLE);
                        findViewById(R.id.bar_search).setVisibility(View.INVISIBLE);
                        findViewById(R.id.search_list_menu).setVisibility(View.INVISIBLE);
                        updateMyWineList();
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
                //名前で並び変えボタンが押されたとき
                findViewById(R.id.sort_name).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<MyWineListItem> listItems = new ArrayList<>();
                        ListView listView = (ListView) findViewById(R.id.my_wine_list);
                        for (int i = 0; i < myWineListLength; i++) {
                            double wineID = myWineListIndex.get(i);
                            int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                            Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                            MyWineListItem item = new MyWineListItem(bmp, wineData.getWineNameList().get(thisWineIndex), wineData.getWineFuriganaList().get(thisWineIndex));

                            listItems.add(item);
                        }
                        MyWineListAdapter adapter = new MyWineListAdapter(getApplication(), R.layout.my_wine_list_item, listItems);
                        Collections.sort(listItems, new NameComparator());
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
                        updateMyWineList();
                    }
                });
            }
        });

        //選択ボタンが押されたとき
        findViewById(R.id.choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.bar_list).setVisibility(View.INVISIBLE);
                findViewById(R.id.bar_choice).setVisibility(View.VISIBLE);
                findViewById(R.id.my_wine_list).setVisibility(View.INVISIBLE);
                findViewById(R.id.my_wine_list_multiple).setVisibility(View.VISIBLE);
                findViewById(R.id.action_choice).setVisibility(View.VISIBLE);

                findViewById(R.id.return_choice_mode).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.bar_list).setVisibility(View.VISIBLE);
                        findViewById(R.id.bar_choice).setVisibility(View.INVISIBLE);
                        findViewById(R.id.my_wine_list).setVisibility(View.VISIBLE);
                        findViewById(R.id.my_wine_list_multiple).setVisibility(View.INVISIBLE);
                        findViewById(R.id.action_choice).setVisibility(View.INVISIBLE);
                    }
                });
                findViewById(R.id.all_choice).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ListView listView = findViewById(R.id.my_wine_list_multiple);
                        for(int i=0; i<myWineListLength; i++){
                            MyWineListMultipleAdapter adapter = (MyWineListMultipleAdapter) listView.getAdapter();
                            MyWineListMultipleItem item = adapter.getItem(i);
                            item.setCheck(true);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                findViewById(R.id.all_clear).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ListView listView = findViewById(R.id.my_wine_list_multiple);
                        for(int i=0; i<myWineListLength; i++){
                            MyWineListMultipleAdapter adapter = (MyWineListMultipleAdapter) listView.getAdapter();
                            MyWineListMultipleItem item = adapter.getItem(i);
                            item.setCheck(false);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                findViewById(R.id.to_favorite_multiple).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //お気に入りへボタンが押されたとき
                    }
                });
                findViewById(R.id.delete_multiple).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RelativeLayout close_delete = findViewById(R.id.for_close_delete);
                        close_delete.setVisibility(View.VISIBLE);
                        close_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //削除タブ表示中は何を押しても何もしない
                            }
                        });

                        RelativeLayout for_delete = findViewById(R.id.for_delete_multiple);
                        for_delete.setVisibility(View.VISIBLE);
                        for_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //削除しますかタブを押しても何もしない
                            }
                        });

                        TextView yes = findViewById(R.id.delete_yes_multiple);
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //はい　が押されたときの処理
                                ArrayList<Integer> newMyWine = new ArrayList<>();
                                newMyWine = myWineListIndex;
                                int newMyWineLength = newMyWine.size();
                                int deleteNum = 0;

                                ListView listView = findViewById(R.id.my_wine_list_multiple);
                                for(int i=0; i<myWineListLength; i++){
                                    MyWineListMultipleItem item = (MyWineListMultipleItem)listView.getItemAtPosition(i);
                                    if( (item.getCheck()) ) {
                                        newMyWine.remove(i-deleteNum);
                                        newMyWineLength--;
                                        deleteNum++;
                                    }
                                }
                                myWineListIndex = newMyWine;
                                myWineListLength = newMyWineLength;
                                resetWineList();
                                updateMyWineList();
                                updateMultipleMyWineList();
                                findViewById(R.id.bar_list).setVisibility(View.VISIBLE);
                                findViewById(R.id.bar_choice).setVisibility(View.INVISIBLE);
                                findViewById(R.id.my_wine_list).setVisibility(View.VISIBLE);
                                findViewById(R.id.my_wine_list_multiple).setVisibility(View.INVISIBLE);
                                findViewById(R.id.action_choice).setVisibility(View.INVISIBLE);

                                findViewById(R.id.for_close_delete).setVisibility(View.INVISIBLE);
                                findViewById(R.id.for_delete_multiple).setVisibility(View.INVISIBLE);
                            }
                        });

                        TextView no = findViewById(R.id.delete_no_multiple);
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //いいえ　が押されたときの処理
                                findViewById(R.id.bar_list).setVisibility(View.VISIBLE);
                                findViewById(R.id.bar_choice).setVisibility(View.INVISIBLE);
                                findViewById(R.id.my_wine_list).setVisibility(View.VISIBLE);
                                findViewById(R.id.my_wine_list_multiple).setVisibility(View.INVISIBLE);
                                findViewById(R.id.action_choice).setVisibility(View.INVISIBLE);

                                findViewById(R.id.for_close_delete).setVisibility(View.INVISIBLE);
                                findViewById(R.id.for_delete_multiple).setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });


            }
        });

    }

    public void searchMyWine(){
        //検索のエンターが押されたとき
        findViewById(R.id.enter_my_wine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //色のチェックボックス
                final CheckBox color_Red = findViewById(R.id.my_wine_color_Red);
                final CheckBox color_Rose = findViewById(R.id.my_wine_color_Rose);
                final CheckBox color_White = findViewById(R.id.my_wine_color_White);
                final CheckBox color_Other = findViewById(R.id.my_wine_color_Other);
                final CheckBox color_All = findViewById(R.id.my_wine_color_All);

                //押されているラジオボタン
                /*
                RadioGroup color = (RadioGroup) findViewById(R.id.RadioGroup_my_wine_list_Color);
                int checkedRadioButtonId = color.getCheckedRadioButtonId();
                RadioButton checkedButton = (RadioButton)findViewById(checkedRadioButtonId);
                 */

                //価格
                Spinner spinner_price_bottom = (Spinner) findViewById(R.id.spinner_my_wine_price_bottom);
                Spinner spinner_price_top = (Spinner) findViewById(R.id.spinner_my_wine_price_top);
                String selected_price_bottom = (String) spinner_price_bottom.getSelectedItem();
                String selected_price_top = (String) spinner_price_top.getSelectedItem();

                //入力値を実数値に変換
                /*
                int colorNum = 0;
                if(checkedButton.getId() == R.id.my_wine_Color_Red)
                    colorNum = 5;
                else if(checkedButton.getId() == R.id.my_wine_Color_Rose)
                    colorNum = 3;
                else if(checkedButton.getId() == R.id.my_wine_Color_white)
                    colorNum = 1;

                 */

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


                ArrayList<Integer> searchedMyWineListIndex = new ArrayList<>();
                int searchedMyWineListLength = 0;
                for(int i=0; i<myWineListLength; i++){
                    double wineID = myWineListIndex.get(i);
                    int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                    //色についての検索
                    /*
                    if(colorNum != 0) {
                        if(wineData.getWineColorList().get(thisWineIndex) == colorNum){
                            if( !(searchedMyWineListIndex.contains(myWineListIndex.get(i))) ){
                                searchedMyWineListIndex.add(myWineListIndex.get(i));
                                searchedMyWineListLength++;
                            }
                        }
                    }

                     */
                    //色についての検索
                    if(! (color_All.isChecked()) ) {
                        if(wineData.getWineColorList().get(thisWineIndex) == 1 && color_White.isChecked()){
                            if( !(searchedMyWineListIndex.contains(myWineListIndex.get(i))) ){
                                searchedMyWineListIndex.add(myWineListIndex.get(i));
                                searchedMyWineListLength++;
                            }
                        }
                        else if(wineData.getWineColorList().get(thisWineIndex) == 3 && color_Rose.isChecked()){
                            if( !(searchedMyWineListIndex.contains(myWineListIndex.get(i))) ){
                                searchedMyWineListIndex.add(myWineListIndex.get(i));
                                searchedMyWineListLength++;
                            }
                        }
                        else if(wineData.getWineColorList().get(thisWineIndex) == 5 && color_Red.isChecked()){
                            if( !(searchedMyWineListIndex.contains(myWineListIndex.get(i))) ){
                                searchedMyWineListIndex.add(myWineListIndex.get(i));
                                searchedMyWineListLength++;
                            }
                        }
                        else if(wineData.getWineColorList().get(thisWineIndex) != 1 && wineData.getWineColorList().get(thisWineIndex) != 3 && wineData.getWineColorList().get(thisWineIndex) != 5 && color_Other.isChecked()) {
                            if( !(searchedMyWineListIndex.contains(myWineListIndex.get(i))) ){
                                searchedMyWineListIndex.add(myWineListIndex.get(i));
                                searchedMyWineListLength++;
                            }
                        }
                    }

                    //価格についての検索
                    if( ((bottom_price != -1) && (top_price != -1)) && (bottom_price <= top_price) ){
                        if( ((wineData.getWinePriceList().get(thisWineIndex) <= top_price) && (bottom_price <= wineData.getWinePriceList().get(thisWineIndex)))){

                            if( !(searchedMyWineListIndex.contains(myWineListIndex.get(i))) ){
                                searchedMyWineListIndex.add(myWineListIndex.get(i));
                                searchedMyWineListLength++;

                            }
                        }
                    }
                }






                if( !color_All.isChecked() || bottom_price != -1 || top_price != -1) {
                    // レイアウトからリストビューを取得
                    ListView listView = (ListView) findViewById(R.id.my_wine_list);
                    // リストビューに表示する要素を設定
                    ArrayList<MyWineListItem> listItems = new ArrayList<>();
                    for (int i = 0; i < searchedMyWineListLength; i++) {
                        double wineID = searchedMyWineListIndex.get(i);
                        int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);

                        Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[thisWineIndex]);
                        MyWineListItem item = new MyWineListItem(bmp, wineData.getWineNameList().get(thisWineIndex), wineData.getWineFuriganaList().get(thisWineIndex));
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
        final CheckBox color_Other = findViewById(R.id.my_wine_color_Other);
        final CheckBox color_All = findViewById(R.id.my_wine_color_All);

        color_Red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(color_Red.isChecked() && color_Rose.isChecked() && color_White.isChecked() && color_Other.isChecked()){
                    color_All.setChecked(true);
                    color_Red.setChecked(false);
                    color_Rose.setChecked(false);
                    color_White.setChecked(false);
                    color_Other.setChecked(false);
                }
                else if(color_All.isChecked()) {
                    color_All.setChecked(false);
                }
            }
        });
        color_Rose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(color_Red.isChecked() && color_Rose.isChecked() && color_White.isChecked() && color_Other.isChecked()){
                    color_All.setChecked(true);
                    color_Red.setChecked(false);
                    color_Rose.setChecked(false);
                    color_White.setChecked(false);
                    color_Other.setChecked(false);
                }
                else if(color_All.isChecked()) {
                    color_All.setChecked(false);
                }
            }
        });
        color_White.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(color_Red.isChecked() && color_Rose.isChecked() && color_White.isChecked() && color_Other.isChecked()){
                    color_All.setChecked(true);
                    color_Red.setChecked(false);
                    color_Rose.setChecked(false);
                    color_White.setChecked(false);
                    color_Other.setChecked(false);
                }
                else if(color_All.isChecked()) {
                    color_All.setChecked(false);
                }
            }
        });
        color_Other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(color_Red.isChecked() && color_Rose.isChecked() && color_White.isChecked() && color_Other.isChecked()){
                    color_All.setChecked(true);
                    color_Red.setChecked(false);
                    color_Rose.setChecked(false);
                    color_White.setChecked(false);
                    color_Other.setChecked(false);
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
                    color_Other.setChecked(false);
                }
                else if(color_All.isChecked()) {
                    color_All.setChecked(false);
                }
            }
        });
    }





}

