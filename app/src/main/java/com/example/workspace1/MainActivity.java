package com.example.workspace1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.*;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.CompassView;
import com.naver.maps.map.widget.LocationButtonView;
import com.naver.maps.map.widget.ScaleBarView;
import com.naver.maps.map.widget.ZoomControlView;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private Context context = this;
    Fragment MapNaver, Account, Favorite, Feed, Popular, Setting;
    Toolbar toolbar;
    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // inflater


//        Intent intent = new Intent(this, LoadingActivity.class);
//        startActivity(intent);//로딩화면
//
//        Intent loginIntent = new Intent(this, LoginActivity.class);
//        startActivity(loginIntent);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);

        toolbar = (Toolbar) findViewById((R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//toolbar 보여주기


        toolbar.findViewById(R.id.menuButton).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(!drawer.isDrawerOpen(Gravity.LEFT))
                     drawer.openDrawer(Gravity.LEFT);
             }

         });

        MapNaver = new MapNaver();
        Account = new Account();
        Favorite = new Favorite();
        Feed = new Feed();
        Popular = new Popular();
        Setting = new Setting();

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.container, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
        mLocationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);



        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawer.closeDrawers();

                int id = item.getItemId();

                if(id == R.id.map)
                {
                    onChangedFragemnt(1,null);
                    Toast.makeText(context, " 지도 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.account)
                {
                    onChangedFragemnt(2,null);
                    Toast.makeText(context, " 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.feed)
                {
                    onChangedFragemnt(3,null);
                    Toast.makeText(context, " 현재피드를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.popular)
                {
                    onChangedFragemnt(4,null);
                    Toast.makeText(context, " 인기글을 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.favorite)
                {
                    onChangedFragemnt(5,null);
                    Toast.makeText(context, "즐겨찾기를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.setting)
                {
                    onChangedFragemnt(6,null);
                    Toast.makeText(context, " 설정을 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


    }


    public void onChangedFragemnt(int position,Bundle bundle)
    {
                Fragment fragment = null;

                switch (position){
                    case 1:
                        FragmentManager fm = getSupportFragmentManager();
                        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
                        if (mapFragment == null) {
                            Log.d("dd","dd");
                            mapFragment = MapFragment.newInstance();
                        }

                        mapFragment.getMapAsync(this);

                        fragment = mapFragment;

                        break;
                    case 2:
                        fragment = Account;

                    break;
                    case 3:
                        fragment = Favorite;

                        break;
                    case 4:
                        fragment = Feed;

                        break;
                    case 5:
                        fragment = Popular;

                        break;
                    case 6:
                        fragment = Setting;

                        break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);
        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setZoomControlEnabled(true); // 기본값 : true
        uiSettings.setLocationButtonEnabled(true); // 기본값 : false
        mNaverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (mLocationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!mLocationSource.isActivated()) { // 권한 거부됨
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }
//    @Override
//    public void onMapReady(@NonNull NaverMap naverMap) {
//
//        Log.d("여기","여기");
//        Marker marker = new Marker();
//        marker.setPosition(new LatLng(35.5670135, 126.9783740));
//        marker.setMap(naverMap);
//
//
//
//
//        mNaverMap = naverMap;
//        mNaverMap.setLocationSource(mLocationSource);
//
//        UiSettings uiSettings = mNaverMap.getUiSettings();
//
//        uiSettings.setScaleBarEnabled(true);
//        uiSettings.setCompassEnabled(true);
//
//    }
}