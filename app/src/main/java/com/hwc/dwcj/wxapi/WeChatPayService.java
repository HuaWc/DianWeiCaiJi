package com.hwc.dwcj.wxapi;

import android.annotation.SuppressLint;
import android.content.Context;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.log.XLog;


/**
 * 创建人 : skyCracks<br>
 * 创建时间 : 2016-7-18上午11:02:34<br>
 * 版本 : [v1.0]<br>
 * 类描述 : 微信支付实现服务端操作及后续调起支付<br>
 */
@SuppressLint("DefaultLocale")
public class WeChatPayService {

    private static final String TAG = WeChatPayService.class.getSimpleName();

    private IWXAPI wxApi;
    private Context context;
    /**
     * 订单类型
     */
    private int type;
    /**
     * 内部订单
     */
    private String out_trade_no;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 商品金额费用, 单位是分
     */
    private String total_fee;

    /**
     * //     * @param context      上下文环境
     * //     * @param out_trade_no 内部订单
     * //     * @param type         订单类型(不同订单类型区分) 只有一种类型的订单时可去掉
     * //     * @param body         商品描述
     * //     * @param total_fee    商品金额费用, 单位是分
     */
    public WeChatPayService(Context context, WXPay pay) {
        this.context = context;
        this.wxPay = pay;
        this.wxApi = WXAPIFactory.createWXAPI(context, pay.getAppid(),
                false);
    }

    private WXPay wxPay;

    public void pay() {
        // 检测是否安装了微信
        boolean isWeChat = wxApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isWeChat) {
            sendPayReq();
        } else {
            ToastUtil.toast("打开微信失败");
        }
    }

    /**
     * 发送支付请求
     */
    private void sendPayReq() {
        PayReq req = new PayReq();
        req.appId = wxPay.getAppid();
        req.partnerId = wxPay.getPartnerid();
        req.prepayId = wxPay.getPrepayid();
        req.nonceStr = wxPay.getNoncestr();
        req.timeStamp = wxPay.getTimestamp();
        req.packageValue = "Sign=WXPay";
        req.sign = wxPay.getSign();
        boolean b1 = wxApi.registerApp(wxPay.getAppid());
        boolean b = wxApi.sendReq(req);
        XLog.d("TAG", b + "api.sendReq(req)" + b1);
    }


}
