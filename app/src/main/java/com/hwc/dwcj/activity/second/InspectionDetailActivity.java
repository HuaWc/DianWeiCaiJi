package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.InspectionDetailAdapter;
import com.hwc.dwcj.adapter.second.InspectionResultAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.InspectionDetailDTO;
import com.hwc.dwcj.entity.second.InspectionManageDetailDTO;
import com.hwc.dwcj.entity.second.InspectionResult;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.FullyLinearLayoutManager;
import com.hwc.dwcj.util.RecyclerViewHelper;
import com.zds.base.entity.EventCenter;
import com.zds.base.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.InstrumentedActivity;

public class InspectionDetailActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.all)
    LinearLayout all;

    @BindView(R.id.tv_task_name)
    TextView tv_task_name;
    @BindView(R.id.tv_inspection_type)
    TextView tv_inspection_type;
    @BindView(R.id.tv_emergency_degree)
    TextView tv_emergency_degree;
    @BindView(R.id.tv_inspection_people)
    TextView tv_inspection_people;
    @BindView(R.id.tv_object)
    TextView tv_object;
    @BindView(R.id.tv_inspection_status)
    TextView tv_inspection_status;
    @BindView(R.id.tv_timeout_status)
    TextView tv_timeout_status;
    @BindView(R.id.tv_task_start_time)
    TextView tv_task_start_time;
    @BindView(R.id.tv_task_end_time)
    TextView tv_task_end_time;
    @BindView(R.id.tv_inspection_sum)
    TextView tv_inspection_sum;
    @BindView(R.id.tv_inspection_already)
    TextView tv_inspection_already;
    @BindView(R.id.tv_inspection_never)
    TextView tv_inspection_never;
    @BindView(R.id.tv_task_start_progress)
    TextView tv_task_start_progress;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String id = "";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_inspection_detail);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
        }

        getData();
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

    public void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        ApiClient.requestNetPost(InspectionDetailActivity.this, AppConfig.inspectionManageDetail, "正在加载", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                InspectionManageDetailDTO inspectionManageDetailDTO = new Gson().fromJson(json, InspectionManageDetailDTO.class);

                tv_task_name.setText(inspectionManageDetailDTO.taskName);
                tv_inspection_type.setText(inspectionManageDetailDTO.map.taskType);
                tv_emergency_degree.setText(inspectionManageDetailDTO.map.urgentType);
                tv_inspection_people.setText(inspectionManageDetailDTO.inspectionName);
                tv_object.setText(inspectionManageDetailDTO.map.groupName);
                tv_inspection_status.setText(inspectionManageDetailDTO.map.inspectionStatus);
                tv_timeout_status.setText(inspectionManageDetailDTO.map.overtimeStatus);
                tv_task_start_time.setText(StringUtil.dealDateFormat(inspectionManageDetailDTO.taskTimeStart));
                tv_task_end_time.setText(inspectionManageDetailDTO.taskTimeEnd);
                tv_inspection_sum.setText(inspectionManageDetailDTO.inspectionSum);
                tv_inspection_already.setText(inspectionManageDetailDTO.map.inspectionSumNow);
                tv_inspection_never.setText(inspectionManageDetailDTO.map.inspectionOver);
                tv_task_start_progress.setText(inspectionManageDetailDTO.map.inspectionProgress);

//                FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(InspectionDetailActivity.this);
                LinearLayoutManager fullyLinearLayoutManager = new LinearLayoutManager(InspectionDetailActivity.this);
                recyclerView.setLayoutManager(fullyLinearLayoutManager);
                InspectionDetailAdapter adapter = new InspectionDetailAdapter(inspectionManageDetailDTO.map.opInspectionGroupList);
                recyclerView.setAdapter(adapter);
                RecyclerViewHelper.recyclerviewAndScrollView(recyclerView);

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_result})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_result:
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                toTheActivity(InspectionResultActivity.class, bundle);
                break;
        }
    }
}
