package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.CheckUser;

import java.util.List;

/**
 * Created by Christ on 2021/6/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class CheckUserAdapter extends BaseQuickAdapter<CheckUser, BaseViewHolder> {
    public CheckUserAdapter(@Nullable List<CheckUser> data) {
        super(R.layout.adapter_check_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckUser item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
