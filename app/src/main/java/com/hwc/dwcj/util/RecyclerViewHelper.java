package com.hwc.dwcj.util;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by hwc on 2020/9/29
 */
public class RecyclerViewHelper {
    /**
     * 解决RecyclerView与ScrollView滑动不流畅问题
     *
     * @param view
     */
    public static void recyclerviewAndScrollView(View view) {
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
