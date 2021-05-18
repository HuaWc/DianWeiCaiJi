package com.hwc.dwcj.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.util.GlideLoadImageUtils;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * Create by hwc on 2020/11/19
 */
public class CommonImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CommonImageAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_common_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        View rl_image = helper.getView(R.id.rl_image);
        View rl_add = helper.getView(R.id.rl_add);
        if (StringUtil.isEmpty(item)) {
            rl_add.setVisibility(View.VISIBLE);
            rl_image.setVisibility(View.GONE);
        } else {
            rl_image.setVisibility(View.VISIBLE);
            rl_add.setVisibility(View.GONE);
            GlideLoadImageUtils.GlideLoadFilletErrorImageUtils(mContext, item, helper.getView(R.id.iv_image), -1, 5);
        }
        helper.addOnClickListener(R.id.iv_delete);
        helper.addOnClickListener(R.id.iv_add);
    }
}
