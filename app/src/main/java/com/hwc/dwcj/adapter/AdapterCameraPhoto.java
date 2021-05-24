package com.hwc.dwcj.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.dwcj.R;
import com.hwc.dwcj.util.GlideLoadImageUtils;

import java.util.List;

public class AdapterCameraPhoto extends BaseQuickAdapter<String, BaseViewHolder> {
    public AdapterCameraPhoto(@Nullable List<String> data) {
        super(R.layout.adapter_camera_photo, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_photo = helper.getView(R.id.iv_photo);

        if(item.startsWith("data:image/")){
            //将Base64编码字符串解码成Bitmap
            byte[] decodedString = Base64.decode(item.split(",")[1], Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            //设置ImageView图片
            iv_photo.setImageBitmap(decodedByte);
        } else{
            GlideLoadImageUtils.GlideLoadImageUtils(mContext, item, helper.getView(R.id.iv_photo));
        }
    }
}
