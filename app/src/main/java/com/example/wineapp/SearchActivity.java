package com.example.wineapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
        implements View.OnClickListener


{  //クリックリスナーを実装

    private SearchedWineData searchedWineData = new SearchedWineData();


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent me = getIntent();
        searchedWineData.setWineIndexList(me.getIntegerArrayListExtra("WINE_INDEX"));
        searchedWineData.setWineNameList(me.getStringArrayListExtra("WINE_NAME"));
        searchedWineData.setWineColorList(me.getIntegerArrayListExtra("WINE_COLOR"));
        searchedWineData.setWineTasteList(me.getIntegerArrayListExtra("WINE_TASTE"));
        searchedWineData.setWinePriceList(me.getIntegerArrayListExtra("NAME_PRICE"));

        searchedWineData.setWineNum(searchedWineData.getWineIndexList().size());

        final boolean [] deleteFlag = new boolean[searchedWineData.getWineNum()];


        findViewById(R.id.wineMap).setOnClickListener(this);
        findViewById(R.id.myWine).setOnClickListener(this);
        findViewById(R.id.label).setOnClickListener(this);
        findViewById(R.id.winery).setOnClickListener(this);
        //リスナーをボタンに登録

        //ラジオボタンを登録
        RadioGroup color = (RadioGroup) findViewById(R.id.RadioGroup_Color);
        color.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedRadioButtonId) {
                //RadioButton checkedButton = (RadioButton)findViewById(checkedRadioButtonId);
                //TextView color = findViewById(R.id.text_color);
                //color.setText(checkedButton.getText());
            }
        });

        //検索ボタンが押されたとき(デバック用)
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

                /*デバック
                String result
                        = "色: " + checkedButton.getText() + "\n"
                        + "味わい(赤): " + selected_red_bottom + "～" + selected_red_top + "\n"
                        + "味わい(白): " + selected_white_bottom + "～" + selected_white_top + "\n"
                        + "価格: " + selected_price_bottom + "～" + selected_price_top;
                TextView result_text = findViewById(R.id.result);
                //result_text.setText(result);
                 */


                //入力値を実数値に変換
                int colorNum = 0;
                if(checkedButton.getId() == R.id.Color_Red)
                    colorNum = 3;
                else if(checkedButton.getId() == R.id.Color_Rose)
                    colorNum = 2;
                else if(checkedButton.getId() == R.id.Color_white)
                    colorNum = 1;

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
                else if(selected_price_bottom.equals("1000"))
                    bottom_price = 1000;
                else if(selected_price_bottom.equals("3000"))
                    bottom_price = 3000;
                else if(selected_price_bottom.equals("5000"))
                    bottom_price = 5000;
                else if(selected_price_bottom.equals("10000"))
                    bottom_price = 10000;
                else if(selected_price_bottom.equals("20000"))
                    bottom_price = 20000;

                //価格上
                int top_price = -1;
                if(selected_price_top.equals("0"))
                    top_price = 0;
                else if(selected_price_top.equals("1000"))
                    top_price = 1000;
                else if(selected_price_top.equals("3000"))
                    top_price = 3000;
                else if(selected_price_top.equals("5000"))
                    top_price = 5000;
                else if(selected_price_top.equals("10000"))
                    top_price = 10000;
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

                //SpannableStringBuilder sb = (SpannableStringBuilder)search.getText();
                //String word = sb.toString();

                for(int i=0; i<searchedWineData.getWineNum(); i++){
                    //色についての検索
                    if(colorNum != 0) {
                        if(searchedWineData.getWineColorList().get(i) != colorNum){
                            deleteFlag[searchedWineData.getWineIndexList().get(i)-1] = true;
                        }
                    }
                    //味わい赤についての検索
                    if(!( (taste_Red_B == 0 || taste_Red_T == 0) || (taste_Red_B > taste_Red_T) )){
                        if( (searchedWineData.getWineColorList().get(i) == 3) && ((searchedWineData.getWineTasteList().get(i) < taste_Red_B) || (taste_Red_T <searchedWineData.getWineTasteList().get(i)))){
                            deleteFlag[searchedWineData.getWineIndexList().get(i)-1] = true;
                        }
                    }
                    //味わい白についての検索
                    if(!( (taste_white_B == 0 || taste_white_T == 0) || (taste_white_B > taste_white_T) )){
                        if( ((searchedWineData.getWineColorList().get(i) == 1) || (searchedWineData.getWineColorList().get(i) == 2)) && ((searchedWineData.getWineTasteList().get(i) < taste_white_B) || (taste_white_T <searchedWineData.getWineTasteList().get(i)))){
                            deleteFlag[searchedWineData.getWineIndexList().get(i)-1] = true;
                        }
                    }
                    //価格についての検索
                    if( ((bottom_price != -1) && (top_price != -1)) && (bottom_price > top_price) ){
                        if( ((searchedWineData.getWinePriceList().get(i) < bottom_price) || (top_price < searchedWineData.getWinePriceList().get(i)))){
                            deleteFlag[searchedWineData.getWineIndexList().get(i)-1] = true;
                        }
                    }
                    //自由検索についての検索
                    if(!(searchedWineData.getWineNameList().get(i).contains(search.getQuery().toString()))){
                        deleteFlag[searchedWineData.getWineIndexList().get(i)-1] = true;
                    }
                }


                //デバック
/*
                String result = search.getQuery().toString();
                result += "\n";
                TextView result_text = findViewById(R.id.result);

                for(int i=0; i<searchedWineData.getWineNum(); i++){
                    result += deleteFlag[i] + " ";
                }
                result_text.setText(result);

 */







                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.putExtra("DELETE_FLAG", deleteFlag);
                startActivity(intent);




            }
        });

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
        intent.putExtra("WINE_INDEX", searchedWineData.getWineIndexList());
        intent.putExtra("WINE_NAME", searchedWineData.getWineNameList());
        intent.putExtra("WINE_COLOR", searchedWineData.getWineColorList());
        intent.putExtra("WINE_TASTE", searchedWineData.getWineTasteList());
        intent.putExtra("WINE_PRICE", searchedWineData.getWinePriceList());
        startActivity(intent);
    }
}