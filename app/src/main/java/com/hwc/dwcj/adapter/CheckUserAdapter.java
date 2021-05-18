package com.hwc.dwcj.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.CheckUser;

import java.util.List;

public class CheckUserAdapter extends BaseQuickAdapter<CheckUser, BaseViewHolder> {
    public CheckUserAdapter(@Nullable List<CheckUser> data) {
        super(R.layout.adapter_add_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckUser item) {
        if (item.getRealName().equals("")) {
            helper.getView(R.id.iv_next).setVisibility(View.GONE);
            helper.getView(R.id.tv_name).setVisibility(View.INVISIBLE);
            helper.getView(R.id.rl_user).setVisibility(View.GONE);
            helper.getView(R.id.rl_add).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_next).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_name).setVisibility(View.VISIBLE);
            helper.getView(R.id.rl_user).setVisibility(View.VISIBLE);
            helper.getView(R.id.rl_add).setVisibility(View.GONE);
            helper.setText(R.id.tv_name, item.getRealName());

        }
        helper.addOnClickListener(R.id.rl_add).addOnClickListener(R.id.iv_delete);
    }
}
