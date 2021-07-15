package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.MessageInfoAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.MessageInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zds.base.entity.EventCenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/7/15.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class MessageListActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;

    private List<MessageInfo> mList;
    private MessageInfoAdapter adapter;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_message_list);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();

    }


    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }
        });
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        adapter = new MessageInfoAdapter(mList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        getData();
    }

    private void getData() {
        for (int i = 0; i < 10; i++) {
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setName("【催办】XXXXXX工单被催办..");
            messageInfo.setStatus(Math.random() > 0.5f ? 0 : 1);
            mList.add(messageInfo);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
