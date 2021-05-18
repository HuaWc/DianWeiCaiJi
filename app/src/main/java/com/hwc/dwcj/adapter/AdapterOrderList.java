package com.hwc.dwcj.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.MeauInfo;

import java.util.List;

/**
 * @author Administrator
 *         日期 2018/6/25
 *         描述  订单菜单
 */

public class AdapterOrderList extends BaseQuickAdapter<MeauInfo, BaseViewHolder> {

    public AdapterOrderList(@Nullable List<MeauInfo> data) {
        super(R.layout.adapter_order_meau, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, MeauInfo item) {
        helper.setImageResource(R.id.img_item, item.getRes());
        helper.setText(R.id.tv_name, item.getTitle());
        int number = 0;
        try {
            number= Integer.valueOf(item.getMsgCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        helper.setGone(R.id.tv_number, false);
        if (number > 0) {
            helper.setGone(R.id.tv_number, true);
            helper.setText(R.id.tv_number, item.getMsgCount());
        }
    }
}