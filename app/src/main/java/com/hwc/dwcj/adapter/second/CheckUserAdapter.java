package com.hwc.dwcj.adapter.second;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.CheckUser;
import com.zds.base.util.StringUtil;

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
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_status2 = helper.getView(R.id.tv_status2);
        TextView tv_look = helper.getView(R.id.tv_look);
        TextView tv_check = helper.getView(R.id.tv_check);

        helper.setText(R.id.tv_name, StringUtil.isEmpty(item.getTaskName()) ? "" : item.getTaskName())
                .setText(R.id.tv_time, StringUtil.dealDateFormat(item.getAddTime()))
                .setText(R.id.tv_person, StringUtil.isEmpty(item.getVerPeopleNames()) ? "" : item.getVerPeopleNames())
                .setText(R.id.tv_g, StringUtil.isEmpty(item.getTaskrequires()) ? "" : item.getTaskrequires())
                .addOnClickListener(R.id.tv_look).addOnClickListener(R.id.tv_check);
        if (item.getVerFeedback() == 1) {
            tv_status.setText("正常");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
            //完成
        } else if (item.getVerFeedback() == 2) {
            //未完成
            tv_status.setText("异常");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
        } else {
            tv_status.setText("");
        }
        switch (item.getCheckStatus()) {
            //0待反馈完成 1待审核 2:审核通过 3:审核驳回
            case 0:
                tv_look.setVisibility(View.VISIBLE);
                tv_check.setVisibility(View.GONE);
                tv_status2.setVisibility(View.GONE);
                break;
            case 1:
                tv_look.setVisibility(View.GONE);
                tv_check.setVisibility(View.VISIBLE);
                tv_status2.setVisibility(View.GONE);
                break;
            case 2:
                tv_look.setVisibility(View.GONE);
                tv_check.setVisibility(View.GONE);
                tv_status2.setVisibility(View.VISIBLE);
                tv_status2.setText("审核已通过");
                tv_status2.setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
                break;
            case 3:
                tv_look.setVisibility(View.GONE);
                tv_check.setVisibility(View.GONE);
                tv_status2.setVisibility(View.VISIBLE);
                tv_status2.setText("审核已驳回");
                tv_status2.setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
                break;
            default:
                tv_look.setVisibility(View.GONE);
                tv_check.setVisibility(View.GONE);
                tv_status2.setVisibility(View.GONE);
        }
    }
}
