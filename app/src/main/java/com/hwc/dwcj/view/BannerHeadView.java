package com.hwc.dwcj.view;

import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.ImageAdapter;
import com.hwc.dwcj.entity.MainDataInfo;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.AlphaPageTransformer;
import com.youth.banner.util.BannerUtils;
import com.zds.base.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 日期 2018/3/5
 * 描述 main banner
 */

public class BannerHeadView extends LinearLayout {
    Banner banner;
    List<MainDataInfo.AdvsBean> mBannerInfos = new ArrayList<>();
    ImageAdapter imageAdapter;
    public BannerHeadView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.main_head_banner, this);
        initView();
    }

    public BannerHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.main_head_banner, this);
        initView();
    }

    private void initView() {
        banner = findViewById(R.id.banner);
        initBanner();
    }

    public void upData(List<MainDataInfo.AdvsBean> list) {
        mBannerInfos.clear();
        mBannerInfos.addAll(list);
        if (imageAdapter!=null){
            imageAdapter.notifyDataSetChanged();
        }
    }

    public Banner getBanner() {
        return banner;
    }

    public void initBanner() {
        imageAdapter=new ImageAdapter(mBannerInfos);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) banner.getLayoutParams();
        layoutParams.height = (int) (DensityUtils.getWidthInPx(getContext()) * 0.92 / 69 * 26);
        //默认直接设置adapter就行了
        banner.setAdapter(imageAdapter);
        //--------------------------详细使用-------------------------------
        banner.setIndicator(new CircleIndicator(getContext()));
//        banner.addPageTransformer(new ScaleInTransformer());
        banner.setBannerRound(BannerUtils.dp2px(5));
        //添加画廊效果(可以和其他PageTransformer组合使用，比如AlphaPageTransformer，注意但和其他带有缩放的PageTransformer会显示冲突)
//        banner.setBannerGalleryEffect(18,10);
        //添加透明效果(画廊配合透明效果更棒)
        banner.addPageTransformer(new AlphaPageTransformer());
        banner.setDelayTime(3000);
        banner.isAutoLoop(true);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
}
