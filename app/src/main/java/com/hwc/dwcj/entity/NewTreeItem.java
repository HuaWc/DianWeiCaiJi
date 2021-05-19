package com.hwc.dwcj.entity;

import java.util.List;

public class NewTreeItem {
    private int id;
    private int parentId;
    private String name;
    private String orgCode;
    private boolean isExpand;
    private List<TreeCamera> cameraList;
    private boolean haveGet;
    private int level;
    private boolean visible;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isHaveGet() {
        return haveGet;
    }

    public void setHaveGet(boolean haveGet) {
        this.haveGet = haveGet;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public List<TreeCamera> getCameraList() {
        return cameraList;
    }

    public void setCameraList(List<TreeCamera> cameraList) {
        this.cameraList = cameraList;
    }
}
