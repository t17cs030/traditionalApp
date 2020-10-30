package com.example.wineapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;


public class WineryActivity extends AppCompatActivity
        implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private SearchedWineData searchedWineData = new SearchedWineData();
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
        searchedWineData.setWineIndexList(me.getIntegerArrayListExtra("WINE_INDEX"));
        searchedWineData.setWineNameList(me.getStringArrayListExtra("WINE_NAME"));
        searchedWineData.setWineColorList(me.getIntegerArrayListExtra("WINE_COLOR"));
        searchedWineData.setWineTasteList(me.getIntegerArrayListExtra("WINE_TASTE"));
        searchedWineData.setWinePriceList(me.getIntegerArrayListExtra("NAME_PRICE"));

        searchedWineData.setWineNum(searchedWineData.getWineIndexList().size());

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



    }

    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                zoomMap(KOFU_STATION);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        // [END maps_check_location_permission]
    }

    private void zoomMap(LatLng latlng){
        // 表示する東西南北の緯度経度を設定
        double south = latlng.latitude * (1-0.00005);
        double west = latlng.longitude * (1-0.00005);
        double north = latlng.latitude * (1+0.00005);
        double east = latlng.longitude * (1+0.00005);

        // LatLngBounds (LatLng southwest, LatLng northeast)
        LatLngBounds bounds = LatLngBounds.builder()
                .include(new LatLng(south , west))
                .include(new LatLng(north, east))
                .build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        // static CameraUpdate.newLatLngBounds(LatLngBounds bounds, int width, int height, int padding)
        mMap.moveCamera(CameraUpdateFactory.
                newLatLngBounds(bounds, width, height, 0));

    }

    @Override
    public boolean onMyLocationButtonClick() {//右上の現在地ボタンが押されたときの処理
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {//現在地のマーカーが押されたときの処理
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    // [START maps_check_location_permission_result]
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
            // [END_EXCLUDE]
        }
    }
    // [END maps_check_location_permission_result]

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }


    void setClickListener(){

        findViewById(R.id.wineMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.putExtra("WINE_INDEX", searchedWineData.getWineIndexList());
                intent.putExtra("WINE_NAME", searchedWineData.getWineNameList());
                intent.putExtra("WINE_COLOR", searchedWineData.getWineColorList());
                intent.putExtra("WINE_TASTE", searchedWineData.getWineTasteList());
                intent.putExtra("WINE_PRICE", searchedWineData.getWinePriceList());
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), SearchActivity.class);
                intent.putExtra("WINE_INDEX", searchedWineData.getWineIndexList());
                intent.putExtra("WINE_NAME", searchedWineData.getWineNameList());
                intent.putExtra("WINE_COLOR", searchedWineData.getWineColorList());
                intent.putExtra("WINE_TASTE", searchedWineData.getWineTasteList());
                intent.putExtra("WINE_PRICE", searchedWineData.getWinePriceList());
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });

        findViewById(R.id.myWine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MyWineActivity.class);
                intent.putExtra("WINE_INDEX", searchedWineData.getWineIndexList());
                intent.putExtra("WINE_NAME", searchedWineData.getWineNameList());
                intent.putExtra("WINE_COLOR", searchedWineData.getWineColorList());
                intent.putExtra("WINE_TASTE", searchedWineData.getWineTasteList());
                intent.putExtra("WINE_PRICE", searchedWineData.getWinePriceList());
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });
        findViewById(R.id.label).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), LabelActivity.class);
                intent.putExtra("WINE_INDEX", searchedWineData.getWineIndexList());
                intent.putExtra("WINE_NAME", searchedWineData.getWineNameList());
                intent.putExtra("WINE_COLOR", searchedWineData.getWineColorList());
                intent.putExtra("WINE_TASTE", searchedWineData.getWineTasteList());
                intent.putExtra("WINE_PRICE", searchedWineData.getWinePriceList());
                intent.putExtra("CENTER_WINE", centerIndex);
                startActivity(intent);
            }
        });
    }
}