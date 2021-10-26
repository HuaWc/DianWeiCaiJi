package com.hwc.dwcj.util;

import com.hwc.dwcj.base.MyApplication;

/**
 * Created by Christ on 2021/10/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class CommonUtils {
    /**
     * 获取dimens定义的大小
     *
     * @param dimensionId
     * @return
     */
    public static int getPixelById(int dimensionId) {
        return MyApplication.getInstance().getResources().getDimensionPixelSize(dimensionId);
    }
}
