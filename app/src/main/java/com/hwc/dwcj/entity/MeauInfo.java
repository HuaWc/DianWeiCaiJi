package com.hwc.dwcj.entity;

/**
 * @author Administrator
 *         日期 2018/8/15
 *         描述 菜单
 */

public class MeauInfo {
    private int res;
    private String title;
    private int id;
    private String msgCount;

    public MeauInfo(int res, String title, int id, String msgCount) {
        this.res = res;
        this.title = title;
        this.id = id;
        this.msgCount = msgCount;
    }

    public String getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(String msgCount) {
        this.msgCount = msgCount;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
