package com.hwc.dwcj.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.DACity;


import java.util.List;

public class DACityAdapter extends BaseQuickAdapter<DACity, BaseViewHolder> {
    public DACityAdapter(@Nullable List<DACity> data) {
        super(R.layout.adapter_da_shi, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DACity item) {
        View tv_city = helper.getView(R.id.tv_city);
        tv_city.setSelected(item.isSelected());
        helper.addOnClickListener(R.id.tv_city)
                .setText(R.id.tv_city, item.getDataName());
    }
}
