package com.hwc.dwcj.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.PointCheckActivity;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.view.AdScrollView;
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
    @BindView(R.id.ll_gzbx)
    LinearLayout llGzbx;
    @BindView(R.id.ll_jqqd)
    LinearLayout llJqqd;
    @BindView(R.id.scview)
    AdScrollView scview;
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

    @OnClick({R.id.ll_dwkc, R.id.ll_gzbx, R.id.ll_jqqd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_dwkc:
                toTheActivity(PointCheckActivity.class);
                break;
            case R.id.ll_gzbx:
                //AddDetailActivity.start(mContext);
                break;
            case R.id.ll_jqqd:
                //LocateActivity.start(mContext);
                break;
        }
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


}
