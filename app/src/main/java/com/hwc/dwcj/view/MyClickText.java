package com.hwc.dwcj.view;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.MyApplication;

/**
 * Created by panj on 2019-11-19.
 */
public class MyClickText extends ClickableSpan {
    private Context context;

    public MyClickText(Context context) {
        this.context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        //设置文本的颜色
        ds.setColor(MyApplication.getInstance().getResources().getColor(R.color.colorPrimary));
        //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
//        Toast.makeText(context,"发生了点击效果",Toast.LENGTH_SHORT).show();
    }
}