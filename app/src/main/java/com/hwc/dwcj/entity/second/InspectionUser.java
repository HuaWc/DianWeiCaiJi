package com.hwc.dwcj.entity.second;

/**
 * Created by Christ on 2021/6/10.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class InspectionUser {


    private String id;
    private String ruleId;
    private String urgentType;
    private String inspectionId;
    private String inspectionStatus;
    private String inspectionSum;
    private String taskTimeStart;
    private Object taskTimeEnd;
    private String effectiveTimeStart;
    private String effectiveTimeEnd;
    private String taskType;
    private String taskName;
    private String inspectionName;
    private Object ckeckType;
    private Object approvalOpinion;
    private Map map;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getUrgentType() {
        return urgentType;
    }

    public void setUrgentType(String urgentType) {
        this.urgentType = urgentType;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    public String getInspectionSum() {
        return inspectionSum;
    }

    public void setInspectionSum(String inspectionSum) {
        this.inspectionSum = inspectionSum;
    }

    public String getTaskTimeStart() {
        return taskTimeStart;
    }

    public void setTaskTimeStart(String taskTimeStart) {
        this.taskTimeStart = taskTimeStart;
    }

    public Object getTaskTimeEnd() {
        return taskTimeEnd;
    }

    public void setTaskTimeEnd(Object taskTimeEnd) {
        this.taskTimeEnd = taskTimeEnd;
    }

    public String getEffectiveTimeStart() {
        return effectiveTimeStart;
    }

    public void setEffectiveTimeStart(String effectiveTimeStart) {
        this.effectiveTimeStart = effectiveTimeStart;
    }

    public String getEffectiveTimeEnd() {
        return effectiveTimeEnd;
    }

    public void setEffectiveTimeEnd(String effectiveTimeEnd) {
        this.effectiveTimeEnd = effectiveTimeEnd;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public Object getCkeckType() {
        return ckeckType;
    }

    public void setCkeckType(Object ckeckType) {
        this.ckeckType = ckeckType;
    }

    public Object getApprovalOpinion() {
        return approvalOpinion;
    }

    public void setApprovalOpinion(Object approvalOpinion) {
        this.approvalOpinion = approvalOpinion;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public static class Map {
        private String groupName;
        private String urgentType;
        private String inspectionPercent;
        private int inspectionSumNow;
        private String overtimeStatus;
        private String inspectionProgress;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getUrgentType() {
            return urgentType;
        }

        public void setUrgentType(String urgentType) {
            this.urgentType = urgentType;
        }

        public String getInspectionPercent() {
            return inspectionPercent;
        }

        public void setInspectionPercent(String inspectionPercent) {
            this.inspectionPercent = inspectionPercent;
        }

        public int getInspectionSumNow() {
            return inspectionSumNow;
        }

        public void setInspectionSumNow(int inspectionSumNow) {
            this.inspectionSumNow = inspectionSumNow;
        }

        public String getOvertimeStatus() {
            return overtimeStatus;
        }

        public void setOvertimeStatus(String overtimeStatus) {
            this.overtimeStatus = overtimeStatus;
        }

        public String getInspectionProgress() {
            return inspectionProgress;
        }

        public void setInspectionProgress(String inspectionProgress) {
            this.inspectionProgress = inspectionProgress;
        }
    }
}
