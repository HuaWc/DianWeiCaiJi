package com.hwc.dwcj.activity.second;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.view.dialog.BaseDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zds.base.entity.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 告警管理页面
 */
public class AlertManagementActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_wdsb)
    TextView tvWdsb;
    @BindView(R.id.tc_ssjg)
    TextView tcSsjg;
    @BindView(R.id.tv_zczl)
    TextView tvZczl;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_alert_management);
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
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_alert, null);
        View tv_sys = view.findViewById(R.id.tv_sys);
        View tv_hand = view.findViewById(R.id.tv_hand);
        View tv_cancel = view.findViewById(R.id.tv_cancel);
        BaseDialog.getInstance()
                .setLayoutView(view, this)
                .dissmissDialog()
                .setWindow(1, 0.5)
                .isCancelable(true)
                .setOnClickListener(tv_sys, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        toTheActivity(AddAlertActivity.class);
                    }
                })
                .setOnClickListener(tv_hand, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        toTheActivity(AddAlertActivity.class);
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
}
