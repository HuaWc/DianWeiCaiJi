package com.hwc.dwcj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.AESCipher;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * 日期 2018/8/16
 * 描述 注册
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.bar)
    View mBar;
    @BindView(R.id.icon_back)
    ImageView mIconBack;
    @BindView(R.id.ll_back)
    LinearLayout mLlBack;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.img_right)
    ImageView mImgRight;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    RelativeLayout mToolbar;
    @BindView(R.id.view_line)
    View mViewLine;
    @BindView(R.id.rel_con)
    RelativeLayout mRelCon;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_password2)
    EditText mEtPassword2;
    @BindView(R.id.tv_xiyi)
    TextView mTvXiyi;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.et_tuijianren)
    EditText etTuijianren;

    private CountDownTimer timer;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initLogic() {
        mViewLine.setVisibility(View.GONE);
        countDown();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }


    private void countDown() {
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvCode.setText(millisUntilFinished / 1000 + "s后重新获取");
                mTvCode.setEnabled(false);
            }

            @Override
            public void onFinish() {
                mTvCode.setText("获取验证码");
                mTvCode.setEnabled(true);
            }
        };
    }

    /**
     * 注册
     */
    public void register() {
        final String phone = mEtPhone.getText().toString();
        final String pwd = mEtPassword.getText().toString();
        final String pwd2 = mEtPassword2.getText().toString();
        String code = mEtCode.getText().toString();

        if (StringUtil.isEmpty(phone)) {
            ToastUtil.toast("手机号不能为空");
            mEtPhone.requestFocus();
            return;
        } else if (StringUtil.isEmpty(code)) {
            ToastUtil.toast("验证码不能为空");
            mEtCode.requestFocus();
            return;
        } else if (StringUtil.isEmpty(pwd)) {
            ToastUtil.toast("密码不能为空");
            mEtPassword.requestFocus();
            return;
        } else if (pwd.length() > 16 || pwd.length() < 6) {
            ToastUtil.toast("请输入6-16位密码");
            mEtPassword.requestFocus();
            return;
        } else if (StringUtil.isEmpty(pwd2) || !pwd.equals(pwd2)) {
            ToastUtil.toast("密码输入不一致");
            mEtPassword.requestFocus();
            return;
        }

        final Map<String, Object> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("verifycode", code);
        map.put("pwd", pwd);
        if (!StringUtil.isEmpty(etTuijianren)) {
            map.put("recommend", etTuijianren.getText().toString());
        }
        ApiClient.requestNetPost(this, AppConfig.registerUrl, "正在提交...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast("注册成功");
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        final String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast("手机号不能为空");
            mEtPhone.requestFocus();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("temp", "sms_reg");
        map.put("mobile", AESCipher.encrypt(phone));
        ApiClient.requestNetGet(this, AppConfig.getPhoneCodeUrl, "获取验证码...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    ToastUtil.toast(msg);
                    timer.start();
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_code, R.id.tv_xiyi, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                getCode();
                break;
            case R.id.tv_xiyi:
                startActivity(new Intent(RegisterActivity.this, WebViewActivity.class).putExtra("title", "用户协议").putExtra("url", AppConfig.zcxy));
                break;
            case R.id.btn_register:
                register();
                break;
            default:
        }
    }
}
