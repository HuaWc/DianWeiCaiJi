package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.DictInfo;
import com.hwc.dwcj.entity.second.ChangeUser;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.GetDictDataHttp;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/6/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class ChangeResultDetailActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_old_name)
    TextView tvOldName;
    @BindView(R.id.tv_old_ip)
    TextView tvOldIp;
    @BindView(R.id.tv_old_jg)
    TextView tvOldJg;
    @BindView(R.id.et_new_name)
    EditText etNewName;
    @BindView(R.id.et_new_ip)
    EditText etNewIp;
    @BindView(R.id.et_new_jg)
    EditText etNewJg;
    @BindView(R.id.tv_people)
    TextView tvPeople;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_report)
    EditText etReport;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.tv_old_status)
    TextView tvOldStatus;
    @BindView(R.id.tv_new_status)
    TextView tvNewStatus;

    private String id;
    private ChangeUser info;
    private List<String> statusOptions;
    private List<DictInfo> statusList;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_change_result_detail);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        statusOptions = new ArrayList<>();
        statusList = new ArrayList<>();
        getStatusData();
        getData();
        tvPeople.setText(MyApplication.getInstance().getUserInfo().getRealName());
    }

    private void getStatusData() {
        GetDictDataHttp.getDictData(this, "OP_ASSET_STATUS", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    statusList.addAll(list);
                    for (DictInfo d : list) {
                        statusOptions.add(d.getDataName());
                    }
                }
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
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        tvNewStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                hideSoftKeyboard3();
                if (statusOptions == null || statusOptions.size() == 0) {
                    ToastUtil.toast("选项数据获取失败！");
                    return;
                }
                PickerViewUtils.selectOptions(ChangeResultDetailActivity.this, "资产状态", statusOptions, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvNewStatus.setText(statusOptions.get(options1));
                    }
                });
            }
        });
    }

    private void submit() {
        if (StringUtil.isEmpty(etNewIp.getText().toString().trim()) && StringUtil.isEmpty(etNewName.getText().toString().trim()) && StringUtil.isEmpty(etNewJg.getText().toString().trim()) && StringUtil.isEmpty(tvNewStatus.getText().toString().trim())) {
            ToastUtil.toast("请至少填写一项变更值！");
            return;
        }
        if (StringUtil.isEmpty(etReport.getText().toString().trim())) {
            ToastUtil.toast("请填写变更报告！");
            return;
        }
        ChangeUser c = new ChangeUser();
        c.setId(id);
        c.setChangeStatus(1);
        c.setCheckStatus(1);
        c.setChangeReport(etReport.getText().toString().trim());
        c.setChangePeopleId(MyApplication.getInstance().getUserInfo().getId());
        c.setChangTime(StringUtil.dealDateFormat(new Date()));
        if (!StringUtil.isEmpty(etNewName.getText().toString().trim())) {
            c.setAssetNameChanged(etNewName.getText().toString().trim());
        }
        if (!StringUtil.isEmpty(etNewIp.getText().toString().trim())) {
            c.setAssetIpChanged(etNewIp.getText().toString().trim());
        }
        if (!StringUtil.isEmpty(etNewJg.getText().toString().trim())) {
            c.setAssetOrgidChanged(etNewJg.getText().toString().trim());
        }
        if (!StringUtil.isEmpty(tvNewStatus.getText().toString().trim())) {
            c.setAssetStatusChanged(tvNewStatus.getText().toString().trim());
        }
        ApiClient.requestNetPost(this, AppConfig.OpChangeTaskEdit, "提交中", FastJsonUtil.toJSONString(c), new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_CHANGE_LIST));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

            }
        });
    }


    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        ApiClient.requestNetGet(this, AppConfig.OpChangeTaskInfo, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                info = FastJsonUtil.getObject(json, ChangeUser.class);
                initData();
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void initData() {
        if (info == null) {
            return;
        }

        tvContent.setText(StringUtil.isEmpty(info.getTaskContent()) ? "" : info.getTaskContent());

        tvOldName.setText(StringUtil.isEmpty(info.getAssetName()) ? "" : info.getAssetName());
        tvOldIp.setText(StringUtil.isEmpty(info.getAssetIp()) ? "" : info.getAssetIp());
        tvOldJg.setText(StringUtil.isEmpty(info.getAssetOrgid()) ? "" : info.getAssetOrgid());
        tvOldStatus.setText(StringUtil.isEmpty(info.getAssetStatus()) ? "" : info.getAssetStatus());

        tvReason.setText(StringUtil.isEmpty(info.getCheckReason()) ? "" : info.getCheckReason());

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
