package com.hwc.dwcj.entity;

public class DACity {
    private boolean selected;
    private String classCode;
    private String dataDescr;
    private int dataEnable;
    private String dataId;
    private String dataName;
    private String dataValue;
    private int orderValue;
    private String parentId;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getDataDescr() {
        return dataDescr;
    }

    public void setDataDescr(String dataDescr) {
        this.dataDescr = dataDescr;
    }

    public int getDataEnable() {
        return dataEnable;
    }

    public void setDataEnable(int dataEnable) {
        this.dataEnable = dataEnable;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public int getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(int orderValue) {
        this.orderValue = orderValue;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
