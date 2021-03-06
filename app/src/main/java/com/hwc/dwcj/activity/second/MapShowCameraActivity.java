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
import com.hwc.dwcj.entity.TreeCamera;
import com.hwc.dwcj.entity.second.MapInspectionCameraInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.GDLocationUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String id;

    private List<MapInspectionCameraInfo> mList;
    private int positionChild;


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
            // ???????????????
            mAMap = map.getMap();
            UiSettings uiSettings = mAMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(false);
            //mAMap.setOnMapLoadedListener(this);
//            mAMap.setMyLocationEnabled(true);
            mList = new ArrayList<>();
            getData();
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permission) {
                    GDLocationUtil.getLocation(new GDLocationUtil.MyLocationListener() {
                        @Override
                        public void result(AMapLocation location) {

                            //??????????????????
                            MyLocationStyle myLocationStyle;
                            myLocationStyle = new MyLocationStyle();//??????????????????????????????myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????1???1???????????????????????????myLocationType????????????????????????????????????
                            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//??????????????????????????????????????????????????????????????????????????????????????????
                            myLocationStyle.interval(2000); //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                            myLocationStyle.showMyLocation(true);
                            myLocationStyle.strokeColor(getResources().getColor(R.color.transparent));
                            myLocationStyle.radiusFillColor(getResources().getColor(R.color.transparent));
                            myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.dw));
                            mAMap.setMyLocationStyle(myLocationStyle);//?????????????????????Style
                            mAMap.setMyLocationEnabled(true);// ?????????true?????????????????????????????????false??????????????????????????????????????????????????????false???
                            //aMap.getUiSettings().setMyLocationButtonEnabled(true);?????????????????????????????????????????????????????????
 /*                           CameraPosition cameraPosition = new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 15, 0, 0);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                            mAMap.moveCamera(cameraUpdate);*/
                        }
                    });
                }

                @Override
                public void permissionDenied(@NonNull String[] permission) {
                    ToastUtil.toast("?????????????????????");
                }
            }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);

        }
    }

    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("taskId", id);
        ApiClient.requestNetGet(this, AppConfig.getInspectionCameList, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<MapInspectionCameraInfo> list = FastJsonUtil.getList(json, MapInspectionCameraInfo.class);
                if (list != null) {
                    mList.addAll(list);
                    initCameraPoint();
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void initCameraPoint() {
        if (mList.size() > 0) {
            boolean haveMove = false;
            positionChild = 1;
            for (MapInspectionCameraInfo t : mList) {
                if (StringUtil.isEmpty(t.getLatitude()) || StringUtil.isEmpty(t.getLongitude())) {
                    positionChild++;
                    continue;
                }
                if (!haveMove) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(Double.parseDouble(t.getLatitude()), Double.parseDouble(t.getLongitude())), 15, 0, 0));
                    mAMap.moveCamera(cameraUpdate);//????????????????????????
                    haveMove = true;
                }
                markerOption = new MarkerOptions();
                LatLng l = new LatLng(Double.parseDouble(t.getLatitude()), Double.parseDouble(t.getLongitude()));
                markerOption.position(l);
                markerOption.title(t.getCameraName()).snippet("1".equals(t.getIsInspection()) ? "?????????" : "?????????");
                markerOption.draggable(false);
                markerOption.setFlat(true);//??????marker??????????????????
                markerOption.icon("1".equals(t.getIsInspection()) ? BitmapDescriptorFactory.fromResource(R.mipmap.camera_point_icon) : BitmapDescriptorFactory.fromResource(R.mipmap.red_camera_point_icon));
                markerOption.period(positionChild);
                Marker marker = mAMap.addMarker(markerOption);
                marker.showInfoWindow();
                positionChild++;
            }

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
                ToastUtil.toast("?????????????????????");
            }
        }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        id = extras.getString("id");
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
