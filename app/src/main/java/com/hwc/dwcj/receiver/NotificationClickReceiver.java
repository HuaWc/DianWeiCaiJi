package com.hwc.dwcj.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hwc.dwcj.activity.second.TaskManageActivity;
import com.hwc.dwcj.activity.second.WorkOrderDetailActivity;
import com.hwc.dwcj.entity.second.MessageInfo;
import com.hwc.dwcj.util.EventUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Christ on 2021/11/8.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //todo 跳转之前要处理的逻辑
        String messageJson = intent.getStringExtra("json");
        MessageInfo message = FastJsonUtil.getObject(messageJson, MessageInfo.class);
        EventBus.getDefault().post(new EventCenter(EventUtil.REED_NOTIFICATION_MESSAGE,message));
        Log.i("Notification—Click", message.getId()==null?" ":message.getId());
        if (message.getMsgType() == null) {
            ToastUtil.toast("该消息通知类型未知！无法进入相关页面");
            return;
        }
        Bundle bundle = new Bundle();
        Intent newIntent;
        switch (message.getMsgType()) {
            case "1":
                //新增处理
            case "2":
                //协同处理
            case "3":
                //工单派发
            case "4":
                //工单催办
                bundle.putString("id", message.getFaultId());
                newIntent = new Intent(context, WorkOrderDetailActivity.class);
                newIntent.putExtras(bundle);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
                break;
            case "5":
                //变更下发
                bundle.putInt("enterPage", 1);
                newIntent = new Intent(context, TaskManageActivity.class);
                newIntent.putExtras(bundle);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
                break;
            case "6":
                //巡检下发
                bundle.putInt("enterPage", 0);
                newIntent = new Intent(context, TaskManageActivity.class);
                newIntent.putExtras(bundle);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
                break;
            case "7":
                //核查下发
                bundle.putInt("enterPage", 2);
                newIntent = new Intent(context, TaskManageActivity.class);
                newIntent.putExtras(bundle);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
                break;
            case "8":
                //保障下发
                bundle.putInt("enterPage", 3);
                newIntent = new Intent(context, TaskManageActivity.class);
                newIntent.putExtras(bundle);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
                break;
            default:
                ToastUtil.toast("该消息通知类型未知！无法进入相关页面");
        }

    }



}
