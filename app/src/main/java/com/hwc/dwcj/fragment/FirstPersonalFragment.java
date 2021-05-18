package com.hwc.dwcj.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.ActivityStackManager;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.zds.base.entity.EventCenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的
 *
 * @author Administrator
 */
public class FirstPersonalFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.tv_logout)
    TextView tvLogout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        initBar();
        initClick();

    }

    private void initClick() {
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("token", MyApplication.getInstance().getToken());
        ApiClient.requestNetGet(getContext(), AppConfig.logout, "退出中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                MyApplication.getInstance().cleanUserInfo();
                MyApplication.getInstance().toLogin(getContext());
            }

            @Override
            public void onFailure(String msg) {
                MyApplication.getInstance().cleanUserInfo();
                MyApplication.getInstance().toLogin(getContext());

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

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
