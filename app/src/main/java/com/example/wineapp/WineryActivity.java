package com.example.wineapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.location.Location;

import java.util.Locale;


public class WineryActivity extends AppCompatActivity
        implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private WineData wineData = new WineData();
    private int centerIndex = 0;

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;


    private static final LatLng SADOYA = new LatLng(35.667524, 138.573845);
    private static final LatLng KOFU_STATION = new LatLng(35.666870, 138.568774);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winery);

        Intent me = getIntent();
        wineData.setWineData((WineData)me.getSerializableExtra("WINE_DATA"));
        centerIndex = me.getIntExtra("CENTER_WINE", 0);

        setClickListener();

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

        mMap.addMarker(new MarkerOptions()
                .position(SADOYA)
                .title("サドヤワイナリー")
                .snippet("〒400-0024 山梨県甲府市北口３丁目３−２４"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(KOFU_STATION));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {//マーカーのクリックイベント
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
                zoomMap(KOFU_STATION);
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


    void setClickListener(){

        findViewById(R.id.wineMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.putExtra("WINE_DATA", wineData);
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), SearchActivity.class);
                intent.putExtra("WINE_DATA", wineData);
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });

        findViewById(R.id.myWine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MyWineActivity.class);
                intent.putExtra("WINE_DATA", wineData);
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });
        findViewById(R.id.label).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), LabelActivity.class);
                intent.putExtra("WINE_DATA", wineData);
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });
    }
}