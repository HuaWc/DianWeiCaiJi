package com.hwc.dwcj.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.PointCheckActivity;
import com.hwc.dwcj.activity.second.AlertManagementActivity;
import com.hwc.dwcj.activity.second.ElectronicMapActivity;
import com.hwc.dwcj.activity.second.KnowledgeBaseActivity;
import com.hwc.dwcj.activity.second.MessageListActivity;
import com.hwc.dwcj.activity.second.TaskManageActivity;
import com.hwc.dwcj.activity.second.WorkOrderEvaluationActivity;
import com.hwc.dwcj.activity.second.WorkOrderManagementAdminActivity;
import com.hwc.dwcj.activity.second.WorkOrderManagementUserActivity;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.view.dialog.BaseDialog;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FirstHomeFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.ll_dwkc)
    LinearLayout llDwkc;
    @BindView(R.id.ll_gjgl)
    LinearLayout llGjgl;
    @BindView(R.id.ll_gdgl)
    LinearLayout llGdgl;
    @BindView(R.id.ll_rwgl)
    LinearLayout llRwgl;
    @BindView(R.id.ll_ywzsk)
    LinearLayout llYwzsk;
    @BindView(R.id.ll_gdpjgl)
    LinearLayout llGdpjgl;
    @BindView(R.id.ll_dzdt)
    LinearLayout llDzdt;
    @BindView(R.id.ll_jqqd)
    LinearLayout llJqqd;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_message_number)
    TextView tvMessageNumber;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        initBar();
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTheActivity(MessageListActivity.class);
            }
        });
        getMessageNumber();
    }

    private void getMessageNumber() {
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetGet(getContext(), AppConfig.PtMsgReceiverMsgCount, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    tvMessageNumber.setText(json);
                    if ("0".equals(json)) {
                        tvMessageNumber.setVisibility(View.GONE);
                    } else {
                        tvMessageNumber.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvMessageNumber.setText("0");
                    tvMessageNumber.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil.REFRESH_MESSAGE_NUMBER:
                getMessageNumber();
                break;
        }
    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.ll_dwkc, R.id.ll_gjgl, R.id.ll_gdgl, R.id.ll_rwgl, R.id.ll_ywzsk, R.id.ll_gdpjgl, R.id.ll_dzdt, R.id.ll_jqqd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_dwkc:
                toTheActivity(PointCheckActivity.class);
                break;
            case R.id.ll_gjgl:
                toTheActivity(AlertManagementActivity.class);
                break;
            case R.id.ll_gdgl:
                toTheActivity(WorkOrderManagementUserActivity.class);
                //showDialog();
                break;
            case R.id.ll_rwgl:
                toTheActivity(TaskManageActivity.class);
                break;
            case R.id.ll_ywzsk:
                toTheActivity(KnowledgeBaseActivity.class);
                break;
            case R.id.ll_gdpjgl:
                toTheActivity(WorkOrderEvaluationActivity.class);
                break;
            case R.id.ll_dzdt:
                toTheActivity(ElectronicMapActivity.class);
                break;
            case R.id.ll_jqqd:
                ToastUtil.toast("敬请期待！");
                break;
        }
    }


    private void showDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_enter_work_order, null);
        View tv_user = view.findViewById(R.id.tv_user);
        View tv_admin = view.findViewById(R.id.tv_admin);
        View tv_cancel = view.findViewById(R.id.tv_cancel);
        BaseDialog.getInstance()
                .setLayoutView(view, getContext())
                .dissmissDialog()
                .setWindow(1, 0.5)
                .isCancelable(true)
                .setOnClickListener(tv_user, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        toTheActivity(WorkOrderManagementUserActivity.class);
                    }
                })
                .setOnClickListener(tv_admin, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        toTheActivity(WorkOrderManagementAdminActivity.class);
                    }
                })
                .setOnClickListener(tv_cancel, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        //取消
                        dialog.dismiss();
                    }
                }).bottomShow();
    }
}
