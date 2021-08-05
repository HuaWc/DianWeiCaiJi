package com.hwc.dwcj.activity.second;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.DictInfo;
import com.hwc.dwcj.entity.second.InspectionDetailDTO;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.interfaces.PickerViewSelectOptionsResult;
import com.hwc.dwcj.util.GDLocationUtil;
import com.hwc.dwcj.util.PickerViewUtils;
import com.hwc.dwcj.view.dialog.SimpleDialog;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_chose_alarm_level)
    TextView tv_chose_alarm_level;
    @BindView(R.id.et_alarm_remark)
    EditText et_alarm_remark;

    private String id = "";
    private String address = "";
    private int alarmStatus = 1;//运行状态(0:异常，1:正常)

    private List<View> listViews = new ArrayList<>();
    public List<InspectionDetailDTO.ItemListDTO> inspectionInfoList;

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
        }

        getData();
    }

    public void getData(){
        Map<String,Object> params = new HashMap<>();
        params.put("taskSubId",id);

        ApiClient.requestNetPost(StartInspectionDetailActivity.this, AppConfig.startInspectionDetail, "正在加载", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                InspectionDetailDTO inspectionDetailDTO = new Gson().fromJson(json, InspectionDetailDTO.class);

                tv_group_name.setText(inspectionDetailDTO.orgName);
                tv_dev_name.setText(inspectionDetailDTO.devName);
                tv_ip.setText(inspectionDetailDTO.ip);
                tv_position_code.setText(inspectionDetailDTO.positionCode);
                tv_type.setText(inspectionDetailDTO.cameraType);

                inspectionInfoList = inspectionDetailDTO.itemList;

                for (int i=0;i<inspectionInfoList.size();i++){
                    View view = LayoutInflater.from(StartInspectionDetailActivity.this).inflate(R.layout.layout_inspection_detail_task, null);

                    MyViewHolder myViewHolder = new MyViewHolder(view);
                    InspectionDetailDTO.ItemListDTO itemListDTO = inspectionInfoList.get(i);

                    myViewHolder.name.setText(itemListDTO.itemName);
                    if (itemListDTO.itemType.equals("1")){
                        myViewHolder.tv_must_fill.setText("*");
                    }else {
                        myViewHolder.tv_must_fill.setText("");
                    }

                    if (itemListDTO.itemInputType.equals("0")){
                        myViewHolder.tv_chose.setVisibility(View.VISIBLE);
                        myViewHolder.et_content.setVisibility(View.GONE);
                        String[] split = itemListDTO.runStatus.split(",");

                        myViewHolder.tv_chose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideSoftKeyboard();
                                hideSoftKeyboard3();
                                PickerViewUtils.selectOptions(StartInspectionDetailActivity.this, itemListDTO.itemName, Arrays.asList(split), null, null, new PickerViewSelectOptionsResult() {
                                    @Override
                                    public void getOptionsResult(int options1, int options2, int options3) {
                                        myViewHolder.tv_chose.setText(split[options1]);
                                        for (int i=0;i<listViews.size();i++){
                                            View v = listViews.get(i);
                                            MyViewHolder mvh = new MyViewHolder(v);
                                            if (mvh.name.getText().toString().equals(myViewHolder.name.getText().toString())){
                                                v.setTag(split[options1]);
                                                listViews.set(i,v);
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }else {
                        myViewHolder.et_content.setVisibility(View.VISIBLE);
                        myViewHolder.tv_chose.setVisibility(View.GONE);
                        for (int j=0;j<listViews.size();j++){
                            View v = listViews.get(j);
                            MyViewHolder mvh = new MyViewHolder(v);
                            if (mvh.name.getText().toString().equals(myViewHolder.name.getText().toString())){
                                v.setTag(mvh.et_content.getText().toString());
                                listViews.set(j,v);
                            }
                        }
                    }

                    listViews.add(view);
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

    @OnClick({R.id.iv_location,R.id.tv_chose_alarm_level,R.id.tv_submit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_location:
                PermissionsUtil.requestPermission(StartInspectionDetailActivity.this, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permission) {
                        tv_address.setText("正在定位...");
                        GDLocationUtil.getLocation(new GDLocationUtil.MyLocationListener() {
                            @Override
                            public void result(AMapLocation location) {
                                address = location.getAddress();
                                tv_address.setText(address);
                            }
                        });
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permission) {
                        ToastUtil.toast("未开启定位权限");
                    }
                }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
                break;
            case R.id.tv_chose_alarm_level:
                hideSoftKeyboard();
                hideSoftKeyboard3();
                List<String> levelOptions = new ArrayList<>();
                Map<String,Object> paramsAlarmLevel = new HashMap<>();
                paramsAlarmLevel.put("dataTypeCode","OP_ALARM_LEVEL");
                ApiClient.requestNetPost(StartInspectionDetailActivity.this, AppConfig.getDataTypeList, "正在加载", paramsAlarmLevel, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        List<DictInfo> list = FastJsonUtil.getList(json, DictInfo.class);
                        if (list != null) {
                            for (DictInfo d : list) {
                                levelOptions.add(d.getDataName());
                            }
                            PickerViewUtils.selectOptions(StartInspectionDetailActivity.this, "告警等级", levelOptions, null, null, new PickerViewSelectOptionsResult() {
                                @Override
                                public void getOptionsResult(int options1, int options2, int options3) {
                                    alarmStatus = 0;
                                    tv_chose_alarm_level.setText(levelOptions.get(options1));
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
                break;
            case R.id.tv_submit:
                JSONArray jsonArray = new JSONArray();
                if (listViews.size()>0){
                    for (int i=0;i<listViews.size();i++){
                        MyViewHolder myViewHolder = new MyViewHolder(listViews.get(i));
                        String tag = (String) listViews.get(i).getTag();
                        if (myViewHolder.tv_must_fill.getText().toString().equals("*")){

                            if (TextUtils.isEmpty(tag)){
                                ToastUtil.toast(myViewHolder.name.getText().toString()+"信息不能为空");
                                return;
                            }
                        }
                        try {
                            if (!TextUtils.isEmpty(tag)){
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("itemId",inspectionInfoList.get(i).itemId);
                                jsonObject.put("itemValue",(String) listViews.get(i).getTag());
                                jsonObject.put("itemDesc","");
                                jsonArray.put(jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (TextUtils.isEmpty(address)){
                    ToastUtil.toast("位置信息不能为空");
                    return;
                }
                Map<String,Object> params = new HashMap<>();
                params.put("taskSubId",id);
                params.put("operateStatus",alarmStatus+"");
                params.put("inspectionAddress",address);
                params.put("itemsJson",jsonArray.toString());
                params.put("userId", MyApplication.getInstance().getUserInfo().getId());
                params.put("otherQuest",et_remark.getText().toString());
                if (alarmStatus == 0){
                    params.put("alarmLevel",tv_chose_alarm_level.getText().toString());
                    params.put("alarmReason",et_alarm_remark.getText().toString().trim());
                }

                ApiClient.requestNetPost(StartInspectionDetailActivity.this, AppConfig.startInspectionInsert, "正在加载", params, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        new SimpleDialog.Builder(StartInspectionDetailActivity.this)
                                .setMessage("提交成功")
                                .setOnConfirmClickListener(new SimpleDialog.OnConfirmClickListener() {
                                    @Override
                                    public void onClick() {
                                        finish();
                                    }
                                })
                                .create().show(getSupportFragmentManager(),"SimpleDialog");
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
                break;
        }
    }

    public class MyViewHolder{
        public LinearLayout root;
        public TextView tv_must_fill;
        public TextView name;
        public TextView tv_chose;
        public TextView et_content;

        public MyViewHolder(View view) {
            root = (LinearLayout) view.findViewById(R.id.ll_root);
            tv_must_fill = (TextView) view.findViewById(R.id.tv_must_fill);
            name = (TextView) view.findViewById(R.id.tv_name);
            tv_chose = (TextView) view.findViewById(R.id.tv_chose);
            et_content = (EditText) view.findViewById(R.id.et_content);
        }
    }
}
