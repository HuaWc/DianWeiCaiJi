package com.hwc.dwcj.adapter.second;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.WorkOrderTrackInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * Created by Christ on 2021/6/30.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class WorkOrderTrackAdapter extends BaseQuickAdapter<WorkOrderTrackInfo, BaseViewHolder> {
    public WorkOrderTrackAdapter(@Nullable List<WorkOrderTrackInfo> data) {
        super(R.layout.adapter_work_order_track, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkOrderTrackInfo item) {
        ImageView down = helper.getView(R.id.iv_arrow_down);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_people = helper.getView(R.id.tv_people);
        TextView tv_phone = helper.getView(R.id.tv_phone);
        View ll_main = helper.getView(R.id.ll_main);

        if (item.getColorType() != null) {

            switch (item.getColorType()) {
                case "red":
                    ll_main.setBackground(mContext.getResources().getDrawable(R.drawable.bg_work_order_track_red));
                    tv_name.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_time.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_people.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_phone.setTextColor(mContext.getResources().getColor(R.color.white));
                    break;
                case "yellow":
                    ll_main.setBackground(mContext.getResources().getDrawable(R.drawable.bg_work_order_track_yellow));
                    tv_name.setTextColor(mContext.getResources().getColor(R.color.black));
                    tv_time.setTextColor(mContext.getResources().getColor(R.color.black));
                    tv_people.setTextColor(mContext.getResources().getColor(R.color.black));
                    tv_phone.setTextColor(mContext.getResources().getColor(R.color.black));
                    break;
                case "blue":
                    ll_main.setBackground(mContext.getResources().getDrawable(R.drawable.bg_work_order_track_blue));
                    tv_name.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_time.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_people.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_phone.setTextColor(mContext.getResources().getColor(R.color.white));
                    break;
                case "green":
                    ll_main.setBackground(mContext.getResources().getDrawable(R.drawable.bg_work_order_track_green));
                    tv_name.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_time.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_people.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_phone.setTextColor(mContext.getResources().getColor(R.color.white));
                    break;
            }
        } else {
            ll_main.setBackground(mContext.getResources().getDrawable(R.drawable.bg_work_order_track_blue));
            tv_name.setTextColor(mContext.getResources().getColor(R.color.white));
            tv_time.setTextColor(mContext.getResources().getColor(R.color.white));
            tv_people.setTextColor(mContext.getResources().getColor(R.color.white));
            tv_phone.setTextColor(mContext.getResources().getColor(R.color.white));
        }

        if (mData.indexOf(item) == (mData.size() - 1)) {
            down.setVisibility(View.GONE);

        } else {
            down.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_name, StringUtil.isEmpty(item.getAlarmStep()) ? "" : item.getAlarmStep())
                .setText(R.id.tv_time, StringUtil.isEmpty(item.getHandleTime()) ? "" : item.getHandleTime())
                .setText(R.id.tv_people, "处理人：" + (StringUtil.isEmpty(item.getAlarmSourceOrPersonName()) ? "" : item.getAlarmSourceOrPersonName()))
                .setText(R.id.tv_phone, "联系电话：" + (StringUtil.isEmpty(item.getTel()) ? "" : item.getTel()))
                .setText(R.id.tv_f1, "处置内容：" + (StringUtil.isEmpty(item.getAlarmName()) ? "" : item.getAlarmName()))
                .setText(R.id.tv_f2, "处置思路：" + (StringUtil.isEmpty(item.getHandleMethod()) ? "" : item.getHandleMethod()))
                .setText(R.id.tv_f3, "问题描述：" + (StringUtil.isEmpty(item.getRemark()) ? "" : item.getRemark()))
                .setText(R.id.tv_f4, "排障日志：" + (StringUtil.isEmpty(item.getErrorLog()) ? "" : item.getErrorLog()));

        helper.getView(R.id.tv_name).setVisibility(StringUtil.isEmpty(item.getAlarmStep()) ? View.GONE : View.VISIBLE);
        helper.getView(R.id.tv_time).setVisibility(StringUtil.isEmpty(item.getHandleTime()) ? View.GONE : View.VISIBLE);
        helper.getView(R.id.tv_people).setVisibility(StringUtil.isEmpty(item.getAlarmSourceOrPersonName()) ? View.GONE : View.VISIBLE);
        helper.getView(R.id.tv_phone).setVisibility(StringUtil.isEmpty(item.getTel()) ? View.GONE : View.VISIBLE);
        helper.getView(R.id.tv_f1).setVisibility(StringUtil.isEmpty(item.getAlarmName()) ? View.GONE : View.VISIBLE);
        helper.getView(R.id.tv_f2).setVisibility(StringUtil.isEmpty(item.getHandleMethod()) ? View.GONE : View.VISIBLE);
        helper.getView(R.id.tv_f3).setVisibility(StringUtil.isEmpty(item.getRemark()) ? View.GONE : View.VISIBLE);
        helper.getView(R.id.tv_f4).setVisibility(StringUtil.isEmpty(item.getErrorLog()) ? View.GONE : View.VISIBLE);


        //red yellow blue green

    }
}
