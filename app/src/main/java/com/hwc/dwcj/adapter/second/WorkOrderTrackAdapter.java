package com.hwc.dwcj.adapter.second;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.WorkOrderTrackInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * Created by Christ on 2021/6/30.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class WorkOrderTrackAdapter extends BaseQuickAdapter<WorkOrderTrackInfo, BaseViewHolder> {
    public WorkOrderTrackAdapter(@Nullable List<WorkOrderTrackInfo> data) {
        super(R.layout.adapter_work_order_track, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkOrderTrackInfo item) {
        ImageView down = helper.getView(R.id.iv_arrow_down);
        if (mData.indexOf(item) == (mData.size() - 1)) {
            down.setVisibility(View.GONE);

        } else {
            down.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_name, StringUtil.isEmpty(item.getAlarmStep()) ? "" : item.getAlarmStep())
                .setText(R.id.tv_time, StringUtil.isEmpty(item.getHandleTime()) ? "" : item.getHandleTime())
                .setText(R.id.tv_people, "处理人：" + (StringUtil.isEmpty(item.getAlarmSourceOrPersonName()) ? "" : item.getAlarmSourceOrPersonName()))
                .setText(R.id.tv_phone, "联系电话：" + (StringUtil.isEmpty(item.getTel()) ? "" : item.getTel()));
    }
}
