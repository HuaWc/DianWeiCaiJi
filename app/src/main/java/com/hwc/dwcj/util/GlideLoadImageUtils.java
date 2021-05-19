package com.hwc.dwcj.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hwc.dwcj.R;

/**
 * Created by zzx on 2018/07/12/下午 5:10
 */

public class GlideLoadImageUtils {

    /**
     * 普通图片
     *
     * @param context
     * @param url
     * @param view
     */
    public static void GlideLoadImageUtils(Context context, String url, View view) {
        Glide.with(context)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.app_logo))
                .load(TextUtils.isEmpty(url) ? R.drawable.ic_check : url)
                .into((ImageView) view);
    }
    public static void GlideLoadImageUtils(Context context, String url, View view,String aa) {
        Glide.with(context)
                .setDefaultRequestOptions(new RequestOptions()).load(TextUtils.isEmpty(url)).into((ImageView) view);
    }

    /**
     * 普通图片
     *
     * @param context
     * @param url
     * @param view
     */
    public static void GlideLoadImageErrorImgUtils(Context context, String url, View view, int errorImg) {
        Glide.with(context)
                .setDefaultRequestOptions(new RequestOptions().placeholder(errorImg))
                .load(TextUtils.isEmpty(url) ? errorImg : url)
                .into((ImageView) view);
    }

    /**
     * 圆形图片
     *
     * @param context
     * @param url
     * @param view
     */
    public static void GlideLoadCircleImageUtils(Context context, String url, View view) {
        Glide.with(context)
                .setDefaultRequestOptions(RequestOptions.circleCropTransform().placeholder(R.mipmap.app_logo))
                .load(TextUtils.isEmpty(url) ? R.mipmap.app_logo : url)
                .into((ImageView) view);
    }

    /**
     * 圆形图片
     * @param context
     * @param url
     * @param view
     */
    public static void GlideLoadCircleErrorImageUtils(Context context, String url, View view, int errorImg) {
        Glide.with(context)
                .setDefaultRequestOptions(RequestOptions.circleCropTransform().placeholder(errorImg))
                .load(TextUtils.isEmpty(url) ? errorImg : url)
                .into((ImageView) view);
    }


    /**
     * 圆角图片
     *
     * @param context
     * @param url
     * @param view
     */
    public static void GlideLoadFilletErrorImageUtils(Context context, String url, View view, int errorImg, int roundingRadius) {
//        //设置图片圆角角度
//        RoundedCorners roundedCorners = new RoundedCorners(roundingRadius);
//        //通过RequestOptions扩展功能
//        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context)
                .setDefaultRequestOptions(new RequestOptions().placeholder(errorImg))
                .load(TextUtils.isEmpty(url) ? errorImg : url)
                .apply(new RequestOptions()
                        .transforms(new CenterCrop(), new RoundedCorners(roundingRadius)))
                .into((ImageView) view);
    }



}
