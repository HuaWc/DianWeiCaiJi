package com.hwc.dwcj.adapter.second;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.ChangeUser;
import com.zds.base.util.StringUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Christ on 2021/6/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class ChangeUserAdapter extends BaseQuickAdapter<ChangeUser, BaseViewHolder> {
    public ChangeUserAdapter(@Nullable List<ChangeUser> data) {
        super(R.layout.adapter_change_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChangeUser item) {
        ProgressBar pb_blue = helper.getView(R.id.pb_blue);
        TextView tv_jd = helper.getView(R.id.tv_jd);

        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_status2 = helper.getView(R.id.tv_status2);
        TextView tv_status3 = helper.getView(R.id.tv_status3);

        TextView tv_change = helper.getView(R.id.tv_change);
        TextView tv_check = helper.getView(R.id.tv_check);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        helper.setText(R.id.tv_name, StringUtil.isEmpty(item.getTaskName()) ? "" : item.getTaskName())
                .setText(R.id.tv_time, StringUtil.dealDateFormat(item.getEffectiveStartTime()) + "~" + StringUtil.dealDateFormat(item.getEffectiveEndTime()))
                .setText(R.id.tv_person, StringUtil.isEmpty(item.getChangePeopleNames()) ? "" : item.getChangePeopleNames())
                .setText(R.id.tv_g, StringUtil.isEmpty(item.getTaskRequest()) ? "" : item.getTaskRequest())
                .addOnClickListener(R.id.tv_change).addOnClickListener(R.id.tv_check);
        try {
            Date d = new Date();
            if (df.parse(StringUtil.dealDateFormat(item.getEffectiveEndTime())).getTime() > d.getTime()) {
                tv_status.setText("正常");
                tv_status.setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
                //完成
            } else {
                //未完成
                tv_status.setText("超时");
                tv_status.setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            tv_status.setText("未知");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
        }
        if (item.getChangeStatus() != null && item.getChangeStatus() == 1) {
            tv_status3.setText("已变更");
            tv_status3.setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
        } else {
            tv_status3.setText("待变更");
            tv_status3.setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
        }

        switch (item.getCheckStatus()) {
            //0待变更完成 1待审核 2:审核通过 3:审核驳回
            case 0:
                tv_change.setVisibility(View.VISIBLE);
                tv_check.setVisibility(View.GONE);
                tv_status2.setVisibility(View.GONE);
                pb_blue.setProgress(0);
                tv_jd.setText("0%");
                break;
            case 1:
                tv_change.setVisibility(View.GONE);
                tv_check.setVisibility(View.VISIBLE);
                tv_status2.setVisibility(View.GONE);
                pb_blue.setProgress(50);
                tv_jd.setText("50%");
                break;
            case 2:
                tv_change.setVisibility(View.GONE);
                tv_check.setVisibility(View.GONE);
                tv_status2.setVisibility(View.VISIBLE);
                tv_status2.setText("审核已通过");
                tv_status2.setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
                pb_blue.setProgress(100);
                tv_jd.setText("100%");
                break;
            case 3:
                tv_change.setVisibility(View.VISIBLE);
                tv_check.setVisibility(View.GONE);
                tv_status2.setVisibility(View.VISIBLE);
                tv_status2.setText("审核已驳回");
                tv_status2.setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
                pb_blue.setProgress(0);
                tv_jd.setText("0%");
                break;
            default:
                tv_change.setVisibility(View.GONE);
                tv_check.setVisibility(View.GONE);
                tv_status2.setVisibility(View.GONE);
        }
    }
}
