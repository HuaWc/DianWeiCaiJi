package com.hwc.dwcj.fragment;

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
import com.hwc.dwcj.adapter.DACityAdapter;
import com.hwc.dwcj.adapter.DAItemAdapter;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.entity.DACity;
import com.hwc.dwcj.entity.DAItem;
import com.hwc.dwcj.entity.DAPcs;
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
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 档案
 *
 * @author Administrator
 */
public class DossierFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.rv_shi)
    RecyclerView rvShi;
    @BindView(R.id.tv_select_pcs)
    TextView tvSelectPcs;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.bar)
    View bar;

    private List<DAItem> mList;
    private DAItemAdapter daItemAdapter;
    private List<DACity> cities;
    private DACityAdapter daCityAdapter;
    private List<DAPcs> pcs;
    private List<String> pcsString;


    String areaCode = "";
    String orgId = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dossier, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initAdapter();
        initClick();
    }


    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {
    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        daItemAdapter = new DAItemAdapter(mList);
        daItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(new EventCenter(EventUtil.OPEN_DA_NEXT1, mList.get(position).getPositionCode()));
            }
        });
        rv.setAdapter(daItemAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        cities = new ArrayList<>();
        daCityAdapter = new DACityAdapter(cities);
        daCityAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (cities.get(position).isSelected()) {
                    return;
                }
                for (DACity item : cities) {
                    if (item.isSelected()) {
                        item.setSelected(false);
                        break;
                    }
                }
                cities.get(position).setSelected(true);
                daCityAdapter.notifyDataSetChanged();
                if (cities.get(position).getDataName().equals("全部")) {
                    //选中的全部
                    //收缩派出所选择框
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSelectPcs.setVisibility(View.GONE);
                            tvSelectPcs.setText("");
                        }
                    });
                    //刷新数据
                    areaCode = "";
                    orgId = "";
                } else {
                    //选中地区
                    //显示派出所的选择框
                    if (tvSelectPcs.getVisibility() == View.GONE) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvSelectPcs.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    //拿派出所数据，刷新列表（清除派出所的id）
                    tvSelectPcs.setText("");
                    orgId = "";
                    areaCode = cities.get(position).getDataValue();
                    getPcsData(cities.get(position).getDataValue());
                }
                getData(false);
            }
        });
        rvShi.setAdapter(daCityAdapter);
        rvShi.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        pcs = new ArrayList<>();
        pcsString = new ArrayList<>();
        getData(false);
        getCityData();
    }

    private void initClick() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSelectPcs.setVisibility(View.GONE);
                        tvSelectPcs.setText("");
                    }
                });
                //刷新数据
                areaCode = "";
                orgId = "";
                getData(false);
                getCityData();
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
            public void onClick(View v) {
/*                if (StringUtil.isEmpty(etSearch.getText().toString().trim())) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "请输入内容后再搜索", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                } else {
                    getData(false);
                }*/
                getData(false);
            }
        });
        tvSelectPcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView();
            }
        });
    }

    private int page = 1;
    private int pageSize = 10;

    private void getData(boolean more) {
        if (more) {
            page++;
        } else {
            page = 1;
            mList.clear();
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        if (!StringUtil.isEmpty(areaCode)) {
            params.put("areaCode", areaCode);
        }
        if (!StringUtil.isEmpty(orgId)) {
            params.put("orgId", orgId);
        }
        if (!StringUtil.isEmpty(etSearch.getText().toString().trim())) {
            params.put("keywords", etSearch.getText().toString().trim());
        }
        ApiClient.requestNetGet(getContext(), AppConfig.filePage, "查询中", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<DAItem> daItems = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), DAItem.class);
                if (daItems != null && daItems.size() != 0) {
                    mList.addAll(daItems);
                } else {
                    if (page > 1) {
                        page--;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext, "暂无更多数据", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
/*                int total = FastJsonUtil.getInt(json, "total");
                if (mList.size() < total) {
                    //总数小于total
                    if (daItems != null && daItems.size() != 0) {
                        mList.addAll(daItems);
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext, "暂无更多数据", Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (page > 1) {
                        page--;
                    }
                }*/
                daItemAdapter.notifyDataSetChanged();
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(String msg) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(mContext, "请求失败" + msg, Toast.LENGTH_SHORT).show();
                        refreshLayout.finishLoadmore();
                        refreshLayout.finishRefresh();

                    }
                });

            }
        });

    }

    private void getCityData() {
        cities.clear();
        Map<String, Object> hashMap = new HashMap();
        ApiClient.requestNetGet(getContext(), AppConfig.selectArea, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<DACity> list = FastJsonUtil.getList(json, DACity.class);
                if (list != null && list.size() != 0) {
                    DACity d = new DACity();
                    d.setSelected(true);
                    d.setDataValue("");
                    d.setDataName("全部");
                    cities.add(d);
                    cities.addAll(list);
                    daCityAdapter.notifyDataSetChanged();

                } else {
                    llSelect.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(String msg) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(mContext, "请求辖区失败：" + msg, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void getPcsData(String id) {
        pcs.clear();
        pcsString.clear();
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("areaCode", id);
        ApiClient.requestNetGet(getContext(), AppConfig.pcsList, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<DAPcs> list = FastJsonUtil.getList(json, DAPcs.class);
                if (list != null && list.size() != 0) {
                    pcs.addAll(list);
                    //装配派出所数据
                    for (DAPcs d : list) {
                        pcsString.add(d.getOrgName());
                    }
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void showPickerView() {
        if (pcsString.size() == 0) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "暂时未获得派出所信息，请稍后再试", Toast.LENGTH_LONG).show();
                }
            });
            return;
        }
        PickerViewUtils.selectOptions(getContext(), "选择派出所", pcsString, null, null, new PickerViewSelectOptionsResult() {
            @Override
            public void getOptionsResult(int options1, int options2, int options3) {
                orgId = String.valueOf(pcs.get(options1).getId());
                tvSelectPcs.setText(pcs.get(options1).getOrgName());
                //刷新数据
                getData(false);
            }
        });
    }


}
