package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.InspectionManageDetailInfoDTO;

import java.util.List;

public class InspectionDetailInfoAdapter extends BaseQuickAdapter<InspectionManageDetailInfoDTO, BaseViewHolder> {
    public InspectionDetailInfoAdapter(@Nullable List<InspectionManageDetailInfoDTO> data) {
        super(R.layout.item_inspection_detail_info,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionManageDetailInfoDTO item) {
        helper.setText(R.id.tv_name,item.orgName);
        helper.setText(R.id.tv_subtitle,"告警数量");
        helper.setText(R.id.tv_content,item.alarmLevel);
    }
}
