package com.example.wineapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.location.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;


public class WineryActivity extends AppCompatActivity
        implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private WineData wineData = new WineData();
    private GrapeData grapeData = new GrapeData();
    private int centerIndex = 0;

    private WineryData wineryData = new WineryData();

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;


    private LatLng lastWinery = new LatLng(35.666870, 138.568774);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winery);

        readWinery();
        lastWinery = wineryData.getWineryLatLng().get(randWinery());

        Intent me = getIntent();
        wineData.setWineData((WineData)me.getSerializableExtra("WINE_DATA"));
        grapeData.setGrapeData((GrapeData)me.getSerializableExtra("GRAPE_DATA"));
        centerIndex = me.getIntExtra("CENTER_WINE", 0);

        //ボトムナビゲーションビューの初期値の設定
        BottomNavigationView navi;
        navi = (BottomNavigationView) findViewById(R.id.navigation);
        navi.setSelectedItemId(R.id.winery_navi);
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
                        Intent intent_search = new Intent(getApplication(), SearchActivity.class);
                        intent_search.putExtra("WINE_DATA", wineData);
                        intent_search.putExtra("GRAPE_DATA", grapeData);
                        intent_search.putExtra("CENTER_WINE", centerIndex);
                        startActivity(intent_search);
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
                        return true;

                }
                return false;
            }
        });

        //ここからGoogleMAP
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.winery);

        for(int i=0; i<wineryData.getWineryNum(); i++){
            mMap.addMarker(new MarkerOptions()
                    .position(wineryData.getWineryLatLng().get(i))
                    .title(wineryData.getWineryName().get(i))
                    .snippet(wineryData.getWineryAddress().get(i))
                    .icon(icon));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(lastWinery));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {//マーカーのクリックイベント

                final boolean [] deleteFlag = new boolean[wineData.getWineNum()];
                Arrays.fill(deleteFlag, true);

                //Toast.makeText(getApplication(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                zoomMap(marker.getPosition());

                RelativeLayout winery_info = findViewById(R.id.for_winery_info);
                winery_info.setVisibility(View.VISIBLE);

                RelativeLayout close_winery_info = findViewById(R.id.for_close_winery_info);
                close_winery_info.setVisibility(View.VISIBLE);

                TextView winery_info_text = findViewById(R.id.winery_info);
                String info = "ワイナリー名:" + marker.getTitle() + "\n" +
                        "住所:" + marker.getSnippet();
                winery_info_text.setText(info);

                winery_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //ワイナリー情報がクリックされたときは何もしない
                    }
                });

                close_winery_info.setOnClickListener(new View.OnClickListener() {//ワイナリー情報閉じるようレイアウトが押されたときの処理
                    @Override
                    public void onClick(View view) {
                        RelativeLayout winery_info = findViewById(R.id.for_winery_info);
                        winery_info.setVisibility(View.INVISIBLE);

                        RelativeLayout close_winery_info = findViewById(R.id.for_close_winery_info);
                        close_winery_info.setVisibility(View.INVISIBLE);
                    }
                });

                TextView to_wine_map = findViewById(R.id.to_wine_map_winery);
                to_wine_map.setOnClickListener(new View.OnClickListener() {//類似度マップへが押されたとき
                    @Override
                    public void onClick(View view) {
                        int thisWineryIndex = wineryData.getWineryName().indexOf(marker.getTitle());
                        int thisWineryID = wineryData.getWineryID().get(thisWineryIndex);

                        for(int i=0; i<wineData.getWineNum(); i++){
                            if(wineData.getWineryIDList().get(i).equals(thisWineryID)){
                                double wineID = wineData.getWineIndexList().get(i);
                                int thisWineIndex = wineData.getWineIndexList().indexOf((int)wineID);
                                deleteFlag[thisWineIndex]=false;
                            }
                        }

                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        intent.putExtra("DELETE_FLAG", deleteFlag);
                        intent.putExtra("CENTER_WINE", centerIndex);
                        startActivity(intent);
                        //ワイナリー名が同じであるワインだけ類似度マップで拡大表示
                    }
                });

                TextView route = findViewById(R.id.route);
                route.setOnClickListener(new View.OnClickListener() {//経路を表示が押されたとき
                    @Override
                    public void onClick(View view) {
                        // 起点の緯度経度

                        //Location myLocation = getLocation();
                        Location myLocation = mMap.getMyLocation();

                        String src_lat = String.valueOf(myLocation.getLatitude());
                        String src_ltg = String.valueOf(myLocation.getLongitude());

                        // 目的地の緯度経度
                        String des_lat = String.valueOf(marker.getPosition().latitude);
                        String des_ltg = String.valueOf(marker.getPosition().longitude);

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);

                        intent.setClassName("com.google.android.apps.maps",
                                "com.google.android.maps.MapsActivity");

                        // 起点の緯度,経度, 目的地の緯度,経度
                        String str = String.format(Locale.US,
                                "http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s",
                                src_lat, src_ltg, des_lat, des_ltg);

                        intent.setData(Uri.parse(str));
                        startActivity(intent);
                    }
                });

                return true;//falseでデフォルトの枠とかが出る
            }
        });


    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                zoomMap(lastWinery);
            }
        } else {

            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }

    }

    public Location getLocation() {
        FusedLocationProviderClient fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
        final Location[] location = new Location[1];
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        fusedLocationClient.getLastLocation().addOnCompleteListener(
                this, new OnCompleteListener<Location>() {

                    @Override
                    public void onComplete(@NonNull Task<Location> task) {

                        if (task.isSuccessful() && task.getResult() != null) {
                            location[0] = task.getResult();
                        }
                    }
                });
        return location[0];
    }

    private void zoomMap(LatLng latlng){
        // 表示する東西南北の緯度経度を設定
        double south = latlng.latitude * (1-0.00005);
        double west = latlng.longitude * (1-0.00005);
        double north = latlng.latitude * (1+0.00005);
        double east = latlng.longitude * (1+0.00005);

        LatLngBounds bounds = LatLngBounds.builder()
                .include(new LatLng(south , west))
                .include(new LatLng(north, east))
                .build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        mMap.moveCamera(CameraUpdateFactory.
                newLatLngBounds(bounds, width, height, 0));

    }

    @Override
    public boolean onMyLocationButtonClick() {//右上の現在地ボタンが押されたときの処理
        //Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {//現在地のマーカーが押されたときの処理
        //Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
            permissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            showMissingPermissionError();
            permissionDenied = false;
        }
    }


    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    public void readWinery() {//CSVファイルを読み込む関数
        try {
            InputStream inputStream =
                    getResources().getAssets().open("winery.csv");

            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream);

            BufferedReader bufferReader =
                    new BufferedReader(inputStreamReader);

            String line = "";

            while ((line = bufferReader.readLine()) != null) {
                StringTokenizer stringTokenizer =
                        new StringTokenizer(line, ",");

                wineryData.addWineryID(stringTokenizer.nextToken());
                wineryData.addWineryName(stringTokenizer.nextToken());
                wineryData.addWineryAddress(stringTokenizer.nextToken());
                wineryData.addWineryLatLng(stringTokenizer.nextToken(), stringTokenizer.nextToken());
            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        wineryData.setWineryNum(wineryData.getWineryID().size());
    }

    public int randWinery(){
        Random random = new Random();
        int randomValue = random.nextInt(wineryData.getWineryNum());
        return randomValue;
    }
}