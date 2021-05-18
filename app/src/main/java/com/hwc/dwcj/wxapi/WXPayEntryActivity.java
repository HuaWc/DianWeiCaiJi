package com.hwc.dwcj.wxapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hwc.dwcj.util.EventUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import org.greenrobot.eventbus.EventBus;


@SuppressLint("Registered")
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WeChatConstans.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        // 支付结果回调...
        if (req.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (req.getType() == 0) {//支付成功
                ToastUtil.toast("支付成功!");
                EventBus.getDefault().post(new EventCenter(EventUtil.PAY_SUCCESS));
                finish();
            } else {
                WXPayEntryActivity.this.finish();
                ToastUtil.toast("支付失败!");
            }
        } else {
            WXPayEntryActivity.this.finish();
        }
    }

    @Override
    public void onResp(BaseResp resp) {
        // 支付结果回调...
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {//支付成功
                ToastUtil.toast("支付成功!");
                EventBus.getDefault().post(new EventCenter(EventUtil.PAY_SUCCESS));
                WXPayEntryActivity.this.finish();
            } else {
                WXPayEntryActivity.this.finish();
                ToastUtil.toast("支付失败!");
            }
        } else {
            WXPayEntryActivity.this.finish();
        }
    }

}