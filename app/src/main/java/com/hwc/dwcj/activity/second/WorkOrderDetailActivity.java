package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.DictInfo;
import com.hwc.dwcj.entity.second.FaultAssetInfo;
import com.hwc.dwcj.entity.second.FaultMapInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.GetDictDataHttp;
import com.hwc.dwcj.http.ResultListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 档案详情查看 -工单处理(管理员)
 */
public class WorkOrderDetailActivity extends BaseActivity {
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
    @BindView(R.id.tv24)
    TextView tv24;
    @BindView(R.id.tv25)
    TextView tv25;
    @BindView(R.id.tv26)
    TextView tv26;
    @BindView(R.id.tv27)
    TextView tv27;
    @BindView(R.id.tv28)
    TextView tv28;
    @BindView(R.id.tv29)
    TextView tv29;
    @BindView(R.id.ll_change_part)
    LinearLayout llChangePart;
    @BindView(R.id.tv30)
    TextView tv30;
    @BindView(R.id.tv31)
    TextView tv31;
    @BindView(R.id.tv32)
    TextView tv32;
    @BindView(R.id.tv33)
    TextView tv33;
    @BindView(R.id.tv34)
    TextView tv34;
    @BindView(R.id.tv35)
    TextView tv35;
    @BindView(R.id.tv36)
    TextView tv36;
    @BindView(R.id.tv37)
    TextView tv37;
    @BindView(R.id.tv38)
    TextView tv38;
    @BindView(R.id.tv39)
    TextView tv39;
    @BindView(R.id.tv40)
    TextView tv40;
    @BindView(R.id.tv_s1)
    TextView tvS1;
    @BindView(R.id.tv_s2)
    TextView tvS2;
    @BindView(R.id.tv_s3)
    TextView tvS3;
    @BindView(R.id.tv_s4)
    TextView tvS4;
    @BindView(R.id.ll_agree_part)
    LinearLayout llAgreePart;

    private String id;
    private FaultMapInfo info;
    private FaultAssetInfo asset;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_detail);
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
                asset = FastJsonUtil.getObject(FastJsonUtil.getString(json, "ofia"), FaultAssetInfo.class);

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
        if (info.getHandleStatus() != null && info.getHandleStatus().equals("DEAL_DONE")) {
            tv17.setText(StringUtil.isEmpty(info.getMap().getHandlePersionName()) ? "" : info.getMap().getHandlePersionName());//实际处理人
            tv18.setText(StringUtil.isEmpty(info.getHandleTel()) ? "" : info.getHandleTel());//联系电话
        }

        //tv19.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//协同处理人
        //tv20.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//联系电话


        tv21.setText(StringUtil.isEmpty(info.getMap().getClosedLoopStatusName()) ? "工单还未闭环" : info.getMap().getClosedLoopStatusName());//闭环状态
        tv22.setText(StringUtil.isEmpty(info.getMap().getTimeoutTime()) ? "" : info.getMap().getTimeoutTime());//超时闭环时间
        tv23.setText(StringUtil.isEmpty(info.getAlarmRemark()) ? "" : info.getAlarmRemark());//告警发生原因

        if (info.getExp1() != null) {
            getStatus();
        } else {
            llChangePart.setVisibility(View.GONE);
        }
        tv30.setText(StringUtil.isEmpty(info.getMap().getHandlePersionName()) ? "" : info.getMap().getHandlePersionName());
        tv31.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());
        tv32.setText(StringUtil.isEmpty(info.getAlarmCode()) ? "" : info.getAlarmCode());
        tv33.setText(StringUtil.isEmpty(info.getMap().getAssetName()) ? "" : info.getMap().getAssetName());
        tv34.setText(StringUtil.isEmpty(info.getMap().getAssetCode()) ? "" : info.getMap().getAssetCode());

        //tv40.setText(StringUtil.isEmpty(info.getRemarkLog()) ? "" : info.getRemarkLog());
        tv40.setText(StringUtil.isEmpty(info.getRemark()) ? "" : info.getRemark());
        tv35.setText(StringUtil.isEmpty(info.getMap().getVerifyStatusName()) ? "" : info.getMap().getVerifyStatusName());

        if (!StringUtil.isEmpty(info.getMap().getVerifyStatusName())) {
            if (info.getMap().getVerifyStatusName().equals("审核通过")) {
                //通过 有分数
                llAgreePart.setVisibility(View.VISIBLE);
                tv39.setVisibility(View.GONE);
                tvS1.setText(StringUtil.isEmpty(info.getServiceRating()) ? "" : info.getServiceRating());
                tvS2.setText(StringUtil.isEmpty(info.getServiceRating2()) ? "" : info.getServiceRating2());
                tvS3.setText(StringUtil.isEmpty(info.getServiceRating3()) ? "" : info.getServiceRating3());
                tvS4.setText(StringUtil.isEmpty(info.getServiceRating4()) ? "" : info.getServiceRating4());
            } else {
                llAgreePart.setVisibility(View.GONE);
                tv39.setVisibility(View.VISIBLE);
                tv39.setText(StringUtil.isEmpty(info.getMap().getVerifyStatusName()) ? "" : info.getMap().getVerifyStatusName());//审批理由

            }
            tv36.setText(StringUtil.isEmpty(info.getMap().getVerifyPersonName()) ? "" : info.getMap().getVerifyPersonName());//审核人
            tv37.setText(StringUtil.isEmpty(info.getVerifyTime()) ? "" : StringUtil.dealDateFormat(info.getVerifyTime()));//审核时间
        }


    }

    private void getStatus() {
        GetDictDataHttp.getDictData(this, "OP_FAULT_METHOD", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (info.getExp1().equals(list.get(i).getDataValue())) {
                            tv24.setText(list.get(i).getDataName());
                            break;
                        }
                    }
                    if (info.getExp1().equals("REPLACE")) {
                        //更换
                        llChangePart.setVisibility(View.VISIBLE);
                        tv25.setText(StringUtil.isEmpty(info.getMap().getAssetName()) ? "" : info.getMap().getAssetName());
                        tv26.setText(StringUtil.isEmpty(info.getMap().getAssetCode()) ? "" : info.getMap().getAssetCode());
                        tv27.setText(StringUtil.isEmpty(info.getMap().getDeviceStatusName()) ? "" : info.getMap().getDeviceStatusName());//原设备状态
                        tv28.setText(StringUtil.isEmpty(asset.getMap().getNewAssetName()) ? "" : asset.getMap().getNewAssetName());//更换设备名称
                        tv29.setText(StringUtil.isEmpty(asset.getMap().getNewAssetCode()) ? "" : asset.getMap().getNewAssetCode());//更换设备编号


                    } else {
                        llChangePart.setVisibility(View.GONE);
                    }
                }
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
        id = extras.getString("id");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
