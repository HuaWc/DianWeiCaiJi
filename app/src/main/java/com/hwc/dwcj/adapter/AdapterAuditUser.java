package com.hwc.dwcj.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.AuditUserEntity;

import java.util.List;

public class AdapterAuditUser extends BaseQuickAdapter<AuditUserEntity.Check, BaseViewHolder> {
    public AdapterAuditUser(@Nullable List<AuditUserEntity.Check> data) {
        super(R.layout.adapter_audit_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuditUserEntity.Check item) {
        ImageView down = helper.getView(R.id.iv_arrow_down);
        if (mData.indexOf(item) == (mData.size() - 1)) {
            down.setVisibility(View.GONE);

        } else {
            down.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_person, item.isMe() ? "发起申请" : "审批人");
        helper.setText(R.id.tv_name, item.getCheckUserName());
        View tv1 = helper.getView(R.id.tv_1);
        View tv2 = helper.getView(R.id.tv_2);
        View tv3 = helper.getView(R.id.tv_3);
        View tv4 = helper.getView(R.id.tv_4);
        View tv5 = helper.getView(R.id.tv_5);
        switch (item.getCheckType()) {
            case 1:
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.GONE);
                tv3.setVisibility(View.GONE);
                tv4.setVisibility(View.GONE);
                tv5.setVisibility(View.GONE);
                helper.getView(R.id.ll_status).setVisibility(View.VISIBLE);

                break;
            case 2:
                tv1.setVisibility(View.GONE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.GONE);
                tv4.setVisibility(View.GONE);
                tv5.setVisibility(View.GONE);
                helper.getView(R.id.ll_status).setVisibility(View.VISIBLE);

                break;
            case 3:
                tv1.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                tv3.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.GONE);
                tv5.setVisibility(View.GONE);
                helper.getView(R.id.ll_status).setVisibility(View.VISIBLE);

                break;
            case 4:
                tv1.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                tv3.setVisibility(View.GONE);
                tv4.setVisibility(View.VISIBLE);
                tv5.setVisibility(View.GONE);
                helper.getView(R.id.ll_status).setVisibility(View.VISIBLE);

                break;
            case 5:
                tv1.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                tv3.setVisibility(View.GONE);
                tv4.setVisibility(View.GONE);
                tv5.setVisibility(View.VISIBLE);
                helper.getView(R.id.ll_status).setVisibility(View.VISIBLE);

                break;
            default:
                tv1.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                tv3.setVisibility(View.GONE);
                tv4.setVisibility(View.GONE);
                tv5.setVisibility(View.GONE);
                helper.getView(R.id.ll_status).setVisibility(View.GONE);
        }
    }
}
