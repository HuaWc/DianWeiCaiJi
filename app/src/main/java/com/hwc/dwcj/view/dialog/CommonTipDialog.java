package com.hwc.dwcj.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hwc.dwcj.R;


/**
 * Create by hwc on 2020/11/5
 * <p>
 * 带标题的标准提示弹窗
 */
public class CommonTipDialog {
    private static CommonTipDialog tipDialog;
    private Activity mActivity;
    private String title, content;
    private SpannableStringBuilder contentBuilder;


    private String btnLeft = "";
    private String btnRight = "";

    private String btnLeftColor = "#333333";
    private String btnRightColor = "#333333";

    private boolean isCancelable;

    private boolean isContentSpannable;

    public boolean isContentSpannable() {
        return isContentSpannable;
    }

    public CommonTipDialog setContentSpannable(boolean contentSpannable) {
        isContentSpannable = contentSpannable;
        return this;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public CommonTipDialog setCancelable(boolean cancelable) {
        isCancelable = cancelable;
        return this;
    }

    private OnClickListener onClickListener;

    public interface OnClickListener {

        void onLeft(View view, Dialog dialog);

        void onRight(View view, Dialog dialog);

    }


    public CommonTipDialog addTipData(Activity mActivity, String title,
                                                                           String btnLeft, String btnRight) {
        this.mActivity = mActivity;
        this.title = title;
        this.btnLeft = btnLeft;
        this.btnRight = btnRight;
        return this;
    }

    public  CommonTipDialog addOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public  CommonTipDialog addBtnColor(String btnLeftColor, String btnRightColor) {
        this.btnLeftColor = btnLeftColor;
        this.btnRightColor = btnRightColor;
        return this;
    }

    public  CommonTipDialog addTipContentBuilder(SpannableStringBuilder contentBuilder) {
        this.contentBuilder = contentBuilder;
        return this;
    }

    public  CommonTipDialog addTipContent(String content) {
        this.content = content;
        return this;
    }

/*
    public static CommonTipDialog getInstance() {
        if (tipDialog == null) {
            synchronized (CommonTipDialog.class) {
                if (tipDialog == null) {
                    tipDialog = new CommonTipDialog();
                }
            }
        }
        return tipDialog;
    }
*/

    public static  CommonTipDialog getInstance() {
        tipDialog = new  CommonTipDialog();
        return tipDialog;
    }

    public  CommonTipDialog show() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_common_tip, null);

        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);
        TextView tv_btn_left = view.findViewById(R.id.tv_btn_left);
        TextView tv_btn_right = view.findViewById(R.id.tv_btn_right);

        tv_title.setText(title);
        if (isContentSpannable) {
            tv_content.setText(contentBuilder);
        } else {
            tv_content.setText(content);
        }
        tv_btn_left.setText(btnLeft);
        tv_btn_right.setText(btnRight);
        tv_btn_left.setTextColor(Color.parseColor(btnLeftColor));
        tv_btn_right.setTextColor(Color.parseColor(btnRightColor));


        BaseDialog
                .getUnInstance()
                .dissmissDialog()
                .setLayoutView(view, mActivity)
                .setWindow(0.75, 0.45)
                .isCancelable(isCancelable)
                .setOnClickListener(tv_btn_left, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        onClickListener.onLeft(view, dialog);
                    }
                })
                .setOnClickListener(tv_btn_right, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        onClickListener.onRight(view, dialog);

                    }
                })
                .show();

        return this;
    }


}
