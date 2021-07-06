package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.FaultMapInfo;
import com.hwc.dwcj.entity.second.WorkOrderClr;
import com.hwc.dwcj.entity.second.WorkOrderUser;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.interfaces.PickerViewSelectOptionsResult;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.util.PickerViewUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

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
 * Created by Christ on 2021/6/8.
 * By an amateur android developer
 * Email 627447123@qq.com
 * <p>
 * <p>
 * 工单处理（管理员） - 事件审核 - 问题上报
 */
public class WorkOrderProblemUpActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv9)
    TextView tv9;
    @BindView(R.id.tv10)
    TextView tv10;
    @BindView(R.id.tv11)
    TextView tv11;
    @BindView(R.id.tv12)
    TextView tv12;
    @BindView(R.id.tv13)
    TextView tv13;
    @BindView(R.id.tv14)
    TextView tv14;
    @BindView(R.id.tv15)
    TextView tv15;
    @BindView(R.id.tv16)
    TextView tv16;
    @BindView(R.id.tv17)
    TextView tv17;
    @BindView(R.id.tv18)
    TextView tv18;
    @BindView(R.id.tv19)
    TextView tv19;
    @BindView(R.id.tv20)
    TextView tv20;
    @BindView(R.id.tv21)
    TextView tv21;
    @BindView(R.id.tv22)
    TextView tv22;
    @BindView(R.id.tv23)
    TextView tv23;
    @BindView(R.id.rb_help)
    RadioButton rbHelp;
    @BindView(R.id.rb_add)
    RadioButton rbAdd;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.ll_help_part)
    LinearLayout llHelpPart;
    @BindView(R.id.ll_add_part)
    LinearLayout llAddPart;
    @BindView(R.id.tv_clr)
    TextView tvClr;
    @BindView(R.id.tv_xtclr)
    TextView tvXtclr;
    @BindView(R.id.et_phone1)
    EditText etPhone1;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_clr2)
    TextView tvClr2;
    @BindView(R.id.et_phone2)
    EditText etPhone2;
    @BindView(R.id.et_sm)
    EditText etSm;

    private String id;
    private FaultMapInfo info;
    private int type = 0;//1 协助 2 新增

    private List<String> helpPeopleOptions;
    private List<String> addPeopleOptions;

    private String id1 = "";
    private String id2 = "";
    private String ids = "";

    private List<WorkOrderClr> clrOld;
    private List<WorkOrderClr> clrHelp;
    private List<WorkOrderClr> clrAdd;


    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_problem_up);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        getData();
        initClick();
        handleListAndData();
    }

    private void handleListAndData() {
        helpPeopleOptions = new ArrayList<>();
        addPeopleOptions = new ArrayList<>();
        clrOld = new ArrayList<>();
        clrAdd = new ArrayList<>();
        clrHelp = new ArrayList<>();
        getClrData();
    }


    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        ApiClient.requestNetGet(this, AppConfig.OpFaultInfoInfo, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                info = FastJsonUtil.getObject(FastJsonUtil.getString(json, "OpFaultInfoModel"), FaultMapInfo.class);
                initData();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void initData() {
        if (info == null) {
            return;
        }
        tv1.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());
        tv2.setText(StringUtil.isEmpty(info.getAlarmCode()) ? "" : info.getAlarmCode());
        tv3.setText(StringUtil.isEmpty(info.getMap().getAssetNatureName()) ? "" : info.getMap().getAssetNatureName());
        tv4.setText(StringUtil.isEmpty(info.getMap().getAssetTypeName()) ? "" : info.getMap().getAssetTypeName());
        tv5.setText(StringUtil.isEmpty(info.getMap().getAssetClassName()) ? "" : info.getMap().getAssetClassName());
        tv6.setText(StringUtil.isEmpty(info.getAlarmSource()) ? "" : info.getAlarmSource());
        tv7.setText(StringUtil.isEmpty(info.getAlarmGrade()) ? "" : info.getAlarmGrade());
        tv8.setText(StringUtil.isEmpty(info.getCameraFaultType() + "") ? "" : info.getCameraFaultType() + "");
        //tv9.setText(StringUtil.isEmpty(info.getMap().getOrgName()) ? "" : info.getMap().getOrgName());
        tv10.setText(StringUtil.isEmpty(info.getMap().getAssetName()) ? "" : info.getMap().getAssetName());
        tv11.setText(StringUtil.isEmpty(info.getMap().getPositionCode()) ? "" : info.getMap().getPositionCode());
        tv12.setText(StringUtil.isEmpty(info.getMap().getManageIp()) ? "" : info.getMap().getManageIp());
        tv13.setText(StringUtil.isEmpty(info.getAlarmTime()) ? "" : StringUtil.dealDateFormat(info.getAlarmTime()));//发生时间
        tv14.setText(StringUtil.isEmpty(info.getAlarmTime()) ? "" : StringUtil.dealDateFormat(info.getAlarmTime()));//派单时间
        //tv15.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//恢复时间
        tv16.setText(StringUtil.isEmpty(info.getMap().getTimeoutTime()) ? "" : info.getMap().getTimeoutTime());//故障时长
        tv17.setText(StringUtil.isEmpty(info.getMap().getHandlePersionName()) ? "" : info.getMap().getHandlePersionName());//工单负责人
        tv18.setText(StringUtil.isEmpty(info.getHandleTel()) ? "" : info.getHandleTel());//联系电话
        //tv19.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//协同处理人
        //tv20.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//联系电话
        //tv21.setText(StringUtil.isEmpty(info.getClosedLoopStatus()) ? "" : info.getClosedLoopStatus());//闭环状态
        //tv22.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//超时闭环时间
        tv23.setText(StringUtil.isEmpty(info.getAlarmRemark()) ? "" : info.getAlarmRemark());//告警发生原因


    }

    private void initClick() {
        etPhone1.setFocusable(false);
        etPhone2.setFocusable(false);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_help:
                        type = 1;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llAddPart.setVisibility(View.GONE);
                                llHelpPart.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                    case R.id.rb_add:
                        type = 2;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llAddPart.setVisibility(View.VISIBLE);
                                llHelpPart.setVisibility(View.GONE);
                            }
                        });
                        break;
                }
            }
        });
        tvXtclr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //协同处理人选择
                if (helpPeopleOptions == null || helpPeopleOptions.size() == 0) {
                    ToastUtil.toast("选项为空或获取数据错误，请稍后重试");
                    return;
                }
                PickerViewUtils.selectOptions(WorkOrderProblemUpActivity.this, "选择", helpPeopleOptions, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvXtclr.setText(helpPeopleOptions.get(options1));
                        id1 = clrHelp.get(options1).getId();
                        if (StringUtil.isEmpty(clrHelp.get(options1).getMobileNo())) {
                            //电话为空
                            //手填电话
                            etPhone1.setText("");
                            etPhone1.setFocusable(true);
                            etPhone1.setFocusableInTouchMode(true);
                        } else {
                            //带入电话信息
                            etPhone1.setFocusable(false);
                            etPhone1.setText(clrHelp.get(options1).getMobileNo());
                        }
                    }
                });
            }
        });
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //处理截止时间
                Calendar nowDate = Calendar.getInstance();
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(WorkOrderProblemUpActivity.this, new OnTimeSelectListener() {
                    public void onTimeSelect(final Date date, View v) {
                        tvTime.setText(formatter.format(date));//日期 String

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
        tvClr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //新增处理人选择
                if (addPeopleOptions == null || addPeopleOptions.size() == 0) {
                    ToastUtil.toast("选项为空或获取数据错误，请稍后重试");
                    return;
                }
                PickerViewUtils.selectOptions(WorkOrderProblemUpActivity.this, "选择", addPeopleOptions, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvClr2.setText(addPeopleOptions.get(options1));
                        id2 = clrAdd.get(options1).getId();
                        if (StringUtil.isEmpty(clrAdd.get(options1).getMobileNo())) {
                            //电话为空
                            //手填电话
                            etPhone2.setText("");
                            etPhone2.setFocusable(true);
                            etPhone2.setFocusableInTouchMode(true);

                        } else {
                            //带入电话信息
                            etPhone2.setFocusable(false);
                            etPhone2.setText(clrAdd.get(options1).getMobileNo());
                        }
                    }
                });
            }
        });
    }

    private void submit() {
        if (type == 0) {
            ToastUtil.toast("请选择处理方式！");
            return;
        }
        if (type == 1) {
            //协同处理
            if (StringUtil.isEmpty(tvXtclr.getText().toString())) {
                ToastUtil.toast("请选择协同处理人！");
                return;
            }
            if (StringUtil.isEmpty(etPhone1.getText().toString().trim())) {
                ToastUtil.toast("缺少协同处理人联系方式，请填写！");
                etPhone1.setFocusable(true);
                return;
            }
        } else {
            //新增节点
            if (StringUtil.isEmpty(tvTime.getText().toString())) {
                ToastUtil.toast("请选择截止时间！");
                return;
            }
            if (StringUtil.isEmpty(tvClr2.getText().toString())) {
                ToastUtil.toast("请选择处理人！");
                return;
            }
            if (StringUtil.isEmpty(etPhone2.getText().toString().trim())) {
                ToastUtil.toast("缺少处理人联系方式，请填写！");
                etPhone2.setFocusable(true);
                return;
            }
            if (StringUtil.isEmpty(etSm.getText().toString().trim())) {
                ToastUtil.toast("请填写说明！");
                return;
            }
        }
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("faultId", id);
        hashMap.put("dealMethod", type == 1 ? "HELP_DEAL" : "CHANGE_DEAL");
        hashMap.put("nameIdStr", id);
        if (type == 1) {
            hashMap.put("userId", id1);
            hashMap.put("tel", etPhone1.getText().toString().trim());
        } else {
            hashMap.put("remark", etSm.getText().toString().trim());
            hashMap.put("deadlineTime", tvTime.getText().toString().trim());
            hashMap.put("userId2", id2);
            hashMap.put("tel2", etPhone2.getText().toString().trim());

        }


        ApiClient.requestNetPost(this, AppConfig.getQsDealCommit, "提交中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_FAULT_LIST));
                finish();

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void getClrData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("faultId", id);
        ApiClient.requestNetPost(this, AppConfig.getQsDealer, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<WorkOrderClr> listOld = FastJsonUtil.getList(FastJsonUtil.getString(json, "ptUserInfosByOld"), WorkOrderClr.class);
                List<WorkOrderClr> listHelp = FastJsonUtil.getList(FastJsonUtil.getString(json, "ptUserInfos"), WorkOrderClr.class);
                List<WorkOrderClr> listAdd = FastJsonUtil.getList(FastJsonUtil.getString(json, "ptUserInfosByNew"), WorkOrderClr.class);

                if (listOld != null) {
                    clrOld.addAll(listOld);
                    initClrName();
                }
                if (listHelp != null) {
                    clrHelp.addAll(listHelp);
                    for (WorkOrderClr clr : listHelp) {
                        helpPeopleOptions.add(clr.getRealName());
                    }
                }
                if (listAdd != null) {
                    clrAdd.addAll(listAdd);
                    for (WorkOrderClr clr : listAdd) {
                        addPeopleOptions.add(clr.getRealName());
                    }
                }


            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

            }
        });
    }

    private void initClrName() {
        StringBuilder builder1 = new StringBuilder("");
        StringBuilder builder2 = new StringBuilder("");

        for (WorkOrderClr c : clrOld) {
            builder1.append(c.getRealName()).append("，");
            builder2.append(c.getId()).append(",");
        }
        if (builder1.length() != 0) {
            builder1.deleteCharAt(builder1.length() - 1);
        }
        if (builder2.length() != 0) {
            builder2.deleteCharAt(builder2.length() - 1);
        }
        tvClr.setText(builder1.toString());
        ids = builder2.toString();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        id = extras.getString("id");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
