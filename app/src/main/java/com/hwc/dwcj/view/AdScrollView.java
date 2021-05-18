package com.hwc.dwcj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class AdScrollView extends ScrollView {

    public AdScrollView(Context context) {
        super(context);

    }

    public AdScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    //这个方法如果返回 true 的话 两个手指移动，启动一个按下的手指的移动不能被传播出去。  
    public boolean onInterceptTouchEvent(MotionEvent event) {
        super.onInterceptTouchEvent(event);
        return false;
    }


    //这个方法如果 true 则整个Activity 的 onTouchEvent() 不会被系统回调
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }
}
