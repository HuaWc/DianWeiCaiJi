/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hwc.dwcj.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.multidex.MultiDex;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.LoginActivity;
import com.hwc.dwcj.entity.UserInfo;
import com.hwc.dwcj.entity.VersionInfo;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.updata.CretinAutoUpdateUtils;
import com.hwc.dwcj.util.GDLocationUtil;
import com.hwc.dwcj.view.dialog.CommonDialog;
import com.hwc.dwcj.wxapi.WeChatConstans;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.base.SelfAppContext;
import com.zds.base.util.StringUtil;
import com.zds.base.util.Utils;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import cn.jpush.android.api.JPushInterface;


/**
 * @author Administrator
 */
public class MyApplication extends SelfAppContext {

    public static Context applicationContext;
    private static MyApplication instance;
    private UploadManager uploadManager;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        applicationContext = this;
        instance = this;
        init();
        JPushInterface.init(this);
        GDLocationUtil.init(this);

    }

    private IWXAPI mIWXAPI;

    public IWXAPI registerWx() {
        mIWXAPI = WXAPIFactory.createWXAPI(this, WeChatConstans.APP_ID, true);
        mIWXAPI.registerApp(WeChatConstans.APP_ID);
        return mIWXAPI;
    }

    /**
     * 初始化
     */
    private void init() {
        // 图片压缩
        Tiny.getInstance().init(this);
        // 下拉刷新上拉加载
        setRefush();
        // 版本更新
        setUpDataApp();
        // util
        Utils.init(this);
        //七牛
        uploadManager = new UploadManager(new Configuration.Builder().build());
        //bugly bug
        CrashReport.initCrashReport(getApplicationContext(), "42a24f434c", false);
    }


    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * 获取软件版本名称
     *
     * @return
     */
    public String getVersionName() {
        return getPackageInfo().versionName;
    }

    /**
     * 获取软件版本号
     *
     * @return
     */
    public int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null) {
            info = new PackageInfo();
        }
        return info;
    }


    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取缓存用户信息
     *
     * @return
     */
    public UserInfo getUserInfo() {
        UserInfo userInfo = Storage.GetUserInfo();
        return userInfo == null ? new UserInfo() : userInfo;
    }

    public String getToken() {
        UserInfo userInfo = getUserInfo();
        return userInfo.getToken();
    }

    private CommonDialog commonDialog;

    /**
     * 分享
     */
    public void shareDialog(Context context, final View.OnClickListener onClickListener) {
        //分享弹窗
        if (commonDialog != null && commonDialog.isShowing()) {
            commonDialog.dismiss();
        }
        commonDialog = new CommonDialog.Builder(context).setView(R.layout.share_dialog).fromBottom().fullWidth().loadAniamtion()
                .setOnClickListener(R.id.wx_chat, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListener.onClick(view);
                        if (commonDialog != null && commonDialog.isShowing()) {
                            commonDialog.dismiss();
                        }
                    }
                })
                .setOnClickListener(R.id.wx_qun, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListener.onClick(view);
                        if (commonDialog != null && commonDialog.isShowing()) {
                            commonDialog.dismiss();
                        }
                    }
                }).setOnClickListener(R.id.tv_cell, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (commonDialog != null && commonDialog.isShowing()) {
                            commonDialog.dismiss();
                        }
                    }
                }).create();
        commonDialog.show();
    }

    /**
     * 保存缓存用户信息
     *
     * @param user
     */
    public void saveUserInfo(final UserInfo user) {
        if (user != null) {
            Storage.saveUsersInfo(user);
        }
    }

    /**
     * 保存缓存用户信息
     *
     * @param user
     */
    public void uPUserInfo(final UserInfo user) {
        if (user != null) {
            UserInfo userInfo = getUserInfo();
            Storage.saveUsersInfo(user);
        }
    }

    /**
     * 用户存在是ture 否则是false
     *
     * @return
     */
    public boolean checkUser() {
        if (StringUtil.isEmpty(getUserInfo().getToken())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置cooks
     *
     * @param url
     * @param
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setCook(String url) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.createInstance(getApplicationContext());
            }
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            if (checkUser()) {
                cookieManager.setCookie(url
                        , "70b9___ewei_shopv2_member_session_1=" + getUserInfo().getToken() + ";path=/");//cookies是在HttpClient中获得的cookie
            } else {
                cookieManager.setCookie(url
                        , "70b9___ewei_shopv2_member_session_1=;path=/");//cookies是在HttpClient中获得的cookie

            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.flush();
            }

        } catch (Exception e) {

        }


    }

    public void toLogin(Context context) {
        ActivityStackManager.getInstance().killAllActivity();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        ToastUtil.toast("登录已失效，请您重新登陆~");
    }

    /**
     * 用户存在是ture 否则是false
     *
     * @return
     */
    public boolean checkUserToLogin(Context context) {
        if (StringUtil.isEmpty(getUserInfo().getToken())) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 清除缓存用户信息
     *
     * @param
     */
    public void cleanUserInfo() {
        Storage.ClearUserInfo();
    }


    /**
     * 设置版本更新
     */
    private void setUpDataApp() {

        //版本更新
        CretinAutoUpdateUtils.Builder builder = new CretinAutoUpdateUtils.Builder()
                //设置更新api
                .setBaseUrl(AppConfig.checkVersion)
                //设置是否显示忽略此版本
                .setIgnoreThisVersion(true)
                //设置下载显示形式 对话框或者通知栏显示 二选一
                .setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG_WITH_PROGRESS)
                //设置下载时展示的图标
                .setIconRes(R.mipmap.app_logo)
                //设置是否打印log日志
                .showLog(true)
                //设置请求方式
                .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_POST)
                //设置下载时展示的应用名称
                .setAppName(getResources().getString(R.string.app_name))
                //设置自定义的Model类
                .setTransition(new VersionInfo())
                .build();
        CretinAutoUpdateUtils.init(builder);
    }

    /**
     * 设置 刷新
     */
    private void setRefush() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                ClassicsHeader header = new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale);
                header.setPrimaryColors(ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.text_gray));
                return header;//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setEnableLoadmoreWhenContentNotFull(true);
                ClassicsFooter footer = new ClassicsFooter(context);
                footer.setBackgroundResource(android.R.color.white);
                footer.setSpinnerStyle(SpinnerStyle.Scale);//设置为拉伸模式
                return footer;//指定为经典Footer，默认是 BallPulseFooter
            }
        });
    }

    public UploadManager getUpM() {
        return uploadManager;
    }


    public void upPic(final String path, final String key, final String token, final UpCompletionHandler upCompletionHandler) {
        Tiny.getInstance().source(path).asFile().compress(new FileCallback() {
            @Override
            public void callback(boolean isSuccess, String outfile, Throwable t) {
                try {
                    if (isSuccess) {
                        getUpM().put(outfile, key, token,
                                upCompletionHandler, null);
                    } else {
                        upCompletionHandler.complete(key, null, null);
                    }
                } catch (Exception e) {
                    upCompletionHandler.complete(key, null, null);

                }
            }
        });

    }

    public void upVideo(final String path, final String key, final String token, final UpCompletionHandler upCompletionHandler) {
        getUpM().put(path, key, token, upCompletionHandler, null);
    }
}
