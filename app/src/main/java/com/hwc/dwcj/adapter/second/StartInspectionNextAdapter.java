package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.StartInspection;
import com.hwc.dwcj.entity.second.StartInspectionNext;

import java.util.List;

/**
 * Created by Christ on 2021/6/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class StartInspectionNextAdapter extends BaseQuickAdapter<StartInspectionNext, BaseViewHolder> {
    public StartInspectionNextAdapter(@Nullable List<StartInspectionNext> data) {
        super(R.layout.adapter_start_inspection, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StartInspectionNext item) {
        helper.setText(R.id.tv_num, item.getName());
    }
}
