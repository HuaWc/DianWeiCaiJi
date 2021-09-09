package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.AlertMapInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/7/15.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class AlertDetailActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv9)
    TextView tv9;
    @BindView(R.id.tv10)
    TextView tv10;
    @BindView(R.id.tv11)
    TextView tv11;
    @BindView(R.id.tv12)
    TextView tv12;
    @BindView(R.id.tv13)
    TextView tv13;
    @BindView(R.id.tv14)
    TextView tv14;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.tv15)
    TextView tv15;
    @BindView(R.id.ll_people)
    LinearLayout llPeople;

    private String alarmId;
    private AlertMapInfo info;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_alert_detail);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        getData();
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("alarmId", alarmId);
        ApiClient.requestNetGet(this, AppConfig.OpAlarmInfo, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                info = FastJsonUtil.getObject(json, AlertMapInfo.class);
                initData();
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    private void initData() {
        if (info == null) {
            return;
        }
        tv1.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());
        tv2.setText(StringUtil.isEmpty(info.getAlarmCode()) ? "" : info.getAlarmCode());
        tv3.setText(StringUtil.isEmpty(info.getMap().getAssetNatureName()) ? "" : info.getMap().getAssetNatureName());
        tv4.setText(StringUtil.isEmpty(info.getMap().getAssetTypeName()) ? "" : info.getMap().getAssetTypeName());
        tv5.setText(StringUtil.isEmpty(info.getMap().getAssetClassName()) ? "" : info.getMap().getAssetClassName());
        tv6.setText(StringUtil.isEmpty(info.getAlarmSource()) ? "" : info.getAlarmSource());
        tv7.setText(StringUtil.isEmpty(info.getAlarmLevel()) ? "" : info.getAlarmLevel());
        tv8.setText(StringUtil.isEmpty(info.getOrgName()) ? "" : info.getOrgName());
        tv9.setText(StringUtil.isEmpty(info.getMap().getDeviceName()) ? "" : info.getMap().getDeviceName());
        tv10.setText(StringUtil.isEmpty(info.getAlarmStatus()) ? "" : info.getAlarmStatus());
        tv11.setText(StringUtil.isEmpty(info.getAlarmTime()) ? "" : StringUtil.dealDateFormat(info.getAlarmTime()));
        tv12.setText(StringUtil.isEmpty(info.getIp()) ? "" : info.getIp());
        //tv13.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());
        tv14.setText(StringUtil.isEmpty(info.getAlarmReason()) ? "" : info.getAlarmReason());
        if (StringUtil.isEmpty(info.getMap().getAlarmPersion())) {
            llPeople.setVisibility(View.GONE);
        } else{
            tv15.setText(info.getMap().getAlarmPersion());
            llPeople.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        alarmId = extras.getString("alarmId");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
