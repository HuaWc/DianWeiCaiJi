package com.hwc.dwcj.adapter.second;

import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.InspectionUser;

import java.util.List;

/**
 * Created by Christ on 2021/6/10.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class InspectionUserAdapter extends BaseQuickAdapter<InspectionUser, BaseViewHolder> {

    public InspectionUserAdapter(@Nullable List<InspectionUser> data) {
        super(R.layout.adapter_inspection_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionUser item) {
        ProgressBar pb_red = helper.getView(R.id.pb_red);
        ProgressBar pb_blue = helper.getView(R.id.pb_blue);
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_status2 = helper.getView(R.id.tv_status2);
        TextView tv_status3 = helper.getView(R.id.tv_status3);

        helper.addOnClickListener(R.id.tv_map).addOnClickListener(R.id.tv_start).addOnClickListener(R.id.tv_check);

        helper.setText(R.id.tv_name, item.getTaskName())
                .setText(R.id.tv_time, item.getEffectiveTimeStart() + "-" + item.getEffectiveTimeEnd())
                .setText(R.id.tv_urgent, item.getMap().getUrgentType());
    }
}
