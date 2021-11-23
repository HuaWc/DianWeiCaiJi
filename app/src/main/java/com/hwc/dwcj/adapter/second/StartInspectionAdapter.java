package com.hwc.dwcj.adapter.second;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.second.StartInspection;

import java.util.List;

/**
 * Created by Christ on 2021/6/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class StartInspectionAdapter extends BaseQuickAdapter<StartInspection.InspectionItemDTO, BaseViewHolder> {
    public StartInspectionAdapter( @Nullable List<StartInspection.InspectionItemDTO> data) {
        super(R.layout.adapter_start_inspection, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StartInspection.InspectionItemDTO item) {
        helper.setText(R.id.tv_num,(helper.getLayoutPosition()+1)+"");
        helper.setText(R.id.tv_name,item.map.devName);
        helper.setText(R.id.tv1,item.map.ip);
        helper.setText(R.id.tv2, item.map.positionCode);
        //helper.setText(R.id.tv3,item.map.memberbarCode);
        helper.setText(R.id.tv4,item.map.inspectionStatus);
    }
}
