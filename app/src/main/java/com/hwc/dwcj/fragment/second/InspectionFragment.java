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
import com.hwc.dwcj.activity.second.MapShowCameraActivity;
import com.hwc.dwcj.activity.second.StartInspectionActivity;
import com.hwc.dwcj.adapter.second.InspectionUserAdapter;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.DictInfo;
import com.hwc.dwcj.entity.second.InspectionUser;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.GetDictDataHttp;
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
    private List<DictInfo> statusList;
    private List<String> statusStrOptions;
    private List<DictInfo> urgentList;
    private List<String> urgentStrOptions;
    private List<DictInfo> overtimeList;
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
                //????????????
                hideSoftKeyboard();
                hideSoftKeyboard3();
                if (statusStrOptions == null || statusStrOptions.size() == 0) {
                    ToastUtil.toast("??????????????????????????????");
                    return;
                }
                PickerViewUtils.selectOptions(getContext(), "??????", statusStrOptions, null, null, new PickerViewSelectOptionsResult() {
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
                hideSoftKeyboard();
                hideSoftKeyboard3();
                //????????????
                if (urgentStrOptions == null || urgentStrOptions.size() == 0) {
                    ToastUtil.toast("??????????????????????????????");
                    return;
                }
                PickerViewUtils.selectOptions(getContext(), "??????", urgentStrOptions, null, null, new PickerViewSelectOptionsResult() {
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
                hideSoftKeyboard();
                hideSoftKeyboard3();
                //????????????
                if (overtimeStrOptions == null || overtimeStrOptions.size() == 0) {
                    ToastUtil.toast("??????????????????????????????");
                    return;
                }
                PickerViewUtils.selectOptions(getContext(), "??????", overtimeStrOptions, null, null, new PickerViewSelectOptionsResult() {
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
                hideSoftKeyboard();
                hideSoftKeyboard3();
                //????????????
                Calendar nowDate = Calendar.getInstance();
                //???????????????
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    public void onTimeSelect(final Date date, View v) {
                        if (endDate != null && date.getTime() > endDate.getTime()) {
                            ToastUtil.toast("???????????????????????????????????????");
                            return;
                        }
                        startStr = formatter.format(date);//?????? String
                        startDate = date;
                        tvKssj.setText(startStr);
                        getData(false);


                    }
                }).setDate(nowDate)//?????????????????????????????????
                        .setType(new boolean[]{true, true, true, false, false, false})//???????????????????????????????????? true:?????? false:??????
                        //.setLabel("???", "???", "???", "???", "???", "???")
                        .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                        .setDividerColor(0x1F191F25)//?????????????????????
                        .isCyclic(false)//???????????????????????? ???????????????31???????????????1??? ????????????????????????????????????????????????
                        .setSubmitColor(0xFFF79D1F)//????????????????????????
                        .setCancelColor(0xFFA3A5A8)//????????????????????????
                        .setTitleText("????????????")//????????????
                        .setTitleColor(0xFF191F25)//??????????????????
                        .setLineSpacingMultiplier(lineSpace)
                        .build();
                pvTime.show();
            }
        });

        tvJssj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                hideSoftKeyboard3();
                //????????????
                Calendar nowDate = Calendar.getInstance();
                //???????????????
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    public void onTimeSelect(final Date date, View v) {
                        if (startDate != null && date.getTime() < startDate.getTime()) {
                            ToastUtil.toast("???????????????????????????????????????");
                            return;
                        }
                        endStr = formatter.format(date);//?????? String
                        endDate = date;
                        tvJssj.setText(endStr);
                        getData(false);


                    }
                }).setDate(nowDate)//?????????????????????????????????
                        .setType(new boolean[]{true, true, true, false, false, false})//???????????????????????????????????? true:?????? false:??????
                        //.setLabel("???", "???", "???", "???", "???", "???")
                        .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                        .setDividerColor(0x1F191F25)//?????????????????????
                        .isCyclic(false)//???????????????????????? ???????????????31???????????????1??? ????????????????????????????????????????????????
                        .setSubmitColor(0xFFF79D1F)//????????????????????????
                        .setCancelColor(0xFFA3A5A8)//????????????????????????
                        .setTitleText("????????????")//????????????
                        .setTitleColor(0xFF191F25)//??????????????????
                        .setLineSpacingMultiplier(lineSpace)
                        .build();
                pvTime.show();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
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
                //???????????????????????????????????????
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                toTheActivity(InspectionDetailActivity.class,bundle);
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
                        //????????????
                        toTheActivity(MapShowCameraActivity.class, bundle);
                        break;
                    case R.id.tv_start:
                        //????????????-->????????????????????????-->???????????????????????????????????????
                        toTheActivity(StartInspectionActivity.class, bundle);
                        break;
                    case R.id.tv_check:
                        //????????????-->????????????????????????-->??????????????????????????????????????????????????????????????????
                        Bundle bundleCheck = new Bundle();
                        bundleCheck.putString("id", mList.get(position).getId());
                        toTheActivity(InspectionDetailAdminActivity.class, bundleCheck);
                        break;
                }
            }
        });
        getList1();
        getList2();
        getList3();

    }

    private void getList1(){
        GetDictDataHttp.getDictData(getContext(), "OP_INSPECTION_STATUS", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    statusList.addAll(list);
                    statusStrOptions.add("??????");
                    for (DictInfo op : list) {
                        statusStrOptions.add(op.getDataName());
                    }
                }
            }
        });
    }
    private void getList2(){
        GetDictDataHttp.getDictData(getContext(), "URGENT_TYPE", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    urgentList.addAll(list);
                    urgentStrOptions.add("??????");
                    for (DictInfo op : list) {
                        urgentStrOptions.add(op.getDataName());
                    }
                }
            }
        });
    }
    private void getList3(){
        GetDictDataHttp.getDictData(getContext(), "OP_OVERTIME", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    overtimeList.addAll(list);
                    overtimeStrOptions.add("??????");
                    for (DictInfo op : list) {
                        overtimeStrOptions.add(op.getDataName());
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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
        ApiClient.requestNetPost(getContext(), AppConfig.inspectionInfoList, "?????????", params, new ResultListener() {
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
