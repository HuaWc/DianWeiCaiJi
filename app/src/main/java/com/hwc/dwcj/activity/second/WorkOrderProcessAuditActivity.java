package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.EventUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/6/8.
 * By an amateur android developer
 * Email 627447123@qq.com
 * <p>
 * <p>
 * 工单处理（管理员） - 事件审核 - 流程审核
 */
public class WorkOrderProcessAuditActivity extends BaseActivity {
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

    private String id;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_process_audit);

    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
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
                // doCheck(true);
            }
        });
        tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // doCheck(false);

            }
        });
    }


    private void doCheck(boolean agree) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        if (agree) {
            hashMap.put("verifyStatus", 1);
        } else {
            hashMap.put("verifyStatus", 2);
            hashMap.put("verifyRemark", "");
        }
        ApiClient.requestNetPost(this, AppConfig.OpFaultInfoCheck, "加载中", hashMap, new ResultListener() {
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
