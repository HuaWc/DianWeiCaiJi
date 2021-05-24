package com.hwc.dwcj.fragment;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.DossierDetailActivity;
import com.hwc.dwcj.adapter.AdapterMapSearchItem;
import com.hwc.dwcj.adapter.NewTreeAdapter;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.entity.MapSearchItem;
import com.hwc.dwcj.entity.NewTreeItem;
import com.hwc.dwcj.entity.TreeCamera;
import com.hwc.dwcj.entity.TreeItem;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.map.ClusterClickListener;
import com.hwc.dwcj.map.ClusterItem;
import com.hwc.dwcj.map.ClusterOverlay;
import com.hwc.dwcj.map.ClusterRender;
import com.hwc.dwcj.map.RegionItem;
import com.hwc.dwcj.util.GDLocationUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.BarUtils;
import com.zds.base.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MapFragment extends BaseFragment implements ClusterRender, AMap.OnMapClickListener,
        AMap.OnMapLoadedListener, ClusterClickListener, LocationSource {

    Unbinder unbinder;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.all)
    RelativeLayout all;
    @BindView(R.id.rl_scan)
    RelativeLayout rlScan;
    @BindView(R.id.rl_locate)
    RelativeLayout rlLocate;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.cv_jg1)
    CardView cvJg1;
    @BindView(R.id.cv_search1)
    CardView cvSearch1;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.bar2)
    View bar2;
    @BindView(R.id.ll_jg2)
    LinearLayout llJg2;
    @BindView(R.id.rv_jg)
    RecyclerView rvJg;
    @BindView(R.id.cv_jg2)
    CardView cvJg2;
    @BindView(R.id.cv_search2)
    CardView cvSearch2;
    @BindView(R.id.ll_2)
    LinearLayout ll2;
    @BindView(R.id.bar3)
    View bar3;
    @BindView(R.id.iv_back3)
    ImageView ivBack3;
    @BindView(R.id.et_search3)
    EditText etSearch3;
    @BindView(R.id.iv_search3)
    ImageView ivSearch3;
    @BindView(R.id.cv_search3)
    CardView cvSearch3;
    @BindView(R.id.rv_search_relative)
    RecyclerView rvSearchRelative;
    @BindView(R.id.cv_search_relative)
    CardView cvSearchRelative;
    @BindView(R.id.ll_3)
    LinearLayout ll3;


    private AMap mAMap;
    private int clusterRadius = 100;


    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<Integer, Drawable>();

    private ClusterOverlay mClusterOverlay;
    private Marker centerMarker;
    private MarkerOptions markerOption;
    private LatLng centerLatLng;
    private List<Marker> markerList = new ArrayList<Marker>();
    private BitmapDescriptor ICON_YELLOW = BitmapDescriptorFactory
            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);


    private List<MapSearchItem> searchItems;
    private AdapterMapSearchItem adapterMapSearchItem;

    private List<NewTreeItem> treeItems;
    private NewTreeAdapter treeAdapter;

    private List<Marker> markers = new ArrayList<>();
    private int positionNow;
    private int positionChild;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMapView.onCreate(savedInstanceState);
        return view;
    }


    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        initNowPosition();
        /* markerOption = new MarkerOptions().draggable(false);*/
/*        mMapView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ((ViewGroup) mMapView.getChildAt(0)).getChildAt(1).setVisibility(View.GONE);
                        mMapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });*/
        initClick();
        initAdapter();
    }

    private void initAdapter() {

        searchItems = new ArrayList<>();
        adapterMapSearchItem = new AdapterMapSearchItem(searchItems);
        rvSearchRelative.setLayoutManager(new LinearLayoutManager(mContext));
        rvSearchRelative.setAdapter(adapterMapSearchItem);
        adapterMapSearchItem.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //搜索结果 直接进入相机详情
                Bundle bundle = new Bundle();
                bundle.putString("cameraId", searchItems.get(position).getId());
                toTheActivity(DossierDetailActivity.class, bundle);
            }
        });

        treeItems = new ArrayList<>();
        treeAdapter = new NewTreeAdapter(treeItems, new NewTreeAdapter.ClickThirdResult() {
            @Override
            public void onClick(int position, TreeCamera camera, int positionChild) {
                //点击第三级的相机 直接移动视角 显示点位
                showSingleCamera(position, camera, positionChild);
            }
        });
        rvJg.setLayoutManager(new LinearLayoutManager(mContext));
        rvJg.setAdapter(treeAdapter);
        treeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (treeItems.get(position).getLevel()) {
                    case 1:
                        treeItems.get(position).setExpand(!treeItems.get(position).isExpand());
                        if (treeItems.get(position).isExpand()) {
                            //收缩到打开
                            for (NewTreeItem item : treeItems) {
                                if (item.getParentId() == treeItems.get(position).getId()) {
                                    item.setVisible(true);
                                }
                            }
                        } else {
                            //打开到收缩
                            for (NewTreeItem item : treeItems) {
                                if (item.getParentId() == treeItems.get(position).getId()) {
                                    item.setVisible(false);
                                }
                            }
                        }
                        treeAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        //先判断那没拿数据
                        if (treeItems.get(position).isHaveGet()) {
                            treeItems.get(position).setExpand(!treeItems.get(position).isExpand());
                            treeAdapter.notifyItemChanged(position);
                            if (treeItems.get(position).isExpand()) {
                                if (treeItems.get(position).getCameraList() == null || treeItems.get(position).getCameraList().size() == 0) {
                                    ToastUtil.toast("机构下暂无点位数据！");
                                } else {
                                    addCameraPointToMap(position);
                                }
                            }
                        } else {
                            getThirdTreeData(position);
                        }
                        break;
                }
            }
        });
        getTreeData();
    }

    private void showSingleCamera(int position, TreeCamera camera, int positionChild) {
        positionNow = position;
        if (markers.size() > 0) {
            for (Marker marker : markers) {
                marker.destroy();
            }
        }
        if (StringUtil.isEmpty(camera.getLatitude()) || StringUtil.isEmpty(camera.getLongitude())) {
            ToastUtil.toast("该相机的坐标信息有误，无法显示！");
            return;
        }
        positionChild = 1;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(Double.parseDouble(camera.getLatitude()), Double.parseDouble(camera.getLongitude())), 15, 0, 0));
        mAMap.moveCamera(cameraUpdate);//地图移向指定区域
        markerOption = new MarkerOptions();
        LatLng l = new LatLng(Double.parseDouble(camera.getLatitude()), Double.parseDouble(camera.getLongitude()));
        markerOption.position(l);
        markerOption.title("相机名称：").snippet(camera.getCameraName());
        markerOption.draggable(false);
        markerOption.setFlat(true);//设置marker平贴地图效果
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.camera_point_icon));
        markerOption.period(positionChild);
        Marker marker = mAMap.addMarker(markerOption);
        marker.showInfoWindow();
        markers.add(marker);
    }

    private void getTreeData() {
        Map<String, Object> hashMap = new HashMap<>();
        // hashMap.put("selectAll", false);
        ApiClient.requestNetPost(getContext(), AppConfig.tree, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<TreeItem> list = FastJsonUtil.getList(json, TreeItem.class);
                if (list != null) {
                    //加工数据
                    handleData(list);
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void handleData(List<TreeItem> list) {
        int id = 1;
        for (int i = 0; i < list.size(); i++) {
            NewTreeItem newTreeItem = new NewTreeItem();
            newTreeItem.setId(id);
            newTreeItem.setLevel(1);
            newTreeItem.setVisible(true);
            newTreeItem.setName(list.get(i).getTitleName() + "(" + list.get(i).getTitleNumber() + ")");
            treeItems.add(newTreeItem);
            if (list.get(i).getOrgUtils() != null && list.get(i).getOrgUtils().size() != 0) {
                for (int j = 0; j < list.get(i).getOrgUtils().size(); j++) {
                    NewTreeItem newTreeItem2 = new NewTreeItem();
                    newTreeItem2.setParentId(id);
                    newTreeItem2.setLevel(2);
                    newTreeItem2.setName(list.get(i).getOrgUtils().get(j).getOrgName() + "(" + list.get(i).getOrgUtils().get(j).getOrgNumber() + ")");
                    newTreeItem2.setOrgCode(list.get(i).getOrgUtils().get(j).getOrgCode());
                    treeItems.add(newTreeItem2);
                }
            }
            id++;
        }
        treeAdapter.notifyDataSetChanged();

    }

    private void getThirdTreeData(int position) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("orgCode", treeItems.get(position).getOrgCode());
        ApiClient.requestNetPost(getContext(), AppConfig.treeThree, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                treeItems.get(position).setExpand(true);
                treeItems.get(position).setHaveGet(true);
                List<TreeCamera> list = FastJsonUtil.getList(json, TreeCamera.class);
                if (list != null && list.size() != 0) {
                    //装配第三级数据
                    if (treeItems.get(position).getCameraList() == null) {
                        treeItems.get(position).setCameraList(new ArrayList<>());
                    }
                    treeItems.get(position).getCameraList().addAll(list);
                    treeAdapter.notifyItemChanged(position);
                    addCameraPointToMap(position);
                } else {
                    ToastUtil.toast("机构下暂无点位数据！");
                }
            }


            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

            }
        });
    }

    private void addCameraPointToMap(int position) {
        positionNow = position;
        if (markers.size() > 0) {
            for (Marker marker : markers) {
                marker.destroy();
            }
        }
        if (treeItems.get(position).getCameraList() != null && treeItems.get(position).getCameraList().size() > 0) {
            boolean haveMove = false;
            positionChild = 1;
            for (TreeCamera t : treeItems.get(position).getCameraList()) {
                if (StringUtil.isEmpty(t.getLatitude()) || StringUtil.isEmpty(t.getLongitude())) {
                    positionChild++;
                    continue;
                }
                if (!haveMove) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(Double.parseDouble(t.getLatitude()), Double.parseDouble(t.getLongitude())), 15, 0, 0));
                    mAMap.moveCamera(cameraUpdate);//地图移向指定区域
                    haveMove = true;
                }
                markerOption = new MarkerOptions();
                LatLng l = new LatLng(Double.parseDouble(t.getLatitude()), Double.parseDouble(t.getLongitude()));
                markerOption.position(l);
                markerOption.title("相机名称：").snippet(t.getCameraName());
                markerOption.draggable(false);
                markerOption.setFlat(true);//设置marker平贴地图效果
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.camera_point_icon));
                markerOption.period(positionChild);
                Marker marker = mAMap.addMarker(markerOption);
                marker.showInfoWindow();
                markers.add(marker);
                positionChild++;
            }

        }
    }


    private void initClick() {
        if (bar != null) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) bar.getLayoutParams();
            params.height = BarUtils.getStatusBarHeight(getActivity());
            bar.setLayoutParams(params);
            bar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent));
        }
        if (bar2 != null) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) bar2.getLayoutParams();
            params.height = BarUtils.getStatusBarHeight(getActivity());
            bar2.setLayoutParams(params);
            bar2.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent));
        }
        if (bar3 != null) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) bar3.getLayoutParams();
            params.height = BarUtils.getStatusBarHeight(getActivity());
            bar3.setLayoutParams(params);
            bar3.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent));
        }
        cvJg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ll1.setVisibility(View.GONE);
                        ll2.setVisibility(View.VISIBLE);
                        ll3.setVisibility(View.GONE);
                    }
                });
            }
        });
        cvSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(View.GONE);
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.VISIBLE);
                etSearch3.requestFocus();
            }
        });
        llJg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.GONE);
            }
        });
        cvSearch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(View.GONE);
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.VISIBLE);
                etSearch3.requestFocus();
            }
        });
        ivBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.GONE);
                hideSoftKeyboard();
                hideSoftKeyboard3();
            }
        });
        ivSearch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //搜索功能实现
            }
        });
        rlScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
        rlLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locate();
            }
        });
        etSearch3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!StringUtil.isEmpty(editable.toString().trim())) {
                    search();
                } else {
                    searchItems.clear();
                    adapterMapSearchItem.notifyDataSetChanged();
                }
            }
        });
    }

    private void search() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("mapKeywords", etSearch3.getText().toString().trim());
        hashMap.put("pageNum", 1);
        hashMap.put("pageSize", 20);
        ApiClient.requestNetPost(getContext(), AppConfig.mapKeywords, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<MapSearchItem> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), MapSearchItem.class);
                searchItems.clear();
                if (list != null && list.size() != 0) {
                    searchItems.addAll(list);
                }
                adapterMapSearchItem.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


    private void initNowPosition() {
        if (mAMap == null) {
            // 初始化地图
            mAMap = mMapView.getMap();
            UiSettings uiSettings = mAMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(false);
            //mAMap.setOnMapLoadedListener(this);
//            mAMap.setMyLocationEnabled(true);
            PermissionsUtil.requestPermission(getContext(), new PermissionListener() {
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
            // 绑定 Marker 被点击事件
            mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    //相机点击事件  进入详情
                    //难点：取到相机id
                    int p2 = marker.getPeriod()-1;
                    Bundle bundle = new Bundle();
/*                    if(markers.size() == 1){
                        bundle.putString("cameraId", treeItems.get(positionNow).getCameraList().get(0).getId());

                    } else{
                        bundle.putString("cameraId", treeItems.get(positionNow).getCameraList().get(p2).getId());
                    }*/
                    bundle.putString("cameraId", treeItems.get(positionNow).getCameraList().get(p2).getId());
                    toTheActivity(DossierDetailActivity.class, bundle);
                    return false;
                }
            });

        }
    }

    public void locate() {
        PermissionsUtil.requestPermission(getContext(), new PermissionListener() {
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


    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //mClusterOverlay.onDestroy();
        mMapView.onDestroy();
        mAMap = null;
        unbinder.unbind();
    }


    public void locateTo(double lat, double lon) {
        CameraPosition cameraPosition = new CameraPosition(new LatLng(lat, lon), 15, 0, 0);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mAMap.moveCamera(cameraUpdate);
    }

    public void drawMarkers(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark))
                .draggable(false);
        Marker marker = mAMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }


    private void addCenterMarker(LatLng latlng) {
        if (null == centerMarker) {
            centerMarker = mAMap.addMarker(markerOption);
        }
        centerMarker.setPosition(latlng);
        markerList.add(centerMarker);


    }

    @Override
    public void onMapLoaded() {
/*        //添加测试数据
        new Thread() {
            public void run() {

                List<ClusterItem> items = new ArrayList<ClusterItem>();

                //随机10000个点
                for (int i = 0; i < 1; i++) {

                    double lat = Math.random() / 30 + 31.866197691467235;
                    double lon = Math.random() / 30 + 117.27927737469572;
//                    Log.d(TAG, "run: " + Math.random() / 10);
                    LatLng latLng = new LatLng(lat, lon, false);
                    RegionItem regionItem = new RegionItem(latLng,
                            "test" + i);
                    items.add(regionItem);
                }
                mClusterOverlay = new ClusterOverlay(mAMap, items,
                        dp2px(getContext(), clusterRadius),
                        getContext());
                mClusterOverlay.setClusterRenderer(MapFragment.this);
                mClusterOverlay.setOnClusterClickListener(MapFragment.this);

            }

        }.start();*/


    }

    @Override
    public void onMapClick(LatLng latLng) {


        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark))
                .draggable(true);
        Marker marker = mAMap.addMarker(markerOptions);
        marker.showInfoWindow();

    }

    @Override
    public void onClick(Marker marker, List<ClusterItem> clusterItems) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (ClusterItem clusterItem : clusterItems) {
            builder.include(clusterItem.getPosition());

        }

        if (clusterItems.size() == 1) {
            //DADetailActivity.start(mContext);
        }
        LatLngBounds latLngBounds = builder.build();
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0)
        );
    }

    @Override
    public Drawable getDrawAble(int clusterNum) {
        int radius = dp2px(getContext(), 80);
        if (clusterNum == 1) {
            Drawable bitmapDrawable = mBackDrawAbles.get(1);
            if (bitmapDrawable == null) {
                bitmapDrawable =
                        getContext().getResources().getDrawable(
                                R.drawable.gps_point);
                mBackDrawAbles.put(1, bitmapDrawable);
            }

            return bitmapDrawable;
        } else if (clusterNum < 5) {

            Drawable bitmapDrawable = mBackDrawAbles.get(2);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(159, 210, 154, 6)));
                mBackDrawAbles.put(2, bitmapDrawable);
            }

            return bitmapDrawable;
        } else if (clusterNum < 10) {
            Drawable bitmapDrawable = mBackDrawAbles.get(3);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(199, 217, 114, 0)));
                mBackDrawAbles.put(3, bitmapDrawable);
            }

            return bitmapDrawable;
        } else {
            Drawable bitmapDrawable = mBackDrawAbles.get(4);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(235, 215, 66, 2)));
                mBackDrawAbles.put(4, bitmapDrawable);
            }

            return bitmapDrawable;
        }
    }

    private Bitmap drawCircle(int radius, int color) {

        Bitmap bitmap = Bitmap.createBitmap(radius * 2, radius * 2,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        RectF rectF = new RectF(0, 0, radius * 2, radius * 2);
        paint.setColor(color);
        canvas.drawArc(rectF, 0, 360, true, paint);
        return bitmap;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    private static final int REQUEST_CODE_SCAN = 0X01;

    /**
     * 扫一扫功能
     */
    private void scanCode() {
        //申请相机和储存权限
        PermissionsUtil.requestPermission(getContext(), new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                ScanUtil.startScan(getActivity(), REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE).create());
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                ToastUtil.toast("拒绝权限将无法使用扫一扫功能");
            }
        }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
    }
}
