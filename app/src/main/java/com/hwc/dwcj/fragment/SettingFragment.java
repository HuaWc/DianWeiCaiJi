package com.hwc.dwcj.fragment;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.hwc.dwcj.activity.AddCameraDetailActivity;
import com.hwc.dwcj.activity.AuditDetailAdminActivity;
import com.hwc.dwcj.activity.AuditDetailUserActivity;
import com.hwc.dwcj.adapter.DWItemAdapter;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.entity.DWItem;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.view.dialog.BaseDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 点位采集
 *
 * @author Administrator
 */
public class SettingFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_select1_type1)
    TextView tvSelect1Type1;
    @BindView(R.id.tv_select1_type2)
    TextView tvSelect1Type2;
    @BindView(R.id.tv_select1_type3)
    TextView tvSelect1Type3;
    @BindView(R.id.tv_select1_type4)
    TextView tvSelect1Type4;
    @BindView(R.id.tv_select2_type1)
    TextView tvSelect2Type1;
    @BindView(R.id.tv_select2_type2)
    TextView tvSelect2Type2;
    @BindView(R.id.tv_select2_type3)
    TextView tvSelect2Type3;
    @BindView(R.id.tv_select_year)
    TextView tvSelectYear;
    @BindView(R.id.tv_select_month)
    TextView tvSelectMonth;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.iv_show)
    ImageView ivShow;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.iv_plus)
    ImageView ivPlus;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.tv_select1_type5)
    TextView tvSelect1Type5;
    @BindView(R.id.iv_qk)
    ImageView ivQk;

    private List<DWItem> mList;
    private DWItemAdapter dwItemAdapter;
    private int selectedType1 = 0;//默认全部  当前状态(1.审批中  2.已撤销  3.已驳回4.已通过 )
    private int selectedType2 = 0;//默认全部  类型(1.信息采集2.草稿,3.采集信息修改)
    private String timeStrStart = "";//开始时间
    private String timeStrEnd = "";
    private int roleId;//当前用户角色状态码 903采集员  907审批员
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
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

    @Override
    public void onResume() {
        super.onResume();

    }


    private void initUserInfo() {

    }

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil.FLUSH_LIST_DW:
                getData(false);
                break;
        }
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


    private void getRole() {
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetGet(getContext(), AppConfig.getUserRole, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                boolean a = FastJsonUtil.getObject(json, boolean.class);
                if (a) {
                    Bundle bundle = new Bundle();
                    toTheActivity(AddCameraDetailActivity.class, bundle);
                } else {
                    ToastUtil.toast("您还不是采集员，权限不足，无法新增！");
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast("获取您的采集员权限失败，请稍后重试！");

            }
        });
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        dwItemAdapter = new DWItemAdapter(mList, new DWItemAdapter.DWChildClickListener() {
            @Override
            public void look(DWItem.CameraInfoList c) {
                Bundle bundle = new Bundle();
                bundle.putString("uuid", c.getId());
/*                switch (roleId) {
                    case 903:
                        toTheActivity(AuditDetailUserActivity.class, bundle);
                        Log.d("role:", "采集员");
                        break;
                    case 907:
                        toTheActivity(AuditDetailAdminActivity.class, bundle);
                        Log.d("role:", "审批员");
                        break;
                    default:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "无法获取您的状态码，请稍后再试", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                }*/
                toTheActivity(AuditDetailUserActivity.class, bundle);
                Log.d("role:", "采集员");
            }

            @Override
            public void edit(DWItem.CameraInfoList c, String positionName) {
                Bundle bundle = new Bundle();
                //bundle.putString("positionName", positionName);
                bundle.putString("cameraId", c.getId());
                bundle.putBoolean("isEdit", true);
                bundle.putBoolean("isDraft", c.getCurrentStatus() == 0);
                toTheActivity(AddCameraDetailActivity.class, bundle);
            }

            @Override
            public void detail(DWItem.CameraInfoList c) {
                Bundle bundle = new Bundle();
                bundle.putString("cameraId", c.getId());
                bundle.putBoolean("isUser", true);
                toTheActivity(AuditDetailAdminActivity.class, bundle);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(dwItemAdapter);
        getData(false);
    }

    private void initClick() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(false);
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvSelect1Type1.setSelected(true);
                tvSelect2Type1.setSelected(true);
            }
        });
        ivShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        llSelect.setVisibility(llSelect.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                        ivShow.setSelected(llSelect.getVisibility() == View.GONE);
                    }
                });
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getData(true);
            }
        });
        tvSelectYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerView(1);
            }
        });
        tvSelectMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerView(2);
            }
        });
        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹窗
                showDialog();
            }
        });
        ivSs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(false);
            }
        });
        ivQk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSelectYear.setText("");
                        tvSelectMonth.setText("");
                    }
                });
                timeStrEnd = "";
                timeStrStart = "";
            }
        });
    }

    private void showDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_dw_plus, null);
        View tv_sys = view.findViewById(R.id.tv_sys);
        View tv_xz = view.findViewById(R.id.tv_xz);
        View tv_cancel = view.findViewById(R.id.tv_cancel);
        BaseDialog.getInstance()
                .setLayoutView(view, getContext())
                .dissmissDialog()
                .setWindow(1, 0.5)
                .isCancelable(true)
                .setOnClickListener(tv_sys, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        //扫一扫
                        dialog.dismiss();
                        scanCode();

                    }
                })
                .setOnClickListener(tv_xz, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        //新增点位档案
                        dialog.dismiss();
                        getRole();


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

    @OnClick({R.id.tv_select1_type1, R.id.tv_select1_type2, R.id.tv_select1_type3, R.id.tv_select1_type4, R.id.tv_select1_type5, R.id.tv_select2_type1, R.id.tv_select2_type2, R.id.tv_select2_type3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select1_type1:
                changeChooseType1(0);
                break;
            case R.id.tv_select1_type2:
                changeChooseType1(4);
                break;
            case R.id.tv_select1_type3:
                changeChooseType1(1);
                break;
            case R.id.tv_select1_type4:
                changeChooseType1(3);
                break;
            case R.id.tv_select1_type5:
                changeChooseType1(2);
                break;
            case R.id.tv_select2_type1:
                changeChooseType2(0);
                break;
            case R.id.tv_select2_type2:
                changeChooseType2(1);
                break;
            case R.id.tv_select2_type3:
                changeChooseType2(3);
                break;
        }
    }

    private void changeChooseType1(int choose) {
        if (selectedType1 == choose) {
            return;
        }
        selectedType1 = choose;
        getData(false);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvSelect1Type1.setSelected(false);
                tvSelect1Type2.setSelected(false);
                tvSelect1Type3.setSelected(false);
                tvSelect1Type4.setSelected(false);
                tvSelect1Type5.setSelected(false);
                switch (choose) {
                    case 0:
                        tvSelect1Type1.setSelected(true);
                        break;
                    case 4:
                        tvSelect1Type2.setSelected(true);
                        break;
                    case 1:
                        tvSelect1Type3.setSelected(true);
                        break;
                    case 3:
                        tvSelect1Type4.setSelected(true);
                        break;
                    case 2:
                        tvSelect1Type5.setSelected(true);
                        break;
                }
            }
        });

    }

    private void changeChooseType2(int choose) {
        if (selectedType2 == choose) {
            return;
        }
        selectedType2 = choose;
        getData(false);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvSelect2Type1.setSelected(false);
                tvSelect2Type2.setSelected(false);
                tvSelect2Type3.setSelected(false);
                switch (choose) {
                    case 0:
                        tvSelect2Type1.setSelected(true);
                        break;
                    case 1:
                        tvSelect2Type2.setSelected(true);
                        break;
                    case 3:
                        tvSelect2Type3.setSelected(true);
                        break;
                }
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
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        if (selectedType1 != 0) {
            params.put("currentStatus", String.valueOf(selectedType1));
        }
/*        if (selectedType2 != 0) {
            params.put("InformationType", String.valueOf(selectedType2));
        }*/
        if (!StringUtil.isEmpty(etSearch.getText().toString().trim())) {
            params.put("mapKeywords", etSearch.getText().toString().trim());
        }
        if (!StringUtil.isEmpty(timeStrStart)) {
            params.put("originDate", timeStrStart);
        }
        if (!StringUtil.isEmpty(timeStrEnd)) {
            params.put("endDate", timeStrEnd);
        }
        ApiClient.requestNetGet(getContext(), AppConfig.pointToOllect, "查询中", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<DWItem> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), DWItem.class);
                if (list != null && list.size() != 0) {
                    mList.addAll(list);
                    if (!StringUtil.isEmpty(msg)) {
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (page > 1) {
                        page--;
                    }
                    /*getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext, "暂无更多数据", Toast.LENGTH_SHORT).show();
                        }
                    });*/
                }
                dwItemAdapter.notifyDataSetChanged();
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(String msg) {
                if (page > 1) {
                    page--;
                }
                Toast.makeText(mContext, "请求失败" + msg, Toast.LENGTH_SHORT).show();
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();

            }
        });
    }

    /**
     * @param type 1 起始  2 结束
     */
    private void showTimePickerView(int type) {
        Date d = new Date();
        //注：（1）年份可以随便设置 (2)月份是从0开始的（0代表1月 11月代表12月），即设置0代表起始时间从1月开始
        //(3)日期必须从1开始，因为2月没有30天，设置其他日期时，2月份会从设置日期开始显示导致出现问题
        Calendar endDate = Calendar.getInstance();
        Calendar nowDate = Calendar.getInstance();
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            public void onTimeSelect(final Date date, View v) {
                String timeGet = formatter.format(date);//日期 String
                try {
                    if (type == 1) {
                        if (!StringUtil.isEmpty(timeStrEnd)) {
                            //对比结束时间

                            if (formatter.parse(timeGet).getTime() > formatter.parse(timeStrEnd).getTime()) {
                                ToastUtil.toast("开始日期不能晚于结束日期");
                            } else {
                                timeStrStart = timeGet;
                                setTime(tvSelectYear, timeGet);
                            }
                        } else {
                            timeStrStart = timeGet;
                            setTime(tvSelectYear, timeGet);
                        }
                    } else {
                        if (!StringUtil.isEmpty(timeStrStart)) {
                            //对比结束时间

                            if (formatter.parse(timeGet).getTime() < formatter.parse(timeStrStart).getTime()) {
                                ToastUtil.toast("结束日期日期不能早于开始日期");
                            } else {
                                timeStrEnd = timeGet;
                                setTime(tvSelectMonth, timeGet);
                            }
                        } else {
                            timeStrEnd = timeGet;
                            setTime(tvSelectMonth, timeGet);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }).setDate(nowDate)//设置系统时间为当前时间
                .setRangDate(null, endDate)//设置控件日期范围 也可以不设置默认1900年到2100年
                .setType(new boolean[]{true, true, true, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                //.setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText("选择日期")//标题文字
                .setTitleColor(0xFF191F25)//标题文字颜色
                .setLineSpacingMultiplier(2.5f)
                .build();
        pvTime.show();
    }


    private void setTime(TextView view, String timeStr) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setText(timeStr);
            }
        });
    }


    private static final int REQUEST_CODE_SCAN = 0X01;

    /**
     * 扫一扫功能
     */
    private void scanCode() {
        //申请相机和储存权限
        PermissionsUtil.requestPermission(getContext(), new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                ScanUtil.startScan(getActivity(), REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE).create());
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                ToastUtil.toast("拒绝权限将无法使用扫一扫功能");
            }
        }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

}
