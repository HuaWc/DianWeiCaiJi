package com.hwc.dwcj.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.LoginActivity;
import com.hwc.dwcj.base.ActivityStackManager;
import com.hwc.dwcj.base.BaseFragment;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_ip)
    TextView tvIp;
    @BindView(R.id.tv_time)
    TextView tvTime;
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
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));

        initClick();
        tvName.setText(MyApplication.getInstance().getUserInfo().getRealName());
        tvAccount.setText(StringUtil.isEmpty(MyApplication.getInstance().getUserInfo().getUserName()) ? "暂无信息" : MyApplication.getInstance().getUserInfo().getUserName());
        tvPhone.setText(StringUtil.isEmpty(MyApplication.getInstance().getUserInfo().getMobileNo()) ? "暂无信息" : MyApplication.getInstance().getUserInfo().getMobileNo());
        tvEmail.setText(StringUtil.isEmpty(MyApplication.getInstance().getUserInfo().getEmail()) ? "暂无信息" : MyApplication.getInstance().getUserInfo().getEmail());
//        tvIp.setText(StringUtil.isEmpty(MyApplication.getInstance().getUserInfo().getMap().getIpAdress()) ? "" : MyApplication.getInstance().getUserInfo().getMap().getIpAdress());
        tvTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(MyApplication.getInstance().getUserInfo().getLoginTime())));



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
                ToastUtil.toast("退出成功！");
                ActivityStackManager.getInstance().killAllActivity();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(String msg) {
                MyApplication.getInstance().cleanUserInfo();
                ToastUtil.toast("退出成功！");
                ActivityStackManager.getInstance().killAllActivity();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
