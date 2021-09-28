package com.hwc.dwcj.adapter.second;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.second.InspectionUser;
import com.zds.base.util.StringUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Christ on 2021/6/10.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class InspectionUserAdapter extends BaseQuickAdapter<InspectionUser, BaseViewHolder> {

    public InspectionUserAdapter(@Nullable List<InspectionUser> data) {
        super(R.layout.adapter_inspection_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionUser item) {
        ProgressBar pb_red = helper.getView(R.id.pb_red);
        ProgressBar pb_blue = helper.getView(R.id.pb_blue);
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_status2 = helper.getView(R.id.tv_status2);
        TextView tv_status3 = helper.getView(R.id.tv_status3);
        TextView tv_start = helper.getView(R.id.tv_start);
        TextView tv_check = helper.getView(R.id.tv_check);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        helper.addOnClickListener(R.id.tv_map).addOnClickListener(R.id.tv_start).addOnClickListener(R.id.tv_check);

        try {
            Date d = new Date();
            if (df.parse(StringUtil.dealDateFormat(item.getEffectiveTimeEnd())).getTime() > d.getTime()) {
                tv_status.setText("正常");
                tv_status.setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
            } else {
                tv_status.setText("超时");
                tv_status.setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            tv_status.setText("未知");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
        }

        double percent = Double.parseDouble(item.getMap().getInspectionPercent().replace("%", ""));
        int i = new Double(percent).intValue();
        pb_blue.setProgress(i);
        pb_red.setProgress(i);

        String myId = MyApplication.getInstance().getUserInfo().getId();

        switch (item.getInspectionStatus()) {
            case "0":
                tv_status2.setText("待巡检");
                if (myId.equals(item.getInspectionId())) {
                    tv_start.setVisibility(View.VISIBLE);
                } else {
                    tv_start.setVisibility(View.GONE);
                }
                tv_check.setVisibility(View.GONE);
                tv_status3.setVisibility(View.GONE);
                break;
            case "1":
                tv_status2.setText("巡检中");
                if (myId.equals(item.getInspectionId())) {
                    tv_start.setVisibility(View.VISIBLE);
                } else {
                    tv_start.setVisibility(View.GONE);
                }
                tv_check.setVisibility(View.GONE);
                tv_status3.setVisibility(View.GONE);
                break;
            case "2":
                tv_status3.setTextColor(mContext.getResources().getColor(R.color.inspection_blue));
                tv_status2.setText("已巡检-正常");
                tv_start.setVisibility(View.GONE);
                switch (item.getCkeckType()) {
                    case "0":
                        //待审核
                        if (myId.equals(item.getMap().getAddId())) {
                            tv_check.setVisibility(View.VISIBLE);
                            tv_status3.setVisibility(View.GONE);
                        } else {
                            tv_status3.setText("待审核");
                            tv_check.setVisibility(View.GONE);
                            tv_status3.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "1":
                        tv_status3.setText("待处理");
                        tv_status3.setVisibility(View.VISIBLE);
                        tv_check.setVisibility(View.GONE);
                        break;
                    case "2":
                        tv_status3.setTextColor(mContext.getResources().getColor(R.color.inspection_green));
                        tv_status3.setText("审核已通过");
                        tv_status3.setVisibility(View.VISIBLE);
                        tv_check.setVisibility(View.GONE);
                        break;
                    case "3":
                        tv_status3.setTextColor(mContext.getResources().getColor(R.color.inspection_red));
                        tv_status3.setText("审核已驳回");
                        tv_status3.setVisibility(View.VISIBLE);
                        if (myId.equals(item.getMap().getAddId())) {
                            tv_check.setVisibility(View.VISIBLE);
                        } else {
                            tv_check.setVisibility(View.GONE);
                        }
                        if (myId.equals(item.getInspectionId())) {
                            tv_start.setVisibility(View.VISIBLE);
                        } else {
                            tv_start.setVisibility(View.GONE);
                        }

                        break;
                    default:
                        break;
                }
                break;
            case "3":
                tv_status3.setTextColor(mContext.getResources().getColor(R.color.inspection_blue));
                tv_status2.setText("已巡检-异常");
                tv_start.setVisibility(View.GONE);
                switch (item.getCkeckType()) {
                    case "0":
                        //待审核
                        if (myId.equals(item.getMap().getAddId())) {
                            tv_check.setVisibility(View.VISIBLE);
                            tv_status3.setVisibility(View.GONE);
                        } else {
                            tv_status3.setText("待审核");
                            tv_check.setVisibility(View.GONE);
                            tv_status3.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "1":
                        tv_status3.setText("待处理");
                        tv_status3.setVisibility(View.VISIBLE);
                        tv_check.setVisibility(View.GONE);
                        break;
                    case "2":
                        tv_status3.setTextColor(mContext.getResources().getColor(R.color.inspection_green));
                        tv_status3.setText("审核已通过");
                        tv_status3.setVisibility(View.VISIBLE);
                        tv_check.setVisibility(View.GONE);
                        break;
                    case "3":
                        tv_status3.setTextColor(mContext.getResources().getColor(R.color.inspection_red));
                        tv_status3.setText("审核已驳回");
                        tv_status3.setVisibility(View.VISIBLE);
                        if (myId.equals(item.getMap().getAddId())) {
                            tv_check.setVisibility(View.VISIBLE);
                        } else {
                            tv_check.setVisibility(View.GONE);
                        }
                        if (myId.equals(item.getInspectionId())) {
                            tv_start.setVisibility(View.VISIBLE);
                        } else {
                            tv_start.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        break;
                }
                break;
/*            case "4":
                tv_status2.setText("待巡检（已驳回）");
                if (myId.equals(item.getInspectionId())) {
                    tv_start.setVisibility(View.VISIBLE);
                } else {
                    tv_start.setVisibility(View.GONE);
                }
                tv_check.setVisibility(View.GONE);
                tv_status3.setVisibility(View.GONE);
                break;*/
        }

        //两个状态  一个是右上角是否巡检  进度条的颜色
        //第二个决定下方显示按键和状态文字

        helper.setText(R.id.tv_name, item.getTaskName())
                .setText(R.id.tv_time, StringUtil.dealDateFormat(item.getEffectiveTimeStart()) + "~" + StringUtil.dealDateFormat(item.getEffectiveTimeEnd()))
                .setText(R.id.tv_urgent, StringUtil.isEmpty(item.getMap().getUrgentType()) ? "" : item.getMap().getUrgentType())
                .setText(R.id.tv_people, StringUtil.isEmpty(item.getInspectionName()) ? "" : item.getInspectionName())
                .setText(R.id.tv_jd, StringUtil.isEmpty(item.getMap().getInspectionPercent()) ? "" : item.getMap().getInspectionPercent())
                .setText(R.id.tv_num_all, StringUtil.isEmpty(item.getMap().getInspectionProgress()) ? "" : item.getMap().getInspectionProgress())
                .setText(R.id.tv_g, StringUtil.isEmpty(item.getMap().getGroupName()) ? "" : item.getMap().getGroupName());

    }
}
