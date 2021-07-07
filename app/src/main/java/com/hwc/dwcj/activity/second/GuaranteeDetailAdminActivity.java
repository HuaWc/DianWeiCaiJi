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
import com.hwc.dwcj.entity.second.GuaranteeUser;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.EventUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuaranteeDetailAdminActivity extends BaseActivity {


    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
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
    @BindView(R.id.tv_bz)
    TextView tvBz;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.et_reason)
    EditText etReason;

    private String id;
    private GuaranteeUser info;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_guarantee_detail_admin);

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
        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //同意
                doCheck(2);
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
    }

    private void doCheck(int type) {
        GuaranteeUser i = new GuaranteeUser();
        i.setId(id);
        i.setCheckStatus(type);
        i.setCheckReason(etReason.getText().toString().trim());
        i.setCheckPeopleId(MyApplication.getInstance().getUserInfo().getId());
        i.setCheckPeople(MyApplication.getInstance().getUserInfo().getRealName());
        i.setCheckTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        ApiClient.requestNetPost(this, AppConfig.OpSecurityTaskEdit, "提交中", FastJsonUtil.toJSONString(i), new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_GUARANTEE_LIST));
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
        ApiClient.requestNetGet(this, AppConfig.OpSecurityTaskInfo, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                info = FastJsonUtil.getObject(json, GuaranteeUser.class);
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
        tv1.setText(StringUtil.isEmpty(info.getTaskName()) ? "" : info.getTaskName());
        tv2.setText(StringUtil.isEmpty(info.getPeopleNames()) ? "" : info.getPeopleNames());
        tv3.setText(StringUtil.isEmpty(info.getTaskContent()) ? "" : info.getTaskContent());
        tv4.setText(StringUtil.isEmpty(info.getTaskRequires()) ? "" : info.getTaskRequires());

        tv5.setText(StringUtil.isEmpty(info.getMap().getAddName()) ? "" : info.getMap().getAddName());
        tv6.setText(StringUtil.isEmpty(info.getAddTime()) ? "" : StringUtil.dealDateFormat(info.getAddTime()));
        //tv7.setText(StringUtil.isEmpty(info.getPeopleNames()) ? "" : info.getPeopleNames());
        switch (info.getSryFeedback()) {
            case 1:
                tv8.setText("保障完成");
                break;
            case 2:
                tv8.setText("保障未完成");
                break;
            default:
                tv8.setText("");
                break;
        }
        tvBz.setText(StringUtil.isEmpty(info.getSryContent()) ? "" : info.getSryContent());
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
