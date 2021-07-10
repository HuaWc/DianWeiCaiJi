package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.InspectionResultAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.InspectionResult;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InspectionResultActivity extends BaseActivity implements TextWatcher {
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

    private List<InspectionResult.InspectionResultItemDTO> mList;
    private InspectionResultAdapter adapter;

    private int pageIndex = 1;
    private int pageSize = 20;
    private String id;
    private String searchTxt = "";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_inspection_result);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initAdapter();
        initClick();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            id = bundle.getString("id");
        }

        etSearch.addTextChangedListener(this);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 1;
                refreshlayout.setEnableLoadmore(true);
                getData();
            }
        });

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getData();
            }
        });

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

    private void initAdapter() {
        mList = new ArrayList<>();
        adapter = new InspectionResultAdapter(mList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id",mList.get(position).taskSubId);
                toTheActivity(InspectionResultDetailActivity.class,bundle);
            }
        });
    }

    private void getData() {
        Map<String,Object> params = new HashMap<>();
        params.put("pageNum",pageIndex);
        params.put("pageSize",pageSize);
        params.put("taskId",id);
        ApiClient.requestNetPost(InspectionResultActivity.this, AppConfig.inspectionResultData, "正在加载", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (pageIndex == 1){
                    if (refreshLayout.isRefreshing()){
                        refreshLayout.finishRefresh();
                    }
                }else {
                    if (refreshLayout.isLoading()){
                        refreshLayout.finishLoadmore();
                    }
                }

                InspectionResult inspectionResult = new Gson().fromJson(json, InspectionResult.class);
                mList.addAll(inspectionResult.list);
                adapter.notifyDataSetChanged();

                if (pageIndex < inspectionResult.pages){
                    pageIndex++;
                }else {
                    refreshLayout.setEnableLoadmore(false);
                }
            }

            @Override
            public void onFailure(String msg) {

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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        searchTxt = charSequence.toString();
        if (TextUtils.isEmpty(etSearch.getText().toString())){
            mList.clear();
            getData();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @OnClick(R.id.iv_ss)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_ss:
                if (TextUtils.isEmpty(etSearch.getText().toString().trim())){
                    ToastUtil.toast("搜索字符串不能为空");
                    return;
                }
                mList.clear();
                pageIndex = 1;
                getData();
                break;
        }
    }
}
