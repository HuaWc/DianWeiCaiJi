package com.hwc.dwcj.adapter.second;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.DictInfo;

import java.util.List;

/**
 * Created by Christ on 2021/7/3.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class SelectOptionsChildAdapter extends BaseQuickAdapter<DictInfo, BaseViewHolder> {
    public SelectOptionsChildAdapter(@Nullable List<DictInfo> data) {
        super(R.layout.adapter_select_options_child, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DictInfo item) {
        helper.setText(R.id.tv_name, item.getDataName()).addOnClickListener(R.id.tv_name);
        TextView tvName = helper.getView(R.id.tv_name);
        tvName.setSelected(item.isSelected());

    }
}