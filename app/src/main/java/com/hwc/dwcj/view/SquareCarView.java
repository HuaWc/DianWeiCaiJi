package com.hwc.dwcj.view;

import android.content.Context;
import androidx.cardview.widget.CardView;
import android.util.AttributeSet;

/**
 * @author Administrator
 *         日期 2018/4/26
 *         描述
 */

public class SquareCarView extends CardView {
    public SquareCarView(Context context) {
        super(context);
    }

    public SquareCarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareCarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        //重写此方法后默认调用父类的onMeasure方法, 分别将宽度测量空间与高度测量空间传入
    }
}