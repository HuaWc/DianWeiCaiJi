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

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.second.InspectionDetailActivity;
import com.hwc.dwcj.activity.second.InspectionDetailAdminActivity;
import com.hwc.dwcj.activity.second.StartInspectionActivity;
import com.hwc.dwcj.adapter.second.InspectionUserAdapter;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.second.InspectionOption;
import com.hwc.dwcj.entity.second.InspectionUser;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hwc.dwcj.util.PickerViewUtils.lineSpace;

public class InspectionFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_xjzt)
    TextView tvXjzt;
    @BindView(R.id.tv_jjcd)
    TextView tvJjcd;
    @BindView(R.id.tv_cszt)
    TextView tvCszt;
    @BindView(R.id.tv_kssj)
    TextView tvKssj;
    @BindView(R.id.tv_jssj)
    TextView tvJssj;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;


    private List<InspectionUser> mList;
    private InspectionUserAdapter adapter;
    private int page = 1;
    private int pageSize = 10;
    private List<InspectionOption> statusList;
    private List<String> statusStrOptions;
    private List<InspectionOption> urgentList;
    private List<String> urgentStrOptions;
    private List<InspectionOption> overtimeList;
    private List<String> overtimeStrOptions;


    private String statusStr = "";
    private String urgentStr = "";
    private String overtimeStr = "";
    private String startStr = "";
    private String endStr = "";

    private Date startDate;
    private Date endDate;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inspection, container, false);
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
        ivSs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(false);
            }
        });
        tvXjzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //巡检状态
                if (statusStrOptions == null || statusStrOptions.size() == 0) {
                    ToastUtil.toast("选项为空，请稍等重试");
                    return;
                }
                PickerViewUtils.selectOptions(getContext(), "选择", statusStrOptions, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvXjzt.setText(statusStrOptions.get(options1));
                        if (options1 == 0) {
                            statusStr = "";
                        } else {
                            statusStr = statusList.get(options1 - 1).getDataValue();
                        }
                        getData(false);
                    }
                });
            }
        });
        tvJjcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //紧急程度
                if (urgentStrOptions == null || urgentStrOptions.size() == 0) {
                    ToastUtil.toast("选项为空，请稍等重试");
                    return;
                }
                PickerViewUtils.selectOptions(getContext(), "选择", urgentStrOptions, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvJjcd.setText(urgentStrOptions.get(options1));
                        if (options1 == 0) {
                            urgentStr = "";
                        } else {
                            urgentStr = urgentList.get(options1 - 1).getDataValue();
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
                if (overtimeStrOptions == null || overtimeStrOptions.size() == 0) {
                    ToastUtil.toast("选项为空，请稍等重试");
                    return;
                }
                PickerViewUtils.selectOptions(getContext(), "选择", overtimeStrOptions, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvCszt.setText(overtimeStrOptions.get(options1));
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

        tvKssj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开始时间
                Calendar nowDate = Calendar.getInstance();
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    public void onTimeSelect(final Date date, View v) {
                        if (endDate != null && date.getTime() > endDate.getTime()) {
                            ToastUtil.toast("开始时间不能晚于结束时间！");
                            return;
                        }
                        startStr = formatter.format(date);//日期 String
                        startDate = date;
                        tvKssj.setText(startStr);
                        getData(false);


                    }
                }).setDate(nowDate)//设置系统时间为当前时间
                        .setType(new boolean[]{true, true, true, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                        //.setLabel("年", "月", "日", "时", "分", "秒")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setDividerColor(0x1F191F25)//设置分割线颜色
                        .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                        .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                        .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                        .setTitleText("安装时间")//标题文字
                        .setTitleColor(0xFF191F25)//标题文字颜色
                        .setLineSpacingMultiplier(lineSpace)
                        .build();
                pvTime.show();
            }
        });

        tvJssj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //结束时间
                Calendar nowDate = Calendar.getInstance();
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    public void onTimeSelect(final Date date, View v) {
                        if (startDate != null && date.getTime() < startDate.getTime()) {
                            ToastUtil.toast("结束时间不能早于开始时间！");
                            return;
                        }
                        endStr = formatter.format(date);//日期 String
                        endDate = date;
                        tvJssj.setText(endStr);
                        getData(false);


                    }
                }).setDate(nowDate)//设置系统时间为当前时间
                        .setType(new boolean[]{true, true, true, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                        //.setLabel("年", "月", "日", "时", "分", "秒")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setDividerColor(0x1F191F25)//设置分割线颜色
                        .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                        .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                        .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                        .setTitleText("安装时间")//标题文字
                        .setTitleColor(0xFF191F25)//标题文字颜色
                        .setLineSpacingMultiplier(lineSpace)
                        .build();
                pvTime.show();
            }
        });


    }

    private void initAdapter() {
        statusList = new ArrayList<>();
        statusStrOptions = new ArrayList<>();
        urgentList = new ArrayList<>();
        urgentStrOptions = new ArrayList<>();
        overtimeList = new ArrayList<>();
        overtimeStrOptions = new ArrayList<>();

        mList = new ArrayList<>();
        adapter = new InspectionUserAdapter(mList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //巡检详情，右上角有结果按钮
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                toTheActivity(InspectionDetailActivity.class);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String myId = MyApplication.getInstance().getUserInfo().getId();
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                switch (view.getId()) {
                    case R.id.tv_map:
                        //地图按钮
                        break;
                    case R.id.tv_start:
                        //开始巡检-->任务下的相机列表-->点击开始巡检，填写巡检信息
                        toTheActivity(StartInspectionActivity.class, bundle);
                        break;
                    case R.id.tv_check:
                        //确认审核-->任务审核详情统览-->点击结果进入相机列表，查看单个相机的巡检信息
                        toTheActivity(InspectionDetailAdminActivity.class, bundle);
                        break;
                }
            }
        });
        getSelectData();
        getData(false);
    }

    private void getSelectData() {

        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", 1);
        params.put("pageSize", 10);
        ApiClient.requestNetPost(getContext(), AppConfig.inspectionInfoList, "", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<InspectionOption> list1 = FastJsonUtil.getList(FastJsonUtil.getString(json, "opInspectionList"), InspectionOption.class);
                List<InspectionOption> list2 = FastJsonUtil.getList(FastJsonUtil.getString(json, "urgentTypeList"), InspectionOption.class);
                List<InspectionOption> list3 = FastJsonUtil.getList(FastJsonUtil.getString(json, "opOvertimeList"), InspectionOption.class);
                if (list1 != null) {
                    statusList.addAll(list1);
                    statusStrOptions.add("全部");
                    for (InspectionOption op : list1) {
                        statusStrOptions.add(op.getDataName());
                    }
                }
                if (list2 != null) {
                    urgentList.addAll(list2);
                    urgentStrOptions.add("全部");
                    for (InspectionOption op : list2) {
                        urgentStrOptions.add(op.getDataName());
                    }
                }

                if (list3 != null) {
                    overtimeList.addAll(list3);
                    overtimeStrOptions.add("全部");
                    for (InspectionOption op : list3) {
                        overtimeStrOptions.add(op.getDataName());
                    }
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });
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
            params.put("inspectionStatus", statusStr);
        }
        if (!StringUtil.isEmpty(urgentStr)) {
            params.put("urgentType", urgentStr);
        }
        if (!StringUtil.isEmpty(overtimeStr)) {
            params.put("overtimeStatus", overtimeStr);
        }
        if (!StringUtil.isEmpty(startStr)) {
            params.put("bTime", startStr);
        }
        if (!StringUtil.isEmpty(endStr)) {
            params.put("eTime", endStr);
        }
        if (!StringUtil.isEmpty(etSearch.getText().toString().trim())) {
            params.put("taskName", etSearch.getText().toString().trim());
        }
        ApiClient.requestNetPost(getContext(), AppConfig.inspectionInfoList, "查询中", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<InspectionUser> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), InspectionUser.class);
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

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
