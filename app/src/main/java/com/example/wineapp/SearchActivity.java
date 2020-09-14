package com.example.wineapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

        Button enter = findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //押されているラジオボタン
                RadioGroup color = (RadioGroup) findViewById(R.id.RadioGroup_Color);
                int checkedRadioButtonId = color.getCheckedRadioButtonId();
                RadioButton checkedButton = (RadioButton)findViewById(checkedRadioButtonId);

                //赤ワインの味わい
                Spinner spinner_taste_red_bottom = (Spinner) findViewById(R.id.spinner_taste_red_bottom);
                Spinner spinner_taste_red_top = (Spinner) findViewById(R.id.spinner_taste_red_top);
                String selected_red_bottom = (String) spinner_taste_red_bottom.getSelectedItem();
                String selected_red_top = (String) spinner_taste_red_top.getSelectedItem();

                //白ワインの味わい
                Spinner spinner_taste_white_bottom = (Spinner) findViewById(R.id.spinner_taste_white_bottom);
                Spinner spinner_taste_white_top = (Spinner) findViewById(R.id.spinner_taste_white_top);
                String selected_white_bottom = (String) spinner_taste_white_bottom.getSelectedItem();
                String selected_white_top = (String) spinner_taste_white_top.getSelectedItem();

                //価格
                Spinner spinner_price_bottom = (Spinner) findViewById(R.id.spinner_price_bottom);
                Spinner spinner_price_top = (Spinner) findViewById(R.id.spinner_price_top);
                String selected_price_bottom = (String) spinner_price_bottom.getSelectedItem();
                String selected_price_top = (String) spinner_price_top.getSelectedItem();

                String result
                        = "色: " + checkedButton.getText() + "\n"
                        + "味わい(赤): " + selected_red_bottom + "～" + selected_red_top + "\n"
                        + "味わい(白): " + selected_white_bottom + "～" + selected_white_top + "\n"
                        + "価格: " + selected_price_bottom + "～" + selected_price_top;
                TextView result_text = findViewById(R.id.result);
                result_text.setText(result);
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