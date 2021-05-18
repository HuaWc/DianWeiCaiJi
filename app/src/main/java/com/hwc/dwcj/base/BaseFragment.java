package com.hwc.dwcj.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.hwc.dwcj.R;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.util.BarUtils;
import com.zds.base.view.Loading_view;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected InputMethodManager inputMethodManager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Bundle extras = getArguments();
        if (null != extras) {
            getBundleExtras(extras);
        }
        EventBus.getDefault().register(this);
        mContext = getActivity();
        initLogic();
    }


    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    /**
     * 隐藏输入法
     */
    protected void hideSoftKeyboard2() {
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                0);
    }

    /**
     * 隐藏输入法
     */
    protected void hideSoftKeyboard3() {
        inputMethodManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
    }


    @Override
    public void onPause() {
        super.onPause();
        hideSoftKeyboard();
    }

    protected void initBar() {
        View bar = getView().findViewById(R.id.bar);
        if (bar != null) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) bar.getLayoutParams();
            params.height = BarUtils.getStatusBarHeight(getActivity());
            bar.setLayoutParams(params);
            bar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        }
    }

    protected void toTheActivity(Class<?> mActivity, Bundle bundle){
        Intent intent = new Intent(getContext(),mActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void toTheActivity(Class<?> mActivity){
        Intent intent = new Intent(getContext(),mActivity);
        startActivity(intent);
    }

    /**
     * 初始化逻辑
     */
    protected abstract void initLogic();

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    protected abstract void onEventComing(EventCenter center);

    /**
     * EventBus接收消息
     *
     * @param center 消息接收
     */
    @Subscribe
    public void onEventMainThread(EventCenter center) {
        if (null != center) {
            onEventComing(center);
        }
    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * 吐司
     *
     * @param msg
     */
    public void toast(String msg) {
        ToastUtil.toast(msg);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 显示加载框
     */
    public void showLoading() {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new Loading_view(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("请求网络中...");
        dialog.show();
    }

    Loading_view dialog;

    /**
     * 显示加载框
     *
     * @param message
     */
    protected void showLoading(String message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.setMessage(message);
            return;
        }
        dialog = new Loading_view(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage(message);
        dialog.show();
    }

    /**
     * 隐藏加载框
     */
    protected void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
