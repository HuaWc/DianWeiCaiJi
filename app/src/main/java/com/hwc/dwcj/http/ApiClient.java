package com.hwc.dwcj.http;

import android.content.Context;

import com.hwc.dwcj.base.Constant;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.http.ServerData;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.log.XLog;
import com.zds.base.util.StringUtil;
import com.zds.base.view.Loading_view;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * 作   者：赵大帅
 * 描   述: 网络请求工具类
 * 日   期: 2017/11/13 16:05
 * 更新日期: 2017/11/13
 */
public class ApiClient {

    static Loading_view progressDialog = null;
    static MediaType mediaType = MediaType.parse("application/json; charset=utf-8");


    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            OkGo.<String>post(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param mapP
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            OkGo.<String>post(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param jsonObject
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, String log, final JSONObject jsonObject, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            OkGo.<String>post(url)
                    .tag(context)
                    .upRequestBody(RequestBody.create(mediaType, jsonObject.toString()))
                    .headers("token", MyApplication.getInstance().getToken())
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param jsonParam
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, String log, String jsonParam, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            OkGo.<String>post(url)
                    .tag(context)
                    .upJson(jsonParam)
                    .headers("token", MyApplication.getInstance().getToken())
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, String log, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            OkGo.<String>post(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, String log, String token, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
//            showDialog(log, context);
            OkGo.<String>post(url)
                    .tag(context)
                    .headers("token", StringUtil.isEmpty(token) ? MyApplication.getInstance().getToken() : token)
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetPostFile(final Context context, String url, String log, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            HttpParams params = new HttpParams();
            Map<String, Object> map = new HashMap<>();
            boolean mult = false;
            if (null != mapP && mapP.size() > 0) {
                for (String key : mapP.keySet()) {
                    if (mapP.get(key) instanceof File) {
                        List<HttpParams.FileWrapper> list = new ArrayList<>();
                        list.add(new HttpParams.FileWrapper((File) mapP.get(key), ((File) mapP.get(key)).getName(), MediaType.parse("text/html;charset=UTF-8")));
                        params.putFileWrapperParams(key, list);
                        mult = true;
                    } else {
                        map.put(key, mapP.get(key));
                    }
                }
            }
/*            if (null != mapP && mapP.size() > 0) {
                for (String key : mapP.keySet()) {
                    if (mapP.get(key) instanceof List<?>) {
                        List<HttpParams.FileWrapper> list = new ArrayList<>();
                        for (File f : (List<File>) mapP.get(key)) {
                            list.add(new HttpParams.FileWrapper(f, f.getName(), MediaType.parse("text/html;charset=UTF-8")));
                        }
                        params.putFileWrapperParams(key, list);
                    } else {
                        map.put(key, mapP.get(key));
                    }
                }
            }*/
            OkGo.<String>post(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .params(params)
                    .params(getFormatMap(map))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }


    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, String log, boolean cache, final Map<String, Object> mapP, final ResultListener listener) {
        if (!cache) {
            if (!MyApplication.getInstance().isNetworkConnected()) {
                //没网络
                listener.onFailure("网络连接异常,请检查您的网络设置");
                return;
            }
        }
        try {
            showDialog(log, context);
            OkGo.<String>post(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .cacheMode(cache ? CacheMode.FIRST_CACHE_THEN_REQUEST : CacheMode.NO_CACHE)
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  *
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onCacheSuccess(Response<String> response) {
                                     super.onCacheSuccess(response);
                                     formatData(response, listener, context);
                                     dismiss();
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, String log, final Map<String, Object> mapP, final Map<String, String> head, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            HttpHeaders httpHeaders = new HttpHeaders();
            if (null != head && head.size() > 0) {
                for (String key : head.keySet()) {
                    httpHeaders.put(key, head.get(key));
                }
            }
            OkGo.<String>post(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .headers(httpHeaders)
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  *
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetPostNoFormat(final Context context, String url, String log, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            OkGo.<String>post(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  *
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     listener.onSuccess(response.body().toString(), "");
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * Get
     *
     * @param context
     * @param url
     * @param listener
     */
    public static void requestNetGet(final Context context, String url, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            OkGo.<String>get(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * Get
     *
     * @param context
     * @param url
     * @param listener
     */
    public static void requestNetGetNoFormat(final Context context, String url, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            OkGo.<String>get(url)
                    .tag(context)
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     listener.onSuccess(response.body().toString(), "");
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }


    /**
     * 请求网络数据接口
     * Get
     *
     * @param context
     * @param url
     * @param mapP
     * @param listener
     */
    public static void requestNetGet(final Context context, String url, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            OkGo.<String>get(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetGet(final Context context, String url, String log, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            OkGo.<String>get(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * Get
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetGet(final Context context, String url, String log, boolean cache, final Map<String, Object> mapP, final ResultListener listener) {
        if (!cache) {
            if (!MyApplication.getInstance().isNetworkConnected()) {
                //没网络
                listener.onFailure("网络连接异常,请检查您的网络设置");
                return;
            }
        }
        try {
            showDialog(log, context);
            OkGo.<String>get(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .cacheMode(cache ? CacheMode.FIRST_CACHE_THEN_REQUEST : CacheMode.NO_CACHE)
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  *
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onCacheSuccess(Response<String> response) {
                                     super.onCacheSuccess(response);
                                     formatData(response, listener, context);
                                     dismiss();
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * Get
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetGet(final Context context, String url, String log, final Map<String, Object> mapP, final Map<String, String> head, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            HttpHeaders httpHeaders = new HttpHeaders();
            if (null != head && head.size() > 0) {
                for (String key : head.keySet()) {
                    httpHeaders.put(key, head.get(key));
                }
            }
            OkGo.<String>get(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .headers(httpHeaders)
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  *
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     formatData(response, listener, context);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * Get
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetGetNoFormat(final Context context, String url, String log, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            OkGo.<String>get(url)
                    .tag(context)
                    .headers("token", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  *
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     listener.onSuccess(response.body().toString(), "");
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }
                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    private static Map<String, String> getFormatMap(Map<String, Object> mapP) {
        Map<String, String> map = new HashMap<>();
        if (null != mapP && mapP.size() > 0) {
            for (String key : mapP.keySet()) {
                map.put(key, String.valueOf(mapP.get(key)));
            }
        }
        return map;
    }

    /**
     * show dialog
     */
    private static void showDialog(String log, Context context) {
        if (!StringUtil.isEmpty(log)) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
            progressDialog = new Loading_view(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage(log);
            progressDialog.show();
        }
    }

    /**
     * dis dialog
     */
    private static void dismiss() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 格式化请求数据
     */
/*    private static void formatData(Response<String> response, ResultListener listener) {
        try {
            ServerData serverData = FastJsonUtil.getObject(response.body().toString(), ServerData.class);
            if (null != serverData) {
                try {
                    if(serverData.getData().toString().indexOf("用户token信息不存在") != -1){
                        //token失效了哦,退出登录,去到登录页
                        MyApplication.getInstance().logoutToLoginActivity();
                        MyToastUtils.refreshToast("登陆失效，请重新登陆");
                    }else{
                        serverData.setMessage(FastJsonUtil.getString(FastJsonUtil.toJSONString(serverData.getData()), "msg"));
                    }
                } catch (Exception e) {

                }
                if (serverData.getCode() == Constant.CODESUCCESS) {
                    if("success".equals(serverData.getStatus())){
                        listener.onSuccess(FastJsonUtil.toJSONString(serverData.getData()), serverData.getMessage() == null ? "" : serverData.getMessage());
                    } else {
                        listener.onFailure(serverData.getData().toString() == null ? "" : serverData.getMessage());
                    }
                } else {
                    listener.onFailure(serverData.getData().toString() == null ? "" : serverData.getMessage());
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
            listener.onFailure("解析异常");
        }

    }*/


    /**
     * 格式化请求数据
     */
    private static void formatData(Response<String> response, ResultListener listener, Context context) {
        try {
            ServerData serverData = FastJsonUtil.getObject(response.body().toString(), ServerData.class);
            if (null != serverData) {
                if ((serverData.getMessage() != null && serverData.getMessage().contains("请先登录")) || (serverData.getData() != null && serverData.getData().toString().contains("请先登录"))) {
                    //token失效了哦,退出登录,去到登录页
                    MyApplication.getInstance().toLogin(context);
                    return;
                }
                if (serverData.getCode() == Constant.CODESUCCESS) {
                    listener.onSuccess(FastJsonUtil.toJSONString(serverData.getData()), serverData.getMessage() == null ? "" : serverData.getMessage());
                } else {
                    try {
                        listener.onFailure(FastJsonUtil.getString(serverData.getMessage(), "msg"));
                    } catch (Exception e) {
                        e.getStackTrace();
                        listener.onFailure(serverData.getMessage());
                    }
                }


            }
        } catch (Exception e) {
            e.getStackTrace();
            listener.onFailure("数据解析异常");
        }

    }

}
