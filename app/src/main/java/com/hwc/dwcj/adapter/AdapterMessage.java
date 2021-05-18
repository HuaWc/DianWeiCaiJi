package com.hwc.dwcj.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.MessageInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * @author Administrator
 *         日期 2018/6/25
 *         描述 消息
 */

public class AdapterMessage extends BaseQuickAdapter<MessageInfo, BaseViewHolder> {

    public AdapterMessage(@Nullable List<MessageInfo> data) {
        super(R.layout.adapter_message, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, MessageInfo item) {
        helper.setText(R.id.tv_msg_title, item.getTitle());
        helper.setText(R.id.tv_msg_content, item.getDetail());
        long time = 0;
        try {
            time = Long.valueOf(item.getCreatetime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        helper.setText(R.id.tv_time, StringUtil.formatDateMinute2(time*1000));
    }
}