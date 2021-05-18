package com.hwc.dwcj.entity;

import java.util.List;

public class TreeItem {
    private String titleName;
    private String titleNumber;
    private List<OrgUtils> orgUtils;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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
        private boolean isSelected;
        private String orgName;
        private String orgNumber;
        private String orgCode;
        private List<CameraInfosList> cameraInfosList;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

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

        public List<CameraInfosList> getCameraInfosList() {
            return cameraInfosList;
        }

        public void setCameraInfosList(List<CameraInfosList> cameraInfosList) {
            this.cameraInfosList = cameraInfosList;
        }

        public static class CameraInfosList {
            private String id;
            private String positionCode;
            private String cameraCode;
            private String cameraName;
            private String longitude;
            private String latitude;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPositionCode() {
                return positionCode;
            }

            public void setPositionCode(String positionCode) {
                this.positionCode = positionCode;
            }

            public String getCameraCode() {
                return cameraCode;
            }

            public void setCameraCode(String cameraCode) {
                this.cameraCode = cameraCode;
            }

            public String getCameraName() {
                return cameraName;
            }

            public void setCameraName(String cameraName) {
                this.cameraName = cameraName;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }
        }
    }
}
