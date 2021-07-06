package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.FaultMapInfo;
import com.hwc.dwcj.entity.second.WorkOrderUser;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.EventUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Christ on 2021/6/8.
 * By an amateur android developer
 * Email 627447123@qq.com
 * <p>
 * <p>
 * 工单处理（管理员） - 事件审核 - 流程审核
 */
public class WorkOrderProcessAuditActivity extends BaseActivity {


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
    @BindView(R.id.rb_agree)
    RadioButton rbAgree;
    @BindView(R.id.rb_refuse)
    RadioButton rbRefuse;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.tv_e1)
    TextView tvE1;
    @BindView(R.id.iv_up1)
    ImageView ivUp1;
    @BindView(R.id.iv_down1)
    ImageView ivDown1;
    @BindView(R.id.tv_e2)
    TextView tvE2;
    @BindView(R.id.iv_up2)
    ImageView ivUp2;
    @BindView(R.id.iv_down2)
    ImageView ivDown2;
    @BindView(R.id.tv_e3)
    TextView tvE3;
    @BindView(R.id.iv_up3)
    ImageView ivUp3;
    @BindView(R.id.iv_down3)
    ImageView ivDown3;
    @BindView(R.id.tv_e4)
    TextView tvE4;
    @BindView(R.id.iv_up4)
    ImageView ivUp4;
    @BindView(R.id.Iv_down4)
    ImageView IvDown4;
    @BindView(R.id.ll_evaluate_part)
    LinearLayout llEvaluatePart;
    @BindView(R.id.et_refuse)
    EditText etRefuse;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;

    private String id;
    private FaultMapInfo info;
    private int maxScore = 10;
    private int minScore = 1;
    private int type = 0;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_process_audit);

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
                info = FastJsonUtil.getObject(FastJsonUtil.getString(json,"OpFaultInfoModel"), FaultMapInfo.class);
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
        tv1.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());
        tv2.setText(StringUtil.isEmpty(info.getAlarmCode()) ? "" : info.getAlarmCode());
        tv3.setText(StringUtil.isEmpty(info.getMap().getAssetNatureName()) ? "" : info.getMap().getAssetNatureName());
        tv4.setText(StringUtil.isEmpty(info.getMap().getAssetTypeName()) ? "" : info.getMap().getAssetTypeName());
        tv5.setText(StringUtil.isEmpty(info.getMap().getAssetClassName()) ? "" : info.getMap().getAssetClassName());
        tv6.setText(StringUtil.isEmpty(info.getAlarmSource()) ? "" : info.getAlarmSource());
        tv7.setText(StringUtil.isEmpty(info.getAlarmGrade()) ? "" : info.getAlarmGrade());
        tv8.setText(StringUtil.isEmpty(info.getCameraFaultType()+"") ? "" : info.getCameraFaultType()+"");
        //tv9.setText(StringUtil.isEmpty(info.getMap().getOrgName()) ? "" : info.getMap().getOrgName());
        tv10.setText(StringUtil.isEmpty(info.getMap().getAssetName()) ? "" : info.getMap().getAssetName());
        tv11.setText(StringUtil.isEmpty(info.getMap().getPositionCode()) ? "" : info.getMap().getPositionCode());
        tv12.setText(StringUtil.isEmpty(info.getMap().getManageIp()) ? "" : info.getMap().getManageIp());
        tv13.setText(StringUtil.isEmpty(info.getAlarmTime()) ? "" : StringUtil.dealDateFormat(info.getAlarmTime()));//发生时间
        tv14.setText(StringUtil.isEmpty(info.getAlarmTime()) ? "" : StringUtil.dealDateFormat(info.getAlarmTime()));//派单时间
        //tv15.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//恢复时间
        tv16.setText(StringUtil.isEmpty(info.getMap().getTimeoutTime()) ? "" : info.getMap().getTimeoutTime());//故障时长
        tv17.setText(StringUtil.isEmpty(info.getMap().getHandlePersionName()) ? "" : info.getMap().getHandlePersionName());//工单负责人
        tv18.setText(StringUtil.isEmpty(info.getHandleTel()) ? "" : info.getHandleTel());//联系电话
        //tv19.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//协同处理人
        //tv20.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//联系电话
        //tv21.setText(StringUtil.isEmpty(info.getClosedLoopStatus()) ? "" : info.getClosedLoopStatus());//闭环状态
        //tv22.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//超时闭环时间
        tv23.setText(StringUtil.isEmpty(info.getAlarmRemark()) ? "" : info.getAlarmRemark());//告警发生原因



    }

    private void initClick() {
        tvE1.setText(String.valueOf(maxScore));
        tvE2.setText(String.valueOf(maxScore));
        tvE3.setText(String.valueOf(maxScore));
        tvE4.setText(String.valueOf(maxScore));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_agree:
                        type = 1;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llEvaluatePart.setVisibility(View.VISIBLE);
                                etRefuse.setVisibility(View.GONE);
                            }
                        });
                        break;
                    case R.id.rb_refuse:
                        type = 2;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llEvaluatePart.setVisibility(View.GONE);
                                etRefuse.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                }
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCheck();
            }
        });

    }


    private void doCheck() {
        if (type == 0) {
            ToastUtil.toast("请选择同意或者拒绝");
            return;
        }
        if (type == 2 && StringUtil.isEmpty(etRefuse.getText().toString().trim())) {
            ToastUtil.toast("请填写拒绝理由");
            return;
        }
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("verifyStatus", type == 1 ? "PASS" : "REJECT");
        if (type == 1) {
            hashMap.put("serviceRating", tvE1.getText().toString());
            hashMap.put("serviceRating2", tvE2.getText().toString());
            hashMap.put("serviceRating3", tvE3.getText().toString());
            hashMap.put("serviceRating4", tvE4.getText().toString());
        } else {
            hashMap.put("verifyRemark", etRefuse.getText().toString().trim());

        }
        ApiClient.requestNetPost(this, AppConfig.OpFaultInfoCheck, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_FAULT_LIST));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
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

    @OnClick({R.id.iv_up1, R.id.iv_down1, R.id.iv_up2, R.id.iv_down2, R.id.iv_up3, R.id.iv_down3, R.id.iv_up4, R.id.Iv_down4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_up1:
                checkScore(true, tvE1);
                break;
            case R.id.iv_down1:
                checkScore(false, tvE1);
                break;
            case R.id.iv_up2:
                checkScore(true, tvE2);
                break;
            case R.id.iv_down2:
                checkScore(false, tvE2);
                break;
            case R.id.iv_up3:
                checkScore(true, tvE3);
                break;
            case R.id.iv_down3:
                checkScore(false, tvE3);
                break;
            case R.id.iv_up4:
                checkScore(true, tvE4);
                break;
            case R.id.Iv_down4:
                checkScore(false, tvE4);
                break;
        }
    }


    private void checkScore(boolean plus, TextView v) {
        int scoreGet = Integer.parseInt(v.getText().toString());
        if (plus) {
            if (scoreGet == maxScore) {
                ToastUtil.toast("已经达到最高分，不能再高了！");
                return;
            }
            v.setText(String.valueOf(scoreGet + 1));
        } else {
            if (scoreGet == minScore) {
                ToastUtil.toast("已经达到最低分，不能再低了！");
                return;
            }
            v.setText(String.valueOf(scoreGet - 1));
        }
    }
}
