package com.hwc.dwcj.activity.second;

import android.content.Intent;
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
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private int page = 1;
    private int pageSize = 15;

    boolean needRefresh = false;//点击消息的时候，如果消息是未读状态，设置为true,onResume的时候刷新数据，如果是已读消息设置为false,onResume的时候不刷新数据

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
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(false);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getData(true);
            }
        });
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        adapter = new MessageInfoAdapter(mList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //先调用已读，再进入详情
                MessageInfo message = mList.get(position);
                if(message.getMsgState() == 0){
                    //未读
                    read(position);
                    needRefresh = true;
                } else{
                    needRefresh = false;
                }
                if (message.getMsgType() == null) {
                    ToastUtil.toast("该消息通知类型未知！无法进入相关页面");
                    return;
                }
                Bundle bundle = new Bundle();
                Intent newIntent;
                switch (message.getMsgType()) {
                    case "1":
                        //新增处理
                    case "2":
                        //协同处理
                    case "3":
                        //工单派发
                    case "4":
                        //工单催办
                        bundle.putString("id", message.getFaultId());
                        newIntent = new Intent(MessageListActivity.this, WorkOrderDetailActivity.class);
                        newIntent.putExtras(bundle);
                        startActivity(newIntent);
                        break;
                    case "5":
                        //变更下发
                        bundle.putInt("enterPage", 1);
                        newIntent = new Intent(MessageListActivity.this, TaskManageActivity.class);
                        newIntent.putExtras(bundle);
                        startActivity(newIntent);
                        break;
                    case "6":
                        //巡检下发
                        bundle.putInt("enterPage", 0);
                        newIntent = new Intent(MessageListActivity.this, TaskManageActivity.class);
                        newIntent.putExtras(bundle);
                        startActivity(newIntent);
                        break;
                    case "7":
                        //核查下发
                        bundle.putInt("enterPage", 2);
                        newIntent = new Intent(MessageListActivity.this, TaskManageActivity.class);
                        newIntent.putExtras(bundle);
                        startActivity(newIntent);
                        break;
                    case "8":
                        //保障下发
                        bundle.putInt("enterPage", 3);
                        newIntent = new Intent(MessageListActivity.this, TaskManageActivity.class);
                        newIntent.putExtras(bundle);
                        startActivity(newIntent);
                        break;
                    default:
                        ToastUtil.toast("该消息通知类型未知！无法进入相关页面");
                }
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        getData(false);
    }

    private void read(int position) {
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("msgId", mList.get(position).getMsgId());
        hashMap.put("msgReceId", mList.get(position).getId());
        ApiClient.requestNetGet(this, AppConfig.PtMsgReceiverChange, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {

            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }


    private void getData(boolean more) {
        if (more) {
            page++;
        } else {
            page = 1;
            mList.clear();
        }
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("pageNum", page);
        hashMap.put("pageSize", pageSize);
        ApiClient.requestNetGet(this, AppConfig.PtMsgReceiverMsgList, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<MessageInfo> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), MessageInfo.class);
                if (list != null && list.size() != 0) {
                    mList.addAll(list);
                } else {
                    if (page > 1) {
                        page--;
                    }
                }
                adapter.notifyDataSetChanged();
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();

            }

            @Override
            public void onFailure(String msg) {
                if (page > 1) {
                    page--;
                }
                ToastUtil.toast(msg);
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(needRefresh){
            getData(false);
        }
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
