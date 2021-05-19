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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.hwc.dwcj.R;
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
    private MarkerOptions markerOption = null;
    private LatLng centerLatLng = null;
    private List<Marker> markerList = new ArrayList<Marker>();
    private BitmapDescriptor ICON_YELLOW = BitmapDescriptorFactory
            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);


    private List<MapSearchItem> searchItems;
    private AdapterMapSearchItem adapterMapSearchItem;

    private List<NewTreeItem> treeItems;
    private NewTreeAdapter treeAdapter;

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
        init();
        markerOption = new MarkerOptions().draggable(true);
        mMapView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ((ViewGroup) mMapView.getChildAt(0)).getChildAt(1).setVisibility(View.GONE);
                        mMapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
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

            }
        });

        treeItems = new ArrayList<>();
        treeAdapter = new NewTreeAdapter(treeItems);
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
                        } else {
                            getThirdTreeData(position);
                        }
                        break;
                }
            }
        });
        getTreeData();
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
                        treeItems.get(position).getCameraList().addAll(list);
                    } else {
                        treeItems.get(position).getCameraList().addAll(list);
                    }
                    treeAdapter.notifyItemChanged(position);
                }
            }


            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

            }
        });
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


    private void init() {
        if (mAMap == null) {
            // 初始化地图
            mAMap = mMapView.getMap();
            mAMap.setOnMapLoadedListener(this);
//            mAMap.setMyLocationEnabled(true);
            PermissionsUtil.requestPermission(getContext(), new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permission) {
                    GDLocationUtil.getLocation(new GDLocationUtil.MyLocationListener() {
                        @Override
                        public void result(AMapLocation location) {
                            CameraPosition cameraPosition = new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 15, 0, 30);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                            mAMap.moveCamera(cameraUpdate);
                            drawMarkers();
                        }
                    });
                }

                @Override
                public void permissionDenied(@NonNull String[] permission) {
                    ToastUtil.toast("未开启定位权限");
                }
            }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
//            CameraPosition cameraPosition = new CameraPosition(new LatLng(31.830596, 117.254799), 15, 0, 30);
//            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
//            mAMap.moveCamera(cameraUpdate);
//            drawMarkers();
            //点击可以动态添加点
            mAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    markerOption.icon(ICON_YELLOW);
                    centerLatLng = latLng;

                }
            });
            UiSettings uiSettings = mAMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(false);
        }
    }

    public void locate() {
        /*
        CameraPosition cameraPosition = new CameraPosition(new LatLng(31.870596, 117.264799), 15, 0, 30);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mAMap.moveCamera(cameraUpdate);*/
        PermissionsUtil.requestPermission(getContext(), new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                GDLocationUtil.getLocation(new GDLocationUtil.MyLocationListener() {
                    @Override
                    public void result(AMapLocation location) {
                        CameraPosition cameraPosition = new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 15, 0, 30);
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
        mClusterOverlay.onDestroy();
        mMapView.onDestroy();
        mAMap = null;
        unbinder.unbind();
    }


    public void locateTo(double lat, double lon) {
        CameraPosition cameraPosition = new CameraPosition(new LatLng(lat, lon), 15, 0, 30);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mAMap.moveCamera(cameraUpdate);
    }

    public void drawMarkers() {


        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(31.870596, 117.264799))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark))
                .draggable(true);
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
        //添加测试数据
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

        }.start();


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
