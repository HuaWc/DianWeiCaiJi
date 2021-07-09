package com.hwc.dwcj.activity.second;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amap.api.location.AMapLocation;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.InspectionDetailDTO;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.interfaces.PickerViewSelectOptionsResult;
import com.hwc.dwcj.util.GDLocationUtil;
import com.hwc.dwcj.util.PickerViewUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartInspectionDetailActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.all)
    LinearLayout all;

    @BindView(R.id.tv_dev_name)
    TextView tv_dev_name;
    @BindView(R.id.tv_group_name)
    TextView tv_group_name;
    @BindView(R.id.tv_ip)
    TextView tv_ip;
    @BindView(R.id.tv_position_code)
    TextView tv_position_code;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;
    @BindView(R.id.tv_address)
    TextView tv_address;

    private String id = "";
    private String devName = "";
    private String groupName = "";
    private String ip = "";
    private String positionCode = "";
    private String memberbarCode = "";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_start_inspection_detail);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            id = bundle.getString("id");
            devName = bundle.getString("devName");
            groupName = bundle.getString("groupName");
            ip = bundle.getString("ip");
            positionCode = bundle.getString("positionCode");
            memberbarCode = bundle.getString("memberbarCode");
        }

        tv_group_name.setText(groupName);
        tv_dev_name.setText(devName);
        tv_ip.setText(ip);
        tv_position_code.setText(positionCode);

        getData();
    }

    public void getData(){
        Map<String,Object> params = new HashMap<>();
        params.put("taskSubId",id);

        ApiClient.getInspectionDetail(StartInspectionDetailActivity.this, AppConfig.startInspectionDetail, "正在加载", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                InspectionDetailDTO inspectionDetailDTO = new Gson().fromJson(json, InspectionDetailDTO.class);
//                ToastUtil.toast(inspectionDetailDTO.map.groupName);

                for (int i=0;i<inspectionDetailDTO.itemList.size();i++){
                    View view = LayoutInflater.from(StartInspectionDetailActivity.this).inflate(R.layout.layout_inspection_detail_task, null);
                    TextView tv_must_fill = (TextView) view.findViewById(R.id.tv_must_fill);
                    TextView name = (TextView) view.findViewById(R.id.tv_name);
                    TextView tv_chose = (TextView) view.findViewById(R.id.tv_chose);
                    EditText et_content = (EditText) view.findViewById(R.id.et_content);
                    InspectionDetailDTO.ItemListDTO itemListDTO = inspectionDetailDTO.itemList.get(i);

                    name.setText(itemListDTO.itemName);
                    if (itemListDTO.itemType.equals("1")){
                        tv_must_fill.setText("*");
                    }else {
                        tv_must_fill.setText("");
                    }

                    if (itemListDTO.itemInputType.equals("0")){
                        tv_chose.setVisibility(View.VISIBLE);
                        et_content.setVisibility(View.GONE);
                        String[] split = itemListDTO.runStatus.split(",");

                        tv_chose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                PickerViewUtils.selectOptions(StartInspectionDetailActivity.this, itemListDTO.itemName, Arrays.asList(split), null, null, new PickerViewSelectOptionsResult() {
                                    @Override
                                    public void getOptionsResult(int options1, int options2, int options3) {
                                        tv_chose.setText(split[options1]);
//                                        tvPcs.setText("");
//                                        getPcsData(mList6.get(options1).getDataValue());
//                                        selectedorgIds = mList6.get(options1).getDataValue();
//                                        getData(false);
                                    }
                                });
                            }
                        });
                    }else {
                        et_content.setVisibility(View.VISIBLE);
                        tv_chose.setVisibility(View.GONE);
                    }
                    ll_container.addView(view);
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

    @OnClick(R.id.iv_location)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_location:
                PermissionsUtil.requestPermission(StartInspectionDetailActivity.this, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permission) {
                        tv_address.setText("正在定位");
                        GDLocationUtil.getLocation(new GDLocationUtil.MyLocationListener() {
                            @Override
                            public void result(AMapLocation location) {
                                tv_address.setText(location.getAddress());
                            }
                        });
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permission) {
                        ToastUtil.toast("未开启定位权限");
                    }
                }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
                break;
        }
    }
}
