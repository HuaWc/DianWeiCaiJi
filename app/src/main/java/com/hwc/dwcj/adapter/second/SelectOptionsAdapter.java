package com.hwc.dwcj.adapter.second;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.DictInfo;
import com.hwc.dwcj.entity.second.SelectOptionData;
import com.hwc.dwcj.util.RecyclerViewHelper;

import java.util.List;

/**
 * Created by Christ on 2021/7/2.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class SelectOptionsAdapter extends BaseQuickAdapter<SelectOptionData, BaseViewHolder> {
    public SelectOptionsAdapter(@Nullable List<SelectOptionData> data) {
        super(R.layout.adapter_select_options, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectOptionData item) {
        helper.setText(R.id.tv_name, item.getName());
        RecyclerView rv = helper.getView(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        SelectOptionsChildAdapter adapter = new SelectOptionsChildAdapter(item.getOptions());
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                for(DictInfo d:item.getOptions()){
                    if(d.isSelected() == true){
                        d.setSelected(false);
                        adapter.notifyItemChanged(item.getOptions().indexOf(d));
                        break;
                    }
                }
                item.getOptions().get(position).setSelected(!item.getOptions().get(position).isSelected());
                adapter.notifyItemChanged(position);
            }
        });
        rv.setAdapter(adapter);
        RecyclerViewHelper.recyclerviewAndScrollView(rv);
        adapter.notifyDataSetChanged();

    }



}
