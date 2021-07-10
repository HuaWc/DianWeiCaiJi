package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.InspectionManageDetailDTO;

import java.util.List;

public class InspectionDetailAdapter extends BaseQuickAdapter<InspectionManageDetailDTO.MapDTO.OpInspectionGroupListDTO, BaseViewHolder> {
    public InspectionDetailAdapter(@Nullable List<InspectionManageDetailDTO.MapDTO.OpInspectionGroupListDTO> data) {
        super(R.layout.item_inspection_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionManageDetailDTO.MapDTO.OpInspectionGroupListDTO item) {
        helper.setText(R.id.tv_title,item.groupName);

        RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.rv_inspection_detail);
        InspectionDetailInfoAdapter adapter = new InspectionDetailInfoAdapter(item.map.alarmList);
//        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
