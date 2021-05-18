package com.zds.base.util;

import android.content.Context;

/**
 * @author Administrator
 *         日期 2018/8/8
 *         描述
 */

public class BarUtils {

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
