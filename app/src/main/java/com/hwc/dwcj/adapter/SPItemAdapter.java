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
import com.zds.base.util.StringUtil;


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
                        case R.id.tv_check:
                            spChildClickListener.check(item.getPositionName(), item.getCameraInfoList().get(position));
                            break;
                    }
                }
            });
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    spChildClickListener.look(item.getPositionName(), item.getCameraInfoList().get(position));

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
            View tv_check = helper.getView(R.id.tv_check);
            View view_circle = helper.getView(R.id.view_circle);
            helper.setText(R.id.tv_name, item.getCameraName());
            helper.addOnClickListener(R.id.tv_check);
            //1.已通过  2.已驳回  3.待审批 4.已撤回 5.审批中
            if (item.getMap().getCheckType() != null) {
                if (Integer.parseInt(item.getMap().getCheckType()) == 1) {
                    helper.setText(R.id.tv_status, "已通过");
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
                    tv_check.setVisibility(View.GONE);
                } else if (Integer.parseInt(item.getMap().getCheckType()) == 2) {
                    helper.setText(R.id.tv_status, "已驳回");
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
                    tv_check.setVisibility(View.GONE);
                } else if (Integer.parseInt(item.getMap().getCheckType()) == 3) {
                    helper.setText(R.id.tv_status, "待审批");
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_gray));
                    tv_check.setVisibility(View.GONE);
                } else if (Integer.parseInt(item.getMap().getCheckType()) == 4) {
                    helper.setText(R.id.tv_status, "已撤回");
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_gray));
                    tv_check.setVisibility(View.GONE);
                } else if (Integer.parseInt(item.getMap().getCheckType()) == 5) {
                    helper.setText(R.id.tv_status, "审批中");
                    ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_red));
                    tv_check.setVisibility(View.VISIBLE);
                }else {
                    helper.setText(R.id.tv_status, "");
                    tv_check.setVisibility(View.GONE);
                    //((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
                }
            } else {
                helper.setText(R.id.tv_status, "");
                tv_check.setVisibility(View.GONE);
                //((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.sp_status_green));
            }
            if (!StringUtil.isEmpty(item.getMap().getExpedite())) {
                if ("1".equals(item.getMap().getExpedite())) {
                    //已催办
                    view_circle.setBackground(mContext.getResources().getDrawable(R.drawable.bg_da_circle_red));
                } else {
                    //未催办
                    view_circle.setBackground(mContext.getResources().getDrawable(R.drawable.bg_da_circle));
                }
            } else {
                view_circle.setBackground(mContext.getResources().getDrawable(R.drawable.bg_da_circle));
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
            if(!StringUtil.isEmpty(item.getInstallTime())){
                helper.setText(R.id.tv_time, item.getInstallTime().toString().substring(0, 7));
            } else{
                helper.setText(R.id.tv_time,"");
            }

        }
    }

    public interface SPChildClickListener {
        void look(String positionName, SPItem.CameraInfoList c);

        void check(String positionName, SPItem.CameraInfoList c);
    }

}
