package com.hwc.dwcj.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.base.BaseAndroidJs;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.base.Storage;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.util.ResourUtil;
import com.hwc.dwcj.view.PhotoDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zds.base.entity.EventCenter;
import com.zds.base.log.XLog;
import com.zds.base.util.StringUtil;
import com.zds.base.util.UriUtil;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 * 日期 2018/8/15
 * 描述 网页
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.bar)
    View mBar;
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.icon_back)
    ImageView mIconBack;
    @BindView(R.id.ll_back)
    LinearLayout mLlBack;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.img_right)
    ImageView mImgRight;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    RelativeLayout mToolbar;
    @BindView(R.id.view_line)
    View mViewLine;
    @BindView(R.id.tv_no_net)
    TextView mTvNoNet;
    @BindView(R.id.tv_flush)
    TextView mTvFlush;
    @BindView(R.id.tv_finsh)
    TextView mTvFinsh;
    @BindView(R.id.ll_no_net)
    LinearLayout mLlNoNet;
    private String title;
    private String url;

    private int typePay;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_web);
    }

    @SuppressLint("NewApi")
    @Override
    protected void initLogic() {
        if (!StringUtil.isEmpty(title)) {
            setTitle(title);
        }
        setTitle("加载失败");
        //MyApplication.getInstance().setCook(url);
        webSet();
        Map<String, String> additionalHttpHeaders = new HashMap<>();
        additionalHttpHeaders.put("cookie", Storage.getToken());
        if (!StringUtil.isEmpty(url)) {
            mWebview.loadUrl(url, additionalHttpHeaders);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.PAY_SUCCESS) {
            if (typePay == 2) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebview.evaluateJavascript("javascript:paySuccess()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为 js 返回的结果
                            }
                        });
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebview.evaluateJavascript("javascript:jsPaySuccess()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为 js 返回的结果
                            }
                        });
                    }
                });
            }
        } else if (center.getEventCode() == EventUtil.FLUSH_LOGIN) {
            //MyApplication.getInstance().setCook(url);
            mWebview.reload();
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
        url = extras.getString("url");
        typePay = extras.getInt("typePay", 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            super.onBackPressed();
            //finish();
        }
    }

    /**
     * web 设置
     */
    private void webSet() {
        if (MyApplication.getInstance().isNetworkConnected()) {
            mLlNoNet.setVisibility(View.GONE);
        } else {
            mLlNoNet.setVisibility(View.VISIBLE);
        }
        mTvFlush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isNetworkConnected()) {
                    mLlNoNet.setVisibility(View.GONE);
                } else {
                    mLlNoNet.setVisibility(View.VISIBLE);
                }
                XLog.json(mWebview.getUrl());
                mWebview.reload();
            }
        });
        mTvFinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebview.addJavascriptInterface(new BaseAndroidJs(this, mWebview), "app");
        WebSettings webSetting = mWebview.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setDomStorageEnabled(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        WebView.setWebContentsDebuggingEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, final String message,
                                     final JsResult result) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(WebViewActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage(message);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message,
                                       final JsResult result) {
                // TODO Auto-generated method stub
                super.onJsConfirm(view, url, message, result);
                return true;
            }


            @Override
            public void onCloseWindow(WebView window) {
                // TODO Auto-generated method stub
                super.onCloseWindow(window);
                // setResult(RESULT_OK);
                finish();
            }

            //For Android API < 11 (3.0 OS)
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadMessage = valueCallback;
//                openImageChooserActivity();
                upDatedImg("");
            }

            //For Android API >= 11 (3.0 OS)
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                upDatedImg("");
            }

            //For Android API >= 21 (5.0 OS)
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                upDatedImg("");
                return true;
            }

        });
        WebViewClient webViewClient = new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (StringUtil.isEmpty(title)) {
                    setTitle(view.getTitle());
                }
                mLlNoNet.setVisibility(View.GONE);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (view.getUrl().contains(AppConfig.goodsList) && request.getUrl().toString().contains(AppConfig.goodDetail)) {
                    startActivity(new Intent(mContext, WebViewActivity.class).putExtra("url", request.getUrl().toString()));
                    return true;
                }
                if (view.getUrl().contains(AppConfig.shoreUrl) && request.getUrl().toString().contains(AppConfig.goodDetail)) {
                    startActivity(new Intent(mContext, WebViewActivity.class).putExtra("url", request.getUrl().toString()));
                    return true;
                }

                String url = request.getUrl().toString();
                return ResourUtil.shouldOverrideUrlLoading(view, super.shouldOverrideUrlLoading(view, request), url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (view.getUrl().contains(AppConfig.goodsList) && url.contains(AppConfig.goodDetail)) {
                    startActivity(new Intent(mContext, WebViewActivity.class).putExtra("url", url));
                    return true;
                }
                if (view.getUrl().contains(AppConfig.shoreUrl) && url.contains(AppConfig.goodDetail)) {
                    startActivity(new Intent(mContext, WebViewActivity.class).putExtra("url", url));
                    return true;
                }
                return ResourUtil.shouldOverrideUrlLoading(view, super.shouldOverrideUrlLoading(view, url), url);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return ResourUtil.shouldInterceptRequest(super.shouldInterceptRequest(view, request), view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return ResourUtil.shouldInterceptRequest(super.shouldInterceptRequest(view, url), view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) { // 重写此方法可以让webview处理https请求
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mLlNoNet.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mLlNoNet.setVisibility(View.VISIBLE);
            }

        };
        mWebview.setWebViewClient(webViewClient);
    }

    /**
     * 上传图片
     */
    public void upDatedImg(String token) {
        PhotoDialog.getInstance().initView(this, token).setOnMyClickListener(new PhotoDialog.onMyClickListener() {
            @Override
            public void onMyXJClick(Dialog dialog, String token, int typeNum) {
                if (!StringUtil.isEmpty(token) && token.equals("1")) {
                    if (uploadMessageAboveL != null) {
                        uploadMessageAboveL.onReceiveValue(null);
                        uploadMessageAboveL = null;
                    } else if (uploadMessage != null) {
                        uploadMessage.onReceiveValue(null);
                        uploadMessage = null;
                    }
                } else if (!StringUtil.isEmpty(token) && token.equals("2")) {
                    PictureSelector.create(WebViewActivity.this)
                            .openCamera(PictureMimeType.ofImage())
                            .maxSelectNum(1)
                            .imageSpanCount(4)
                            .isCamera(true)
                            .enableCrop(false)
                            .compress(false)// 是否压缩 true or false
                            .freeStyleCropEnabled(true)
                            .selectionMode(PictureConfig.MULTIPLE)
                            .forResult(119);

                } else {
                    PictureSelector.create(WebViewActivity.this)
                            .openGallery(PictureMimeType.ofImage())
                            .maxSelectNum(1)
                            .imageSpanCount(4)
                            .isCamera(true)
                            .enableCrop(false)
                            .compress(false)// 是否压缩 true or false
                            .freeStyleCropEnabled(true)
                            .selectionMode(PictureConfig.MULTIPLE)
                            .forResult(119);
                }
                dialog.dismiss();
            }


        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 119:
                    final List<String> list1 = new ArrayList<>();
                    List<LocalMedia> selectList1 = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < selectList1.size(); i++) {
                        if (selectList1.get(i).isCompressed()) {
                            list1.add(selectList1.get(i).getCompressPath());
                        } else if (selectList1.get(i).isCut()) {
                            list1.add(selectList1.get(i).getCutPath());
                        } else {
                            list1.add(selectList1.get(i).getPath());
                        }
                    }
                    if (uploadMessageAboveL != null) {
                        if (list1.size() > 0) {
                            Tiny.getInstance().source(list1.get(0)).asFile().compress(new FileCallback() {
                                @Override
                                public void callback(boolean isSuccess, String outfile, Throwable t) {
                                    if (isSuccess) {
                                        uploadMessageAboveL.onReceiveValue(new Uri[]{UriUtil.getUri(WebViewActivity.this, outfile)});
                                    } else {
                                        uploadMessageAboveL.onReceiveValue(new Uri[]{UriUtil.getUri(WebViewActivity.this, list1.get(0))});
                                    }
                                    uploadMessageAboveL = null;
                                }
                            });

                        } else {
                            uploadMessageAboveL.onReceiveValue(null);
                            uploadMessageAboveL = null;
                        }
                    } else if (uploadMessage != null) {
                        if (list1.size() > 0) {
                            Tiny.getInstance().source(list1.get(0)).asFile().compress(new FileCallback() {
                                @Override
                                public void callback(boolean isSuccess, String outfile, Throwable t) {
                                    if (isSuccess) {
                                        uploadMessage.onReceiveValue(UriUtil.getUri(WebViewActivity.this, outfile));
                                    } else {
                                        uploadMessage.onReceiveValue(UriUtil.getUri(WebViewActivity.this, list1.get(0)));
                                    }
                                    uploadMessage = null;
                                }
                            });

                        } else {
                            uploadMessage.onReceiveValue(null);
                            uploadMessage = null;
                        }
                    }

                    break;
            }
        } else {
            if (uploadMessageAboveL != null) {
                uploadMessageAboveL.onReceiveValue(null);
                uploadMessageAboveL = null;
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }
        }


    }

    //5.0以下使用
    private ValueCallback<Uri> uploadMessage;
    // 5.0及以上使用
    private ValueCallback<Uri[]> uploadMessageAboveL;


}
