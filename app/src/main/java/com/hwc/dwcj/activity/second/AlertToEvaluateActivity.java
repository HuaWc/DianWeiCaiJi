package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Christ on 2021/6/29.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class AlertToEvaluateActivity extends BaseActivity {
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
    @BindView(R.id.iv_up1)
    ImageView ivUp1;
    @BindView(R.id.iv_down1)
    ImageView ivDown1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.iv_up2)
    ImageView ivUp2;
    @BindView(R.id.iv_down2)
    ImageView ivDown2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.iv_up3)
    ImageView ivUp3;
    @BindView(R.id.iv_down3)
    ImageView ivDown3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.iv_up4)
    ImageView ivUp4;
    @BindView(R.id.Iv_down4)
    ImageView IvDown4;

    private String alarmId;
    private int maxScore = 10;
    private int minScore = 1;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_alert_to_evaluate);

    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
    }


    private void initClick() {
        tv1.setText(String.valueOf(maxScore));
        tv2.setText(String.valueOf(maxScore));
        tv3.setText(String.valueOf(maxScore));
        tv4.setText(String.valueOf(maxScore));

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // doEvaluate();
            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        alarmId = extras.getString("alarmId");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_up1, R.id.iv_down1, R.id.iv_up2, R.id.iv_down2, R.id.iv_up3, R.id.iv_down3, R.id.iv_up4, R.id.Iv_down4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_up1:
                checkScore(true, tv1);
                break;
            case R.id.iv_down1:
                checkScore(false, tv1);
                break;
            case R.id.iv_up2:
                checkScore(true, tv2);
                break;
            case R.id.iv_down2:
                checkScore(false, tv2);
                break;
            case R.id.iv_up3:
                checkScore(true, tv3);
                break;
            case R.id.iv_down3:
                checkScore(false, tv3);
                break;
            case R.id.iv_up4:
                checkScore(true, tv4);
                break;
            case R.id.Iv_down4:
                checkScore(false, tv4);
                break;
        }
    }


    private void checkScore(boolean plus, TextView v) {
        int scoreGet = Integer.parseInt(v.getText().toString());
        if (plus) {
            if (scoreGet == maxScore) {
                ToastUtil.toast("已经达到最高分，不能再高了！");
                return;
            }
            v.setText(String.valueOf(scoreGet + 1));
        } else {
            if (scoreGet == minScore) {
                ToastUtil.toast("已经达到最低分，不能再低了！");
                return;
            }
            v.setText(String.valueOf(scoreGet - 1));
        }
    }
}
