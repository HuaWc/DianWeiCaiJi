package com.hwc.dwcj.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.util.GlideLoadImageUtils;

import java.util.List;

public class AdapterCameraPhoto extends BaseQuickAdapter<String, BaseViewHolder> {
    public AdapterCameraPhoto(@Nullable List<String> data) {
        super(R.layout.adapter_camera_photo, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideLoadImageUtils.GlideLoadImageUtils(mContext, item, helper.getView(R.id.iv_photo));
    }
}
