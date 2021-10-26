package com.hwc.dwcj.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;

import java.util.List;

/**
 * Created by Christ on 2021/10/8.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class ItemTestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ItemTestAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_item_test, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);
    }
}
