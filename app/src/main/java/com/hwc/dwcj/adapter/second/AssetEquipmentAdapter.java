package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.AssetEquipment;

import java.util.List;

/**
 * Created by Christ on 2021/6/29.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class AssetEquipmentAdapter extends BaseQuickAdapter<AssetEquipment, BaseViewHolder> {
    public AssetEquipmentAdapter(@Nullable List<AssetEquipment> data) {
        super(R.layout.adapter_select_asset_equipment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AssetEquipment item) {
        helper.setText(R.id.tv_name, item.getAssetName());
    }
}
