package com.hwc.dwcj.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;

import java.util.List;

/**
 * @author Administrator
 *         日期 2018/6/25
 *         描述 报告
 */

public class AdapterTest extends BaseQuickAdapter<String, BaseViewHolder> {

    public AdapterTest(@Nullable List<String> data) {
        super(R.layout.adapter_test, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, String item) {
    }
}