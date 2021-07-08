package com.hwc.dwcj.activity.second;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.AlertMenuAdapter;
import com.hwc.dwcj.adapter.second.SelectOptionsChildAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.DAPcs;
import com.hwc.dwcj.entity.DictInfo;
import com.hwc.dwcj.entity.second.AlertMenuInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.GetDictDataHttp;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.interfaces.PickerViewSelectOptionsResult;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.util.PickerViewUtils;
import com.hwc.dwcj.util.RecyclerViewHelper;
import com.hwc.dwcj.view.dialog.BaseDialog;
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
 * 告警管理页面
 */
public class AlertManagementActivity extends BaseActivity {


    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_fj)
    TextView tvFj;
    @BindView(R.id.tv_pcs)
    TextView tvPcs;
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
    @BindView(R.id.rv4)
    RecyclerView rv4;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.rv5)
    RecyclerView rv5;
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
    private List<AlertMenuInfo> mList;
    private AlertMenuAdapter adapter;

    private int page = 1;
    private int pageSize = 10;


    private List<DictInfo> mList1;
    private SelectOptionsChildAdapter adapter1;
    private String value1 = "";
    private String name1 = "";


    private List<DictInfo> mList2;
    private SelectOptionsChildAdapter adapter2;
    private String value2 = "";
    private String name2 = "";

    private List<DictInfo> mList3;
    private SelectOptionsChildAdapter adapter3;
    private String value3 = "";
    private String name3 = "";

    private List<DictInfo> mList4;
    private SelectOptionsChildAdapter adapter4;
    private String value4 = "";
    private String name4 = "";

    private List<DictInfo> mList5;
    private SelectOptionsChildAdapter adapter5;
    private String value5 = "";
    private String name5 = "";

    private String startStr = "";
    private String endStr = "";

    private Date startDate;
    private Date endDate;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private List<DictInfo> mList6;
    private List<DAPcs> mList7;

    private List<String> fjList;
    private List<String> pcsList;

    private String selectedorgIds = "";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_alert_management);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();
        initFjData();
    }

    private void initFjData(){
        mList6 = new ArrayList<>();
        mList7 = new ArrayList<>();
        fjList = new ArrayList<>();
        pcsList = new ArrayList<>();

        getFjData();
    }

    private void getFjData(){
        mList6.clear();
        fjList.clear();
        GetDictDataHttp.getDictData(this, "PT_FEN_JU", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList6.addAll(list);
                    for(DictInfo d:list){
                        fjList.add(d.getDataName());
                    }
                }
            }
        });
    }

    private void getPcsData(String id){
        mList7.clear();
        pcsList.clear();
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("areaCode", id);
        ApiClient.requestNetGet(this, AppConfig.pcsList, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<DAPcs> list = FastJsonUtil.getList(json, DAPcs.class);
                if (list != null && list.size() != 0) {
                    mList7.addAll(list);
                    //装配派出所数据
                    for (DAPcs d : list) {
                        pcsList.add(d.getOrgName());
                    }
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void initClick() {
        tvFj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fjList == null || fjList.size() == 0){
                    ToastUtil.toast("暂无分局数据，请稍后再试！");
                    return;
                }
                PickerViewUtils.selectOptions(AlertManagementActivity.this, "分局", fjList, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvFj.setText(fjList.get(options1));
                        tvPcs.setText("");
                        getPcsData(mList6.get(options1).getDataValue());
                        selectedorgIds = mList6.get(options1).getDataValue();
                        getData(false);
                    }
                });
            }
        });
        tvPcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pcsList == null || pcsList.size() == 0){
                    ToastUtil.toast("暂无派出所数据，请稍后再试！");
                    return;
                }
                PickerViewUtils.selectOptions(AlertManagementActivity.this, "派出所", pcsList, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvPcs.setText(pcsList.get(options1));
                        selectedorgIds = mList7.get(options1).getId()+"";
                        getData(false);
                    }
                });
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
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
        tvTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开始时间
                Calendar nowDate = Calendar.getInstance();
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(AlertManagementActivity.this, new OnTimeSelectListener() {
                    public void onTimeSelect(final Date date, View v) {
                        if (endDate != null && date.getTime() > endDate.getTime()) {
                            ToastUtil.toast("开始时间不能晚于结束时间！");
                            return;
                        }
                        startStr = formatter.format(date);//日期 String
                        startDate = date;
                        tvTimeStart.setText(startStr);
                        //getData(false);


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
                TimePickerView pvTime = new TimePickerBuilder(AlertManagementActivity.this, new OnTimeSelectListener() {
                    public void onTimeSelect(final Date date, View v) {
                        if (startDate != null && date.getTime() < startDate.getTime()) {
                            ToastUtil.toast("结束时间不能早于开始时间！");
                            return;
                        }
                        endStr = formatter.format(date);//日期 String
                        endDate = date;
                        tvTimeEnd.setText(endStr);
                        //getData(false);


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
        mList4.clear();
        GetDictDataHttp.getDictData(this, "OP_ALARM_SOURCE", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList4.addAll(list);
                    adapter4.notifyDataSetChanged();
                }
            }
        });
    }

    private void getDictData5() {
        mList5.clear();
        GetDictDataHttp.getDictData(this, "OP_ALARM_SEND_STATUS", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList5.addAll(list);
                    adapter5.notifyDataSetChanged();
                }
            }
        });
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
                    name1 = mList1.get(position).getDataName();
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
                    name2 = mList2.get(position).getDataName();
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
                    name3 = mList3.get(position).getDataName();


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
        adapter4 = new SelectOptionsChildAdapter(mList4);
        adapter4.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mList4.get(position).isSelected()) {
                    //当前未选中
                    for (DictInfo dictInfo : mList4) {
                        if (dictInfo.isSelected()) {
                            dictInfo.setSelected(false);
                            adapter4.notifyItemChanged(mList4.indexOf(dictInfo));
                            break;
                        }
                    }
                    mList4.get(position).setSelected(true);
                    adapter4.notifyItemChanged(position);
                    value4 = mList4.get(position).getDataValue();
                    name4 = mList4.get(position).getDataName();

                } else {
                    //当前选中
                    mList4.get(position).setSelected(false);
                    adapter4.notifyItemChanged(position);
                    value4 = "";
                }
            }
        });
        rv4.setAdapter(adapter4);
        rv4.setLayoutManager(new GridLayoutManager(this, 3));
        RecyclerViewHelper.recyclerviewAndScrollView(rv4);
        getDictData4();

        mList5 = new ArrayList<>();
        adapter5 = new SelectOptionsChildAdapter(mList5);
        adapter5.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mList5.get(position).isSelected()) {
                    //当前未选中
                    for (DictInfo dictInfo : mList5) {
                        if (dictInfo.isSelected()) {
                            dictInfo.setSelected(false);
                            adapter5.notifyItemChanged(mList5.indexOf(dictInfo));
                            break;
                        }
                    }
                    mList5.get(position).setSelected(true);
                    adapter5.notifyItemChanged(position);
                    value5 = mList5.get(position).getDataValue();
                    name5 = mList5.get(position).getDataName();

                } else {
                    //当前选中
                    mList5.get(position).setSelected(false);
                    adapter5.notifyItemChanged(position);
                    value5 = "";
                }
            }
        });
        rv5.setAdapter(adapter5);
        rv5.setLayoutManager(new GridLayoutManager(this, 3));
        RecyclerViewHelper.recyclerviewAndScrollView(rv5);
        getDictData5();


        mList = new ArrayList<>();
        adapter = new AlertMenuAdapter(mList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
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
                        toTheActivity(AlertToTrackActivity.class, bundle);
                        break;
                    case R.id.tv_evaluate:
                        toTheActivity(AlertToEvaluateActivity.class, bundle);
                        break;
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
        params.put("pageNum", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        if (!StringUtil.isEmpty(etSearch.getText().toString().trim())) {
            params.put("keyWords", etSearch.getText().toString().trim());
        }
        if (!StringUtil.isEmpty(selectedorgIds)) {
            params.put("selectedorgIds", selectedorgIds);
        }
        if (!StringUtil.isEmpty(value1)) {
            params.put("assetNature", name1);
        }
        if (!StringUtil.isEmpty(value2)) {
            params.put("assetType", name2);
        }
        if (!StringUtil.isEmpty(value3)) {
            params.put("assetClass", name3);
        }
        if (!StringUtil.isEmpty(value4)) {
            params.put("alarmSource", name4);
        }
        if (!StringUtil.isEmpty(value5)) {
            params.put("alarmStatus", name5);
        }
        if (!StringUtil.isEmpty(startStr)) {
            params.put("startTime", startStr);
        }
        if (!StringUtil.isEmpty(endStr)) {
            params.put("endTime", endStr);
        }
        ApiClient.requestNetGet(this, AppConfig.OpAlarmInfoList, "查询中", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<AlertMenuInfo> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), AlertMenuInfo.class);
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


    private static final int REQUEST_CODE_SCAN = 0X01;


    /**
     * 扫一扫功能
     */
    private void scanCode() {
        //申请相机和储存权限
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                ScanUtil.startScan(AlertManagementActivity.this, REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE).create());
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                ToastUtil.toast("拒绝权限将无法使用扫一扫功能");
            }
        }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil.REFRESH_ALERT_LIST:
                getData(false);
        }
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

    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_alert, null);
        View tv_sys = view.findViewById(R.id.tv_sys);
        View tv_hand = view.findViewById(R.id.tv_hand);
        View tv_cancel = view.findViewById(R.id.tv_cancel);
        BaseDialog.getInstance()
                .setLayoutView(view, this)
                .dissmissDialog()
                .setWindow(1, 0.5)
                .isCancelable(true)
                .setOnClickListener(tv_sys, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        toTheActivity(AddAlertActivity.class);
                    }
                })
                .setOnClickListener(tv_hand, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        toTheActivity(AddAlertActivity.class);
                    }
                })
                .setOnClickListener(tv_cancel, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        //取消
                        dialog.dismiss();
                    }
                }).bottomShow();
    }
}
