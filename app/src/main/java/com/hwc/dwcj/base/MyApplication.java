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

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.multidex.MultiDex;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.MapsInitializer;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.AddCameraDetailActivity;
import com.hwc.dwcj.activity.LoginActivity;
import com.hwc.dwcj.activity.second.AlertManagementActivity;
import com.hwc.dwcj.activity.second.WorkOrderDetailActivity;
import com.hwc.dwcj.entity.CheckVersionInfo;
import com.hwc.dwcj.entity.UserInfo;
import com.hwc.dwcj.entity.VersionInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.updata.CretinAutoUpdateUtils;
import com.hwc.dwcj.util.GDLocationUtil;
import com.hwc.dwcj.view.dialog.CommonDialog;
import com.hwc.dwcj.view.dialog.CommonTipDialog;
import com.hwc.dwcj.wxapi.WeChatConstans;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
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
import com.zds.base.json.FastJsonUtil;
import com.zds.base.log.XLog;
import com.zds.base.util.StringUtil;
import com.zds.base.util.Utils;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

import static com.hwc.dwcj.http.ApiClient.getFormatMap;


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
        //sddJPushInterface.init(this);
        /**
         * ????????????????????????,????????????????????????????????????
         * @param  context: ?????????
         * @param  isContains: ??????????????????????????????????????????????????????  true?????????
         * @param  isShow: ????????????????????????????????????????????? true?????????
         * @since 8.1.0
         */
        MapsInitializer.updatePrivacyShow(this, true, true);
        /**
         * ????????????????????????,????????????????????????????????????
         * @param context: ?????????
         * @param isAgree: ???????????????????????????????????????  true???????????????
         * @since 8.1.0
         */
        MapsInitializer.updatePrivacyAgree(this, true);
        /** ?????????????????????????????????????????????????????? <b>?????????AmapLocationClient?????????????????????</b>
         *
         * @param context
         * @param isContains: ?????????????????????????????????????????????????????????  true?????????
         * @param isShow: ????????????????????????????????????????????? true?????????
         * @since 5.6.0
         */
        AMapLocationClient.updatePrivacyShow(this, true, true);
        /**
         * ???????????????????????????????????? <b>?????????AmapLocationClient?????????????????????</b>
         * @param context
         * @param isAgree:???????????????????????????????????????  true???????????????
         *
         * @since 5.6.0
         */
        AMapLocationClient.updatePrivacyAgree(this, true);
        GDLocationUtil.init(this);

    }

    private IWXAPI mIWXAPI;

    public IWXAPI registerWx() {
        mIWXAPI = WXAPIFactory.createWXAPI(this, WeChatConstans.APP_ID, true);
        mIWXAPI.registerApp(WeChatConstans.APP_ID);
        return mIWXAPI;
    }

    /**
     * ?????????
     */
    private void init() {
        // ????????????
        Tiny.getInstance().init(this);
        // ????????????????????????
        setRefush();
        // ????????????
        //setUpDataApp();
        // util
        Utils.init(this);
        //??????
        //uploadManager = new UploadManager(new Configuration.Builder().build());
        //bugly bug
        //CrashReport.initCrashReport(getApplicationContext(), "42a24f434c", false);
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
     * ????????????????????????
     *
     * @return
     */
    public String getVersionName() {
        return getPackageInfo().versionName;
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    /**
     * ??????App???????????????
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
     * ????????????????????????
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * ????????????????????????
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
     * ??????
     */
    public void shareDialog(Context context, final View.OnClickListener onClickListener) {
        //????????????
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
     * ????????????????????????
     *
     * @param user
     */
    public void saveUserInfo(final UserInfo user) {
        if (user != null) {
            Storage.saveUsersInfo(user);
        }
    }

    /**
     * ????????????????????????
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
     * ???????????????ture ?????????false
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
     * ??????cooks
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
                        , "70b9___ewei_shopv2_member_session_1=" + getUserInfo().getToken() + ";path=/");//cookies??????HttpClient????????????cookie
            } else {
                cookieManager.setCookie(url
                        , "70b9___ewei_shopv2_member_session_1=;path=/");//cookies??????HttpClient????????????cookie

            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.flush();
            }

        } catch (Exception e) {

        }


    }

    public void toLogin(Context context) {
        cleanUserInfo();
        ActivityStackManager.getInstance().killAllActivity();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        ToastUtil.toast("????????????????????????????????????~");
    }

    /**
     * ???????????????ture ?????????false
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
     * ????????????????????????
     *
     * @param
     */
    public void cleanUserInfo() {
        Storage.ClearUserInfo();
    }


    /**
     * ??????????????????
     */
    private void setUpDataApp() {

        //????????????
        CretinAutoUpdateUtils.Builder builder = new CretinAutoUpdateUtils.Builder()
                //????????????api
                .setBaseUrl(AppConfig.checkVersion)
                //?????????????????????????????????
                .setIgnoreThisVersion(true)
                //???????????????????????? ?????????????????????????????? ?????????
                .setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG_WITH_PROGRESS)
                //??????????????????????????????
                .setIconRes(R.mipmap.app_logo)
                //??????????????????log??????
                .showLog(true)
                //??????????????????
                .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_POST)
                //????????????????????????????????????
                .setAppName(getResources().getString(R.string.app_name))
                //??????????????????Model???
                .setTransition(new VersionInfo())
                .build();
        CretinAutoUpdateUtils.init(builder);
    }

    /**
     * ?????? ??????
     */
    private void setRefush() {
        //???????????????Header?????????
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                ClassicsHeader header = new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale);
                header.setPrimaryColors(ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.text_gray));
                return header;//???????????????Header???????????? ???????????????Header
            }
        });
        //???????????????Footer?????????
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setEnableLoadmoreWhenContentNotFull(true);
                ClassicsFooter footer = new ClassicsFooter(context);
                footer.setBackgroundResource(android.R.color.white);
                footer.setSpinnerStyle(SpinnerStyle.Scale);//?????????????????????
                return footer;//???????????????Footer???????????? BallPulseFooter
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


    public void checkVersion(Activity mContext) {
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetPost(mContext, AppConfig.checkVersion, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                //?????????????????????????????????
                CheckVersionInfo codeAndroid = FastJsonUtil.getObject(json, CheckVersionInfo.class);
                if (codeAndroid != null) {
                    if (!MyApplication.getInstance().getVersionName().equals(codeAndroid.getVerCodeAndroid().replace("v-", ""))) {
                        //??????????????????????????????????????????????????????
                        showUpdateDialog(mContext, codeAndroid);
                    }
                }

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void showUpdateDialog(Activity mContext, CheckVersionInfo codeAndroid) {
        CommonTipDialog.getInstance()
                .addTipData(mContext, "????????????", "??????", "??????")
                .addBtnColor("#FF191F25", "#FF0000FF")
                .addTipContent(codeAndroid.getVerDesc())
                .setCancelable(false)
                .addOnClickListener(new CommonTipDialog.OnClickListener() {
                    @Override
                    public void onLeft(View v, Dialog dialog) {
                        ActivityStackManager.getInstance().killAllActivity();
                    }

                    @Override
                    public void onRight(View v, Dialog dialog) {
                        dialog.dismiss();
                        //??????????????????
                        PermissionsUtil.requestPermission(mContext, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permission) {

                                //?????????????????????
                                getUpdateFileStream(mContext, codeAndroid);
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permission) {
                                ToastUtil.toast("?????????????????????");
                                ActivityStackManager.getInstance().killAllActivity();
                            }
                        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                })
                .show();
    }

    private void getUpdateFileStream(Activity mContext, CheckVersionInfo codeAndroid) {
        String apkName = "YunWeiGuanLi" + "." + codeAndroid.getVerCodeAndroid() + ".apk";
        String llsApkFilePath = Environment.getExternalStorageDirectory() + File.separator + apkName;
        File file = new File(llsApkFilePath);
        if (file.exists()) { // ????????????????????????
            file.delete();
        } else {
            Log.i("FileManageUtils", "???????????????:" + file.toString());
        }

        showDownloadDialog(mContext);

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("filePath", codeAndroid.getVerUrl());
        hashMap.put("fileName", apkName);
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //?????????
            ToastUtil.toast("??????????????????,???????????????????????????");
            return;
        }
        try {
            OkGo.<File>get(AppConfig.getDownloadAccessory)
                    .tag(mContext)
                    .params(getFormatMap(hashMap))
                    .execute(new com.lzy.okgo.callback.FileCallback() {
                        @Override
                        public void onSuccess(Response<File> response) {
                            //????????????
                            dialog1.dismiss();

                            String path = response.body().getPath();
                            File file = new File(path);
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                Uri uri;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // ??????Android 7????????????
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //???????????????????????????????????????????????????Uri??????????????????
                                    uri = FileProvider.getUriForFile(MyApplication.this, MyApplication.getInstance().getPackageName() + ".fileProvider", file);//??????FileProvider????????????content?????????Uri
                                } else {
                                    uri = Uri.fromFile(file);
                                }
                                intent.setDataAndType(uri, "application/vnd.android.package-archive"); // ??????apk??????
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                MyApplication.getInstance().startActivity(intent);
                                //????????????????????????
                                ActivityStackManager.getInstance().killAllActivity();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Response<File> response) {
                            super.onError(response);
                            ToastUtil.toast("????????????,???????????????????????????");
                            ActivityStackManager.getInstance().killAllActivity();

                        }

                        @Override
                        public void downloadProgress(Progress progress) {
                            super.downloadProgress(progress);
                            float small = progress.currentSize / 1024;
                            float total = progress.totalSize / 1024;
                            float bytes = progress.speed / 1024;
                            float pro = small / total * 100;
                            DecimalFormat decimalFormat = new DecimalFormat("##0.0");
                            mTvTitle.setText("????????????...");
                            mTvProgress.setText("???????????????" + decimalFormat.format(pro) + "%");
                            mTvSpeed.setText(formatSpeed(bytes));
                            bar1.setProgress((int) (small / total));
                        }
                    });
        } catch (
                Exception e) {
            ToastUtil.toast(e.getMessage());
        }

    }

    private Dialog dialog1;
    ProgressBar bar1;
    TextView mTvTitle;
    TextView mTvProgress;
    TextView mTvSpeed;

    private void showDownloadDialog(Activity mContext) {
        View view = View.inflate(mContext, R.layout.dialog_progressbar, null);
        dialog1 = new Dialog(mContext, R.style.SimpleDialogLight);
        dialog1.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        bar1 = view.findViewById(R.id.pb_bar);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvProgress = view.findViewById(R.id.tv_progress);
        mTvSpeed = view.findViewById(R.id.tv_speed);

        Window dialogWindow = dialog1.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // ????????????????????????
        lp.width = (int) (d.widthPixels * 0.7); // ????????????????????????0.6
        dialogWindow.setAttributes(lp);
        dialog1.setCancelable(false);
        dialog1.show();
    }

    public static boolean isWifiOpen() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        if (!info.isAvailable() || !info.isConnected()) return false;
        if (info.getType() != ConnectivityManager.TYPE_WIFI) return false;
        return true;
    }

    /**
     * ???????????????
     *
     * @param downSpeed
     * @return
     */
    private String formatSpeed(float downSpeed) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.0");
        if (downSpeed < 1024) {
            return decimalFormat.format(downSpeed) + "KB/s";
        } else if (downSpeed > 1024 && downSpeed < (1024 * 1024)) {
            return decimalFormat.format((downSpeed / (1024))) + "M/s";
        } else if (downSpeed > (1024 * 1024) && downSpeed < (1024 * 1024 * 1024)) {
            return decimalFormat.format((downSpeed / (1024 * 1024))) + "G/s";
        } else {
            return decimalFormat.format((downSpeed / (1024 * 1024 * 1024))) + "T/s";
        }
    }


    public void showAllScreenBase64ImageDialog(Activity activity, String base64) {
        //?????????dialog???????????????
        Dialog dialog = new Dialog(activity, R.style.AllScreenImage);

        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(attributes);

        ImageView imageView = new ImageView(activity);

        //??????
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //???Base64????????????????????????Bitmap
        byte[] decodedString = Base64.decode(base64.split(",")[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //??????ImageView??????
        imageView.setImageBitmap(decodedByte);

        dialog.setContentView(imageView);

        //?????????????????????????????????????????????
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
