package com.hwc.dwcj.adapter;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.ClassifyInfo;

import java.util.List;

/**
 * @author Administrator
 *         日期 2018/6/25
 *         描述 分类菜单
 */

public class AdapterClassifyMeau extends BaseQuickAdapter<ClassifyInfo, BaseViewHolder> {

    private ClassifyInfo selectItem;

    public AdapterClassifyMeau(@Nullable List<ClassifyInfo> data) {
        super(R.layout.adapter_classify_meau, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, ClassifyInfo item) {

        if (selectItem != null && selectItem.equals(item)) {
            helper.setGone(R.id.view_bg, true);
            helper.setBackgroundColor(R.id.rel_bg, ContextCompat.getColor(mContext, R.color.white));
        } else {
            helper.setGone(R.id.view_bg, false);
            helper.setBackgroundColor(R.id.rel_bg, ContextCompat.getColor(mContext, R.color.meau_bg));
        }

        helper.setText(R.id.tv_title_name, item.getName());
    }

    public ClassifyInfo getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(ClassifyInfo selectItem) {
        this.selectItem = selectItem;
    }
}