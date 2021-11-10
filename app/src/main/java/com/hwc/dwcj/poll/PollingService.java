package com.hwc.dwcj.poll;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.MessageInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.receiver.NotificationClickReceiver;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Christ on 2021/11/2.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class PollingService extends Service {
    public static final String ACTION = "com.hwc.dwcj.poll.PollingService";

    private NotificationManager mManager;

    private static final String CHANNEL_ID = "移动运维";   //通道渠道id
    public static final String CHANEL_NAME = "移动运维新通知"; //通道渠道名称

    private int count = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        initNotificationManager();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new PollingThread().start();
        return START_STICKY;
    }

    //初始化通知栏配置
    private void initNotificationManager() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    //弹出Notification
    private void showNotification(MessageInfo message) {
        Notification.Builder builder = new Notification.Builder(this);
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建 通知通道  channelid和channelname是必须的（自己命名就好）
            channel = new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);//是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.RED);//小红点颜色
            channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            builder.setChannelId(CHANNEL_ID);
        }

        builder.setSmallIcon(R.mipmap.notification_small); //设置图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_notification));//大图标
        builder.setContentTitle(StringUtil.isEmpty(message.getMsgTitle())?"暂无标题":message.getMsgTitle()); //设置标题
        builder.setContentText(StringUtil.isEmpty(message.getMsgContent())?"暂无内容":message.getMsgContent()); //消息内容
        builder.setWhen(System.currentTimeMillis()); //发送时间
        builder.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
        builder.setAutoCancel(true);//打开程序后图标消失
        Intent intent = new Intent(this, NotificationClickReceiver.class);
        intent.putExtra("data",message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        Notification notification1 = builder.build();
        count++;
        Log.i("Notification—Polling", message.getId()==null?" ":message.getId());
        mManager.notify(count, notification1); // 通过通知管理器发送通知


/*        Notification notification;
        //获取Notification实例   获取Notification实例有很多方法处理    在此我只展示通用的方法（虽然这种方式是属于api16以上，但是已经可以了，毕竟16以下的Android机很少了，如果非要全面兼容可以用）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //向上兼容 用Notification.Builder构造notification对象
            notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("新通知" + count)
                    .setContentText("测试消息内容" + count)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.notification_small)
                    .setColor(Color.parseColor("#FEDA26"))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_notification))
                    .setTicker("您有新通知")
                    .build();
        } else {
            //向下兼容 用NotificationCompat.Builder构造notification对象
            notification = new NotificationCompat.Builder(this)
                    .setContentTitle("新通知" + count)
                    .setContentText("测试消息内容" + count)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.notification_small)
                    .setColor(Color.parseColor("#FEDA26"))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_notification))
                    .setTicker("您有新通知")
                    .build();
        }

        //发送通知
        int notifiId = count;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mManager.createNotificationChannel(channel);
        }
        mManager.notify(notifiId, notification);*/


    }

    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     *
     * @Author Ryan
     * @Create 2013-7-13 上午10:18:34
     */

    class PollingThread extends Thread {
        @Override
        public void run() {
            Map<String, Object> hashMap = new HashMap();
            hashMap.put("pageNum", 1);
            hashMap.put("pageSize", 10);
            hashMap.put("pushState", 1);

            ApiClient.requestNetGet(PollingService.this, AppConfig.PtMsgReceiverMsgList, "", hashMap, new ResultListener() {
                @Override
                public void onSuccess(String json, String msg) {
                    List<MessageInfo> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), MessageInfo.class);
                    if (list != null && list.size() != 0) {
                        //推通知
                        for (MessageInfo m : list) {
                            showNotification(m);
                        }
                    }

                }

                @Override
                public void onFailure(String msg) {
                    ToastUtil.toast(msg);
                }
            });

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service:onDestroy");
    }

}
