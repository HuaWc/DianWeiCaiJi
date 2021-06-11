package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.InspectionResultAdminNext;

import java.util.List;

/**
 * Created by Christ on 2021/6/10.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class InspectionResultAdminNextAdapter extends BaseQuickAdapter<InspectionResultAdminNext, BaseViewHolder> {
    public InspectionResultAdminNextAdapter(@Nullable List<InspectionResultAdminNext> data) {
        super(R.layout.adapter_inspection_result_admin_next, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionResultAdminNext item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
