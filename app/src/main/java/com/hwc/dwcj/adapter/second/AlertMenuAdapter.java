package com.hwc.dwcj.adapter.second;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.second.AlertMenuInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

public class AlertMenuAdapter extends BaseQuickAdapter<AlertMenuInfo, BaseViewHolder> {
    public AlertMenuAdapter(@Nullable List<AlertMenuInfo> data) {
        super(R.layout.adapter_main_alert, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlertMenuInfo item) {
        TextView tv_look = helper.getView(R.id.tv_look);
        TextView tv_evaluate = helper.getView(R.id.tv_evaluate);
        helper.setText(R.id.tv_name, item.getAlarmName())
                .setText(R.id.tv_status, item.getAlarmStatus())
                .setText(R.id.tv_time, item.getMap().getAlarmTime())
                .setText(R.id.tv_g, item.getMap().getOrgName())
                .setText(R.id.tv_ip, item.getIp())
                .setText(R.id.tv_jg, item.getAssetNature() + ">" + item.getAssetType() + ">" + item.getMap().getAssetClass());
        helper.addOnClickListener(R.id.tv_look).addOnClickListener(R.id.tv_evaluate);

        String myId = MyApplication.getInstance().getUserInfo().getId();
        if((!StringUtil.isEmpty(item.getAlarmPersonId()) && item.getAlarmPersonId().equals(myId)) && "闭环".equals(item.getAlarmStatus())
                && StringUtil.isEmpty(item.getServiceRating()) && StringUtil.isEmpty(item.getServiceRating2())
                && StringUtil.isEmpty(item.getServiceRating3()) && StringUtil.isEmpty(item.getServiceRating4())){
            tv_evaluate.setVisibility(View.VISIBLE);
        } else {
            tv_evaluate.setVisibility(View.GONE);
        }
    }
}
