package com.example.wineapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LabelActivity extends AppCompatActivity

{  //クリックリスナーを実装
    private WineData wineData = new WineData();
    private GrapeData grapeData = new GrapeData();
    private int centerIndex = 0;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent me = getIntent();
        wineData.setWineData((WineData)me.getSerializableExtra("WINE_DATA"));
        grapeData.setGrapeData((GrapeData)me.getSerializableExtra("GRAPE_DATA"));
        centerIndex = me.getIntExtra("CENTER_WINE", 0);

        setContentView(R.layout.activity_label);

        //ボトムナビゲーションビューの初期値の設定
        BottomNavigationView navi;
        navi = (BottomNavigationView) findViewById(R.id.navigation);
        navi.setSelectedItemId(R.id.label_navi);
        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.wineMap_navi:
                        Intent intent_wine = new Intent(getApplication(), BeforeMainActivity.class);
                        intent_wine.putExtra("WINE_DATA", wineData);
                        intent_wine.putExtra("GRAPE_DATA", grapeData);
                        intent_wine.putExtra("CENTER_WINE", centerIndex);
                        startActivity(intent_wine);
                        return true;
                    case R.id.search_navi:
                        Intent intent_search = new Intent(getApplication(), SearchActivity.class);
                        intent_search.putExtra("WINE_DATA", wineData);
                        intent_search.putExtra("GRAPE_DATA", grapeData);
                        intent_search.putExtra("CENTER_WINE", centerIndex);
                        startActivity(intent_search);
                        return true;
                    case R.id.myWine_navi:
                        Intent intent_myWine = new Intent(getApplication(), MainActivity.class);
                        intent_myWine.putExtra("WINE_DATA", wineData);
                        intent_myWine.putExtra("GRAPE_DATA", grapeData);
                        intent_myWine.putExtra("CENTER_WINE", centerIndex);
                        startActivity(intent_myWine);
                        return true;
                    case R.id.label_navi:
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

        //findViewById(R.id.wineMap).setOnClickListener(this);
        //findViewById(R.id.search).setOnClickListener(this);
        //findViewById(R.id.myWine).setOnClickListener(this);
//        findViewById(R.id.label).setOnClickListener(this);
        //findViewById(R.id.winery).setOnClickListener(this);
        //リスナーをボタンに登録
    }
    //ボタンが押された時の処理
    /*
    public void onClick (View view){
        Intent intent;
        if (view.getId() == R.id.search) {//検索画面へ
            intent = new Intent(this, SearchActivity.class);
        }
        else if (view.getId() == R.id.myWine) {//MYワインリストへ
            intent = new Intent(this, MyWineActivity.class);
        }
        else if (view.getId() == R.id.wineMap) {//ワインマップへ
            intent = new Intent(this, MainActivity.class);
        }
        else if (view.getId() == R.id.winery) {//ワイナリーマップへ
            intent = new Intent(this, WineryActivity.class);
        }
        else{//ラベル読み込みを維持
            intent = new Intent(this, LabelActivity.class);
        }
        intent.putExtra("WINE_DATA", wineData);
        intent.putExtra("GRAPE_DATA", grapeData);
        intent.putExtra("CENTER_WINE", centerIndex);
        startActivity(intent);
    }

     */
}