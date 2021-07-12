package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.InspectionResultDetailAdapter;
import com.hwc.dwcj.adapter.second.InspectionResultDetailAlarmAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.InspectionResultDetailDTO;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.FullyLinearLayoutManager;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InspectionResultDetailActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.all)
    LinearLayout all;

    @BindView(R.id.tv_organization_name)
    TextView tv_organization_name;
    @BindView(R.id.tv_dev_name)
    TextView tv_dev_name;
    @BindView(R.id.tv_ip)
    TextView tv_ip;
    @BindView(R.id.tv_position_code)
    TextView tv_position_code;
    @BindView(R.id.tv_memberbarCode)
    TextView tv_memberbarCode;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_inspection_people)
    TextView tv_inspection_people;
    @BindView(R.id.tv_inspection_time)
    TextView tv_inspection_time;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_other_remark)
    TextView tv_other_remark;

    @BindView(R.id.rv_inspection_task)
    RecyclerView rv_inspection_task;
    @BindView(R.id.rv_inspection_alarm)
    RecyclerView rv_inspection_alarm;

    private String id;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_inspection_result_detail);
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

    public void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("taskSubId", id);
        ApiClient.requestNetPost(InspectionResultDetailActivity.this, AppConfig.inspectionResultDetail, "正在加载", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                InspectionResultDetailDTO inspectionResultDetailDTO = new Gson().fromJson(json, InspectionResultDetailDTO.class);

                tv_organization_name.setText(inspectionResultDetailDTO.orgName);
                tv_dev_name.setText(inspectionResultDetailDTO.devName);
                tv_ip.setText(inspectionResultDetailDTO.ip);
                tv_position_code.setText(inspectionResultDetailDTO.positionCode);
                tv_memberbarCode.setText(inspectionResultDetailDTO.memberbarCode);
                tv_type.setText(inspectionResultDetailDTO.cameraType);
                tv_inspection_people.setText(inspectionResultDetailDTO.inspectionName);
                tv_inspection_time.setText(inspectionResultDetailDTO.inspectionTime);
                tv_address.setText(inspectionResultDetailDTO.address);
                if (TextUtils.isEmpty(inspectionResultDetailDTO.otherQuestion)) {
                    tv_other_remark.setText("无");
                } else {
                    tv_other_remark.setText(inspectionResultDetailDTO.otherQuestion);
                }

                rv_inspection_task.setLayoutManager(new LinearLayoutManager(InspectionResultDetailActivity.this));

                rv_inspection_task.setAdapter(new InspectionResultDetailAdapter(inspectionResultDetailDTO.itemList));

//                rv_inspection_alarm.setLayoutManager(new FullyLinearLayoutManager(InspectionResultDetailActivity.this));
//
//                rv_inspection_alarm.setAdapter(new InspectionResultDetailAlarmAdapter(inspectionResultDetailDTO.alarmList));

                rv_inspection_alarm.setLayoutManager(new LinearLayoutManager(InspectionResultDetailActivity.this));

                rv_inspection_alarm.setAdapter(new InspectionResultDetailAlarmAdapter(inspectionResultDetailDTO.alarmList));
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
}
