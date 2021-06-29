package com.hwc.dwcj.fragment.second;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.second.ChangeDetailActivity;
import com.hwc.dwcj.activity.second.ChangeDetailAdminActivity;
import com.hwc.dwcj.activity.second.ChangeResultDetailActivity;
import com.hwc.dwcj.activity.second.CheckDetailAdminActivity;
import com.hwc.dwcj.activity.second.StartCheckActivity;
import com.hwc.dwcj.adapter.second.ChangeUserAdapter;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.DictInfo;
import com.hwc.dwcj.entity.second.ChangeUser;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.interfaces.PickerViewSelectOptionsResult;
import com.hwc.dwcj.util.EventUtil;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChangeFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_bgzt)
    TextView tvBgzt;
    @BindView(R.id.tc_yxj)
    TextView tcYxj;
    @BindView(R.id.tv_cszt)
    TextView tvCszt;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;


    private List<ChangeUser> mList;
    private ChangeUserAdapter adapter;
    private int page = 1;
    private int pageSize = 10;


    private List<String> statusOptions;
    private List<String> priorityOptions;
    private List<String> overTimeOptions;

    private List<DictInfo> priorityList;
    private List<DictInfo> overtimeList;

    private String statusStr = "";
    private String priorityStr = "";
    private String overtimeStr = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getContext().getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();
    }

    private void initClick() {
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
        tvBgzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //变更状态
                if (statusOptions == null || statusOptions.size() == 0) {
                    ToastUtil.toast("数据为空，请稍后重试！");
                    return;
                }
                PickerViewUtils.selectOptions(getContext(), "变更状态", statusOptions, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvBgzt.setText(statusOptions.get(options1));
                        if (options1 == 0) {
                            statusStr = "";
                        } else {
                            statusStr = String.valueOf(options1 - 1);
                        }
                        getData(false);
                    }
                });

            }
        });
        tcYxj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //优先级
                if (priorityOptions == null || priorityOptions.size() == 0) {
                    ToastUtil.toast("数据为空，请稍后重试！");
                    return;
                }
                PickerViewUtils.selectOptions(getContext(), "优先级", priorityOptions, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tcYxj.setText(priorityOptions.get(options1));
                        if (options1 == 0) {
                            priorityStr = "";
                        } else {
                            priorityStr = priorityList.get(options1 - 1).getDataValue();
                        }
                        getData(false);
                    }
                });
            }
        });
        tvCszt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //超时状态
                if (overTimeOptions == null || overTimeOptions.size() == 0) {
                    ToastUtil.toast("数据为空，请稍后重试！");
                    return;
                }
                PickerViewUtils.selectOptions(getContext(), "超时状态", overTimeOptions, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvCszt.setText(overTimeOptions.get(options1));
                        if (options1 == 0) {
                            overtimeStr = "";
                        } else {
                            overtimeStr = overtimeList.get(options1 - 1).getDataValue();
                        }
                        getData(false);
                    }
                });
            }
        });

    }

    private void initAdapter() {
        statusOptions = new ArrayList<>();
        priorityOptions = new ArrayList<>();
        overTimeOptions = new ArrayList<>();
        priorityList = new ArrayList<>();
        overtimeList = new ArrayList<>();


        mList = new ArrayList<>();
        adapter = new ChangeUserAdapter(mList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String myId = MyApplication.getInstance().getUserInfo().getId();
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                switch (view.getId()) {
                    case R.id.tv_change:
                        //开始变更
                        if (!StringUtil.isEmpty(mList.get(position).getChangePeopleIds()) && Arrays.asList(mList.get(position).getChangePeopleIds().split(",")).contains(myId)) {
                            //进入反馈页面
                            toTheActivity(ChangeResultDetailActivity.class, bundle);
                        } else {
                            ToastUtil.toast("您还不是该任务的变更人，无法开始变更");
                        }
                        break;
                    case R.id.tv_check:
                        //确认审核
                        if (!StringUtil.isEmpty(mList.get(position).getAddId()) && mList.get(position).getAddId().equals(myId)) {
                            //进入审核页面
                            toTheActivity(ChangeDetailAdminActivity.class, bundle);
                        } else {
                            ToastUtil.toast("您还不是该任务的创建人，无法审核");
                        }
                        break;
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //详情
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                toTheActivity(ChangeDetailActivity.class, bundle);

            }
        });
        getData(false);
        getChangeStatus();
        getChangePriority();
        getChangeOvertime();
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
        if (!StringUtil.isEmpty(statusStr)) {
            params.put("checkStatus", statusStr);
        }
        if (!StringUtil.isEmpty(priorityStr)) {
            params.put("priority", priorityStr);
        }
        if (!StringUtil.isEmpty(overtimeStr)) {
            params.put("taskStatus", overtimeStr);
        }
        ApiClient.requestNetGet(getContext(), AppConfig.OpChangeTaskList, "查询中", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<ChangeUser> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), ChangeUser.class);
                if (list != null && list.size() != 0) {
                    mList.addAll(list);
                } else {
                    if (page > 1) {
                        page--;
                    }
                }
                adapter.notifyDataSetChanged();
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


    private void getChangeStatus() {
        statusOptions.add("全部");
        statusOptions.add("待变更");
        statusOptions.add("待审核");
        statusOptions.add("审核通过");
        statusOptions.add("审核驳回");

    }


    private void getChangePriority() {

        Map<String, Object> params = new HashMap<>();
        params.put("dataTypeCode", "OP_CHANGE_PRIORITY");
        ApiClient.requestNetPost(getContext(), AppConfig.getDataTypeList, "", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<DictInfo> list = FastJsonUtil.getList(json, DictInfo.class);
                if (list != null) {
                    priorityList.addAll(list);
                    priorityOptions.add("全部");
                    for (DictInfo d : list) {
                        priorityOptions.add(d.getDataName());
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void getChangeOvertime() {
        Map<String, Object> params = new HashMap<>();
        params.put("dataTypeCode", "OP_CHANGE_STATUS");
        ApiClient.requestNetPost(getContext(), AppConfig.getDataTypeList, "", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<DictInfo> list = FastJsonUtil.getList(json, DictInfo.class);
                if (list != null) {
                    overtimeList.addAll(list);
                    overTimeOptions.add("全部");
                    for (DictInfo d : list) {
                        overTimeOptions.add(d.getDataName());
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil.REFRESH_CHANGE_LIST:
                getData(false);
                break;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
