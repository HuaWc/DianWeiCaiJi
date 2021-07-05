package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.WorkOrderUser;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * Created by Christ on 2021/6/7.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class WorkOrderUserAdapter extends BaseQuickAdapter<WorkOrderUser, BaseViewHolder> {
    public WorkOrderUserAdapter(@Nullable List<WorkOrderUser> data) {
        super(R.layout.adapter_work_order_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkOrderUser item) {
        helper.setText(R.id.tv_name, item.getAlarmName())
                .setText(R.id.tv_time, StringUtil.isEmpty(item.getAlarmTime()) ? "" : StringUtil.dealDateFormat(item.getAlarmTime()))
                .setText(R.id.tv_jg, item.getMap().getAssetNatureName() + ">" + item.getMap().getAssetTypeName() + ">" + item.getMap().getAssetClassName())
                .setText(R.id.tv_g, StringUtil.isEmpty(item.getMap().getOrgName()) ? "" : item.getMap().getOrgName())
                .setText(R.id.tv_status, StringUtil.isEmpty(item.getMap().getDeviceStatus()) ? "" : item.getMap().getDeviceStatus())
                .addOnClickListener(R.id.tv_look).addOnClickListener(R.id.tv_do).addOnClickListener(R.id.tv_track).addOnClickListener(R.id.tv_check);
    }
}
