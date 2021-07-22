package com.hwc.dwcj.adapter.second;

import android.graphics.Typeface;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.MessageInfo;

import java.util.List;

/**
 * Created by Christ on 2021/7/15.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class MessageInfoAdapter extends BaseQuickAdapter<MessageInfo, BaseViewHolder> {
    public MessageInfoAdapter(@Nullable List<MessageInfo> data) {
        super(R.layout.adapter_message_info, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageInfo item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        helper.setText(R.id.tv_name, "【" + item.getMsgTitle() + "】" + item.getMsgContent());
        if (item.getIsDel() == 0) {
            tv_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            tv_name.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }
}
