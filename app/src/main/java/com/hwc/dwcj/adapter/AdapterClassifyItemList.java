package com.hwc.dwcj.adapter;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.WebViewActivity;
import com.hwc.dwcj.entity.ClassifyInfo;
import com.hwc.dwcj.http.AppConfig;

import java.util.List;

/**
 * @author Administrator
 *         日期 2018/6/25
 *         描述 分类商品列表
 */

public class AdapterClassifyItemList extends BaseQuickAdapter<ClassifyInfo, BaseViewHolder> {


    public AdapterClassifyItemList(@Nullable List<ClassifyInfo> data) {
        super(R.layout.adapter_classify_goods_list, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, final ClassifyInfo item) {
        helper.setText(R.id.tv_name, item.getName());
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        AdapterClassifyItem adapterClassifyItem = new AdapterClassifyItem(item.getChild());
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(adapterClassifyItem);
        adapterClassifyItem.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mContext.startActivity(new Intent(mContext, WebViewActivity.class).putExtra("url", AppConfig.searchTypeId + item.getChild().get(position).getId()));
            }
        });


    }
}