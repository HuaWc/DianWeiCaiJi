package com.hwc.dwcj.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.hwc.dwcj.R;
import com.hwc.dwcj.alipay.MyALipayUtils;
import com.hwc.dwcj.entity.ShareInfo;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.util.ShareUtil;
import com.hwc.dwcj.wxapi.WXPay;
import com.hwc.dwcj.wxapi.WeChatPayService;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.SystemUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zlw on 2018/08/06/上午 9:11
 * 与H5交互的Js方法基类
 */

public class BaseAndroidJs extends Object {

    public Activity mActivity;
    public WebView mWeb;

    public BaseAndroidJs(Activity mActivity, WebView mWeb) {
        this.mActivity = mActivity;
        this.mWeb = mWeb;
    }

    /**
     * 1.	js调用原生
     *  <> ①页面跳转  </>
     */

    /**
     * 跳转到首页
     */
    @JavascriptInterface
    public void appGoHome() {
        mActivity.finish();
//        mActivity.startActivity(new Intent(mActivity, MainActivity.class));
    }

    /**
     * 跳转到登录页面
     */
    @JavascriptInterface
    public void appToLogin() {
        MyApplication.getInstance().cleanUserInfo();
        MyApplication.getInstance().checkUserToLogin(mActivity);
    }

    /**
     * 跳转到个人中心
     */
    @JavascriptInterface
    public void appGoUserCenterk() {
        mActivity.finish();
    }

    /**
     * 跳转到购物车
     */
    @JavascriptInterface
    public void appGoShoppingCar() {

    }

    /**
     * 返回上一页
     */
    @JavascriptInterface
    public void appGoBack() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mWeb.canGoBack()) {
                    mWeb.goBack();
                } else {
                    mActivity.finish();
                }
            }
        });

    }

    /**
     * <> ②	功能函数 </>
     */

    /**
     * 获取用户信息
     */
    @JavascriptInterface
    public void appGetUserToken() {

    }

    /**
     * 微信支付
     */
    @JavascriptInterface
    @SuppressLint("SetJavaScriptEnabled")
    public void appDoWechatPayment(String order) {
        payWX(order.toString());
    }

    private void payWX(String msg) {
        WXPay wxPay = FastJsonUtil.getObject(msg, WXPay.class);
        if (wxPay != null) {
            WeChatPayService weChatPayService = new WeChatPayService(mActivity, wxPay);
            weChatPayService.pay();
        } else {
            ToastUtil.toast("支付失败");
        }
    }


    /**
     * 支付宝支付
     */
    @JavascriptInterface
    public void appDoAlipayPayment(String order) {
        MyALipayUtils.ALiPayBuilder builder = new MyALipayUtils.ALiPayBuilder();
        builder.build().toALiPay(mActivity, order);
    }

    /**
     * 拨打电话
     */
    @JavascriptInterface
    public void appCallPhone(final String phone) {
        PermissionsUtil.requestPermission(mActivity.getApplication(), new PermissionListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                mActivity.startActivity(intent);
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                ToastUtil.toast("请打开手机拨号权限");
            }
        }, Manifest.permission.CALL_PHONE);
    }

    /**
     * 退出
     */
    @JavascriptInterface
    public void appLogout() {
        MyApplication.getInstance().cleanUserInfo();
        mActivity.finish();
    }

    /**
     * 复制文字到剪贴板
     */
    @JavascriptInterface
    public void appCopyText(String text) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", text);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            ToastUtil.toast("复制成功");
        } catch (Exception e) {
        }
    }

    /**
     * 复制文字到剪贴板
     */
    @JavascriptInterface
    public void copyText(String text) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", text);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            ToastUtil.toast("复制成功");
        } catch (Exception e) {
        }
    }


    /**
     * 弹提示信息
     */
    @JavascriptInterface
    public void appShowMessage(String text) {
        ToastUtil.toast(text);
    }

    /**
     * 版本名
     */
    @JavascriptInterface
    public String appGetVersionName() {
        return "v" + SystemUtil.getAppVersionName();
    }

    /**
     * 版本号
     */
    @JavascriptInterface
    public String appGetVersionCode() {
        return SystemUtil.getAppVersionNumber() + "";
    }
    /**
     * 2.	原生调用js
     * <> 	(1)	常用函数  </>
     */

    /**
     * 支付成功
     *
     * @return
     */
    public static String jsPaySuccess() {
        return "javascript:jsPaySuccess()";
    }

    @JavascriptInterface
    public void appGetShare(String str) {
        ShareInfo shareInfo = FastJsonUtil.getObject(str, ShareInfo.class);
        if (shareInfo != null) {
            share(shareInfo);
        } else {
            ToastUtil.toast("数据异常");
        }
    }

    /**
     * 分享
     */
    private void share(final ShareInfo shareInfo) {
        MyApplication.getInstance().shareDialog(mActivity, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    /**
                     * 微信好友
                     */
                    case R.id.wx_chat:
                        ShareUtil.shareUrl(mActivity, shareInfo.getUrl(), shareInfo.getTitle(), shareInfo.getPrice(), shareInfo.getThumb(), SendMessageToWX.Req.WXSceneSession);
                        break;
                    /**
                     * 微信朋友圈
                     */
                    case R.id.wx_qun:
                        ShareUtil.shareUrl(mActivity, shareInfo.getUrl(), shareInfo.getTitle(), shareInfo.getPrice(), shareInfo.getThumb(), SendMessageToWX.Req.WXSceneTimeline);
                        break;
                    default:
                }
            }
        });
    }

}
