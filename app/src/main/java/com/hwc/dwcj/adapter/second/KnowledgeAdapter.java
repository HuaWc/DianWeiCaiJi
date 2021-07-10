package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.Knowledge;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * Created by Christ on 2021/6/22.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class KnowledgeAdapter extends BaseQuickAdapter<Knowledge, BaseViewHolder> {
    public KnowledgeAdapter(@Nullable List<Knowledge> data) {
        super(R.layout.adapter_knowledge, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Knowledge item) {
        helper.setText(R.id.tv_name, StringUtil.isEmpty(item.getCode()) ? "" : item.getCode()).setText(R.id.tv_theme,item.getTheme())
                .setText(R.id.tv_type, item.getMap().getKnowledgeType() + (StringUtil.isEmpty(item.getMap().getKnowledgeClass()) ? "" : (" > " + item.getMap().getKnowledgeClass())))
                .setText(R.id.tv_time, StringUtil.isEmpty(item.getCreateTime()) ? "" : item.getCreateTime())
                .setText(R.id.tv_author, StringUtil.isEmpty(item.getMap().getAuthor()) ? "" : item.getMap().getAuthor())
                .setText(R.id.tv_num, item.getCount() + "");
    }
}
