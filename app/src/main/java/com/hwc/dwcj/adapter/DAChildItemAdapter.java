package com.hwc.dwcj.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.DAChildItem;

import java.util.List;

public class DAChildItemAdapter extends BaseQuickAdapter<DAChildItem, BaseViewHolder> {
    public DAChildItemAdapter(@Nullable List<DAChildItem> data) {
        super(R.layout.da_child_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DAChildItem item) {
        helper.setText(R.id.tv_name, item.getCameraName()).setText(R.id.tv_g, item.getPoliceStation());
    }
}
