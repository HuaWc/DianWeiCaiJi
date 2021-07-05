package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.WorkOrderTrackAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.WorkOrderTrackInfo;
import com.hwc.dwcj.entity.second.WorkOrderUser;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.RecyclerViewHelper;
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
 * 事件跟踪  工单详情
 */
public class WorkOrderTrackActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_track)
    RecyclerView rvTrack;

    private String id;
    private WorkOrderUser info;
    private List<WorkOrderTrackInfo> mList;
    private WorkOrderTrackAdapter adapter;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_track);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        adapter = new WorkOrderTrackAdapter(mList);
        rvTrack.setAdapter(adapter);
        rvTrack.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewHelper.recyclerviewAndScrollView(rvTrack);
        getData();
        getTrackData();
    }

    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        ApiClient.requestNetGet(this, AppConfig.OpFaultInfoInfo, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                info = FastJsonUtil.getObject(json, WorkOrderUser.class);
                initData();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void initData() {
        if (info == null) {
            return;
        }
    }

    private void getTrackData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("faultOrAlarmId", id);
        hashMap.put("checkFlag", 1);
        ApiClient.requestNetGet(this, AppConfig.lookOpFaultHandleMap, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<WorkOrderTrackInfo> list = FastJsonUtil.getList(json, WorkOrderTrackInfo.class);
                if(list != null){
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        id = extras.getString("id");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
