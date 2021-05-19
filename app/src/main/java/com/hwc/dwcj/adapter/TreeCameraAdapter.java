package com.hwc.dwcj.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.TreeCamera;

import java.util.List;

public class TreeCameraAdapter extends BaseQuickAdapter<TreeCamera, BaseViewHolder> {
    public TreeCameraAdapter(@Nullable List<TreeCamera> data) {
        super(R.layout.adapter_new_tree_child, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, TreeCamera item) {
        helper.setText(R.id.text, item.getCameraName());
    }
}
