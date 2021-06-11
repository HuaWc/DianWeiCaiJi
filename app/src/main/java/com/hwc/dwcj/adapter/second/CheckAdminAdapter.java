package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.CheckAdmin;

import java.util.List;

/**
 * Created by Christ on 2021/6/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class CheckAdminAdapter extends BaseQuickAdapter<CheckAdmin, BaseViewHolder> {
    public CheckAdminAdapter(@Nullable List<CheckAdmin> data) {
        super(R.layout.adapter_check_admin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckAdmin item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
