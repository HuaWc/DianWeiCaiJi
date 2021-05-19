package com.hwc.dwcj.entity;

import java.util.List;

public class TreeItem {

    private String titleName;
    private String titleNumber;
    private List<OrgUtils> orgUtils;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleNumber() {
        return titleNumber;
    }

    public void setTitleNumber(String titleNumber) {
        this.titleNumber = titleNumber;
    }

    public List<OrgUtils> getOrgUtils() {
        return orgUtils;
    }

    public void setOrgUtils(List<OrgUtils> orgUtils) {
        this.orgUtils = orgUtils;
    }

    public static class OrgUtils {
        private String orgName;
        private String orgNumber;
        private String orgCode;
        private Object cameraInfosList;

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getOrgNumber() {
            return orgNumber;
        }

        public void setOrgNumber(String orgNumber) {
            this.orgNumber = orgNumber;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public Object getCameraInfosList() {
            return cameraInfosList;
        }

        public void setCameraInfosList(Object cameraInfosList) {
            this.cameraInfosList = cameraInfosList;
        }
    }
}
