package com.hwc.dwcj.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.base.Constant;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.view.MyClickText;
import com.hwc.dwcj.view.dialog.CommonDialog;
import com.zds.base.entity.EventCenter;
import com.zds.base.util.Preference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 赵大帅
 * 日期 2018/10/12
 * 描述
 */
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.img_welcome)
    ImageView imgWelcome;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void initLogic() {
        //imgWelcome.setImageResource(R.mipmap.img_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //do something
                if (MyApplication.getInstance().checkUser()) {
                    if (MyApplication.getInstance().getUserInfo().isRemember()) {
                        toTheActivity(MainActivity.class);
                    } else {
                        MyApplication.getInstance().cleanUserInfo();
                        toTheActivity(LoginActivity.class);
                    }
                } else {
                    MyApplication.getInstance().cleanUserInfo();
                    toTheActivity(LoginActivity.class);
                }

                finish();

            }
        }, 3000);
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
