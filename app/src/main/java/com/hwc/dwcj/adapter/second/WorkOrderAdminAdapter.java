package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.WorkOrderAdmin;
import com.hwc.dwcj.entity.second.WorkOrderUser;

import java.util.List;

/**
 * Created by Christ on 2021/6/7.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class WorkOrderAdminAdapter extends BaseQuickAdapter<WorkOrderAdmin, BaseViewHolder> {
    public WorkOrderAdminAdapter(@Nullable List<WorkOrderAdmin> data) {
        super(R.layout.adapter_work_order_admin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkOrderAdmin item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
