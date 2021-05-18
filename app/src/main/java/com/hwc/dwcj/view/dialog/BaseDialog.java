package com.hwc.dwcj.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.hwc.dwcj.R;
import com.hwc.dwcj.util.RegularCheckUtil;

import java.util.List;

/**
 * Created by zzx on 2018/08/07/上午 10:50
 */

public class BaseDialog {

    private static  BaseDialog mDiaolog;
    private Dialog dialog;
    private View mLayoutView;
    private Context mContext;

    private double weith = 0.7;
    private double height = 0.4;

    public boolean isCancelable = true;

    private int dialogHegint = ViewGroup.LayoutParams.WRAP_CONTENT;


    /**
     * 任意控件点击事件接口
     */
    public interface OnClickListener {
        void onClick(View view, Dialog dialog);
    }

    public  BaseDialog setDialogHegint(int dialogHegint) {
        this.dialogHegint = dialogHegint;
        return this;
    }

    public  BaseDialog isCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        return this;
    }

    public interface OnAdapterChangeListener {
        void onClick(int position, Object o, Dialog dialog);
    }


    /**
     * 任意控件点击事件
     */
    public  BaseDialog setOnClickListener(View view, final OnClickListener onClickListener) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v, dialog);
                }
            }
        });
        return mDiaolog;
    }



    public static  BaseDialog getInstance() {
        if (mDiaolog == null) {
            synchronized ( BaseDialog.class) {
                if (mDiaolog == null) {
                    mDiaolog = new  BaseDialog();
                }
            }
        }
        return mDiaolog;
    }

    public static  BaseDialog getUnInstance() {
        mDiaolog = new  BaseDialog();
        return mDiaolog;
    }

    public  BaseDialog setWindow(double weith, double height) {
        this.weith = weith;
        this.height = height;
        return this;
    }

    public  BaseDialog setLayoutView(View layoutView, Context mContext) {
        mLayoutView = layoutView;
        this.mContext = mContext;
        return mDiaolog;
    }

    /**
     * 弹窗位于中间
     *
     * @return
     */
    public  BaseDialog show() {
        dialog = new Dialog(mContext, R.style.SimpleDialogLight);
        dialog.setContentView(mLayoutView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dialogHegint));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * weith);
        if (height != 0.0){
            lp.height = (int) (d.heightPixels * height);
        }else {
            lp.height = d.heightPixels;
        }
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(isCancelable);
        dialog.show();
        return mDiaolog;
    }

    /**
     * 弹窗位于中间
     *
     * @return
     */
    public  BaseDialog show2() {
        dialog = new Dialog(mContext, R.style.SimpleDialogLight);
        dialog.setContentView(mLayoutView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dialogHegint));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //dialog宽高适应子布局xml
        lp.height= LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.width= LinearLayout.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(isCancelable);
        dialog.show();
        return mDiaolog;
    }


    /**
     * 弹窗位于底部
     *
     * @return
     */
    public BaseDialog bottomShow() {
        dialog = new Dialog(mContext, R.style.SimpleDialogLight);
        dialog.setContentView(mLayoutView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dialogHegint));

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(isCancelable);
        dialog.show();
        RegularCheckUtil.animation(0, mLayoutView, dialog);
        return mDiaolog;
    }

    /**
     * 弹窗位于底部
     *
     * @return
     */
    public BaseDialog bottomShow2() {
        dialog = new Dialog(mContext, R.style.SimpleDialogLight2);
        dialog.setContentView(mLayoutView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dialogHegint));

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(isCancelable);
        dialog.show();
        //RegularCheckUtil.animation(0, mLayoutView, dialog);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialog_bottom_animstyle);
        return mDiaolog;
    }

    public  BaseDialog dissmissDialog() {
        if (mDiaolog != null && dialog != null) {
            dialog.dismiss();
        }
        return mDiaolog;
    }

    /**
     * 设置高度
     */
    public static <T> int setViewHegith(Activity activity, float size, List<T> list, View view, int listSize) {
        int hh;
        if (list.size() > listSize) {
            hh = activity.getWindowManager().getDefaultDisplay().getHeight();
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (hh * size));
            view.setLayoutParams(lp);
            hh = (int) (hh * size);
        } else {
            hh = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        return hh;
    }
}
