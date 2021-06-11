package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.ChangeAdmin;

import java.util.List;

/**
 * Created by Christ on 2021/6/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class ChangeAdminAdapter extends BaseQuickAdapter<ChangeAdmin, BaseViewHolder> {
    public ChangeAdminAdapter(@Nullable List<ChangeAdmin> data) {
        super(R.layout.adapter_change_admin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChangeAdmin item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
