package com.example.wineapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LabelActivity extends AppCompatActivity
        implements View.OnClickListener

{  //クリックリスナーを実装
    private WineData wineData = new WineData();
    private int centerIndex = 0;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent me = getIntent();
        wineData.setWineData((WineData)me.getSerializableExtra("WINE_DATA"));
        centerIndex = me.getIntExtra("CENTER_WINE", 0);

        setContentView(R.layout.activity_label);
        findViewById(R.id.wineMap).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);
        findViewById(R.id.myWine).setOnClickListener(this);
//        findViewById(R.id.label).setOnClickListener(this);
        findViewById(R.id.winery).setOnClickListener(this);
        //リスナーをボタンに登録
    }
    //ボタンが押された時の処理
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
        intent.putExtra("CENTER_WINE", centerIndex);
        startActivity(intent);
    }
}