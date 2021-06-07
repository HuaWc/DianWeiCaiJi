package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.PersonalEvaluationInfo;

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
        helper.setText(R.id.tv_name, item.getName());
    }
}
