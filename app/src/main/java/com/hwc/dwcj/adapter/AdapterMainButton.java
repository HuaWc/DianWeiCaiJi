package com.hwc.dwcj.adapter;

import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.MainDataInfo;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * @author Administrator
 *         日期 2018/6/25
 *         描述 首页菜单
 */

public class AdapterMainButton extends BaseQuickAdapter<MainDataInfo.NavsBean, BaseViewHolder> {

    public AdapterMainButton(@Nullable List<MainDataInfo.NavsBean> data) {
        super(R.layout.adapter_meau, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, MainDataInfo.NavsBean item) {
        GlideUtils.loadImageViewLoding(item.getIcon(), (ImageView) helper.getView(R.id.img_item));
        helper.setText(R.id.tv_meau_title, item.getNavname());
    }
}