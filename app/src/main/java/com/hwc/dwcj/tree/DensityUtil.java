package com.hwc.dwcj.tree;

import android.content.Context;

/**
 * Created by xulc on 2018/8/7.
 */

public class DensityUtil {

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}

