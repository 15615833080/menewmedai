package com.sdut.mynewmedia.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.sdut.mynewmedia.R;
import com.sdut.mynewmedia.view.SwipeBackLayout;


public class MapActivity extends AppCompatActivity {

    private SwipeBackLayout layout;
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private boolean isFirstLocate = true;
    private TextView tv_main_title, tv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.base,null);
        layout.attachToActivity(this);
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("地图");
        tv_back = findViewById(R.id.tv_back);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapActivity.this.finish();
            }
        });
        mapView = findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        requestLocation();

    }

    private void requestLocation() {
        Log.d("MainActivity", "33333");
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        option.setCoorType("bd0911");
        option.setScanSpan(1000);

        locationClient.setLocOption(option);
        locationClient.start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);

    }

    public void navigateTo(BDLocation location){
        if(isFirstLocate){
            Log.d("MainActivity", "2222");
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.getDirection()).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        baiduMap.setMyLocationData(locData);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null){
                return;
            }
            else {
                Log.d("MainActivity", "1111");
                navigateTo(location);
            }
        }
    }
}

