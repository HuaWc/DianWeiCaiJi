package com.hwc.dwcj.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.PointCheckActivity;
import com.hwc.dwcj.activity.second.AlertManagementActivity;
import com.hwc.dwcj.activity.second.InspectionManageActivity;
import com.hwc.dwcj.activity.second.KnowledgeBaseListActivity;
import com.hwc.dwcj.activity.second.WorkOrderEvaluationActivity;
import com.hwc.dwcj.activity.second.WorkOrderManagementAdminActivity;
import com.hwc.dwcj.activity.second.WorkOrderManagementUserActivity;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.view.dialog.BaseDialog;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

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

    }

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {
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
                showDialog();
                break;
            case R.id.ll_rwgl:
                toTheActivity(InspectionManageActivity.class);
                break;
            case R.id.ll_ywzsk:
                toTheActivity(KnowledgeBaseListActivity.class);
                break;
            case R.id.ll_gdpjgl:
                toTheActivity(WorkOrderEvaluationActivity.class);
                break;
            case R.id.ll_dzdt:
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
