package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.ChangeResult;

import java.util.List;

/**
 * Created by Christ on 2021/6/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class ChangeResultAdapter extends BaseQuickAdapter<ChangeResult, BaseViewHolder> {
    public ChangeResultAdapter(@Nullable List<ChangeResult> data) {
        super(R.layout.adapter_change_result, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChangeResult item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
