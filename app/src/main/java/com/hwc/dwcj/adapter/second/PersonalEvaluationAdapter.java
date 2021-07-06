package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.PersonalEvaluationInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * Created by Christ on 2021/6/4.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class PersonalEvaluationAdapter extends BaseQuickAdapter<PersonalEvaluationInfo, BaseViewHolder> {
    public PersonalEvaluationAdapter(@Nullable List<PersonalEvaluationInfo> data) {
        super(R.layout.adapter_personal_evaluation, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalEvaluationInfo item) {
        helper.setText(R.id.tv_name, StringUtil.isEmpty(item.getAlarmName()) ? "" : item.getAlarmName())
                .setText(R.id.tv_number, String.valueOf(item.getMap().getTotalScore()))
                .setText(R.id.tv_pname, StringUtil.isEmpty(item.getMap().getHandlePersionName()) ? "" : item.getMap().getHandlePersionName())
                .setText(R.id.tv_jg, ">>")
                .setText(R.id.tv_g, StringUtil.isEmpty(item.getMap().getOrgName()) ? "" : item.getMap().getOrgName())
                .setText(R.id.tv_time, StringUtil.isEmpty(item.getMap().getAlarmTime()) ? "" : StringUtil.dealDateFormat(item.getMap().getAlarmTime()))
                .addOnClickListener(R.id.tv_look);

    }
}
