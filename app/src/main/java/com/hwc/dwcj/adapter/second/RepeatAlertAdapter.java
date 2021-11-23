package com.hwc.dwcj.adapter.second;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.second.AlertMenuInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

public class RepeatAlertAdapter extends BaseQuickAdapter<AlertMenuInfo, BaseViewHolder> {
    public RepeatAlertAdapter(@Nullable List<AlertMenuInfo> data) {
        super(R.layout.adapter_repeat_alert, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlertMenuInfo item) {
        helper.setText(R.id.tv_name, item.getAlarmName())
                .setText(R.id.tv_ip, item.getIp())
                .setText(R.id.tv_status, item.getAlarmStatus())
                .setText(R.id.tv_device_name, item.getMap().getAssetName())
                .setText(R.id.tv_time, item.getMap().getAlarmTime())
                .setText(R.id.tv_alarm_type, item.getAlarmSource())
                .setText(R.id.tv_alarm_reason, item.getAlarmReason());


    }
}
