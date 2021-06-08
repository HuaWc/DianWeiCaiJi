package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.UpcomingEventAdmin;

import java.util.List;

/**
 * Created by Christ on 2021/6/8.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class UpcomingEventAdminAdapter extends BaseQuickAdapter<UpcomingEventAdmin, BaseViewHolder> {
    public UpcomingEventAdminAdapter(@Nullable List<UpcomingEventAdmin> data) {
        super(R.layout.adapter_upcoming_event_admin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UpcomingEventAdmin item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
