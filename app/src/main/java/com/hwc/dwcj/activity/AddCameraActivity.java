package com.hwc.dwcj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.AdapterAddCameraChild;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.AddCameraChild;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.RecyclerViewHelper;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCameraActivity extends BaseActivity {
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
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.bar)
    View bar;

    private AdapterAddCameraChild adapterAddCameraChild;
    private List<AddCameraChild> addCameraChildList;
    private String positionName;
    private String orgName;
    private String describe;
    private String positionCode;
    private String longitude;
    private String latitude;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_add_camera);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initAdapter();
        initClick();
    }

    private void initAdapter() {
        addCameraChildList = new ArrayList<>();
        adapterAddCameraChild = new AdapterAddCameraChild(addCameraChildList);
        rv.setAdapter(adapterAddCameraChild);
        rv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewHelper.recyclerviewAndScrollView(rv);
        adapterAddCameraChild.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("cameraId", addCameraChildList.get(position).getId());
                //bundle.putString("positionName", positionName);
                toTheActivity(DossierDetailActivity.class, bundle);
            }
        });
        getData();
    }

    private void getRole() {
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetGet(this, AppConfig.getUserRole, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                boolean a = FastJsonUtil.getObject(json, boolean.class);
                if(a){
                    Bundle bundle = new Bundle();
                    bundle.putString("positionCode", positionCode);
/*                    bundle.putString("longitude",longitude);
                    bundle.putString("latitude",latitude);
                    bundle.putString("positionName", positionName);*/
                    bundle.putInt("from", 1);
                    toTheActivity(AddCameraDetailActivity.class, bundle);
                } else {
                    ToastUtil.toast("您还不是采集员，权限不足，无法新增！");
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast("获取您的采集员权限失败，请稍后重试！");

            }
        });
    }

    private void initClick() {
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRole();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData() {
        if (StringUtil.isEmpty(positionCode)) {
            Toast.makeText(this, "杆件位置码不存在，请重试", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("positionCode", positionCode);
        ApiClient.requestNetPost(this, AppConfig.filePositionDetails, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                positionName = FastJsonUtil.getString(json, "positionName");
                orgName = FastJsonUtil.getString(json, "orgName");
                describe = FastJsonUtil.getString(json, "describe");
                longitude = FastJsonUtil.getString(json,"longitude");
                latitude = FastJsonUtil.getString(json,"latitude");
                List<AddCameraChild> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "ptCameraInfoList"), AddCameraChild.class);
                addCameraChildList.clear();
                if (list != null && list.size() != 0) {
                    addCameraChildList.addAll(list);
                }
                AddCameraActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterAddCameraChild.notifyDataSetChanged();
                        tv1.setText(positionName);
                        tv2.setText(orgName);
                        tv3.setText(describe);
                    }
                });

            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(AddCameraActivity.this, "杆件位置码不存在，请重试", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        positionCode = extras.getString("positionCode");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
