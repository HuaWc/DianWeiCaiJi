package com.hwc.dwcj.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.hwc.dwcj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SimpleDialog extends DialogFragment {
    private Unbinder unbinder;

    private static final int TYPE_DEFAULT = 1000;//默认确认/取消按钮
    private boolean isShowTitle;

    private String title;
    private String message;

    private OnConfirmClickListener onConfirmClickListener;

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_message)
    TextView mMessage;

    public static class Builder{
        private Context context;
        private String title = "提示";
        private String message = "内容";
        private boolean isShowTitle = false;
        private boolean isCancelable = true;
        private String tag = "AlertDialog";
        private OnConfirmClickListener onConfirmClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
            this.onConfirmClickListener = onConfirmClickListener;
            return this;
        }

        public SimpleDialog create(){
            SimpleDialog alertDialog = new SimpleDialog();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setOnClickListener(onConfirmClickListener);
            alertDialog.setCancelable(isCancelable);
            return alertDialog;
        }
    }

    public interface OnConfirmClickListener{
        void onClick();
    }

    public void setOnClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_alert, container, false);
        unbinder = ButterKnife.bind(this, view);
        mTitle.setText(title);
        mMessage.setText(message);
        initView();
        return view;
    }

    public void initView(){
        //对话框内部背景设置透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (isShowTitle){
            mTitle.setVisibility(View.VISIBLE);
        }else {
            mTitle.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_confirm)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_confirm:
                if (onConfirmClickListener != null){
                    onConfirmClickListener.onClick();
                }
                dismiss();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
