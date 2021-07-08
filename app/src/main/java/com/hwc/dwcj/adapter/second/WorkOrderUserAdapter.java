package com.hwc.dwcj.adapter.second;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.second.WorkOrderHandleActivity;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.second.WorkOrderUser;
import com.zds.base.util.StringUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Christ on 2021/6/7.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class WorkOrderUserAdapter extends BaseQuickAdapter<WorkOrderUser, BaseViewHolder> {
    private List<String> userTypes;

    public WorkOrderUserAdapter(@Nullable List<WorkOrderUser> data, List<String> userTypes) {
        super(R.layout.adapter_work_order_user, data);
        this.userTypes = userTypes;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkOrderUser item) {
        View tv_look = helper.getView(R.id.tv_look);
        View tv_do = helper.getView(R.id.tv_do);
        View tv_track = helper.getView(R.id.tv_track);
        View tv_check = helper.getView(R.id.tv_check);
        View tv_submit = helper.getView(R.id.tv_submit);


        StringBuilder builder = new StringBuilder();
        if (!StringUtil.isEmpty(item.getMap().getHandleStatusName())) {
            builder.append(item.getMap().getHandleStatusName());
        }
        if (builder.length() > 0) {
            if(!StringUtil.isEmpty(item.getMap().getVerifyStatusName())){
                builder.append("-").append(item.getMap().getVerifyStatusName());
            }
        } else {
            builder.append(item.getMap().getVerifyStatusName());
        }

        helper.setText(R.id.tv_name, item.getAlarmName())
                .setText(R.id.tv_time, StringUtil.isEmpty(item.getAddTime()) ? "" : StringUtil.dealDateFormat(item.getAddTime()))
                .setText(R.id.tv_ip, item.getMap().getManageIp())
                .setText(R.id.tv_jg, item.getMap().getAssetNatureName() + ">" + item.getMap().getAssetTypeName() + ">" + item.getMap().getAssetClassName())
                .setText(R.id.tv_g, StringUtil.isEmpty(item.getMap().getOrgName()) ? "" : item.getMap().getOrgName())
                .setText(R.id.tv_status, builder.toString());

        //权限控制
        tv_look.setVisibility(View.VISIBLE);
        tv_track.setVisibility(View.VISIBLE);
        helper.addOnClickListener(R.id.tv_look).addOnClickListener(R.id.tv_track);

        //处理
        if ((userTypes.contains("4") && item.getHandlePersionId().equals(MyApplication.getInstance().getUserInfo().getId())) || userTypes.contains("1")) {
            if ((StringUtil.isEmpty(item.getExp1()) || !item.getExp1().equals("QUESTION_REPORT"))
                    && (StringUtil.isEmpty(item.getMap().getHandleStatusZibiao()) || !item.getMap().getHandleStatusZibiao().equals("CHANGE_DEAL"))
                    && (item.getHandleStatus() != null && (item.getHandleStatus().equals("UN_DEAL") || item.getHandleStatus().equals("DEALING") || item.getHandleStatus().equals("HELP_DEAL") || item.getHandleStatus().equals("CHANGE_DEAL")))) {
                tv_do.setBackground(mContext.getResources().getDrawable(R.drawable.bg_common_btn));
                helper.addOnClickListener(R.id.tv_do);
            } else {
                tv_do.setBackground(mContext.getResources().getDrawable(R.drawable.bg_common_btn_disabled));
            }
            tv_do.setVisibility(View.VISIBLE);
        } else {
            tv_do.setVisibility(View.GONE);
        }
        //上报
        //审核
        if ((userTypes.contains("5") && item.getAddId().equals(MyApplication.getInstance().getUserInfo().getId())) || userTypes.equals("1")) {
            if ((item.getExp1() != null && item.getExp1().equals("QUESTION_REPORT")) && (item.getHandleStatus() != null && item.getHandleStatus().equals("DEALING"))) {
                tv_submit.setBackground(mContext.getResources().getDrawable(R.drawable.bg_common_btn));
                helper.addOnClickListener(R.id.tv_submit);
            } else {
                tv_submit.setBackground(mContext.getResources().getDrawable(R.drawable.bg_common_btn_disabled));
            }
            if ((item.getHandleStatus() != null && item.getHandleStatus().equals("DEALED")) && (item.getVerifyStatus() != null && (item.getVerifyStatus().equals("UN_AUDITED") || item.getVerifyStatus().equals("'AUDITING")))) {
                tv_check.setBackground(mContext.getResources().getDrawable(R.drawable.bg_common_btn));
                helper.addOnClickListener(R.id.tv_check);
            } else {
                tv_check.setBackground(mContext.getResources().getDrawable(R.drawable.bg_common_btn_disabled));
            }
            tv_submit.setVisibility(View.VISIBLE);
            tv_check.setVisibility(View.VISIBLE);
        } else {
            tv_submit.setVisibility(View.GONE);
            tv_check.setVisibility(View.GONE);
        }

    }
}
