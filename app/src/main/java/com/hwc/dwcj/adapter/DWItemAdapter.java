package com.hwc.dwcj.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.DWItem;
import com.hwc.dwcj.util.RecyclerViewHelper;
import com.zds.base.util.StringUtil;

import java.util.List;

public class DWItemAdapter extends BaseQuickAdapter<DWItem, BaseViewHolder> {
    private DWChildClickListener dwChildClickListener;

    public DWItemAdapter(@Nullable List<DWItem> data, DWChildClickListener dwChildClickListener) {
        super(R.layout.dw_item, data);
        this.dwChildClickListener = dwChildClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, DWItem item) {
        helper.setText(R.id.tv_name, item.getPositionName());
        if (item.getCameraInfoList() != null && item.getCameraInfoList().size() != 0) {
            RecyclerView rv = helper.getView(R.id.rv_child);
            DWChildItemAdapter adapter = new DWChildItemAdapter(item.getCameraInfoList());
            rv.setLayoutManager(new LinearLayoutManager(mContext));
            rv.setAdapter(adapter);
            RecyclerViewHelper.recyclerviewAndScrollView(rv);
            adapter.notifyDataSetChanged();
            adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.tv_look:
                            dwChildClickListener.look(item.getCameraInfoList().get(position));
                            break;
                        case R.id.tv_edit:
                            dwChildClickListener.edit(item.getCameraInfoList().get(position),item.getPositionName());
                            break;
                    }
                }
            });
        }
    }

    class DWChildItemAdapter extends BaseQuickAdapter<DWItem.CameraInfoList, BaseViewHolder> {
        public DWChildItemAdapter(@Nullable List<DWItem.CameraInfoList> data) {
            super(R.layout.dw_child_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, DWItem.CameraInfoList item) {
            View tvLook = helper.getView(R.id.tv_look);
            View tvEdit = helper.getView(R.id.tv_edit);
            helper.addOnClickListener(R.id.tv_look).addOnClickListener(R.id.tv_edit);
            helper.setText(R.id.tv_name, item.getCameraName());
            if (item.getCurrentStatus() != null) {
                //1.审批中  2.已撤销  3.已驳回 4.已通过
                if (item.getCurrentStatus() == 1) {
                    helper.setText(R.id.tv_status, "审批中");
                    tvEdit.setVisibility(View.GONE);
                    tvLook.setVisibility(View.VISIBLE);
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_yellow));
                } else if (item.getCurrentStatus() == 2) {
                    helper.setText(R.id.tv_status, "已撤销");
                    tvEdit.setVisibility(View.GONE);
                    tvLook.setVisibility(View.VISIBLE);
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_gray));
                } else if (item.getCurrentStatus() == 3) {
                    helper.setText(R.id.tv_status, "已驳回");
                    tvEdit.setVisibility(View.GONE);
                    tvLook.setVisibility(View.VISIBLE);
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
                } else if (item.getCurrentStatus() == 4) {
                    helper.setText(R.id.tv_status, "已通过");
                    tvEdit.setVisibility(View.GONE);
                    tvLook.setVisibility(View.VISIBLE);
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
                }  else if (item.getCurrentStatus() == 0) {
                    helper.setText(R.id.tv_status, "草稿");
                    tvEdit.setVisibility(View.VISIBLE);
                    tvLook.setVisibility(View.GONE);
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_gray));
                }else {
                    helper.setText(R.id.tv_status, "");
                }
            } else{
                helper.setText(R.id.tv_status, "");
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
            if (!StringUtil.isEmpty(item.getAddTime())) {
                helper.setText(R.id.tv_time, item.getAddTime().substring(0, 7));
            } else {
                helper.setText(R.id.tv_time, "");
            }

        }
    }

    public interface DWChildClickListener {
        void look(DWItem.CameraInfoList c);

        void edit(DWItem.CameraInfoList c,String positionName);
    }

}
