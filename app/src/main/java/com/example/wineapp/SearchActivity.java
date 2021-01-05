package com.example.wineapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity


{

    private WineData wineData = new WineData();
    private GrapeData grapeData = new GrapeData();
    private int centerIndex = 0;


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent me = getIntent();
        wineData.setWineData((WineData)me.getSerializableExtra("WINE_DATA"));
        grapeData.setGrapeData((GrapeData)me.getSerializableExtra("GRAPE_DATA"));
        centerIndex = me.getIntExtra("CENTER_WINE", 0);

        final boolean [] deleteFlag = new boolean[wineData.getWineNum()];
        Arrays.fill(deleteFlag, true);

        //ボトムナビゲーションビューの初期値の設定
        BottomNavigationView navi;
        navi = (BottomNavigationView) findViewById(R.id.navigation);
        navi.setSelectedItemId(R.id.search_navi);
        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.wineMap_navi:
                        Intent intent_wine = new Intent(getApplication(), MainActivity.class);
                        intent_wine.putExtra("WINE_DATA", wineData);
                        intent_wine.putExtra("GRAPE_DATA", grapeData);
                        intent_wine.putExtra("CENTER_WINE", centerIndex);
                        startActivity(intent_wine);
                        return true;
                    case R.id.search_navi:
                        return true;
                    case R.id.myWine_navi:
                        Intent intent_myWine = new Intent(getApplication(), MyWineActivity.class);
                        intent_myWine.putExtra("WINE_DATA", wineData);
                        intent_myWine.putExtra("GRAPE_DATA", grapeData);
                        intent_myWine.putExtra("CENTER_WINE", centerIndex);
                        startActivity(intent_myWine);
                        return true;
                    case R.id.label_navi:
                        Intent intent_label = new Intent(getApplication(), LabelActivity.class);
                        intent_label.putExtra("WINE_DATA", wineData);
                        intent_label.putExtra("GRAPE_DATA", grapeData);
                        intent_label.putExtra("CENTER_WINE", centerIndex);
                        startActivity(intent_label);
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
        //findViewById(R.id.myWine).setOnClickListener(this);
        //findViewById(R.id.label).setOnClickListener(this);
        //findViewById(R.id.winery).setOnClickListener(this);

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


        //色のチェックボックス
        final CheckBox color_Red = findViewById(R.id.color_Red);
        final CheckBox color_Rose = findViewById(R.id.color_Rose);
        final CheckBox color_White = findViewById(R.id.color_White);
        final CheckBox color_Orange = findViewById(R.id.color_Orange);
        final CheckBox color_Sparkling = findViewById(R.id.color_Sparkling);
        final CheckBox color_All = findViewById(R.id.color_All);

        //チェックボックスのアクション
        color_check_box();



        //検索ボタンが押されたとき(デバック用)
        Button enter = findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean color_search = false;
                boolean taste_red_search = false;
                boolean taste_white_search = false;
                boolean price_search = false;
                boolean grape_search = false;
                boolean free_search = false;


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

    public void color_check_box(){
        //色のチェックボックス
        final CheckBox color_Red = findViewById(R.id.color_Red);
        final CheckBox color_Rose = findViewById(R.id.color_Rose);
        final CheckBox color_White = findViewById(R.id.color_White);
        final CheckBox color_Orange = findViewById(R.id.color_Orange);
        final CheckBox color_Sparkling = findViewById(R.id.color_Sparkling);
        final CheckBox color_All = findViewById(R.id.color_All);

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

