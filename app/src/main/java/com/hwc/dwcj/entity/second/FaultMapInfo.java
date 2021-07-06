package com.hwc.dwcj.entity.second;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Christ on 2021/7/6.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class FaultMapInfo {

    private String addId;
    private String addTime;
    private String alarmCode;
    private String alarmGrade;
    private String alarmId;
    private String alarmName;
    private String alarmRemark;
    private String alarmSource;
    private String alarmTime;
    private String assetId;
    private int cameraFaultType;
    private String deadlineTime;
    private String exp1;
    private String faultCode;
    private String handlePersionId;
    private String handleStatus;
    private String handleTel;
    private String id;
    private int isDel;
    private Map map;
    private String orgId;
    private String remark;
    private String remark2;

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getAlarmGrade() {
        return alarmGrade;
    }

    public void setAlarmGrade(String alarmGrade) {
        this.alarmGrade = alarmGrade;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmRemark() {
        return alarmRemark;
    }

    public void setAlarmRemark(String alarmRemark) {
        this.alarmRemark = alarmRemark;
    }

    public String getAlarmSource() {
        return alarmSource;
    }

    public void setAlarmSource(String alarmSource) {
        this.alarmSource = alarmSource;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public int getCameraFaultType() {
        return cameraFaultType;
    }

    public void setCameraFaultType(int cameraFaultType) {
        this.cameraFaultType = cameraFaultType;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getExp1() {
        return exp1;
    }

    public void setExp1(String exp1) {
        this.exp1 = exp1;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getHandlePersionId() {
        return handlePersionId;
    }

    public void setHandlePersionId(String handlePersionId) {
        this.handlePersionId = handlePersionId;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getHandleTel() {
        return handleTel;
    }

    public void setHandleTel(String handleTel) {
        this.handleTel = handleTel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public static class Map {
        @SerializedName("FaultTime")
        private double faultTime;
        private String alarmTime;
        private String assetClass;
        private String assetClassName;
        private String assetCode;
        private String assetName;
        private String assetNature;
        private String assetNatureName;
        private String assetType;
        private String assetTypeName;
        private String deviceStatus;
        private String handlePersionName;
        private String manageIp;
        private String orgType;
        private String positionCode;
        private String selectAssetType;
        private boolean showRelatedEquipment;
        private String timeoutTime;

        public double getFaultTime() {
            return faultTime;
        }

        public void setFaultTime(double faultTime) {
            this.faultTime = faultTime;
        }

        public String getAlarmTime() {
            return alarmTime;
        }

        public void setAlarmTime(String alarmTime) {
            this.alarmTime = alarmTime;
        }

        public String getAssetClass() {
            return assetClass;
        }

        public void setAssetClass(String assetClass) {
            this.assetClass = assetClass;
        }

        public String getAssetClassName() {
            return assetClassName;
        }

        public void setAssetClassName(String assetClassName) {
            this.assetClassName = assetClassName;
        }

        public String getAssetCode() {
            return assetCode;
        }

        public void setAssetCode(String assetCode) {
            this.assetCode = assetCode;
        }

        public String getAssetName() {
            return assetName;
        }

        public void setAssetName(String assetName) {
            this.assetName = assetName;
        }

        public String getAssetNature() {
            return assetNature;
        }

        public void setAssetNature(String assetNature) {
            this.assetNature = assetNature;
        }

        public String getAssetNatureName() {
            return assetNatureName;
        }

        public void setAssetNatureName(String assetNatureName) {
            this.assetNatureName = assetNatureName;
        }

        public String getAssetType() {
            return assetType;
        }

        public void setAssetType(String assetType) {
            this.assetType = assetType;
        }

        public String getAssetTypeName() {
            return assetTypeName;
        }

        public void setAssetTypeName(String assetTypeName) {
            this.assetTypeName = assetTypeName;
        }

        public String getDeviceStatus() {
            return deviceStatus;
        }

        public void setDeviceStatus(String deviceStatus) {
            this.deviceStatus = deviceStatus;
        }

        public String getHandlePersionName() {
            return handlePersionName;
        }

        public void setHandlePersionName(String handlePersionName) {
            this.handlePersionName = handlePersionName;
        }

        public String getManageIp() {
            return manageIp;
        }

        public void setManageIp(String manageIp) {
            this.manageIp = manageIp;
        }

        public String getOrgType() {
            return orgType;
        }

        public void setOrgType(String orgType) {
            this.orgType = orgType;
        }

        public String getPositionCode() {
            return positionCode;
        }

        public void setPositionCode(String positionCode) {
            this.positionCode = positionCode;
        }

        public String getSelectAssetType() {
            return selectAssetType;
        }

        public void setSelectAssetType(String selectAssetType) {
            this.selectAssetType = selectAssetType;
        }

        public boolean isShowRelatedEquipment() {
            return showRelatedEquipment;
        }

        public void setShowRelatedEquipment(boolean showRelatedEquipment) {
            this.showRelatedEquipment = showRelatedEquipment;
        }

        public String getTimeoutTime() {
            return timeoutTime;
        }

        public void setTimeoutTime(String timeoutTime) {
            this.timeoutTime = timeoutTime;
        }
    }
}
