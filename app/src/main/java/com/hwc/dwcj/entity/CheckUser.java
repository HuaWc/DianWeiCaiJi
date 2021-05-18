package com.hwc.dwcj.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckUser implements Parcelable {

    private String id;
    private String realName;

    private boolean isSelected;

    protected CheckUser(Parcel in) {
        id = in.readString();
        realName = in.readString();
        isSelected = in.readByte() != 0;
    }

    public CheckUser() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(realName);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CheckUser> CREATOR = new Creator<CheckUser>() {
        @Override
        public CheckUser createFromParcel(Parcel in) {
            return new CheckUser(in);
        }

        @Override
        public CheckUser[] newArray(int size) {
            return new CheckUser[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
