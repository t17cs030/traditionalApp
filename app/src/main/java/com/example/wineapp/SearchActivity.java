package com.example.wineapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity
        implements View.OnClickListener


{  //クリックリスナーを実装

    private WineData wineData = new WineData();
    private int centerIndex = 0;


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent me = getIntent();
        wineData.setWineData((WineData)me.getSerializableExtra("WINE_DATA"));
        centerIndex = me.getIntExtra("CENTER_WINE", 0);

        final boolean [] deleteFlag = new boolean[wineData.getWineNum()];
        Arrays.fill(deleteFlag, true);


        findViewById(R.id.wineMap).setOnClickListener(this);
        findViewById(R.id.myWine).setOnClickListener(this);
        findViewById(R.id.label).setOnClickListener(this);
        findViewById(R.id.winery).setOnClickListener(this);

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
            }
        });
        findViewById(R.id.grape_enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.for_search_grape).setVisibility(View.INVISIBLE);
                findViewById(R.id.for_close_search_grape).setVisibility(View.INVISIBLE);
            }
        });

        //ラジオボタンを登録
        /*
        RadioGroup color = (RadioGroup) findViewById(R.id.RadioGroup_Color);
        color.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedRadioButtonId) {
                //RadioButton checkedButton = (RadioButton)findViewById(checkedRadioButtonId);
                //TextView color = findViewById(R.id.text_color);
                //color.setText(checkedButton.getText());
            }
        });
         */

        //色のチェックボックス
        final CheckBox color_Red = findViewById(R.id.color_Red);
        final CheckBox color_Rose = findViewById(R.id.color_Rose);
        final CheckBox color_White = findViewById(R.id.color_White);
        final CheckBox color_Other = findViewById(R.id.color_Other);
        final CheckBox color_All = findViewById(R.id.color_All);

        //チェックボックスのアクション
        color_check_box();



        //検索ボタンが押されたとき(デバック用)
        Button enter = findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //押されているラジオボタン
                /*
                RadioGroup color = (RadioGroup) findViewById(R.id.RadioGroup_Color);
                int checkedRadioButtonId = color.getCheckedRadioButtonId();
                RadioButton checkedButton = (RadioButton)findViewById(checkedRadioButtonId);
                 */


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

                //デバック
                /*
                String result
                        = "色: " + checkedButton.getText() + "\n"
                        + "味わい(赤): " + selected_red_bottom + "～" + selected_red_top + "\n"
                        + "味わい(白): " + selected_white_bottom + "～" + selected_white_top + "\n"
                        + "価格: " + selected_price_bottom + "～" + selected_price_top;
                TextView result_text = findViewById(R.id.result);
                result_text.setText(result);
                 */



                //入力値を実数値に変換
                /*
                int colorNum = 0;
                if(checkedButton.getId() == R.id.Color_Red)
                    colorNum = 5;
                else if(checkedButton.getId() == R.id.Color_Rose)
                    colorNum = 3;
                else if(checkedButton.getId() == R.id.Color_white)
                    colorNum = 1;
                 */

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

                /*
                String result
                        = "色: " + colorNum + "\n"
                        + "味わい(赤): " + taste_Red_B + "～" + taste_Red_T+ "\n"
                        + "味わい(白): " + taste_white_B + "～" + taste_white_T + "\n"
                        + "価格: " + bottom_price + "～" + top_price + "\n";
                TextView result_text = findViewById(R.id.result);
                result_text.setText(result);
                 */

                //SpannableStringBuilder sb = (SpannableStringBuilder)search.getText();
                //String word = sb.toString();

                for(int i=0; i<wineData.getWineNum(); i++){

                    double wineID = wineData.getWineIndexList().get(i);
                    int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);


                    //色についての検索
                    if(! (color_All.isChecked()) ) {
                        if(wineData.getWineColorList().get(i) == 1 && color_White.isChecked()){
                            deleteFlag[thisWineIndex] = false;
                        }
                        else if(wineData.getWineColorList().get(i) == 3 && color_Rose.isChecked()){
                            deleteFlag[thisWineIndex] = false;
                        }
                        else if(wineData.getWineColorList().get(i) == 5 && color_Red.isChecked()){
                            deleteFlag[thisWineIndex] = false;
                        }
                        else if(wineData.getWineColorList().get(i) != 1 && wineData.getWineColorList().get(i) != 3 && wineData.getWineColorList().get(i) != 5 && color_Other.isChecked()) {
                            deleteFlag[thisWineIndex] = false;
                        }
                    }
                    //味わい赤についての検索
                    if(taste_Red_B != 0 && taste_Red_T != 0 && (taste_Red_B <= taste_Red_T) ){
                        if( (wineData.getWineColorList().get(i) == 5) && (wineData.getWineTasteList().get(i) >= taste_Red_B) && (taste_Red_T >= wineData.getWineTasteList().get(i)) ){
                            deleteFlag[thisWineIndex] = false;
                        }
                    }
                    //味わい白についての検索
                    if(taste_white_B != 0 && taste_white_T != 0 && (taste_white_B <= taste_white_T) ){
                        if( (wineData.getWineColorList().get(i) != 5) && (wineData.getWineTasteList().get(i) >= taste_white_B) && (taste_white_T >= wineData.getWineTasteList().get(i)) ){
                            deleteFlag[thisWineIndex] = false;
                        }
                    }
                    //価格についての検索
                    if( (bottom_price != -1) && (top_price != -1) && (bottom_price <= top_price) ){
                        if( (wineData.getWinePriceList().get(i) >= bottom_price) && (top_price >= wineData.getWinePriceList().get(i)) ){
                            deleteFlag[thisWineIndex] = false;
                        }
                    }
                    //自由検索についての検索
                    if(search.getQuery().toString().trim().length() != 0 && (wineData.getWineNameList().get(i).contains(search.getQuery().toString()))){
                        deleteFlag[thisWineIndex] = false;
                    }

                }

                boolean check = false;
                for(int j=0; j<wineData.getWineNum(); j++){
                    if(deleteFlag[j] == false)
                        check = true;
                }


                if(check){//検索結果に当てはまるものがある場合
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.putExtra("DELETE_FLAG", deleteFlag);
                    intent.putExtra("CENTER_WINE", centerIndex);
                    startActivity(intent);
                }
                else{//検索結果に当てはまるものがない場合
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                    builder.setTitle("検索結果");
                    builder.setMessage("当てはまるワインはありません");
                    builder.show();
                }


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
        //intent.putExtra("WINE_INDEX", searchedWineData.getWineIndexList());
        //intent.putExtra("WINE_NAME", searchedWineData.getWineNameList());
        //intent.putExtra("WINE_COLOR", searchedWineData.getWineColorList());
        //intent.putExtra("WINE_TASTE", searchedWineData.getWineTasteList());
        //intent.putExtra("WINE_PRICE", searchedWineData.getWinePriceList());
        //intent.putExtra("WINE_FURIGANA", searchedWineData.getWineFuriganaList());
        intent.putExtra("WINE_DATA", wineData);

        intent.putExtra("CENTER_WINE", centerIndex);
        startActivity(intent);
    }

    public void color_check_box(){
        //色のチェックボックス
        final CheckBox color_Red = findViewById(R.id.color_Red);
        final CheckBox color_Rose = findViewById(R.id.color_Rose);
        final CheckBox color_White = findViewById(R.id.color_White);
        final CheckBox color_Other = findViewById(R.id.color_Other);
        final CheckBox color_All = findViewById(R.id.color_All);

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

