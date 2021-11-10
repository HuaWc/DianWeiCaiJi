package com.hwc.dwcj.entity.second;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Christ on 2021/7/15.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class MessageInfo implements Parcelable {


    private String id;
    private long msgId;
    private String receiverId;
    private int msgState;
    private int isDel;
    private String msgTitle;
    private String msgType;
    private String msgContent;
    private String userName;
    private String subsysName;
    private String sendTime;
    private String faultId;
    private int isSync;
    private int pushState;
    private Map map;

    public MessageInfo() {
    }

    protected MessageInfo(Parcel in) {
        id = in.readString();
        msgId = in.readLong();
        receiverId = in.readString();
        msgState = in.readInt();
        isDel = in.readInt();
        msgTitle = in.readString();
        msgType = in.readString();
        msgContent = in.readString();
        userName = in.readString();
        subsysName = in.readString();
        sendTime = in.readString();
        faultId = in.readString();
        isSync = in.readInt();
        pushState = in.readInt();
    }

    public static final Creator<MessageInfo> CREATOR = new Creator<MessageInfo>() {
        @Override
        public MessageInfo createFromParcel(Parcel in) {
            return new MessageInfo(in);
        }

        @Override
        public MessageInfo[] newArray(int size) {
            return new MessageInfo[size];
        }
    };

    public int getPushState() {
        return pushState;
    }

    public void setPushState(int pushState) {
        this.pushState = pushState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public int getMsgState() {
        return msgState;
    }

    public void setMsgState(int msgState) {
        this.msgState = msgState;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubsysName() {
        return subsysName;
    }

    public void setSubsysName(String subsysName) {
        this.subsysName = subsysName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getFaultId() {
        return faultId;
    }

    public void setFaultId(String faultId) {
        this.faultId = faultId;
    }

    public int getIsSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeLong(msgId);
        parcel.writeString(receiverId);
        parcel.writeInt(msgState);
        parcel.writeInt(isDel);
        parcel.writeString(msgTitle);
        parcel.writeString(msgType);
        parcel.writeString(msgContent);
        parcel.writeString(userName);
        parcel.writeString(subsysName);
        parcel.writeString(sendTime);
        parcel.writeString(faultId);
        parcel.writeInt(isSync);
        parcel.writeInt(pushState);
    }

    public static class Map {
    }
}
