package com.hwc.dwcj.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.DAItem;

import java.util.List;

public class DAItemAdapter extends BaseQuickAdapter<DAItem, BaseViewHolder> {
    public DAItemAdapter(@Nullable List<DAItem> data) {
        super(R.layout.da_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DAItem item) {
        helper.setText(R.id.tv_name, item.getAddress())
                .setText(R.id.tv_g,item.getMap().getOrgName());
    }
}
