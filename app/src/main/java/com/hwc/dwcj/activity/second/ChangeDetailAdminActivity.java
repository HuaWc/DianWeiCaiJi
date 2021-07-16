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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeDetailAdminActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_priority)
    TextView tvPriority;
    @BindView(R.id.tv_change_people)
    TextView tvChangePeople;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_require)
    TextView tvRequire;
    @BindView(R.id.tv_name_old)
    TextView tvNameOld;
    @BindView(R.id.tv_ip_old)
    TextView tvIpOld;
    @BindView(R.id.tv_jg_old)
    TextView tvJgOld;
    @BindView(R.id.tv_name_new)
    TextView tvNameNew;
    @BindView(R.id.tv_ip_new)
    TextView tvIpNew;
    @BindView(R.id.tv_jg_new)
    TextView tvJgNew;
    @BindView(R.id.tv_add_name)
    TextView tvAddName;
    @BindView(R.id.tv_change_status)
    TextView tvChangeStatus;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.et_reason)
    EditText etReason;
    @BindView(R.id.tv_bgdx)
    TextView tvBgdx;
    @BindView(R.id.tv_zq)
    TextView tvZq;
    @BindView(R.id.tv_cszt)
    TextView tvCszt;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_zt_old)
    TextView tvZtOld;
    @BindView(R.id.tv_zt_new)
    TextView tvZtNew;

    private String id;
    private ChangeUser info;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_change_detail_admin);
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
        tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拒绝
                if (StringUtil.isEmpty(etReason.getText().toString().trim())) {
                    ToastUtil.toast("拒绝请先填写拒绝理由！");
                    return;
                }
                doCheck(3);
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //接受
                doCheck(2);
            }
        });
    }

    private void doCheck(int type) {
        ChangeUser c = new ChangeUser();
        c.setId(id);
        c.setCheckStatus(type);
        c.setCheckReason(etReason.getText().toString().trim());
        c.setCheckPeopleId(MyApplication.getInstance().getUserInfo().getId());
        c.setCheckPeople(MyApplication.getInstance().getUserInfo().getRealName());
        c.setCheckTime(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'").format(new Date()));
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
        tvName.setText(StringUtil.isEmpty(info.getTaskName()) ? "" : info.getTaskName());
        switch (info.getPriority()) {
            case 1:
                tvPriority.setText("高");
                break;
            case 2:
                tvPriority.setText("中");
                break;
            case 3:
                tvPriority.setText("低");
                break;
        }
        tvBgdx.setText(StringUtil.isEmpty(info.getAssetName()) ? "" : info.getAssetName());
        tvZq.setText(StringUtil.dealDateFormat(info.getEffectiveStartTime()) + "~" + StringUtil.dealDateFormat(info.getEffectiveEndTime()));
        tvChangePeople.setText(StringUtil.isEmpty(info.getChangePeopleNames()) ? "" : info.getChangePeopleNames());
        tvContent.setText(StringUtil.isEmpty(info.getTaskContent()) ? "" : info.getTaskContent());
        tvRequire.setText(StringUtil.isEmpty(info.getTaskRequest()) ? "" : info.getTaskRequest());

        tvNameOld.setText(StringUtil.isEmpty(info.getAssetName()) ? "" : info.getAssetName());
        tvIpOld.setText(StringUtil.isEmpty(info.getAssetIp()) ? "" : info.getAssetIp());
        tvJgOld.setText(StringUtil.isEmpty(info.getAssetOrgid()) ? "" : info.getAssetOrgid());
        tvZtOld.setText(StringUtil.isEmpty(info.getAssetStatus()) ? "" : info.getAssetStatus());

        tvNameNew.setText(StringUtil.isEmpty(info.getAssetNameChanged()) ? "" : info.getAssetNameChanged());
        tvIpNew.setText(StringUtil.isEmpty(info.getAssetIpChanged()) ? "" : info.getAssetIpChanged());
        tvJgNew.setText(StringUtil.isEmpty(info.getAssetOrgidChanged()) ? "" : info.getAssetOrgidChanged());
        tvZtNew.setText(StringUtil.isEmpty(info.getAssetStatusChanged()) ? "" : info.getAssetStatusChanged());

        tvAddName.setText(StringUtil.isEmpty(info.getMap().getAddName()) ? "" : info.getMap().getAddName());
        if (info.getChangeStatus() != null) {
            switch (info.getChangeStatus()) {
                case 0:
                    tvChangeStatus.setText("待变更");
                    break;
                case 1:
                    tvChangeStatus.setText("已变更");
                    break;
                default:
                    tvChangeStatus.setText("");
                    break;
            }
        } else {
            tvChangeStatus.setText("");
        }

        try {
            Date d = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (df.parse(StringUtil.dealDateFormat(info.getEffectiveEndTime())).getTime() > d.getTime()) {
                tvCszt.setText("正常");
                //tvCszt.setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
            } else {
                tvCszt.setText("超时");
                //tvCszt.setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            tvCszt.setText("未知");
            //tvCszt.setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
        }

        tvTimeStart.setText(StringUtil.isEmpty(info.getAddTime()) ? "" : StringUtil.dealDateFormat(info.getAddTime()));
        //tvTimeEnd.setText(StringUtil.isEmpty(info.getEffectiveEndTime()) ? "" : StringUtil.dealDateFormat(info.getEffectiveEndTime()));
        tvRemark.setText(StringUtil.isEmpty(info.getChangeReport()) ? "" : info.getChangeReport());


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
