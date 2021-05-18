package com.hwc.dwcj.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.SPItem;
import com.hwc.dwcj.util.RecyclerViewHelper;


import java.util.List;

public class SPItemAdapter extends BaseQuickAdapter<SPItem, BaseViewHolder> {
    private SPChildClickListener spChildClickListener;

    public SPItemAdapter(@Nullable List<SPItem> data, SPChildClickListener spChildClickListener) {
        super(R.layout.sp_item, data);
        this.spChildClickListener = spChildClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, SPItem item) {
        helper.setText(R.id.tv_name, item.getPositionName());
        if (item.getCameraInfoList() != null && item.getCameraInfoList().size() != 0) {
            RecyclerView rv = helper.getView(R.id.rv_child);
            SPChildItemAdapter adapter = new SPChildItemAdapter(item.getCameraInfoList());
            rv.setLayoutManager(new LinearLayoutManager(mContext));
            rv.setAdapter(adapter);
            RecyclerViewHelper.recyclerviewAndScrollView(rv);
            adapter.notifyDataSetChanged();
            adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.tv_look:
                            spChildClickListener.look(item.getPositionName(),item.getCameraInfoList().get(position));
                            break;
                        case R.id.tv_edit:
                            spChildClickListener.edit(item.getCameraInfoList().get(position));
                            break;
                    }
                }
            });
        }
    }

    class SPChildItemAdapter extends BaseQuickAdapter<SPItem.CameraInfoList, BaseViewHolder> {
        public SPChildItemAdapter(@Nullable List<SPItem.CameraInfoList> data) {
            super(R.layout.sp_child_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SPItem.CameraInfoList item) {
            View tvLook = helper.getView(R.id.tv_look);
            View tvEdit = helper.getView(R.id.tv_edit);
            helper.setText(R.id.tv_name, item.getCameraName());
            helper.addOnClickListener(R.id.tv_look).addOnClickListener(R.id.tv_edit);
            //1.审批中  2.已撤销  3.已驳回 4.已通过
            if (item.getCurrentStatus() != null) {

                if (item.getCurrentStatus() == 1) {
                    helper.setText(R.id.tv_status, "审批中");
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
                    tvEdit.setVisibility(View.GONE);
                } else if (item.getCurrentStatus() == 2) {
                    helper.setText(R.id.tv_status, "已撤销");
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_gray));
                    tvEdit.setVisibility(View.GONE);
                } else if (item.getCurrentStatus() == 3) {
                    helper.setText(R.id.tv_status, "已驳回");
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
                    tvEdit.setVisibility(View.VISIBLE);
                } else if (item.getCurrentStatus() == 4) {
                    helper.setText(R.id.tv_status, "已通过");
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
                    tvEdit.setVisibility(View.GONE);
                } else {
                    helper.setText(R.id.tv_status, "");
                    //((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
                    tvEdit.setVisibility(View.GONE);
                }
            } else {
                helper.setText(R.id.tv_status, "");
                //((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
                tvEdit.setVisibility(View.GONE);
            }

/*            switch (item.getInformationType()) {
                //1.信息采集  2.草稿  3.采集信息修改
                case 1:
                    helper.setText(R.id.tv_g1, "信息采集");
                    break;
                case 2:
                    helper.setText(R.id.tv_g1, "草稿");
                    break;
                case 3:
                    helper.setText(R.id.tv_g1, "采集信息修改");
                    break;
            }*/
            //helper.setText(R.id.tv_g2, item.get);
            helper.setText(R.id.tv_time, item.getAddTime().substring(0, 7));

        }
    }

    public interface SPChildClickListener {
        void look(String positionName,SPItem.CameraInfoList c);

        void edit(SPItem.CameraInfoList c);

    }

}
