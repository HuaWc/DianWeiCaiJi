package com.hwc.dwcj.entity.second;

import java.util.Date;

public class OpAlarmParam {

    /**告警编码*/
    private String alarmCode;
    /**告警名称*/
    private String alarmName;
    /**设备编码*/
    private String deviceCode;
    /**告警来源*/
    private String alarmSource;
    /**告警等级*/
    private String alarmLevel;
    /**报警原因*/
    private String alarmReason;
    /**处理状态*/
    private String alarmStatus;
    /**发生时间*/
    private String alarmTime;
    /**告警人*/
    private String alarmPersonId;
    /**处理人*/
    private String handlePersonId;
    /**处理时间*/
   // private String handleTime;
    /**巡检任务id*/
    private String taskId;
    /**巡检分组id*/
    private String groupId;

    /**处理截止时间*/
   // private String deadlineTime;
    /**设备种类，紧适用于动环*/
    private String deviceKind;
    /**设备名称，紧适用于动环*/
    private String deviceName;
    /**ip*/
    private String ip;
    /**附图url*/
    private String pictureUrl;
    /**设备或机房id，手动添加时有数据*/
    private String deviceId;
    /**巡检任务子表Id*/
    private String taskSubId;
    /**资产种类*/
    private String assetNature;
    /**资产类别*/
    private String assetType;
    /**故障类型*/
    private String faultType;
    /**扩展字段：网管告警的唯一标记**/
    private String exp1;
    /**是否删除：0-正常，1-删除**/
    private Long isDel;
    /**
     *无参构造函数
     */
    public OpAlarmParam(){
        super();
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getAlarmSource() {
        return alarmSource;
    }

    public void setAlarmSource(String alarmSource) {
        this.alarmSource = alarmSource;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmReason() {
        return alarmReason;
    }

    public void setAlarmReason(String alarmReason) {
        this.alarmReason = alarmReason;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmPersonId() {
        return alarmPersonId;
    }

    public void setAlarmPersonId(String alarmPersonId) {
        this.alarmPersonId = alarmPersonId;
    }

    public String getHandlePersonId() {
        return handlePersonId;
    }

    public void setHandlePersonId(String handlePersonId) {
        this.handlePersonId = handlePersonId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDeviceKind() {
        return deviceKind;
    }

    public void setDeviceKind(String deviceKind) {
        this.deviceKind = deviceKind;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTaskSubId() {
        return taskSubId;
    }

    public void setTaskSubId(String taskSubId) {
        this.taskSubId = taskSubId;
    }

    public String getAssetNature() {
        return assetNature;
    }

    public void setAssetNature(String assetNature) {
        this.assetNature = assetNature;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getExp1() {
        return exp1;
    }

    public void setExp1(String exp1) {
        this.exp1 = exp1;
    }

    public Long getIsDel() {
        return isDel;
    }

    public void setIsDel(Long isDel) {
        this.isDel = isDel;
    }
}
