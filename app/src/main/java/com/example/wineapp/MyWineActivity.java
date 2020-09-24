package com.example.wineapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MyWineActivity extends AppCompatActivity
        implements View.OnClickListener

{  //クリックリスナーを実装
    private SearchedWineData searchedWineData = new SearchedWineData();
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
            R.drawable.wine_40
    };//画像のIDを配列に格納//最終的にDisplayViewsに移動予定


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wine);

        Intent me = getIntent();
        searchedWineData.setWineIndexList(me.getIntegerArrayListExtra("WINE_INDEX"));
        searchedWineData.setWineNameList(me.getStringArrayListExtra("WINE_NAME"));
        searchedWineData.setWineColorList(me.getIntegerArrayListExtra("WINE_COLOR"));
        searchedWineData.setWineTasteList(me.getIntegerArrayListExtra("WINE_TASTE"));
        searchedWineData.setWinePriceList(me.getIntegerArrayListExtra("NAME_PRICE"));

        searchedWineData.setWineNum(searchedWineData.getWineIndexList().size());

        centerIndex = me.getIntExtra("CENTER_WINE", 0);

        findViewById(R.id.wineMap).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);
//        findViewById(R.id.myWine).setOnClickListener(this);
        findViewById(R.id.label).setOnClickListener(this);
        findViewById(R.id.winery).setOnClickListener(this);
        //リスナーをボタンに登録


        readMyWineList();
        // レイアウトからリストビューを取得
        ListView listView = (ListView)findViewById(R.id.my_wine_list);
        // リストビューに表示する要素を設定
        ArrayList<MyWineListItem> listItems = new ArrayList<>();
        for (int i = 0; i < myWineListLength; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), imageViewId[myWineListIndex.get(i)-1]);  // 今回はサンプルなのでデフォルトのAndroid Iconを利用
            int indexNum = searchedWineData.getWineIndexList().indexOf(myWineListIndex.get(i));
            MyWineListItem item = new MyWineListItem(bmp, searchedWineData.getWineNameList().get(indexNum));
            listItems.add(item);
        }
        // 出力結果をリストビューに表示
        MyWineListAdapter adapter = new MyWineListAdapter(this, R.layout.my_wine_list_item, listItems);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(onItemClickListener);  // タップ時のイベントを追加
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
        intent.putExtra("WINE_INDEX", searchedWineData.getWineIndexList());
        intent.putExtra("WINE_NAME", searchedWineData.getWineNameList());
        intent.putExtra("WINE_COLOR", searchedWineData.getWineColorList());
        intent.putExtra("WINE_TASTE", searchedWineData.getWineTasteList());
        intent.putExtra("WINE_PRICE", searchedWineData.getWinePriceList());
        intent.putExtra("CENTER_WINE", centerIndex);
        startActivity(intent);
    }

    public void readMyWineList() {//MyWineリストのCSVを読み込む関数
        try {
            InputStream inputStream =
                    getResources().getAssets().open("myWineList.csv");

            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream);

            BufferedReader bufferReader =
                    new BufferedReader(inputStreamReader);

            String line = "";

            while ((line = bufferReader.readLine()) != null) {
                StringTokenizer stringTokenizer =
                        new StringTokenizer(line, ",");

                myWineListIndex.add(Integer.parseInt(stringTokenizer.nextToken()));

            }
            bufferReader.close();
            myWineListLength = myWineListIndex.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * リストビューのタップイベント
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // タップしたアイテムの取得
            ListView listView = (ListView)parent;
            MyWineListItem item = (MyWineListItem)listView.getItemAtPosition(position);  // SampleListItemにキャスト

            AlertDialog.Builder builder = new AlertDialog.Builder(MyWineActivity.this);
            builder.setTitle("Tap No. " + String.valueOf(position));
            builder.setMessage(item.getTitle());
            builder.show();
        }
    };

}

