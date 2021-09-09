package com.hwc.dwcj.entity;

/**
 * Created by Christ on 2021/9/9.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class CheckVersionInfo {

    private String fileName;
    private Map map;
    private String verCodeAndroid;
    private String verDesc;
    private String verId;
    private String verName;
    private long verTime;
    private String verUrl;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getVerCodeAndroid() {
        return verCodeAndroid;
    }

    public void setVerCodeAndroid(String verCodeAndroid) {
        this.verCodeAndroid = verCodeAndroid;
    }

    public String getVerDesc() {
        return verDesc;
    }

    public void setVerDesc(String verDesc) {
        this.verDesc = verDesc;
    }

    public String getVerId() {
        return verId;
    }

    public void setVerId(String verId) {
        this.verId = verId;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public long getVerTime() {
        return verTime;
    }

    public void setVerTime(long verTime) {
        this.verTime = verTime;
    }

    public String getVerUrl() {
        return verUrl;
    }

    public void setVerUrl(String verUrl) {
        this.verUrl = verUrl;
    }

    public static class Map {
    }
}
