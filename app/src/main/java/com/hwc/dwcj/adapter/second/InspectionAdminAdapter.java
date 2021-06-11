package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.InspectionAdmin;
import com.hwc.dwcj.entity.second.InspectionUser;

import java.util.List;

/**
 * Created by Christ on 2021/6/10.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class InspectionAdminAdapter extends BaseQuickAdapter<InspectionAdmin, BaseViewHolder> {

    public InspectionAdminAdapter(@Nullable List<InspectionAdmin> data) {
        super(R.layout.adapter_inspection_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionAdmin item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
