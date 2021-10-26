package com.hwc.dwcj.http;

import android.content.Context;

import com.zds.base.Toast.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Christ on 2021/7/20.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class GetWorkOrderImgHttp {
    public static void getImg(String faultId, Context mContext,ImgDataListener imgDataListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("faultId", faultId);
        ApiClient.requestNetPost(mContext, AppConfig.getCameraImgCommon, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                imgDataListener.result(json);
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
                //ToastUtil.toast("获取相关图片数据失败");
            }
        });
    }


    public static void getImgByFtpAddress(String ftpAddress, Context mContext,ImgDataListener imgDataListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("ftpURL", ftpAddress);
        ApiClient.requestNetPost(mContext, AppConfig.getCameraImgCommon, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                imgDataListener.result(json);
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
                //ToastUtil.toast("获取相关图片数据失败");
            }
        });
    }

    public interface ImgDataListener {
        void result(String json);
    }
}
