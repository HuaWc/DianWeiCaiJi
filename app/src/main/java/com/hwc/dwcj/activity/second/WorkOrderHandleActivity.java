package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.DictInfo;
import com.hwc.dwcj.entity.second.WorkOrderUser;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.GetDictDataHttp;
import com.hwc.dwcj.http.ResultListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 工单处理 界面
 */
public class WorkOrderHandleActivity extends BaseActivity {
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
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.rb_fix)
    RadioButton rbFix;
    @BindView(R.id.rb_change)
    RadioButton rbChange;
    @BindView(R.id.rb_other)
    RadioButton rbOther;
    @BindView(R.id.rb_problem)
    RadioButton rbProblem;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.ll_change_part)
    LinearLayout llChangePart;
    @BindView(R.id.ll_clr)
    LinearLayout llClr;
    @BindView(R.id.ll_pzrz)
    LinearLayout llPzrz;

    private String id;
    private WorkOrderUser info;
    private int type = 0;
    private List<DictInfo> methodList;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_handle);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();
        getData();
        rbFix.setSelected(true);
    }

    private void initAdapter() {
        methodList = new ArrayList<>();
        getMethodData();
    }

    private void getMethodData() {
        GetDictDataHttp.getDictData(this, "OP_FAULT_METHOD", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    methodList.addAll(list);
                }
            }
        });
    }

    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        ApiClient.requestNetGet(this, AppConfig.OpFaultInfoInfo, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                info = FastJsonUtil.getObject(json, WorkOrderUser.class);
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
        tv3.setText(StringUtil.isEmpty(info.getMap().getAssetNature()) ? "" : info.getMap().getAssetNature());
        tv4.setText(StringUtil.isEmpty(info.getMap().getAssetType()) ? "" : info.getMap().getAssetType());
        tv5.setText(StringUtil.isEmpty(info.getMap().getAssetClass()) ? "" : info.getMap().getAssetClass());
        tv6.setText(StringUtil.isEmpty(info.getAlarmSource()) ? "" : info.getAlarmSource());
        tv7.setText(StringUtil.isEmpty(info.getAlarmGrade()) ? "" : info.getAlarmGrade());
        tv8.setText(StringUtil.isEmpty(info.getFaultType()) ? "" : info.getFaultType());
        tv9.setText(StringUtil.isEmpty(info.getMap().getOrgName()) ? "" : info.getMap().getOrgName());
        tv10.setText(StringUtil.isEmpty(info.getMap().getAssetName()) ? "" : info.getMap().getAssetName());
        tv11.setText(StringUtil.isEmpty(info.getMap().getAssetCode()) ? "" : info.getMap().getAssetCode());
        tv12.setText(StringUtil.isEmpty(info.getMap().getManageIp()) ? "" : info.getMap().getManageIp());
        tv13.setText(StringUtil.isEmpty(info.getAlarmTime()) ? "" : StringUtil.dealDateFormat(info.getAlarmTime()));//发生时间
        tv14.setText(StringUtil.isEmpty(info.getAlarmTime()) ? "" : StringUtil.dealDateFormat(info.getAlarmTime()));//派单时间
        //tv15.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//恢复时间
        //tv16.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//故障时长
        //tv17.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//工单负责人
        //tv18.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//联系电话
        //tv19.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//协同处理人
        //tv20.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//联系电话
        tv21.setText(StringUtil.isEmpty(info.getClosedLoopStatus()) ? "" : info.getClosedLoopStatus());//闭环状态
        //tv22.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//超时闭环时间
        tv23.setText(StringUtil.isEmpty(info.getAlarmRemark()) ? "" : info.getAlarmRemark());//告警发生原因


    }

    private void initClick() {
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
                    case R.id.rb_fix:
                        type = 0;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llChangePart.setVisibility(View.GONE);
                                llClr.setVisibility(View.GONE);
                                llPzrz.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                    case R.id.rb_other:
                        type = 2;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llChangePart.setVisibility(View.GONE);
                                llClr.setVisibility(View.GONE);
                                llPzrz.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                    case R.id.rb_problem:
                        type = 3;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llChangePart.setVisibility(View.GONE);
                                llClr.setVisibility(View.GONE);
                                llPzrz.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                    case R.id.rb_change:
                        type = 1;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llChangePart.setVisibility(View.VISIBLE);
                                llClr.setVisibility(View.VISIBLE);
                                llPzrz.setVisibility(View.GONE);
                            }
                        });
                }
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    private void submit() {
            Map<String,Object> hashMap = new HashMap<>();
            hashMap.put("id",id);
            hashMap.put("handleMethod",methodList.get(type).getDataValue());
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
