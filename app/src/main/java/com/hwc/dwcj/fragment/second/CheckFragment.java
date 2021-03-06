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
import com.hwc.dwcj.activity.second.CheckDetailActivity;
import com.hwc.dwcj.activity.second.CheckDetailAdminActivity;
import com.hwc.dwcj.activity.second.StartCheckActivity;
import com.hwc.dwcj.adapter.second.CheckUserAdapter;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.second.CheckUser;
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

public class CheckFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_hczt)
    TextView tvHczt;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;

    private List<CheckUser> mList;
    private CheckUserAdapter adapter;
    private int page = 1;
    private int pageSize = 10;
    private String status = "";
    //0???????????????1??????????????? 2:???????????? 3:???????????????
    private List<String> selectOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, container, false);
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
        selectOptions.add("??????");
        selectOptions.add("?????????");
        selectOptions.add("?????????");
        selectOptions.add("????????????");
        selectOptions.add("????????????");
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
        tvHczt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                hideSoftKeyboard3();
                if (selectOptions == null || selectOptions.size() == 0) {
                    ToastUtil.toast("??????????????????");
                    return;
                }
                PickerViewUtils.selectOptions(getContext(), "????????????", selectOptions, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvHczt.setText(selectOptions.get(options1));
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
        adapter = new CheckUserAdapter(mList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String myId = MyApplication.getInstance().getUserInfo().getId();
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                switch (view.getId()) {
                    case R.id.tv_look:
                        //??????
                        if (!StringUtil.isEmpty(mList.get(position).getVerPeopleIds()) && Arrays.asList(mList.get(position).getVerPeopleIds().split(",")).contains(myId)) {
                            //??????????????????
                            toTheActivity(StartCheckActivity.class, bundle);
                        } else {
                            ToastUtil.toast("????????????????????????????????????????????????");
                        }
                        break;
                    case R.id.tv_check:
                        //??????
                        if (!StringUtil.isEmpty(mList.get(position).getAddId()) && mList.get(position).getAddId().equals(myId)) {
                            //??????????????????
                            toTheActivity(CheckDetailAdminActivity.class, bundle);
                        } else {
                            ToastUtil.toast("????????????????????????????????????????????????");
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
                toTheActivity(CheckDetailActivity.class, bundle);

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
        ApiClient.requestNetGet(getContext(), AppConfig.OpVerificationTaskList, "?????????", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<CheckUser> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), CheckUser.class);
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
            case EventUtil.REFRESH_CHECK_LIST:
                getData(false);
                break;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
