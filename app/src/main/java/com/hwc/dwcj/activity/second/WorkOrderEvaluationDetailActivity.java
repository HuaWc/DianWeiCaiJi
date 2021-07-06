package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.PersonalEvaluationDetail;
import com.hwc.dwcj.entity.second.PersonalEvaluationInfo;
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

public class WorkOrderEvaluationDetailActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.all)
    LinearLayout all;
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

    private String faultId;
    private PersonalEvaluationDetail info;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_evaluation_statistics_detail);
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
        hashMap.put("faultId", faultId);
        ApiClient.requestNetGet(this, AppConfig.faultEvaluationDetails, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                info = FastJsonUtil.getObject(json, PersonalEvaluationDetail.class);
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
            ToastUtil.toast("获取信息为空");
            return;
        }
        tv1.setText(StringUtil.isEmpty(info.getAlarmName())?"":info.getAlarmName());
        tv2.setText(StringUtil.isEmpty(info.getMap().getManageIp())?"":info.getMap().getManageIp());
        tv3.setText(StringUtil.isEmpty(info.getAlarmCode())?"":info.getAlarmCode());
        tv4.setText(StringUtil.isEmpty(info.getMap().getOrgName())?"":info.getMap().getOrgName());
        tv5.setText(StringUtil.isEmpty(info.getAddTime())?"":StringUtil.dealDateFormat(info.getAddTime()));
        tv6.setText(StringUtil.isEmpty(info.getMap().getHandlePersionName())?"":info.getMap().getHandlePersionName());
        tv7.setText(String.valueOf(info.getMap().getTotalAverageScore()));
        tv8.setText(String.valueOf(info.getMap().getAverageFault()));
        tv9.setText(StringUtil.isEmpty(info.getServiceRating())?"":info.getServiceRating());
        tv10.setText(StringUtil.isEmpty(info.getServiceRating2())?"":info.getServiceRating2());
        tv11.setText(StringUtil.isEmpty(info.getServiceRating3())?"":info.getServiceRating3());
        tv12.setText(StringUtil.isEmpty(info.getServiceRating4())?"":info.getServiceRating4());
        tv13.setText(String.valueOf(info.getMap().getAverageAlarm()));
        tv14.setText(StringUtil.isEmpty(info.getMap().getAlarmRating())?"":info.getMap().getAlarmRating());
        tv15.setText(StringUtil.isEmpty(info.getMap().getAlarmRating2())?"":info.getMap().getAlarmRating2());
        tv16.setText(StringUtil.isEmpty(info.getMap().getAlarmRating3())?"":info.getMap().getAlarmRating3());
        tv17.setText(StringUtil.isEmpty(info.getMap().getAlarmRating4())?"":info.getMap().getAlarmRating4());

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        faultId = extras.getString("faultId");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
