package com.hwc.dwcj.activity.second;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
import com.hwc.dwcj.activity.DossierDetailActivity;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.util.GDLocationUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapShowCameraActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.rl_locate)
    RelativeLayout rlLocate;

    private AMap mAMap;
    private MarkerOptions markerOption;
    private Marker marker;
    private double latitude;
    private double longitude;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_map_show_camera);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initNowPosition();
    }

    @Override
    protected void initMap(Bundle savedInstanceState) {
        super.initMap(savedInstanceState);
        map.onCreate(savedInstanceState);
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rlLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locate();
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
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permission) {
                    GDLocationUtil.getLocation(new GDLocationUtil.MyLocationListener() {
                        @Override
                        public void result(AMapLocation location) {

                            //显示定位蓝点
                            MyLocationStyle myLocationStyle;
                            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
                            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
                            myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
                            myLocationStyle.showMyLocation(true);
                            myLocationStyle.strokeColor(getResources().getColor(R.color.transparent));
                            myLocationStyle.radiusFillColor(getResources().getColor(R.color.transparent));
                            myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.dw));
                            mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
                            mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
                            //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
 /*                           CameraPosition cameraPosition = new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 15, 0, 0);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                            mAMap.moveCamera(cameraUpdate);*/
                        }
                    });
                }

                @Override
                public void permissionDenied(@NonNull String[] permission) {
                    ToastUtil.toast("未开启定位权限");
                }
            }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);

        }
    }

    public void locate() {
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                GDLocationUtil.getLocation(new GDLocationUtil.MyLocationListener() {
                    @Override
                    public void result(AMapLocation location) {
                        CameraPosition cameraPosition = new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 15, 0, 0);
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        mAMap.moveCamera(cameraUpdate);
                    }
                });
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                ToastUtil.toast("未开启定位权限");
            }
        }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

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
