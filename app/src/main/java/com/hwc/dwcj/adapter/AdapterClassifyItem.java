package com.hwc.dwcj.adapter;

import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.ClassifyInfo;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * @author Administrator
 *         日期 2018/6/25
 *         描述 分类商品
 */

public class AdapterClassifyItem extends BaseQuickAdapter<ClassifyInfo.ChildBean, BaseViewHolder> {


    public AdapterClassifyItem(@Nullable List<ClassifyInfo.ChildBean> data) {
        super(R.layout.adapter_classify_goods, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, ClassifyInfo.ChildBean item) {
        GlideUtils.loadImageViewLodingCir(item.getThumb(), (ImageView) helper.getView(R.id.img_pic),4);
        helper.setText(R.id.tv_name, item.getName());
    }
}