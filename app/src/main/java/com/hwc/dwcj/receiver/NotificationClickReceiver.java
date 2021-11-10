package com.hwc.dwcj.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hwc.dwcj.activity.LoginActivity;
import com.hwc.dwcj.activity.second.TaskManageActivity;
import com.hwc.dwcj.activity.second.WorkOrderDetailActivity;
import com.hwc.dwcj.entity.second.MessageInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.zds.base.Toast.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Christ on 2021/11/8.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //todo 跳转之前要处理的逻辑
        MessageInfo message = (MessageInfo) intent.getExtras().get("data");
        read(context,message);
        Log.i("Notification—Click", message.getId()==null?" ":message.getId());
        Bundle bundle = new Bundle();
        Intent newIntent;
        if (message.getMsgType() == null) {
            ToastUtil.toast("该消息通知类型未知！无法进入相关页面");
            return;
        }
        switch (message.getMsgType()) {
            case "1":
                //新增处理
                bundle.putString("id", message.getFaultId());
                newIntent = new Intent(context, WorkOrderDetailActivity.class);
                newIntent.putExtras(bundle);
                context.startActivity(newIntent);
                break;
            case "2":
                //协同处理
                bundle.putString("id", message.getFaultId());
                newIntent = new Intent(context, WorkOrderDetailActivity.class);
                newIntent.putExtras(bundle);
                context.startActivity(newIntent);
                break;
            case "3":
                //工单派发
                bundle.putString("id", message.getFaultId());
                newIntent = new Intent(context, WorkOrderDetailActivity.class);
                newIntent.putExtras(bundle);
                context.startActivity(newIntent);
                break;
            case "4":
                //工单催办
                bundle.putString("id", message.getFaultId());
                newIntent = new Intent(context, WorkOrderDetailActivity.class);
                newIntent.putExtras(bundle);
                context.startActivity(newIntent);
                break;
            case "5":
                //变更下发
                bundle.putInt("enterPage", 1);
                newIntent = new Intent(context, TaskManageActivity.class);
                newIntent.putExtras(bundle);
                context.startActivity(newIntent);
                break;
            case "6":
                //巡检下发
                bundle.putInt("enterPage", 0);
                newIntent = new Intent(context, TaskManageActivity.class);
                newIntent.putExtras(bundle);
                context.startActivity(newIntent);
                break;
            case "7":
                //核查下发
                bundle.putInt("enterPage", 2);
                newIntent = new Intent(context, TaskManageActivity.class);
                newIntent.putExtras(bundle);
                context.startActivity(newIntent);
                break;
            case "8":
                //保障下发
                bundle.putInt("enterPage", 3);
                newIntent = new Intent(context, TaskManageActivity.class);
                newIntent.putExtras(bundle);
                context.startActivity(newIntent);
                break;
            default:
                ToastUtil.toast("该消息通知类型未知！无法进入相关页面");
        }

    }




    private void read(Context context,MessageInfo messageInfo) {
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("msgId", messageInfo.getMsgId());
        hashMap.put("msgReceId", messageInfo.getId());
        ApiClient.requestNetGet(context, AppConfig.PtMsgReceiverChange, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {

            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }
}
