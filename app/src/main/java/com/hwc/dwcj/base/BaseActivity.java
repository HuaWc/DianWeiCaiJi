package com.hwc.dwcj.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OnKeyboardListener;
import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.LoginActivity;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.util.BarUtils;
import com.zds.base.view.Loading_view;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * @author Administrator
 */
@SuppressLint({"NewApi", "Registered"})
public abstract class BaseActivity extends AppCompatActivity {
    protected InputMethodManager inputMethodManager;
    protected RelativeLayout mToolbar;
    protected TextView title;
    protected ImageView img_right;
    protected TextView toolbar_subtitle;
    private LinearLayout ll_back;
    private View bar;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ActivityStackManager.getInstance().addActivity(new WeakReference<Activity>(this));
        EventBus.getDefault().register(this);
        mContext = this;
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        initContentView(savedInstanceState);
        immersionBar = ImmersionBar.with(this);
        initToolBar();
        initLogic();
        initMap(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void initMap(Bundle savedInstanceState) {

    }

    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    protected void toTheActivity(Class<?> mActivity, Bundle bundle) {
        Intent intent = new Intent(this, mActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void toTheActivity(Class<?> mActivity) {
        Intent intent = new Intent(this, mActivity);
        startActivity(intent);
    }

    /**
     * 设置白色状态栏
     */
    protected void setBarWhite() {
        initBar();
        if (bar != null) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) bar.getLayoutParams();
            params.height = BarUtils.getStatusBarHeight(this);
            bar.setLayoutParams(params);
            bar.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    /**
     * 初始化Toolbar
     */
    protected void initToolBar() {
        try {
            mToolbar = (RelativeLayout) findViewById(R.id.toolbar);
            if (null != mToolbar) {
                title = (TextView) findViewById(R.id.toolbar_title);
                img_right = (ImageView) findViewById(R.id.img_right);
                toolbar_subtitle = (TextView) findViewById(R.id.toolbar_subtitle);
            }
            ll_back = (LinearLayout) findViewById(R.id.ll_back);
            if (ll_back != null) {
                ll_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideSoftKeyboard();
                        finish();
                    }
                });
            }
            bar = findViewById(R.id.bar);
            setBarWhite();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置 title
     *
     * @param string
     */
    public void setTitle(String string) {
        if (null != title) {
            title.setText(string);
        }
    }

    /**
     * 设置 title
     *
     * @param id
     */
    @Override
    public void setTitle(int id) {
        if (null != title) {
            title.setText(id);
        }
    }

    /**
     * 替代onCreate的使用
     */
    protected abstract void initContentView(Bundle bundle);


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


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        hideSoftKeyboard();
        hideSoftKeyboard3();
        EventBus.getDefault().unregister(this);
        ActivityStackManager.getInstance().removeActivity(new WeakReference<Activity>(this));
/*        if (immersionBar != null) {
            immersionBar.destroy();
        }*/
        super.onDestroy();
    }

    /**
     * 吐司
     *
     * @param msg
     */
    public void toast(String msg) {
        ToastUtil.toast(msg);
    }

    Loading_view dialog;

    /**
     * 显示加载框
     */
    public void showLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.setMessage("请求网络中...");
            return;
        }
        dialog = new Loading_view(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("请求网络中...");
        dialog.show();
    }

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
        dialog = new Loading_view(this);
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

    /**
     * 打开输入法
     *
     * @param view
     */
    protected void showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏输入法
     */
    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 隐藏输入法
     */
    protected void hideSoftKeyboard2() {
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                0);
    }

    /**
     * 隐藏输入法
     */
    protected void hideSoftKeyboard3() {
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    public ImmersionBar immersionBar;

    public void initBar() {
        if (immersionBar != null) {
            immersionBar.statusBarDarkFont(true, 0.2f).keyboardEnable(false).init();  //必须调用方可沉浸式
        }
    }

    public void setBarDarkFont(Boolean isBarDarkFont) {
        if (immersionBar != null) {
            //必须调用方可沉浸式
            immersionBar.statusBarDarkFont(isBarDarkFont, 0.2f).init();
        }
    }

    public void setBarDarkFontNoAlpha(Boolean isBarDarkFont) {
        if (immersionBar != null) {
            //必须调用方可沉浸式
            immersionBar.statusBarDarkFont(isBarDarkFont).init();
        }
    }

    //    软键盘与底部输入
    public void initkeyboardShow() {
        immersionBar.statusBarDarkFont(true).keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
                .setOnKeyboardListener(new OnKeyboardListener() {    //软键盘监听回调
                    @Override
                    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                        //  LogUtils.e(isPopup);  //isPopup为true，软键盘弹出，为false，软键盘关闭
                    }
                })
                .init();  //必须调用方可沉浸式
    }

    /**
     * 开启View闪烁效果
     */
    public void startFlick(View view) {
        if (null == view) {
            return;
        }
        if (view.getAnimation() != null) {
            return;
        }
        Animation alphaAnimation = new AlphaAnimation(1, 0.4f);
        alphaAnimation.setDuration(600);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }

    /**
     * 取消View闪烁效果
     */
    public void stopFlick(View view) {
        if (null == view) {
            return;
        }
        view.clearAnimation();
    }

    public void webViewSet(WebView webview) {
        webview.setInitialScale(25);
        WebSettings settings = webview.getSettings();
        //适应屏幕
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);// 设置允许访问文件数据
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    protected void toLogin() {
        new AlertDialog.Builder(this).setTitle("提示").setMessage("请登录").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissLoading();
            }
        }).show();
    }
}


