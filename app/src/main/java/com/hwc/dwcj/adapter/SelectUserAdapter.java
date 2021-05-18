package com.hwc.dwcj.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.CheckUser;

import java.util.List;

public class SelectUserAdapter extends BaseQuickAdapter<CheckUser, BaseViewHolder> {
    public SelectUserAdapter(@Nullable List<CheckUser> data) {
        super(R.layout.adapter_select_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckUser item) {
        helper.setText(R.id.tv_name, item.getRealName());
        ImageView iv_select = helper.getView(R.id.iv_select);
        iv_select.setSelected(item.isSelected());
        helper.addOnClickListener(R.id.iv_select);
    }
}
