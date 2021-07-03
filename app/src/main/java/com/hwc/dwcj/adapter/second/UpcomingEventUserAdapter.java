package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.UpcomingEventUser;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * Created by Christ on 2021/6/8.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class UpcomingEventUserAdapter extends BaseQuickAdapter<UpcomingEventUser, BaseViewHolder> {
    public UpcomingEventUserAdapter(@Nullable List<UpcomingEventUser> data) {
        super(R.layout.adapter_upcoming_event_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UpcomingEventUser item) {
        helper.setText(R.id.tv_name, item.getAlarmName())
                .setText(R.id.tv_time, StringUtil.isEmpty(item.getAlarmTime()) ? "" : StringUtil.dealDateFormat(item.getAlarmTime()))
                .setText(R.id.tv_jg, StringUtil.isEmpty(item.getMap().getAssetName()) ? "" : item.getMap().getAssetName())
                .setText(R.id.tv_g, StringUtil.isEmpty(item.getMap().getOrgName()) ? "" : item.getMap().getOrgName())
                .setText(R.id.tv_status, StringUtil.isEmpty(item.getMap().getDeviceStatus()) ? "" : item.getMap().getDeviceStatus())
                .addOnClickListener(R.id.tv_look).addOnClickListener(R.id.tv_do).addOnClickListener(R.id.tv_track);
    }
}
