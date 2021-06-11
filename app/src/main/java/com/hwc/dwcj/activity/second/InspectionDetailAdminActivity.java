package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.zds.base.entity.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InspectionDetailAdminActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.all)
    LinearLayout all;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_inspection_detail_admin);
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
}
