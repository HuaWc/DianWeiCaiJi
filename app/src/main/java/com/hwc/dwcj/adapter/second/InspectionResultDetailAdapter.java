package com.hwc.dwcj.adapter.second;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.InspectionResultDetailDTO;

import java.util.List;

public class InspectionResultDetailAdapter extends BaseQuickAdapter<InspectionResultDetailDTO.ItemListDTO, BaseViewHolder> {
    public InspectionResultDetailAdapter(@Nullable List<InspectionResultDetailDTO.ItemListDTO> data) {
        super(R.layout.item_inspection_result_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionResultDetailDTO.ItemListDTO item) {
        helper.setText(R.id.tv_title, TextUtils.isEmpty(item.itemName)?"无":item.itemName);
        helper.setText(R.id.tv_content,TextUtils.isEmpty(item.itemValue)?"无":item.itemValue);
    }
}
