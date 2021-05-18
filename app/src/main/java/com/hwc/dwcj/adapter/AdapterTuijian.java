package com.hwc.dwcj.adapter;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.MainDataInfo;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * @author Administrator
 *         日期 2018/6/25
 *         描述 推荐
 */

public class AdapterTuijian extends BaseMultiItemQuickAdapter<MainDataInfo.GoodsBean, BaseViewHolder> {

    public AdapterTuijian(@Nullable List<MainDataInfo.GoodsBean> data) {
        super(data);
        addItemType(1, R.layout.adapter_goods_tj);
        addItemType(2, R.layout.adapter_weinituijian);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, MainDataInfo.GoodsBean item) {
        switch (helper.getItemViewType()) {
            case 1:
                GlideUtils.loadImageViewLodingCir(item.getThumb(), (ImageView) helper.getView(R.id.img_item), 4);
                helper.setText(R.id.tv_goods_title, item.getTitle());
                helper.setText(R.id.tv_price, "￥" + item.getGroupsprice());
                helper.setText(R.id.tv_ls_price, "零售价 ￥" + item.getSingleprice());
                helper.setText(R.id.tv_pt_number, "已拼团" + item.getGroupnum() + "件");
                if (item.getIs_dispatch() == 1) {
                    helper.getView(R.id.tv_by).setVisibility(View.VISIBLE);
                } else {
                    helper.getView(R.id.tv_by).setVisibility(View.INVISIBLE);
                }
                if (item.getDeduct_price() != null && !item.getDeduct_price().equals("") && !item.getDeduct_price().equals("0.00")) {
                    helper.getView(R.id.ll_fan).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_fan, " " + item.getDeduct_price() + " ");
                } else {
                    helper.getView(R.id.ll_fan).setVisibility(View.GONE);
                }
                helper.addOnClickListener(R.id.tv_ls_price);
                break;
            case 2:
                if (item.getGoodslist().size()==3) {
                    GlideUtils.loadImageViewLodingCir(item.getGoodslist().get(0).getThumb(), (ImageView) helper.getView(R.id.iv_img1), 4);
                    helper.setText(R.id.tv_price1, "￥" + item.getGoodslist().get(0).getGroupsprice());
                    helper.setText(R.id.yjprice1, "￥" + item.getGoodslist().get(0).getSingleprice());

                    GlideUtils.loadImageViewLodingCir(item.getGoodslist().get(1).getThumb(), (ImageView) helper.getView(R.id.iv_img2), 4);
                    helper.setText(R.id.tv_price2, "￥" + item.getGoodslist().get(1).getGroupsprice());
                    helper.setText(R.id.yjprice2, "￥" + item.getGoodslist().get(1).getSingleprice());

                    GlideUtils.loadImageViewLodingCir(item.getGoodslist().get(2).getThumb(), (ImageView) helper.getView(R.id.iv_img3), 4);
                    helper.setText(R.id.tv_price3, "￥" + item.getGoodslist().get(2).getGroupsprice());
                    helper.setText(R.id.yjprice3, "￥" + item.getGoodslist().get(2).getSingleprice());
                }




                break;
            default:
                break;

        }


    }
}