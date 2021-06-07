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
import com.hwc.dwcj.adapter.second.PersonalEvaluationAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.PersonalEvaluationInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zds.base.entity.EventCenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalEvaluationActivity extends BaseActivity {
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
    @BindView(R.id.tc_ssjg)
    TextView tcSsjg;
    @BindView(R.id.tv_fz)
    TextView tvFz;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;

    private PersonalEvaluationAdapter mAdapter;
    private List<PersonalEvaluationInfo> mList;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_personal_evaluation_statistics);
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
        mAdapter = new PersonalEvaluationAdapter(mList);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        getData();
    }

    private void getData() {
        for (int i = 0; i < 10; i++) {
            PersonalEvaluationInfo info = new PersonalEvaluationInfo();
            info.setName("长宁路摄像机离线（123.32.3.45）");
            mList.add(info);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initClick() {

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
