package com.hwc.dwcj.entity.second;

/**
 * Created by Christ on 2021/6/30.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class WorkOrderTrackInfo {

    private String alarmStep;
    private String alarmSourceOrPersonName;
    private String tel;
    private String handleTime;
    private String alarmName;
    private String handleMethod;
    private String remark;
    private String errorLog;
    private String colorType;

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public String getAlarmStep() {
        return alarmStep;
    }

    public void setAlarmStep(String alarmStep) {
        this.alarmStep = alarmStep;
    }

    public String getAlarmSourceOrPersonName() {
        return alarmSourceOrPersonName;
    }

    public void setAlarmSourceOrPersonName(String alarmSourceOrPersonName) {
        this.alarmSourceOrPersonName = alarmSourceOrPersonName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getHandleMethod() {
        return handleMethod;
    }

    public void setHandleMethod(String handleMethod) {
        this.handleMethod = handleMethod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }
}
