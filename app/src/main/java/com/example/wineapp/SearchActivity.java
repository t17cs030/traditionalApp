package com.example.wineapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class SearchActivity extends AppCompatActivity
        implements View.OnClickListener

{  //クリックリスナーを実装
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViewById(R.id.wineMap).setOnClickListener(this);
//        findViewById(R.id.search).setOnClickListener(this);
        findViewById(R.id.myWine).setOnClickListener(this);
        findViewById(R.id.label).setOnClickListener(this);
        findViewById(R.id.winery).setOnClickListener(this);
        //リスナーをボタンに登録

        //ラジオボタンを登録
        RadioGroup color = (RadioGroup) findViewById(R.id.RadioGroup_Color);
        color.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedRadioButtonId) {
                RadioButton checkedButton = (RadioButton)findViewById(checkedRadioButtonId);
                TextView color = findViewById(R.id.text_color);
                color.setText(checkedButton.getText());
            }
        });
        //今押されているラジオボタンのIDを取得(コメントアウト)
        //int checkedRadioButtonId = color.getCheckedRadioButtonId();
        //RadioButton checkedButton = (RadioButton)findViewById(checkedRadioButtonId);
    }
    //ボタンが押された時の処理
    public void onClick (View view){
        Intent intent;
        if (view.getId() == R.id.wineMap) {//ワインマップ画面へ
            intent = new Intent(this, MainActivity.class);
        }
        else if (view.getId() == R.id.myWine) {//MYワインリストへ
            intent = new Intent(this, MyWineActivity.class);
        }
        else if (view.getId() == R.id.label) {//ラベル読み込みへ
            intent = new Intent(this, LabelActivity.class);
        }
        else if (view.getId() == R.id.winery) {//ワイナリーマップへ
            intent = new Intent(this, WineryActivity.class);
        }
        else{//検索画面を維持
            intent = new Intent(this, SearchActivity.class);
        }
        startActivity(intent);
    }
}