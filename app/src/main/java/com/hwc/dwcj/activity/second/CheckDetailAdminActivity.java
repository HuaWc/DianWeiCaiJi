package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.CheckUser;
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

public class CheckDetailAdminActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.all)
    LinearLayout all;
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
    @BindView(R.id.tv_bz)
    TextView tvBz;

    private String id;
    private CheckUser info;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_check_detail_admin);

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
        tvSure.setOnClickListener(new View.OnClickListener() {
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
                doCheck(3);
            }
        });
    }

    private void doCheck(int type) {
        Map<String, Object> hashMap = new HashMap<>();
        CheckUser i = new CheckUser();
        i.setId(id);
        i.setCheckStatus(type);
        hashMap.put("opVerificationTask", FastJsonUtil.toJSONString(i));
        ApiClient.requestNetPost(this, AppConfig.OpVerificationTaskEdit, "提交中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_CHECK_LIST));
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
        ApiClient.requestNetGet(this, AppConfig.OpVerificationTaskInfo, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                info = FastJsonUtil.getObject(json, CheckUser.class);
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
        tv2.setText(StringUtil.isEmpty(info.getVerPeopleNames()) ? "" : info.getVerPeopleNames());
        tv4.setText(StringUtil.isEmpty(info.getVerContent()) ? "" : info.getVerContent());
        tv5.setText(StringUtil.isEmpty(info.getTaskrequires()) ? "" : info.getTaskrequires());
        tv7.setText(StringUtil.isEmpty(info.getMap().getAddName())?"":info.getMap().getAddName());
        tv9.setText(StringUtil.isEmpty(info.getAddTime()) ? "" : StringUtil.dealDateFormat(info.getAddTime()));
        switch (info.getVerFeedback()) {
            case 1:
                tv11.setText("正常");
                break;
            case 2:
                tv11.setText("异常");
                break;
            default:
                tv11.setText("");
                break;
        }
        tvBz.setText(StringUtil.isEmpty(info.getExp1()) ? "" : info.getExp1());

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
