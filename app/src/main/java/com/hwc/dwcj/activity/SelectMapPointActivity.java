package com.hwc.dwcj.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.util.GDLocationUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/6/10.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class SelectMapPointActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.map)
    MapView map;

    private AMap mAMap;
    private MarkerOptions markerOption;
    private Marker marker;
    private double latitude;
    private double longitude;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_select_map_point);
    }

    @Override
    protected void initMap(Bundle savedInstanceState) {
        super.initMap(savedInstanceState);
        map.onCreate(savedInstanceState);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initNowPosition();
        initClick();
    }

    private void initClick() {
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (marker != null) {
                    EventBus.getDefault().post(new EventCenter(EventUtil.SELECT_MAP_POINT, new LatLng(latitude,longitude)));
                    finish();
                }

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initNowPosition() {
        if (mAMap == null) {
            // 初始化地图
            mAMap = map.getMap();
            UiSettings uiSettings = mAMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(false);
            //mAMap.setOnMapLoadedListener(this);
//            mAMap.setMyLocationEnabled(true);
            if(latitude != 0.0 && longitude != 0.0){
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latitude,longitude), 15, 0, 0));
                mAMap.moveCamera(cameraUpdate);//地图移向指定区域
                markerOption = new MarkerOptions();
                LatLng l = new LatLng(latitude,longitude);
                markerOption.position(l);
                markerOption.draggable(false);
                markerOption.setFlat(true);//设置marker平贴地图效果
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark));
                marker = mAMap.addMarker(markerOption);
                marker.showInfoWindow();
            } else{
                PermissionsUtil.requestPermission(this, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permission) {
                        GDLocationUtil.getLocation(new GDLocationUtil.MyLocationListener() {
                            @Override
                            public void result(AMapLocation location) {

                                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 15, 0, 0));
                                mAMap.moveCamera(cameraUpdate);//地图移向指定区域
                                markerOption = new MarkerOptions();
                                LatLng l = new LatLng(location.getLatitude(), location.getLongitude());
                                latitude = l.latitude;
                                longitude = l .longitude;
                                markerOption.position(l);
                                markerOption.draggable(false);
                                markerOption.setFlat(true);//设置marker平贴地图效果
                                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark));
                                marker = mAMap.addMarker(markerOption);
                                marker.showInfoWindow();
                            }
                        });
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permission) {
                        ToastUtil.toast("未开启定位权限");
                    }
                }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
            }


            mAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    if (marker != null) {
                        marker.destroy();
                    }
                    latitude = latLng.latitude;
                    longitude = latLng .longitude;
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 15, 0, 0));
                    mAMap.moveCamera(cameraUpdate);//地图移向指定区域
                    markerOption = new MarkerOptions();
                    markerOption.position(latLng);
                    markerOption.draggable(false);
                    markerOption.setFlat(true);//设置marker平贴地图效果
                    markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark));
                    Marker marker = mAMap.addMarker(markerOption);
                    marker.showInfoWindow();
                }
            });
        }
    }


    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        latitude = extras.getDouble("latitude");
        longitude = extras.getDouble("longitude");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }
}
