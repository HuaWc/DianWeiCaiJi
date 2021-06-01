package com.hwc.dwcj.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.DossierDetailActivity;
import com.hwc.dwcj.adapter.DAChildItemAdapter;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.entity.DAChildItem;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.EventUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DossierNextFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.all)
    LinearLayout all;

    List<DAChildItem> mList;
    DAChildItemAdapter mAdapter;
    @BindView(R.id.bar)
    View bar;
    private String positionName;
    private String orgName;
    private String describe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dossier_next, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initAdapter();
        initClick();
    }

    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil
                    .OPEN_DA_NEXT2:
                String code = (String) center.getData();
                getData(code);
                break;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventCenter(EventUtil.EXIT_DA_NEXT));

            }
        });
    }

    private void getData(String code) {
        if (StringUtil.isEmpty(code)) {
            Toast.makeText(getContext(), "获取位置码失败，请稍后再试", Toast.LENGTH_LONG).show();
            EventBus.getDefault().post(new EventCenter(EventUtil.EXIT_DA_NEXT));
            return;
        }
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("positionCode", code);
        ApiClient.requestNetPost(getContext(), AppConfig.filePositionDetails, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                positionName = FastJsonUtil.getString(json, "positionName");
                orgName = FastJsonUtil.getString(json, "orgName");
                describe = FastJsonUtil.getString(json, "describe");
                List<DAChildItem> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "ptCameraInfoList"), DAChildItem.class);
                mList.clear();
                if (list != null && list.size() != 0) {
                    mList.addAll(list);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        tv1.setText(positionName);
                        tv2.setText(orgName);
                        tv3.setText(describe);
                    }
                });

            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getContext(), "获取信息失败：" + msg, Toast.LENGTH_LONG).show();
                //EventBus.getDefault().post(new EventCenter(EventUtil.EXIT_DA_NEXT));
            }
        });

    }

    private void initAdapter() {
        mList = new ArrayList<>();
        mAdapter = new DAChildItemAdapter(mList);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("cameraId", mList.get(position).getId());
                //bundle.putString("positionName", positionName);
                toTheActivity(DossierDetailActivity.class, bundle);
            }
        });
    }
}
