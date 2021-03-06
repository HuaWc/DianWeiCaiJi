package com.zds.base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;

/**
 * 作   者：赵大帅
 * 描   述: Utils初始化相关
 * 日   期: 2017/9/15 10:06
 * 更新日期: 2017/9/15
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }


}