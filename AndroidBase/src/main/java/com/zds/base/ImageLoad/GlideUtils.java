package com.zds.base.ImageLoad;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zds.base.util.DensityUtils;
import com.zds.base.util.StringUtil;
import com.zds.base.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 作   者：赵大帅
 * 描   述: glide 封装
 * 日   期: 2017/9/6 17:03
 * 更新日期: 2017/9/6
 */
public class GlideUtils {

    /**
     * Glide特点
     * 使用简单
     * 可配置度高，自适应程度高
     * 支持常见图片格式 Jpg png gif webp
     * 支持多种数据源  网络、本地、资源、Assets 等
     * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
     * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
     * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
     * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
     */
    // 加载圆型网络图片
    public void loadCircle(String url, ImageView imageView, int defaultImg) {
        Glide.with(Utils.getContext()).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(defaultImg)).into(imageView);
    }

    //默认加载
    public static void loadImageView(int path, ImageView mImageView) {
        Glide.with(Utils.getContext()).load(path).into(mImageView);
    }

    //加载指定大小
    public static void loadImageViewSize(String path, int width, int height, ImageView mImageView) {
        Glide.with(Utils.getContext()).load(path).apply(new RequestOptions().override(width, height)).into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageViewLoding(String path, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(Utils.getContext()).load(path).apply(new RequestOptions().placeholder(lodingImage).error(errorImageView)).into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageViewCircleLoding(String path, ImageView mImageView, int errorImageView) {
        Glide.with(Utils.getContext()).load(path).apply(RequestOptions.bitmapTransform(new CircleCrop()).error(errorImageView)).into(mImageView);
    }


    //设置加载中以及加载失败图片
    public static void loadImageViewLoding(String path, ImageView mImageView, int errorImageView) {
        Glide.with(Utils.getContext()).load(path).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(errorImageView).error(errorImageView)).into(mImageView);
    }

    //加载图片
    public static void loadImageViewLoding(String path, ImageView mImageView) {
        Glide.with(Utils.getContext()).load(path).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(mImageView);
    }

    //加载圆角
    public static void loadImageViewLodingCir(String path, final ImageView mImageView, int size) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(DensityUtils.dip2px(Utils.getContext(), size));
        //通过RequestOptions扩展功能
        RequestOptions options = new RequestOptions().transforms(new CenterCrop(), roundedCorners).diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(Utils.getContext()).load(path).apply(options).into(mImageView);
    }

    /**
     * 加载圆角图片
     */
    public static void loadRoundCircleImage(String url, ImageView imageView, int errorImageView, int radius) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(errorImageView)
                .error(errorImageView)
                .transforms(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(Utils.getContext()).load(url).apply(options).into(imageView);

    }


    /**
     * 加载圆角图片-指定任意部分圆角（图片上、下、左、右四个角度任意定义）
     *
     * @param url
     * @param imageView
     * @param type
     */
    public static void loadCustRoundCircleImage(String url, ImageView imageView, int errorImageView, int radius, RoundedCornersTransformation.CornerType type) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(errorImageView)
                .error(errorImageView)
                .transforms(new RoundedCornersTransformation(radius, 0, type))
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(Utils.getContext()).load(url).apply(options).into(imageView);
    }

    //加载圆形
    public static void loadImageViewLodingCi(String path, final ImageView mImageView, int errorImageView) {
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true).error(errorImageView);
        Glide.with(Utils.getContext()).load(path).apply(mRequestOptions).into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageViewLoding2(String path, ImageView mImageView, int errorImageView) {
        Glide.with(Utils.getContext()).load(path).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(errorImageView).error(errorImageView)).into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageViewLodingByCircle(String path, ImageView mImageView, int errorImageView) {
        Glide.with(Utils.getContext()).load(path).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(errorImageView).error(errorImageView)).into(mImageView);
    }

    //设置加载中以及加载失败图片并且指定大小
    public static void loadImageViewLodingSize(String path, int width, int height, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(Utils.getContext()).load(path).apply(new RequestOptions().override(width, height).placeholder(lodingImage).error(errorImageView)).into(mImageView);
    }

    //设置跳过内存缓存
    public static void loadImageViewCache(String path, ImageView mImageView) {
        Glide.with(Utils.getContext()).load(path).apply(new RequestOptions().skipMemoryCache(true)).into(mImageView);
    }

    //设置下载优先级
    public static void loadImageViewPriority(String path, ImageView mImageView) {
        Glide.with(Utils.getContext()).load(path).apply(new RequestOptions().priority(Priority.NORMAL)).into(mImageView);
    }

    public static void loadImageViewFile(String path, ImageView mImageView, int errorImageView) {
        Glide.with(Utils.getContext()).load(new File(path)).apply(new RequestOptions().error(errorImageView)).into(mImageView);
    }

    public static void loadImageViewFile(String path, ImageView mImageView) {
        if (StringUtil.isEmpty(path)) {
            return;
        }
        Glide.with(Utils.getContext()).load(new File(path)).into(mImageView);
    }

    /**
     * 策略解说：
     * <p>
     * all:缓存源资源和转换后的资源
     * <p>
     * none:不作任何磁盘缓存
     * <p>
     * source:缓存源资源
     * <p>
     * result：缓存转换后的资源
     */

    //设置缓存策略
    public static void loadImageViewDiskCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(mImageView);
    }

    //设置缓存策略
    public static void loadDiskCache(String path) {
        Glide.with(Utils.getContext()).load(path).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL));
    }


    //设置缓存策略
    public static InputStream getDiskCache(String path) {
        try {
            File file = Glide.with(Utils.getContext()).downloadOnly().load(path).apply(new RequestOptions().onlyRetrieveFromCache(true)).submit().get();
            InputStream input = new FileInputStream(file);
            return input;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * api也提供了几个常用的动画：比如crossFade()
     */

    //设置加载动画
    public static void loadImageViewAnim(Context mContext, String path, int anim, ImageView mImageView) {
        Glide.with(mContext).load(path).transition(withCrossFade(anim)).into(mImageView);
    }

    /**
     * 会先加载缩略图
     */

    //设置缩略图支持
    public static void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
    }

    /**
     * api提供了比如：centerCrop()、fitCenter()等
     */

    //设置动态转换
    public static void loadImageViewCrop(String path, ImageView mImageView) {
        Glide.with(Utils.getContext()).load(path).apply(new RequestOptions().centerCrop()).into(mImageView);
    }

    //设置动态GIF加载方式
    public static void loadImageViewDynamicGif(String path, ImageView mImageView) {
        Glide.with(Utils.getContext()).asGif().load(path).into(mImageView);
    }
    //设置动态GIF加载方式
    public static void loadImageViewDynamicGif(int path, ImageView mImageView) {
        Glide.with(Utils.getContext()).asGif().load(path).into(mImageView);
    }


    //设置静态GIF加载方式
    public static void loadImageViewStaticGif(String path, ImageView mImageView) {
        Glide.with(Utils.getContext()).asBitmap().load(path).into(mImageView);
    }

    //设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘

    //设置监听请求接口
    public static void loadImageViewListener(String path, ImageView mImageView, RequestListener<Drawable> requstlistener) {
        Glide.with(Utils.getContext()).load(path).listener(requstlistener).into(mImageView);
    }

    //项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排

    //设置要加载的内容
    public static void loadImageViewContent(String path, SimpleTarget<Drawable> simpleTarget) {
        Glide.with(Utils.getContext()).load(path).apply(new RequestOptions().centerCrop()).into(simpleTarget);
    }

    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }

}