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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.hwc.dwcj.base.Constant;
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
import com.hwc.dwcj.util.GDLocationUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.BarUtils;
import com.zds.base.util.StringUtil;

import java.util.ArrayList;
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
    @BindView(R.id.view_l1)
    View viewL1;


    private AMap mAMap;
    private int clusterRadius = 100;


    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<Integer, Drawable>();

    private ClusterOverlay mClusterOverlay;
    private Marker centerMarker;
    private MarkerOptions markerOption;
    private List<Marker> markerList = new ArrayList<Marker>();
    private BitmapDescriptor ICON_YELLOW = BitmapDescriptorFactory
            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
    private double nowLongitude, nowLatitude;

    private List<MapSearchItem> searchItems;
    private AdapterMapSearchItem adapterMapSearchItem;

    private List<NewTreeItem> treeItems;
    private NewTreeAdapter treeAdapter;

    private List<Marker> markers = new ArrayList<>();
    private int positionNow;
    private int positionChild;

    private int from;//0 ??????????????????  1 ??????????????????


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMapView.onCreate(savedInstanceState);
        return view;
    }


    /**
     * ???????????????
     */
    @Override
    protected void initLogic() {
        if (from == 1) {
            viewL1.setVisibility(View.GONE);
            rlScan.setVisibility(View.GONE);
        }
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
        adapterMapSearchItem.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.ll_text:
                        //???????????? ????????????????????????
                        Bundle bundle = new Bundle();
                        bundle.putString("cameraId", searchItems.get(position).getId());
                        toTheActivity(DossierDetailActivity.class, bundle);
                        break;
                    case R.id.iv_locate:
                        //???????????????????????????????????????????????????
                        MapSearchItem camera = searchItems.get(position);

                        if (StringUtil.isEmpty(camera.getLatitude()) || StringUtil.isEmpty(camera.getLongitude())) {
                            ToastUtil.toast("????????????????????????????????????????????????");
                            return;
                        }
                        cvSearchRelative.setVisibility(View.GONE);
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(Double.parseDouble(camera.getLatitude()), Double.parseDouble(camera.getLongitude())), 15, 0, 0));
                        mAMap.moveCamera(cameraUpdate);//????????????????????????
                        markerOption = new MarkerOptions();
                        LatLng l = new LatLng(Double.parseDouble(camera.getLatitude()), Double.parseDouble(camera.getLongitude()));
                        markerOption.position(l);
                        markerOption.title("???????????????").snippet(camera.getCameraName());
                        markerOption.draggable(false);
                        markerOption.setFlat(true);//??????marker??????????????????
                        markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.camera_point_icon));
                        markerOption.period(positionChild);
                        Marker marker = mAMap.addMarker(markerOption);
                        marker.showInfoWindow();
                        markers.add(marker);
                        break;
                }
            }
        });

        treeItems = new ArrayList<>();
        treeAdapter = new NewTreeAdapter(treeItems, new NewTreeAdapter.ClickThirdResult() {
            @Override
            public void onClick(int position, TreeCamera camera, int positionChild) {
                //???????????????????????? ?????????????????? ????????????
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
                            //???????????????
                            for (NewTreeItem item : treeItems) {
                                if (item.getParentId() == treeItems.get(position).getId()) {
                                    item.setVisible(true);
                                }
                            }
                        } else {
                            //???????????????
                            for (NewTreeItem item : treeItems) {
                                if (item.getParentId() == treeItems.get(position).getId()) {
                                    item.setVisible(false);
                                }
                            }
                        }
                        treeAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        //????????????????????????
                        if (treeItems.get(position).isHaveGet()) {
                            treeItems.get(position).setExpand(!treeItems.get(position).isExpand());
                            treeAdapter.notifyItemChanged(position);
                            if (treeItems.get(position).isExpand()) {
                                if (treeItems.get(position).getCameraList() == null || treeItems.get(position).getCameraList().size() == 0) {
                                    ToastUtil.toast("??????????????????????????????");
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
            ToastUtil.toast("????????????????????????????????????????????????");
            return;
        }
        positionChild = 1;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(Double.parseDouble(camera.getLatitude()), Double.parseDouble(camera.getLongitude())), 15, 0, 0));
        mAMap.moveCamera(cameraUpdate);//????????????????????????
        markerOption = new MarkerOptions();
        LatLng l = new LatLng(Double.parseDouble(camera.getLatitude()), Double.parseDouble(camera.getLongitude()));
        markerOption.position(l);
        markerOption.title("???????????????").snippet(camera.getCameraName());
        markerOption.draggable(false);
        markerOption.setFlat(true);//??????marker??????????????????
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.camera_point_icon));
        markerOption.period(positionChild);
        Marker marker = mAMap.addMarker(markerOption);
        marker.showInfoWindow();
        markers.add(marker);
    }

    private void getTreeData() {
        Map<String, Object> hashMap = new HashMap<>();
        // hashMap.put("selectAll", false);
        ApiClient.requestNetPost(getContext(), AppConfig.tree, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<TreeItem> list = FastJsonUtil.getList(json, TreeItem.class);
                if (list != null) {
                    //????????????
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
        ApiClient.requestNetPost(getContext(), AppConfig.treeThree, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                treeItems.get(position).setExpand(true);
                treeItems.get(position).setHaveGet(true);
                List<TreeCamera> list = FastJsonUtil.getList(json, TreeCamera.class);
                if (list != null && list.size() != 0) {
                    //?????????????????????
                    if (treeItems.get(position).getCameraList() == null) {
                        treeItems.get(position).setCameraList(new ArrayList<>());
                    }
                    treeItems.get(position).getCameraList().addAll(list);
                    treeAdapter.notifyItemChanged(position);
                    addCameraPointToMap(position);
                } else {
                    ToastUtil.toast("??????????????????????????????");
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
                    mAMap.moveCamera(cameraUpdate);//????????????????????????
                    haveMove = true;
                }
                markerOption = new MarkerOptions();
                LatLng l = new LatLng(Double.parseDouble(t.getLatitude()), Double.parseDouble(t.getLongitude()));
                markerOption.position(l);
                markerOption.title("???????????????").snippet(t.getCameraName());
                markerOption.draggable(false);
                markerOption.setFlat(true);//??????marker??????????????????
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
                //??????????????????
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
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.GONE);
                hideSoftKeyboard();
                hideSoftKeyboard3();
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.GONE);
                hideSoftKeyboard();
                hideSoftKeyboard3();
            }
        });


    }

    private void search() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("mapKeywords", etSearch3.getText().toString().trim());
        hashMap.put("pageNum", 1);
        hashMap.put("pageSize", 10);
        ApiClient.requestNetPost(getContext(), AppConfig.mapKeywords, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<MapSearchItem> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), MapSearchItem.class);
                searchItems.clear();
                if (list != null && list.size() != 0) {
                    searchItems.addAll(list);
                }
                adapterMapSearchItem.notifyDataSetChanged();
                cvSearchRelative.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


    private void initNowPosition() {
        if (mAMap == null) {
            // ???????????????
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
                            nowLongitude = location.getLongitude();
                            nowLatitude = location.getLatitude();
                            if(nowLatitude == 0 && nowLongitude == 0){
                                ToastUtil.toast("????????????????????????????????????GPS?????????????????????");
                                //????????????????????????
                                CameraPosition cameraPosition = new CameraPosition(new LatLng(Constant.LUAN_CENTER_LATITUDE,Constant.LUAN_CENTER_LONGITUDE), 15, 0, 0);
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                                mAMap.moveCamera(cameraUpdate);
                                return;
                            }
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
                            CameraPosition cameraPosition = new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 15, 0, 0);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                            mAMap.moveCamera(cameraUpdate);
                        }
                    });
                }

                @Override
                public void permissionDenied(@NonNull String[] permission) {
                    ToastUtil.toast("?????????????????????");
                    //????????????????????????
                    CameraPosition cameraPosition = new CameraPosition(new LatLng(Constant.LUAN_CENTER_LATITUDE,Constant.LUAN_CENTER_LONGITUDE), 15, 0, 0);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    mAMap.moveCamera(cameraUpdate);
                }
            }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
            // ?????? Marker ???????????????
            mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    //??????????????????  ????????????
                    //?????????????????????id
                    int p2 = marker.getPeriod() - 1;
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
                if(nowLatitude == 0 && nowLongitude == 0){
                    GDLocationUtil.getLocation(new GDLocationUtil.MyLocationListener() {
                        @Override
                        public void result(AMapLocation location) {
                            nowLongitude = location.getLongitude();
                            nowLatitude = location.getLatitude();
                            if(nowLatitude == 0 && nowLongitude == 0){
                                ToastUtil.toast("????????????????????????????????????GPS?????????????????????");
                                //????????????????????????
                                CameraPosition cameraPosition = new CameraPosition(new LatLng(Constant.LUAN_CENTER_LATITUDE,Constant.LUAN_CENTER_LONGITUDE), 15, 0, 0);
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                                mAMap.moveCamera(cameraUpdate);
                                return;
                            }
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
                            CameraPosition cameraPosition = new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 15, 0, 0);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                            mAMap.moveCamera(cameraUpdate);
                        }
                    });
                } else{
                    CameraPosition cameraPosition = new CameraPosition(new LatLng(nowLatitude,nowLongitude), 15, 0, 0);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    mAMap.moveCamera(cameraUpdate);
                }

            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                ToastUtil.toast("?????????????????????");
                //????????????????????????
                CameraPosition cameraPosition = new CameraPosition(new LatLng(Constant.LUAN_CENTER_LATITUDE,Constant.LUAN_CENTER_LONGITUDE), 15, 0, 0);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mAMap.moveCamera(cameraUpdate);
            }
        }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }


    /**
     * EventBus????????????
     *
     * @param center ????????????????????????
     */
    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * Bundle  ????????????
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {
        from = extras.getInt("from", 0);
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
/*        //??????????????????
        new Thread() {
            public void run() {

                List<ClusterItem> items = new ArrayList<ClusterItem>();

                //??????10000??????
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
     * ??????????????????????????? dp ????????? ????????? px(??????)
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
     * ???????????????
     */
    private void scanCode() {
        //???????????????????????????
        PermissionsUtil.requestPermission(getContext(), new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                ScanUtil.startScan(getActivity(), REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE).create());
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                ToastUtil.toast("??????????????????????????????????????????");
            }
        }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
    }
}
