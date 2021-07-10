package com.hwc.dwcj.activity.second;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.InspectionDetailAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.InspectionManageDetailDTO;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.view.dialog.SimpleDialog;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InspectionDetailAdminActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
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
        setContentView(R.layout.activity_inspection_detail_admin);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            id = extras.getString("id");
        }

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

    public void getData(){
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        ApiClient.requestNetPost(InspectionDetailAdminActivity.this, AppConfig.inspectionManageDetail, "正在加载", params, new ResultListener() {
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
                tv_task_start_time.setText(inspectionManageDetailDTO.taskTimeStart);
                tv_task_end_time.setText(inspectionManageDetailDTO.taskTimeEnd);
                tv_inspection_sum.setText(inspectionManageDetailDTO.inspectionSum);
                tv_inspection_already.setText(inspectionManageDetailDTO.map.inspectionSumNow);
                tv_inspection_never.setText(inspectionManageDetailDTO.map.inspectionOver);
                tv_task_start_progress.setText(inspectionManageDetailDTO.map.inspectionProgress);

                LinearLayoutManager fullyLinearLayoutManager = new LinearLayoutManager(InspectionDetailAdminActivity.this);
                recyclerView.setLayoutManager(fullyLinearLayoutManager);
                InspectionDetailAdapter adapter = new InspectionDetailAdapter(inspectionManageDetailDTO.map.opInspectionGroupList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @OnClick({R.id.tv_result,R.id.tv_refuse,R.id.tv_sure})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_result:
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                toTheActivity(InspectionResultActivity.class,bundle);
                break;
            case R.id.tv_refuse:
                EditText editText = new EditText(InspectionDetailAdminActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(InspectionDetailAdminActivity.this);
                builder.setTitle("拒绝原因")
                        .setView(editText)
                        .setNegativeButton("取消", null);

                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Map<String,Object> paramsRefuse = new HashMap<>();
                        paramsRefuse.put("taskId",id);
                        paramsRefuse.put("checkType","2");
                        paramsRefuse.put("approvalOpinion",editText.getText().toString());
                        ApiClient.requestNetGet(InspectionDetailAdminActivity.this, AppConfig.inspectionCheck, "正在加载", paramsRefuse, new ResultListener() {
                            @Override
                            public void onSuccess(String json, String msg) {
                                new SimpleDialog.Builder(InspectionDetailAdminActivity.this)
                                        .setMessage("拒绝成功")
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
                    }
                });
                builder.show();
                break;
            case R.id.tv_sure:
                Map<String,Object> paramsSure = new HashMap<>();
                paramsSure.put("taskId",id);
                paramsSure.put("checkType","1");
                ApiClient.requestNetGet(InspectionDetailAdminActivity.this, AppConfig.inspectionCheck, "正在加载", paramsSure, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        new SimpleDialog.Builder(InspectionDetailAdminActivity.this)
                                .setMessage("同意成功")
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
}
