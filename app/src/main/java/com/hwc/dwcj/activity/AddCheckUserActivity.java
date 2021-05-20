package com.hwc.dwcj.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.CheckUserAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.CheckUser;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.EventUtil;
import com.zds.base.Toast.ToastUtil;
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

public class AddCheckUserActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_point_name)
    TextView tvPointName;
    @BindView(R.id.tv_camera_name)
    TextView tvCameraName;
    @BindView(R.id.rv_check)
    RecyclerView rvCheck;
    @BindView(R.id.rv_send)
    RecyclerView rvSend;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.bar)
    View bar;

    private String positionName;
    private String cameraName;
    private ArrayList<CheckUser> users1;
    private ArrayList<CheckUser> users2;
    private CheckUserAdapter adapter1;
    private CheckUserAdapter adapter2;
    private ArrayList<CheckUser> selectedUsers1;
    private ArrayList<CheckUser> selectedUsers2;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_add_select_check);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        tvPointName.setText(positionName);
        tvCameraName.setText(cameraName);
        initClick();
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTheResultString();
            }
        });
        initAdapter();
    }

    private void handleTheResultString() {
        StringBuilder check = new StringBuilder();
        if (selectedUsers1.size() > 1) {
            for (CheckUser u : selectedUsers1) {
                if (!StringUtil.isEmpty(u.getId())) {
                    check.append(u.getId()).append(",");
                }
            }
            check.deleteCharAt(check.length() - 1);
        }
        StringBuilder send = new StringBuilder();
        if (selectedUsers2.size() > 1) {
            for (CheckUser u : selectedUsers2) {
                if (!StringUtil.isEmpty(u.getId())) {
                    send.append(u.getId()).append(",");
                }
            }
            send.deleteCharAt(send.length() - 1);
        }
        if (StringUtil.isEmpty(check.toString())) {
            ToastUtil.toast("请选择审批员");
            return;
        }

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("check", check.toString());
        hashMap.put("send", send.toString());
        EventBus.getDefault().post(new EventCenter(EventUtil.SELECT_CHECK_SEND_RESULT, hashMap));
        Log.d("check&send", check + "&" + send);
        finish();
        //ToastUtil.toast("审批人:" + check.toString() + ",抄送人:" + send.toString());
    }

    private void initAdapter() {
        users1 = new ArrayList<>();
        users2 = new ArrayList<>();

        selectedUsers1 = new ArrayList<>();
        selectedUsers2 = new ArrayList<>();
        adapter1 = new CheckUserAdapter(selectedUsers1);
        adapter2 = new CheckUserAdapter(selectedUsers2);
        rvCheck.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvSend.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvCheck.setAdapter(adapter1);
        rvSend.setAdapter(adapter2);
        adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        deleteFromSelected(selectedUsers1.get(position).getId(), 1);
                        selectedUsers1.remove(position);
                        adapter1.notifyDataSetChanged();
                        break;
                    case R.id.rl_add:
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 1);
                        bundle.putParcelableArrayList("data", users1);
                        bundle.putParcelableArrayList("selectedData", selectedUsers1);
                        toTheActivity(SelectCheckUserActivity.class, bundle);
                        break;
                }
            }
        });
        adapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        deleteFromSelected(selectedUsers2.get(position).getId(), 2);
                        selectedUsers2.remove(position);
                        adapter2.notifyDataSetChanged();
                        break;
                    case R.id.rl_add:
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 2);
                        bundle.putParcelableArrayList("data", users2);
                        bundle.putParcelableArrayList("selectedData", selectedUsers2);
                        toTheActivity(SelectCheckUserActivity.class, bundle);
                        break;
                }
            }
        });
        CheckUser user = new CheckUser();
        user.setRealName("");
        selectedUsers1.add(user);
        selectedUsers2.add(user);
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        getUserData1();
        getUserData2();
    }

    private void deleteFromSelected(String id, int type) {
        switch (type) {
            case 1:
                for (int i = 0; i < users1.size(); i++) {
                    if (users1.get(i).getId().equals(id)) {
                        users1.get(i).setSelected(false);
                        break;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < users2.size(); i++) {
                    if (users2.get(i).getId().equals(id)) {
                        users2.get(i).setSelected(false);
                        break;
                    }
                }
                break;
        }

    }


    private void getUserData1() {
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetGet(this, AppConfig.getCheckUsers, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<CheckUser> list = FastJsonUtil.getList(json, CheckUser.class);
                if (list != null && list.size() != 0) {
                    users1.addAll(list);
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void getUserData2() {
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetGet(this, AppConfig.getSupervisor, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<CheckUser> list = FastJsonUtil.getList(json, CheckUser.class);
                if (list != null && list.size() != 0) {
                    users2.addAll(list);
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil.SELECT_CHECK:
                Map<String, Object> hashMap = (Map<String, Object>) center.getData();
                ArrayList<CheckUser> data = (ArrayList<CheckUser>) hashMap.get("data");
                ArrayList<CheckUser> selectedData = (ArrayList<CheckUser>) hashMap.get("selectedData");
                users1.clear();
                users1.addAll(data);
                selectedUsers1.clear();
                selectedUsers1.addAll(selectedData);
                CheckUser user = new CheckUser();
                user.setRealName("");
                selectedUsers1.add(user);
                adapter1.notifyDataSetChanged();

                break;
            case EventUtil.SELECT_SEND:
                Map<String, Object> hashMap2 = (Map<String, Object>) center.getData();
                ArrayList<CheckUser> data2 = (ArrayList<CheckUser>) hashMap2.get("data");
                ArrayList<CheckUser> selectedData2 = (ArrayList<CheckUser>) hashMap2.get("selectedData");
                users2.clear();
                users2.addAll(data2);
                selectedUsers2.clear();
                selectedUsers2.addAll(selectedData2);
                CheckUser user2 = new CheckUser();
                user2.setRealName("");
                selectedUsers2.add(user2);
                adapter2.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        positionName = extras.getString("positionName");
        cameraName = extras.getString("cameraName");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
