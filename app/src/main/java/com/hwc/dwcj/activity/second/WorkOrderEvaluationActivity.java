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
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.interfaces.PickerViewSelectOptionsResult;
import com.hwc.dwcj.util.PickerViewUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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

public class WorkOrderEvaluationActivity extends BaseActivity {


    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;

    private int page = 1;
    private int pageSize = 10;

    List<WorkOrderEvaluationInfo> mList;
    WorkOrderEvaluationAdapter mAdapter;

    private String timeSlot = "";
    private List<String> options;

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
        initOptions();
    }

    private void initOptions() {
        options = new ArrayList<>();
        options.add("近一周");
        options.add("近一个月");
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        mAdapter = new WorkOrderEvaluationAdapter(mList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", mList.get(position).getHandlePersionId());
                toTheActivity(PersonalEvaluationActivity.class, bundle);
            }
        });
        getData(false);
    }

    private void getData(boolean more) {
        if (more) {
            page++;
        } else {
            page = 1;
            mList.clear();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        if (!StringUtil.isEmpty(timeSlot)) {
            params.put("timeSlot", timeSlot);
        }

        if (!StringUtil.isEmpty(etSearch.getText().toString().trim())) {
            params.put("keyWord", etSearch.getText().toString().trim());
        }
        ApiClient.requestNetGet(this, AppConfig.faultEvaluationStatistics, "查询中", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<WorkOrderEvaluationInfo> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), WorkOrderEvaluationInfo.class);
                if (list != null && list.size() != 0) {
                    mList.addAll(list);
                } else {
                    if (page > 1) {
                        page--;
                    }
                }
                mAdapter.notifyDataSetChanged();
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(String msg) {
                if (page > 1) {
                    page--;
                }
                ToastUtil.toast(msg);
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();

            }
        });
    }


    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(false);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getData(true);
            }
        });
        ivSs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(false);
            }
        });
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                hideSoftKeyboard3();
                if (options == null || options.size() == 0) {
                    ToastUtil.toast("选项为空，请稍后再试！");
                    return;
                }
                PickerViewUtils.selectOptions(WorkOrderEvaluationActivity.this, "查询时间", options, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        timeSlot = options1 + "";
                        tvTime.setText(options.get(options1));
                        getData(false);
                    }
                });
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
