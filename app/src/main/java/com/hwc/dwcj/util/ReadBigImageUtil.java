package com.hwc.dwcj.util;

import android.app.Activity;
import android.os.Bundle;

import com.awen.photo.photopick.controller.PhotoPagerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看大图
 * Created by zlw on 2018/10/31/上午 11:41
 */

public class ReadBigImageUtil {

    /**
     * 查看大图
     *
     * @param activity
     * @param imgList
     * @param position
     */
    public static void readingBigImage(Activity activity, List<?> imgList, int position) {
        new PhotoPagerConfig.Builder(activity)
                .setBigImageUrls((ArrayList<String>) imgList)      //大图片url,可以是sd卡res，asset，网络图片.
                .setPosition(position)                                     //默认展示第2张图片
                .setOpenDownAnimate(false)                          //是否开启下滑关闭activity，默认开启。类似微信的                                                                         //图片浏览，可下滑关闭一样
                .build();
    }

    /**
     * 查看大图
     *
     * @param activity
     * @param imgList
     * @param position
     */
    public static void readingBigImage(Activity activity, List<?> imgList, int position,Class<?> cla) {
        Bundle bundle = new Bundle();
        bundle.putInt("size",imgList.size());
        bundle.putInt("position",position);
        new PhotoPagerConfig.Builder(activity,cla)
                .setBigImageUrls((ArrayList<String>) imgList)      //大图片url,可以是sd卡res，asset，网络图片.
                .setPosition(position)                                     //默认展示第2张图片
                .setOpenDownAnimate(false)                          //是否开启下滑关闭activity，默认开启。类似微信的                                                                         //图片浏览，可下滑关闭一样
                .setBundle(bundle)
                .build();
    }
}
