package com.hwc.dwcj.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.SelectUserAdapter;
import com.hwc.dwcj.adapter.SelectedUserAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.CheckUser;
import com.hwc.dwcj.util.EventUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.util.BarUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCheckUserActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.icon_back)
    ImageView iconBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.rel_con)
    RelativeLayout relCon;
    @BindView(R.id.rv_user)
    RecyclerView rvUser;
    @BindView(R.id.tv_no_select)
    TextView tvNoSelect;
    @BindView(R.id.tv_select_number)
    TextView tvSelectNumber;
    @BindView(R.id.tv_qd)
    TextView tvQd;
    @BindView(R.id.ll_btn)
    RelativeLayout llBtn;
    @BindView(R.id.bar2)
    View bar2;
    @BindView(R.id.icon_back2)
    ImageView iconBack2;
    @BindView(R.id.ll_back2)
    LinearLayout llBack2;
    @BindView(R.id.toolbar_subtitle2)
    TextView toolbarSubtitle2;
    @BindView(R.id.img_right2)
    ImageView imgRight2;
    @BindView(R.id.toolbar_title2)
    TextView toolbarTitle2;
    @BindView(R.id.toolbar2)
    RelativeLayout toolbar2;
    @BindView(R.id.view_line2)
    View viewLine2;
    @BindView(R.id.rel_con2)
    RelativeLayout relCon2;
    @BindView(R.id.rv_selected)
    RecyclerView rvSelected;
    @BindView(R.id.ll_selected)
    LinearLayout llSelected;

    private int type;//1 审批人 2 抄送人
    private ArrayList<CheckUser> data;
    private ArrayList<CheckUser> selectedData;

    private SelectUserAdapter selectUserAdapter;
    private SelectedUserAdapter selectedUserAdapter;
    private int sizeAll;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_add_check_user);

    }

    @Override
    protected void initLogic() {
        if (bar2 != null) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) bar2.getLayoutParams();
            params.height = BarUtils.getStatusBarHeight(this);
            bar2.setLayoutParams(params);
            bar2.setBackgroundColor(getResources().getColor(R.color.white));
        }
        sizeAll = data.size();
        tvQd.setText("确定(" + 0 + "/" + sizeAll + ")");
        initAdapter();
        initClick();
    }

    private void initAdapter() {
        if (selectedData == null) {
            selectedData = new ArrayList<>();
        } else {
            for (int i = 0; i < selectedData.size(); i++) {
                if (selectedData.get(i).getRealName().equals("")) {
                    selectedData.remove(i);
                    break;
                }
            }
        }
        selectUserAdapter = new SelectUserAdapter(data);
        selectedUserAdapter = new SelectedUserAdapter(selectedData);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        rvSelected.setLayoutManager(new LinearLayoutManager(this));
        rvUser.setAdapter(selectUserAdapter);
        selectUserAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_select:
                        data.get(position).setSelected(!data.get(position).isSelected());
                        selectUserAdapter.notifyItemChanged(position);
                        if (data.get(position).isSelected()) {
                            selectedData.add(data.get(position));
                            changeNumber();
                            selectedUserAdapter.notifyDataSetChanged();
                        } else {
                            deleteFromAll(data.get(position).getId());
                        }
                        break;
                }
            }
        });
        rvSelected.setAdapter(selectedUserAdapter);
        selectedUserAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        deleteFromSelected(selectedData.get(position).getId());
                        selectedData.remove(position);
                        selectedUserAdapter.notifyDataSetChanged();
                        changeNumber();
                        break;
                }
            }
        });
        if (selectedData.size() != 0) {
            changeNumber();
            selectedUserAdapter.notifyDataSetChanged();
        }
    }

    private void changeNumber() {
        int size = selectedData.size();

        if (size == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvQd.setText("确定(" + 0 + "/" + sizeAll + ")");
                    tvNoSelect.setVisibility(View.VISIBLE);
                    tvSelectNumber.setVisibility(View.GONE);
                    tvQd.setSelected(false);
                    tvQd.setClickable(false);
                }
            });

        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvQd.setText("确定(" + size + "/" + sizeAll + ")");
                    tvSelectNumber.setText("已选择:" + size + "人");
                    tvNoSelect.setVisibility(View.GONE);
                    tvSelectNumber.setVisibility(View.VISIBLE);
                    tvQd.setSelected(true);
                    tvQd.setClickable(true);
                }
            });


        }
    }

    private void initClick() {
        tvSelectNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animBottomIn = AnimationUtils.loadAnimation(SelectCheckUserActivity.this,
                        R.anim.bottom_in);
                animBottomIn.setDuration(500);
                llSelected.setVisibility(View.VISIBLE);
                llSelected.startAnimation(animBottomIn);

            }
        });
        toolbarSubtitle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animBottomOut = AnimationUtils.loadAnimation(SelectCheckUserActivity.this,
                        R.anim.bottom_out);
                animBottomOut.setDuration(500);
                llSelected.setVisibility(View.GONE);
                llSelected.startAnimation(animBottomOut);
            }
        });

        tvQd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("data", data);
                hashMap.put("selectedData", selectedData);
                EventBus.getDefault().post(new EventCenter(type == 1 ? EventUtil.SELECT_CHECK : EventUtil.SELECT_SEND, hashMap));
                finish();
            }
        });
    }

    private void deleteFromAll(String id) {
        for (int i = 0; i < selectedData.size(); i++) {
            if (selectedData.get(i).getId().equals(id)) {
                selectedData.remove(i);
                selectedUserAdapter.notifyDataSetChanged();
                break;
            }
        }
        changeNumber();
    }

    private void deleteFromSelected(String id) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(id)) {
                data.get(i).setSelected(false);
                selectUserAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        data = extras.getParcelableArrayList("data");
        selectedData = extras.getParcelableArrayList("selectedData");
        type = extras.getInt("type");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
