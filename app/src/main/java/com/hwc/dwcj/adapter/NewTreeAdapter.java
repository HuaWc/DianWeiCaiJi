package com.hwc.dwcj.adapter;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.entity.NewTreeItem;
import com.hwc.dwcj.entity.TreeCamera;
import com.hwc.dwcj.util.RecyclerViewHelper;

import java.util.List;

public class NewTreeAdapter extends BaseQuickAdapter<NewTreeItem, BaseViewHolder> {
    private ClickThirdResult clickThirdResult;

    public NewTreeAdapter(@Nullable List<NewTreeItem> data, ClickThirdResult clickThirdResult) {
        super(R.layout.adapter_new_tree, data);
        this.clickThirdResult = clickThirdResult;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewTreeItem item) {
        View ll_main = helper.getView(R.id.ll_main);
        View rl_child = helper.getView(R.id.rl_child);
        RecyclerView rv_child = helper.getView(R.id.rv);
        if (item.getLevel() == 1) {
            helper.setText(R.id.tv_add_point, "");
        } else {
            helper.setText(R.id.tv_add_point, "···");
        }
        helper.setText(R.id.text, item.getName());
        ll_main.setVisibility(item.isVisible() ? View.VISIBLE : View.GONE);
        if (item.getLevel() == 2) {
            rl_child.setVisibility(item.isExpand() ? View.VISIBLE : View.GONE);
            if (item.getCameraList() != null && item.getCameraList().size() != 0) {
                TreeCameraAdapter adapter = new TreeCameraAdapter(item.getCameraList());
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        clickThirdResult.onClick(NewTreeAdapter.this.mData.indexOf(item), item.getCameraList().get(position),position);
                    }
                });
                rv_child.setLayoutManager(new LinearLayoutManager(mContext));
                rv_child.setAdapter(adapter);
                RecyclerViewHelper.recyclerviewAndScrollView(rv_child);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public interface ClickThirdResult {
        void onClick(int position, TreeCamera camera,int positionChild);
    }
}
