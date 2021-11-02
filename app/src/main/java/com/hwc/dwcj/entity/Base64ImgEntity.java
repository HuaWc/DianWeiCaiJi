package com.hwc.dwcj.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Christ on 2021/11/1.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class Base64ImgEntity implements Parcelable {
    String imgString;

    public Base64ImgEntity(String imgString) {
        this.imgString = imgString;
    }

    protected Base64ImgEntity(Parcel in) {
        imgString = in.readString();
    }

    public static final Creator<Base64ImgEntity> CREATOR = new Creator<Base64ImgEntity>() {
        @Override
        public Base64ImgEntity createFromParcel(Parcel in) {
            return new Base64ImgEntity(in);
        }

        @Override
        public Base64ImgEntity[] newArray(int size) {
            return new Base64ImgEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imgString);
    }
}
