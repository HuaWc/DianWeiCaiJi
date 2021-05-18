package com.hwc.dwcj.view;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.util.RegularCheckUtil;

/**
 * Created by zzx on 2018/05/29/上午 10:56
 */

public class PhotoDialog {
    private Activity mActivity;
    private static PhotoDialog photoDialog;
    private Dialog dialog;
    private TextView mTvXJ, mTvXC, mTvCancel;

    public PhotoDialog() {
    }

    public static PhotoDialog getInstance() {
        if (photoDialog == null) {
            synchronized (PhotoDialog.class) {
                if (photoDialog == null) {
                    photoDialog = new PhotoDialog();
                }
            }
        }
        return photoDialog;
    }


    public PhotoDialog initView(Activity mActivity, String token) {
        this.mActivity = mActivity;
        showDialog(mActivity, token, -1);
        return photoDialog;
    }

    public PhotoDialog initView(Activity mActivity, String token, final int typeNum) {
        this.mActivity = mActivity;
        showDialog(mActivity, token, typeNum);
        return photoDialog;
    }

    private void showDialog(Activity mActivity, final String token, final int typeNum) {
        final View view = View.inflate(mActivity, R.layout.dialog_photo, null);
        dialog = new Dialog(mActivity, R.style.SimpleDialogLight);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTvXJ = (TextView) view.findViewById(R.id.tv_xj);
        mTvXC = (TextView) view.findViewById(R.id.tv_xc);
        mTvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMyClickListener != null) {
                    onMyClickListener.onMyXJClick(dialog, "1", typeNum);
                }
            }
        });


        mTvXJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMyClickListener != null) {
                    onMyClickListener.onMyXJClick(dialog, "2", typeNum);
                }
            }
        });

        mTvXC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMyClickListener != null) {
                    onMyClickListener.onMyXJClick(dialog, "3", typeNum);
                }
            }
        });

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mActivity.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 1);
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        RegularCheckUtil.animation(0, view, dialog);
        dialog.setCancelable(false);
        dialog.show();
    }

    private onMyClickListener onMyClickListener;

    public interface onMyClickListener {
        void onMyXJClick(Dialog dialog, String token, int typeNum);
    }

    public void setOnMyClickListener(onMyClickListener listener) {
        this.onMyClickListener = listener;
    }

}
