package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.FaultMapInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 事件详情 审批通过后
 */
public class EventDetailAfterReviewActivity extends BaseActivity {
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
    @BindView(R.id.tv15)
    TextView tv15;
    @BindView(R.id.tv16)
    TextView tv16;
    @BindView(R.id.tv17)
    TextView tv17;
    @BindView(R.id.tv18)
    TextView tv18;
    @BindView(R.id.tv19)
    TextView tv19;
    @BindView(R.id.tv20)
    TextView tv20;
    @BindView(R.id.tv21)
    TextView tv21;
    @BindView(R.id.tv22)
    TextView tv22;
    @BindView(R.id.tv23)
    TextView tv23;
    @BindView(R.id.tv_pd_remark)
    TextView tvPdRemark;

    private String id;
    private FaultMapInfo info;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_event_detail_after_review);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        getData();
        initClick();
    }

    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        ApiClient.requestNetGet(this, AppConfig.OpFaultInfoInfo, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                info = FastJsonUtil.getObject(FastJsonUtil.getString(json, "OpFaultInfoModel"), FaultMapInfo.class);
                initData();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void initData() {
        if (info == null) {
            return;
        }
        tvPdRemark.setText(StringUtil.isEmpty(info.getRemark2()) ? "" : info.getRemark2());

        tv1.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());
        tv2.setText(StringUtil.isEmpty(info.getAlarmCode()) ? "" : info.getAlarmCode());
        tv3.setText(StringUtil.isEmpty(info.getMap().getAssetNatureName()) ? "" : info.getMap().getAssetNatureName());
        tv4.setText(StringUtil.isEmpty(info.getMap().getAssetTypeName()) ? "" : info.getMap().getAssetTypeName());
        tv5.setText(StringUtil.isEmpty(info.getMap().getAssetClassName()) ? "" : info.getMap().getAssetClassName());
        tv6.setText(StringUtil.isEmpty(info.getMap().getAlarmSourceName()) ? "" : info.getMap().getAlarmSourceName());
        tv7.setText(StringUtil.isEmpty(info.getMap().getAlarmGradeName()) ? "" : info.getMap().getAlarmGradeName());
        //tv8.setText(StringUtil.isEmpty(info.getCameraFaultType() + "") ? "" : info.getCameraFaultType() + "");
        tv9.setText(StringUtil.isEmpty(info.getMap().getOrgName()) ? "" : info.getMap().getOrgName());
        tv10.setText(StringUtil.isEmpty(info.getMap().getAssetName()) ? "" : info.getMap().getAssetName());
        tv11.setText(StringUtil.isEmpty(info.getMap().getPositionCode()) ? "" : info.getMap().getPositionCode());
        tv12.setText(StringUtil.isEmpty(info.getMap().getManageIp()) ? "" : info.getMap().getManageIp());
        tv13.setText(StringUtil.isEmpty(info.getAlarmTime()) ? "" : StringUtil.dealDateFormat(info.getAlarmTime()));//发生时间
        tv14.setText(StringUtil.isEmpty(info.getAddTime()) ? "" : StringUtil.dealDateFormat(info.getAddTime()));//派单时间
        tv15.setText(StringUtil.isEmpty(info.getRecoverTime()) ? "" : StringUtil.dealDateFormat(info.getRecoverTime()));//恢复时间
        tv16.setText(info.getMap().getFaultTime() + "");//故障时长
        if(info.getHandleStatus() != null && info.getHandleStatus().equals("DEAL_DONE")){
            tv17.setText(StringUtil.isEmpty(info.getMap().getHandlePersionName()) ? "" : info.getMap().getHandlePersionName());//实际处理人
            tv18.setText(StringUtil.isEmpty(info.getHandleTel()) ? "" : info.getHandleTel());//联系电话
        }

        //tv19.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//协同处理人
        //tv20.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//联系电话


        tv21.setText(StringUtil.isEmpty(info.getMap().getClosedLoopStatusName()) ? "工单还未闭环" : info.getMap().getClosedLoopStatusName());//闭环状态
        tv22.setText(StringUtil.isEmpty(info.getMap().getTimeoutTime()) ? "" : info.getMap().getTimeoutTime());//超时闭环时间
        tv23.setText(StringUtil.isEmpty(info.getAlarmRemark()) ? "" : info.getAlarmRemark());//告警发生原因


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
        id = extras.getString("id");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
