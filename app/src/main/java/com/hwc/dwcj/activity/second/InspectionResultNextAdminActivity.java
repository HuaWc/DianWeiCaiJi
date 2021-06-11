package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.InspectionResultAdapter;
import com.hwc.dwcj.adapter.second.InspectionResultAdminNextAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.InspectionResult;
import com.hwc.dwcj.entity.second.InspectionResultAdminNext;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zds.base.entity.EventCenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InspectionResultNextAdminActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;

    private List<InspectionResultAdminNext> mList;
    private InspectionResultAdminNextAdapter adapter;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_inspection_result_next_admin);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initAdapter();
        initClick();
    }
    private void initClick(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        adapter = new InspectionResultAdminNextAdapter(mList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        getData();
    }

    private void getData() {
        for (int i = 0; i < 10; i++) {
            InspectionResultAdminNext inspectionResultAdminNext = new InspectionResultAdminNext();
            inspectionResultAdminNext.setName("金安分局机房");
            mList.add(inspectionResultAdminNext);
        }
        adapter.notifyDataSetChanged();
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
