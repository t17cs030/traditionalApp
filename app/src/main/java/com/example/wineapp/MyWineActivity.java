package com.example.wineapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MyWineActivity extends AppCompatActivity
        implements View.OnClickListener

{  //クリックリスナーを実装
    private SearchedWineData searchedWineData = new SearchedWineData();
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

        findViewById(R.id.wineMap).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);
//        findViewById(R.id.myWine).setOnClickListener(this);
        findViewById(R.id.label).setOnClickListener(this);
        findViewById(R.id.winery).setOnClickListener(this);
        //リスナーをボタンに登録
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
        startActivity(intent);
    }
}