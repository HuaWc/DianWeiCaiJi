package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.ChangeResult;
import com.hwc.dwcj.entity.second.ChangeResultAdmin;

import java.util.List;

/**
 * Created by Christ on 2021/6/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class ChangeResultAdminAdapter extends BaseQuickAdapter<ChangeResultAdmin, BaseViewHolder> {
    public ChangeResultAdminAdapter(@Nullable List<ChangeResultAdmin> data) {
        super(R.layout.adapter_change_result_admin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChangeResultAdmin item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
