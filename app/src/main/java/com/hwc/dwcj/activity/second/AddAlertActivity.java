package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.DictInfo;
import com.hwc.dwcj.entity.second.AssetEquipment;
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
 * 新增告警
 */
public class AddAlertActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_select_level)
    TextView tvSelectLevel;
    @BindView(R.id.tv_asset_name)
    TextView tvAssetName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_reason)
    EditText etReason;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.tv_jg)
    TextView tvJg;

    private int from = 0;//0默认手动填写进入  1扫码进入
    private String alarmLevel;
    private String assetId;
    private String orgId;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<String> levelOptions;
    private List<DictInfo> levelList;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_add_alert);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        getLevelData();
    }

    private void getLevelData() {
        levelList = new ArrayList<>();
        levelOptions = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("dataTypeCode", "OP_ALARM_LEVEL");
        ApiClient.requestNetPost(this, AppConfig.getDataTypeList, "", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<DictInfo> list = FastJsonUtil.getList(json, DictInfo.class);
                if (list != null) {
                    levelList.addAll(list);
                    for (DictInfo d : list) {
                        levelOptions.add(d.getDataName());
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvSelectLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard3();
                hideSoftKeyboard();
                if (levelOptions == null || levelOptions.size() == 0) {
                    ToastUtil.toast("条件为空，请稍后重试！");
                    return;
                }
                PickerViewUtils.selectOptions(AddAlertActivity.this, "告警等级", levelOptions, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvSelectLevel.setText(levelOptions.get(options1));
                        alarmLevel = levelList.get(options1).getDataValue();
                    }
                });
            }
        });
        tvAssetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTheActivity(SelectAssetEquipmentActivity.class);

            }
        });
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar nowDate = Calendar.getInstance();
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(AddAlertActivity.this, new OnTimeSelectListener() {
                    public void onTimeSelect(final Date date, View v) {
                        tvTime.setText(formatter.format(date));
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
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    private void submit() {
        if (StringUtil.isEmpty(etName.getText().toString().trim())) {
            ToastUtil.toast("请输入告警名称");
            return;
        }
        if (StringUtil.isEmpty(tvSelectLevel.getText().toString().trim())) {
            ToastUtil.toast("请选择告警等级");
            return;
        }
        if (StringUtil.isEmpty(tvJg.getText().toString().trim()) || StringUtil.isEmpty(orgId)) {
            ToastUtil.toast("请选择设备以带出机构信息");
            return;
        }
        if (StringUtil.isEmpty(etName.getText().toString().trim()) || StringUtil.isEmpty(assetId)) {
            ToastUtil.toast("请选择设备信息");
            return;
        }

        if (StringUtil.isEmpty(tvTime.getText().toString().trim())) {
            ToastUtil.toast("请选择发生时间");
            return;
        }
/*        if (StringUtil.isEmpty(etReason.getText().toString().trim())) {
            ToastUtil.toast("请输入发生原因");
            return;
        }*/
        //提交
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("alarmName", etName.getText().toString().trim());
        hashMap.put("alarmLevel", tvSelectLevel.getText().toString());
        hashMap.put("assetId", assetId);
        hashMap.put("alarmTime", tvTime.getText().toString().trim());
        if (!StringUtil.isEmpty(etReason.getText().toString().trim())) {
            hashMap.put("alarmReason", etReason.getText().toString().trim());
        }
        hashMap.put("alarmPersonId", MyApplication.getInstance().getUserInfo().getId());
        hashMap.put("orgId", orgId);
        ApiClient.requestNetPost(this, AppConfig.OpAlarmInfoAdd, "提交中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_ALERT_LIST));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }


    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil.SELECT_ASSET_EQUIPMENT:
                AssetEquipment assetEquipment = (AssetEquipment) center.getData();
                tvAssetName.setText(assetEquipment.getAssetName());
                assetId = assetEquipment.getAssetCode();
                orgId = assetEquipment.getOrgId();
                tvJg.setText(assetEquipment.getMap().getOrg_name());
                break;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        from = extras.getInt("from", 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
