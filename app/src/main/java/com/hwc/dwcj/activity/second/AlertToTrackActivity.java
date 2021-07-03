package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.second.WorkOrderTrackAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.WorkOrderTrackInfo;
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
 * Created by Christ on 2021/6/29.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class AlertToTrackActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.rv_track)
    RecyclerView rvTrack;

    private String alarmId;
    private String faultId;
    private List<WorkOrderTrackInfo> mList;
    private WorkOrderTrackAdapter adapter;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_alert_to_track);
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
        getTrackData();
    }


    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void getTrackData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("faultId", faultId);
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

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        alarmId = extras.getString("alarmId");
        faultId = extras.getString("faultId");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
