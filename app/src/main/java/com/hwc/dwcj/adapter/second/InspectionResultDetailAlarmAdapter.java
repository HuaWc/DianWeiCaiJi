package com.hwc.dwcj.adapter.second;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.InspectionResultDetailDTO;

import java.util.List;

public class InspectionResultDetailAlarmAdapter extends BaseQuickAdapter<InspectionResultDetailDTO.AlarmListDTO, BaseViewHolder> {
    public InspectionResultDetailAlarmAdapter(@Nullable List<InspectionResultDetailDTO.AlarmListDTO> data) {
        super(R.layout.item_inspection_result_detail_alarm, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionResultDetailDTO.AlarmListDTO item) {
        helper.setText(R.id.tv_title,"告警信息"+(helper.getLayoutPosition()+1));
        helper.setText(R.id.tv_alarm_level, TextUtils.isEmpty(item.alarmLevel)?"无":item.alarmLevel);
        helper.setText(R.id.tv_alram_content,TextUtils.isEmpty(item.alarmReason)?"无":item.alarmReason);
    }
}
