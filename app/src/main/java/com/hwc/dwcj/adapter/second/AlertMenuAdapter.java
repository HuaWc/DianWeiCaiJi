package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.AlertMenuInfo;

import java.util.List;

public class AlertMenuAdapter extends BaseQuickAdapter<AlertMenuInfo, BaseViewHolder> {
    public AlertMenuAdapter(@Nullable List<AlertMenuInfo> data) {
        super(R.layout.adapter_main_alert, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlertMenuInfo item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
