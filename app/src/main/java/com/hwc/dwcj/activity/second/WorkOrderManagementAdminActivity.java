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
import com.hwc.dwcj.adapter.second.WorkOrderAdminAdapter;
import com.hwc.dwcj.adapter.second.WorkOrderUserAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.WorkOrderAdmin;
import com.hwc.dwcj.entity.second.WorkOrderUser;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zds.base.entity.EventCenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 工单管理 管理员
 */
public class WorkOrderManagementAdminActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_dbsj)
    TextView tvDbsj;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_clzt)
    TextView tvClzt;
    @BindView(R.id.tc_shzt)
    TextView tcShzt;
    @BindView(R.id.tv_ssjg)
    TextView tvSsjg;
    @BindView(R.id.tv_gd)
    TextView tvGd;
    @BindView(R.id.iv_px)
    ImageView ivPx;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;

    List<WorkOrderAdmin> mList;
    WorkOrderAdminAdapter adapter;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_management_admin);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();
    }

    private void initAdapter(){
        mList = new ArrayList<>();
        adapter = new WorkOrderAdminAdapter(mList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        getData();
    }

    private void getData() {
        for (int i = 0; i < 10; i++) {
            WorkOrderAdmin workOrderAdmin = new WorkOrderAdmin();
            workOrderAdmin.setName("XX路XX路摄像机设备离线（123.12.12.1）");
            mList.add(workOrderAdmin);
        }
        adapter.notifyDataSetChanged();
    }



    private void initClick() {
        tvDbsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTheActivity(UpcomingEventListAdminActivity.class);
            }
        });
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
