package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.SelectOptionsChildAdapter;
import com.hwc.dwcj.adapter.second.WorkOrderUserAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.DictInfo;
import com.hwc.dwcj.entity.second.WorkOrderUser;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.GetDictDataHttp;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.interfaces.PickerViewSelectOptionsResult;
import com.hwc.dwcj.util.PickerViewUtils;
import com.hwc.dwcj.util.RecyclerViewHelper;
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

import static com.hwc.dwcj.util.PickerViewUtils.lineSpace;

/**
 * 工单管理 运维人员
 */
public class WorkOrderManagementUserActivity extends BaseActivity {


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
    @BindView(R.id.tv_shzt)
    TextView tvShzt;
    @BindView(R.id.tv_ssjg)
    TextView tvSsjg;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.tv_more_blue)
    TextView tvMoreBlue;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv1)
    RecyclerView rv1;
    @BindView(R.id.rv2)
    RecyclerView rv2;
    @BindView(R.id.rv3)
    RecyclerView rv3;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.all)
    LinearLayout all;

    private int page = 1;
    private int pageSize = 10;


    List<WorkOrderUser> mList;
    WorkOrderUserAdapter adapter;

    private List<DictInfo> mList1;
    private SelectOptionsChildAdapter adapter1;
    private String value1 = "";

    private List<DictInfo> mList2;
    private SelectOptionsChildAdapter adapter2;
    private String value2 = "";

    private List<DictInfo> mList3;
    private SelectOptionsChildAdapter adapter3;
    private String value3 = "";

    private String startStr = "";
    private String endStr = "";

    private Date startDate;
    private Date endDate;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private List<DictInfo> mList4;
    private List<String> optionsList4;
    private String value4 = "";

    private List<DictInfo> mList5;
    private List<String> optionsList5;
    private String value5 = "";


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_management_user);

    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();
    }

    private void initAdapter() {
        mList1 = new ArrayList<>();
        adapter1 = new SelectOptionsChildAdapter(mList1);
        adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mList1.get(position).isSelected()) {
                    //当前未选中
                    for (DictInfo dictInfo : mList1) {
                        if (dictInfo.isSelected()) {
                            dictInfo.setSelected(false);
                            adapter1.notifyItemChanged(mList1.indexOf(dictInfo));
                            break;
                        }
                    }
                    mList1.get(position).setSelected(true);
                    adapter1.notifyItemChanged(position);
                    value1 = mList1.get(position).getDataValue();
                    value2 = "";
                    value3 = "";
                    getDictData2();
                    mList3.clear();
                    adapter3.notifyDataSetChanged();
                } else {
                    //当前选中
                    mList1.get(position).setSelected(false);
                    adapter1.notifyItemChanged(position);
                    value1 = "";
                    value2 = "";
                    value3 = "";
                    mList2.clear();
                    adapter2.notifyDataSetChanged();
                    mList3.clear();
                    adapter3.notifyDataSetChanged();
                }
            }
        });
        rv1.setAdapter(adapter1);
        rv1.setLayoutManager(new GridLayoutManager(this, 3));
        RecyclerViewHelper.recyclerviewAndScrollView(rv1);
        getDictData1();


        mList2 = new ArrayList<>();
        adapter2 = new SelectOptionsChildAdapter(mList2);
        adapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mList2.get(position).isSelected()) {
                    //当前未选中
                    for (DictInfo dictInfo : mList2) {
                        if (dictInfo.isSelected()) {
                            dictInfo.setSelected(false);
                            adapter2.notifyItemChanged(mList2.indexOf(dictInfo));
                            break;
                        }
                    }
                    mList2.get(position).setSelected(true);
                    adapter2.notifyItemChanged(position);
                    value2 = mList2.get(position).getDataValue();
                    value3 = "";
                    getDictData3();
                } else {
                    //当前选中
                    mList2.get(position).setSelected(false);
                    adapter2.notifyItemChanged(position);
                    value2 = "";
                    value3 = "";
                    mList3.clear();
                    adapter3.notifyDataSetChanged();
                }
            }
        });
        rv2.setAdapter(adapter2);
        rv2.setLayoutManager(new GridLayoutManager(this, 3));
        RecyclerViewHelper.recyclerviewAndScrollView(rv2);

        mList3 = new ArrayList<>();
        adapter3 = new SelectOptionsChildAdapter(mList3);
        adapter3.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mList3.get(position).isSelected()) {
                    //当前未选中
                    for (DictInfo dictInfo : mList3) {
                        if (dictInfo.isSelected()) {
                            dictInfo.setSelected(false);
                            adapter3.notifyItemChanged(mList3.indexOf(dictInfo));
                            break;
                        }
                    }
                    mList3.get(position).setSelected(true);
                    adapter3.notifyItemChanged(position);
                    value3 = mList3.get(position).getDataValue();

                } else {
                    //当前选中
                    mList3.get(position).setSelected(false);
                    adapter3.notifyItemChanged(position);
                    value3 = "";
                }
            }
        });
        rv3.setAdapter(adapter3);
        rv3.setLayoutManager(new GridLayoutManager(this, 3));
        RecyclerViewHelper.recyclerviewAndScrollView(rv3);

        mList4 = new ArrayList<>();
        optionsList4 = new ArrayList<>();
        getDictData4();

        mList5 = new ArrayList<>();
        optionsList5 = new ArrayList<>();
        getDictData5();

        mList = new ArrayList<>();
        adapter = new WorkOrderUserAdapter(mList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                switch (view.getId()) {
                    case R.id.tv_look:
                        //查看
                        break;
                    case R.id.tv_do:
                        //处理
                        toTheActivity(WorkOrderHandleActivity.class, bundle);
                        break;
                    case R.id.tv_track:
                        //跟踪
                        toTheActivity(WorkOrderTrackActivity.class, bundle);
                        break;
                    case R.id.tv_check:
                        //审核
                        toTheActivity(WorkOrderProcessAuditActivity.class, bundle);
                        break;
                    //问题上报
                    //toTheActivity(WorkOrderProblemUpActivity.class, bundle);
                }
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
        params.put("userId", MyApplication.getInstance().getUserInfo().getId());
        params.put("pageNum", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        if (!StringUtil.isEmpty(etSearch.getText().toString().trim())) {
            params.put("keywords", etSearch.getText().toString().trim());
        }
        if (!StringUtil.isEmpty(value1)) {
            params.put("assetNature", value1);
        }
        if (!StringUtil.isEmpty(value2)) {
            params.put("assetType", value2);
        }
        if (!StringUtil.isEmpty(value3)) {
            params.put("assetClass", value3);
        }
        if (!StringUtil.isEmpty(value4)) {
            params.put("handleStatus", value4);
        }
        if (!StringUtil.isEmpty(value5)) {
            params.put("verifyStatus", value5);
        }
        if (!StringUtil.isEmpty(startStr)) {
            params.put("startTime", startStr);
        }
        if (!StringUtil.isEmpty(endStr)) {
            params.put("endTime", endStr);
        }
        ApiClient.requestNetGet(this, AppConfig.OpFaultInfoList, "查询中", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<WorkOrderUser> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), WorkOrderUser.class);
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

    private void initClick() {
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getData(true);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(false);
            }
        });
        tvDbsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTheActivity(UpcomingEventListUserActivity.class);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llSelect.getVisibility() == View.GONE) {
                    llSelect.setVisibility(View.VISIBLE);
                    show(llSelect);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMore.setVisibility(View.GONE);
                            tvMoreBlue.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
        tvMoreBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llSelect.getVisibility() == View.VISIBLE) {
                    llSelect.setVisibility(View.GONE);
                    hide(llSelect);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMoreBlue.setVisibility(View.GONE);
                            tvMore.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llSelect.getVisibility() == View.VISIBLE) {
                    llSelect.setVisibility(View.GONE);
                    hide(llSelect);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMoreBlue.setVisibility(View.GONE);
                            tvMore.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llSelect.getVisibility() == View.VISIBLE) {
                    llSelect.setVisibility(View.GONE);
                    hide(llSelect);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMoreBlue.setVisibility(View.GONE);
                            tvMore.setVisibility(View.VISIBLE);
                        }
                    });
                }
                getData(false);
            }
        });
        tvTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开始时间
                Calendar nowDate = Calendar.getInstance();
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(WorkOrderManagementUserActivity.this, new OnTimeSelectListener() {
                    public void onTimeSelect(final Date date, View v) {
                        if (endDate != null && date.getTime() > endDate.getTime()) {
                            ToastUtil.toast("开始时间不能晚于结束时间！");
                            return;
                        }
                        startStr = formatter.format(date);//日期 String
                        startDate = date;
                        tvTimeStart.setText(startStr);
                        getData(false);


                    }
                }).setDate(nowDate)//设置系统时间为当前时间
                        .setType(new boolean[]{true, true, true, true, true, true})//设置年月日时分秒是否显示 true:显示 false:隐藏
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
        tvTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //结束时间
                Calendar nowDate = Calendar.getInstance();
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(WorkOrderManagementUserActivity.this, new OnTimeSelectListener() {
                    public void onTimeSelect(final Date date, View v) {
                        if (startDate != null && date.getTime() < startDate.getTime()) {
                            ToastUtil.toast("结束时间不能早于开始时间！");
                            return;
                        }
                        endStr = formatter.format(date);//日期 String
                        endDate = date;
                        tvTimeEnd.setText(endStr);
                        getData(false);


                    }
                }).setDate(nowDate)//设置系统时间为当前时间
                        .setType(new boolean[]{true, true, true, true, true, true})//设置年月日时分秒是否显示 true:显示 false:隐藏
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
        ivSs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(false);
            }
        });
        tvClzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionsList4 == null || optionsList4.size() == 0) {
                    ToastUtil.toast("选项为空，请稍等重试");
                    return;
                }
                PickerViewUtils.selectOptions(WorkOrderManagementUserActivity.this, "选择", optionsList4, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvClzt.setText(optionsList4.get(options1));
                        if (options1 == 0) {
                            value4 = "";
                        } else {
                            value4 = mList4.get(options1 - 1).getDataValue();
                        }
                        getData(false);
                    }
                });
            }
        });
        tvShzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionsList5 == null || optionsList5.size() == 0) {
                    ToastUtil.toast("选项为空，请稍等重试");
                    return;
                }
                PickerViewUtils.selectOptions(WorkOrderManagementUserActivity.this, "选择", optionsList5, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvShzt.setText(optionsList5.get(options1));
                        if (options1 == 0) {
                            value5 = "";
                        } else {
                            value5 = mList5.get(options1 - 1).getDataValue();
                        }
                        getData(false);
                    }
                });
            }
        });
    }

    private void getDictData1() {
        GetDictDataHttp.getDictData(this, "OP_ASSET_TYPE", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList1.addAll(list);
                    adapter1.notifyDataSetChanged();
                }
            }
        });
    }

    private void getDictData2() {
        mList2.clear();
        GetDictDataHttp.getDictData(this, value1, new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList2.addAll(list);
                    adapter2.notifyDataSetChanged();
                }
            }
        });
    }

    private void getDictData3() {
        mList3.clear();
        GetDictDataHttp.getDictData(this, value2, new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList3.addAll(list);
                    adapter3.notifyDataSetChanged();
                }
            }
        });
    }

    private void getDictData4() {
        mList3.clear();
        GetDictDataHttp.getDictData(this, "OP_FAULT_STATUS", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList4.addAll(list);
                    optionsList4.add("全部");
                    for (DictInfo op : list) {
                        optionsList4.add(op.getDataName());
                    }
                }
            }
        });
    }


    private void getDictData5() {
        mList3.clear();
        GetDictDataHttp.getDictData(this, "OP_VERIFY_STATUS", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList5.addAll(list);
                    optionsList5.add("全部");
                    for (DictInfo op : list) {
                        optionsList5.add(op.getDataName());
                    }
                }
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

    public void show(View view) {
        // 显示动画
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, -0.0f);
        mShowAction.setRepeatMode(Animation.REVERSE);
        mShowAction.setDuration(500);
        view.startAnimation(mShowAction);
    }

    public void hide(View view) {
        // 隐藏动画
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(500);
        view.startAnimation(mHiddenAction);
    }
}
