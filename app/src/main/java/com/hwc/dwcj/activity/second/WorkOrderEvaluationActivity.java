package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.WorkOrderEvaluationAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.WorkOrderEvaluationInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zds.base.entity.EventCenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkOrderEvaluationActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_clr)
    TextView tvClr;
    @BindView(R.id.tc_fwlx)
    TextView tcFwlx;
    @BindView(R.id.tv_ssxd)
    TextView tvSsxd;
    @BindView(R.id.tv_ljfz)
    TextView tvLjfz;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;

    List<WorkOrderEvaluationInfo> mList;
    WorkOrderEvaluationAdapter mAdapter;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_evaluation_statistics);

    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initAdapter();
        initClick();
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        mAdapter = new WorkOrderEvaluationAdapter(mList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                toTheActivity(PersonalEvaluationActivity.class);
            }
        });
        getData();
    }

    private void getData() {
        for (int i = 0; i < 10; i++) {
            WorkOrderEvaluationInfo workOrderEvaluationInfo = new WorkOrderEvaluationInfo();
            workOrderEvaluationInfo.setName("张三");
            mList.add(workOrderEvaluationInfo);
        }
        mAdapter.notifyDataSetChanged();
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
}
