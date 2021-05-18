package com.hwc.dwcj.view.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.hwc.dwcj.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Create by hwc on 2020/11/7
 */
public class PictureSelectDialogUtils {
    public static BaseDialog mDialog;

    /**
     * 打开选择图片的弹窗（包含权限申请）
     *
     * @param mActivity
     * @param maxNum
     */
    public static void showSelectPictureSelector(Activity mActivity, int maxNum) {
        mDialog = BaseDialog.getUnInstance();

        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_select_image, null);
        View rl_take_photo = view.findViewById(R.id.rl_take_photo);
        View rl_select_photo = view.findViewById(R.id.rl_select_photo);
        View rl_cancel = view.findViewById(R.id.rl_cancel);

        mDialog
                .dissmissDialog()
                .setLayoutView(view, mActivity)
                .setWindow(1, 0.45)
                .isCancelable(false)
                .setOnClickListener(rl_select_photo, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permission) {
                                PictureSelector.create(mActivity)
                                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                        .maxSelectNum(maxNum)// 最大图片选择数量
                                        .minSelectNum(1)// 最小选择数量
                                        .imageSpanCount(4)// 每行显示个数
                                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
                                        .previewImage(true)// 是否可预览图片
                                        .isCamera(true)// 是否显示拍照按钮
                                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                                        .enableCrop(false)// 是否裁剪
                                        .compress(true)// 是否压缩
                                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                        .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                        //.selectionMedia(selectList)// 是否传入已选图片
                                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                                        //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                                        //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                                        .rotateEnabled(false) // 裁剪是否可旋转图片
                                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                                        //.recordVideoSecond()//录制视频秒数 默认60s
                                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permission) {
                                ToastUtil.toast("拒绝权限将无法选择图片");
                            }
                        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                })
                .setOnClickListener(rl_take_photo, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permission) {
                                PictureSelector.create(mActivity)
                                        .openCamera(PictureMimeType.ofImage())
                                        .forResult(PictureConfig.CHOOSE_REQUEST);
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permission) {
                                ToastUtil.toast("拒绝权限将无法选择图片");
                            }
                        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);

                    }
                })
                .setOnClickListener(rl_cancel, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                })
                .bottomShow();
    }

    public interface SelectExampleHeadResult {
        void selectExample(String url);
    }
}
