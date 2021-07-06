package com.hwc.dwcj.fragment.second;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.second.GuaranteeDetailActivity;
import com.hwc.dwcj.activity.second.GuaranteeDetailAdminActivity;
import com.hwc.dwcj.activity.second.StartGuaranteeActivity;
import com.hwc.dwcj.adapter.second.GuaranteeUserAdapter;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.DWItem;
import com.hwc.dwcj.entity.second.GuaranteeUser;
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

public class GuaranteeFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_bzzt)
    TextView tvBzzt;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;

    private List<GuaranteeUser> mList;
    private GuaranteeUserAdapter adapter;
    private int page = 1;
    private int pageSize = 10;
    private String status = "";
    //0：待反馈；1：待审核； 2:审核通过 3:审核驳回。
    private List<String> selectOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guarantee, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getContext().getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();
        initSelectData();
    }

    private void initSelectData() {
        selectOptions = new ArrayList<>();
        selectOptions.add("全部");
        selectOptions.add("待反馈");
        selectOptions.add("待审核");
        selectOptions.add("审核通过");
        selectOptions.add("审核驳回");
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
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
        tvBzzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectOptions == null || selectOptions.size() == 0) {
                    ToastUtil.toast("条件加载中！");
                    return;
                }
                PickerViewUtils.selectOptions(getContext(), "保障状态", selectOptions, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvBzzt.setText(selectOptions.get(options1));
                        if (options1 == 0) {
                            status = "";
                        } else {
                            status = String.valueOf(options1 - 1);
                        }
                        getData(false);
                    }
                });
            }
        });
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        adapter = new GuaranteeUserAdapter(mList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String myId = MyApplication.getInstance().getUserInfo().getId();
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                switch (view.getId()) {
                    case R.id.tv_look:
                        //反馈
                        if (!StringUtil.isEmpty(mList.get(position).getSryPeopleIds()) && Arrays.asList(mList.get(position).getSryPeopleIds().split(",")).contains(myId)) {
                            //进入反馈页面
                            toTheActivity(StartGuaranteeActivity.class, bundle);
                        } else {
                            ToastUtil.toast("您还不是该任务的保障人，无法反馈");
                        }
                        break;
                    case R.id.tv_check:
                        //审核
                        if (!StringUtil.isEmpty(mList.get(position).getAddId()) && mList.get(position).getAddId().equals(myId)) {
                            //进入审核页面
                            toTheActivity(GuaranteeDetailAdminActivity.class, bundle);
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
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                toTheActivity(GuaranteeDetailActivity.class, bundle);
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
        if (!StringUtil.isEmpty(status)) {
            params.put("checkStatus", status);
        }
        if (!StringUtil.isEmpty(etSearch.getText().toString().trim())) {
            params.put("taskName", etSearch.getText().toString().trim());
        }
        ApiClient.requestNetGet(getContext(), AppConfig.OpSecurityTaskList, "查询中", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<GuaranteeUser> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), GuaranteeUser.class);
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

    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil.REFRESH_GUARANTEE_LIST:
                getData(false);
                break;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
