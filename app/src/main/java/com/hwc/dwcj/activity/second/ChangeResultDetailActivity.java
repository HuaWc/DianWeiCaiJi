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
import com.hwc.dwcj.entity.second.ChangeUser;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.EventUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
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

    private String id;
    private ChangeUser info;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_change_result_detail);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        getData();
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
    }

    private void submit() {
        if (StringUtil.isEmpty(etNewIp.getText().toString().trim()) && StringUtil.isEmpty(etNewName.getText().toString().trim()) && StringUtil.isEmpty(etNewJg.getText().toString().trim())) {
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
        if (!StringUtil.isEmpty(etNewName.getText().toString().trim())) {
            c.setAssetNameChanged(etNewName.getText().toString().trim());
        }
        if (!StringUtil.isEmpty(etNewIp.getText().toString().trim())) {
            c.setAssetIpChanged(etNewIp.getText().toString().trim());
        }
        if (!StringUtil.isEmpty(etNewJg.getText().toString().trim())) {
            c.setAssetOrgidChanged(etNewJg.getText().toString().trim());
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
