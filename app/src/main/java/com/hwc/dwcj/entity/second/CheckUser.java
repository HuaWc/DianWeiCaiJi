package com.hwc.dwcj.entity.second;

/**
 * Created by Christ on 2021/6/10.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class CheckUser {

    private String id;
    private String taskName;
    private String taskrequires;
    private String taskStartTime;
    private String taskEndTime;
    private String verPeopleNames;
    private String verPeopleIds;
    private String feedbackId;
    private int verFeedback;
    private String verContent;
    private String checkPeopleId;
    private String checkPeople;
    private int checkStatus;
    private String checkTime;
    private String checkReason;
    private String addId;
    private String addTime;
    private String modifyId;
    private String modifyTime;
    private String exp1;
    private String exp2;
    private Map map;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskrequires() {
        return taskrequires;
    }

    public void setTaskrequires(String taskrequires) {
        this.taskrequires = taskrequires;
    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getVerPeopleNames() {
        return verPeopleNames;
    }

    public void setVerPeopleNames(String verPeopleNames) {
        this.verPeopleNames = verPeopleNames;
    }

    public String getVerPeopleIds() {
        return verPeopleIds;
    }

    public void setVerPeopleIds(String verPeopleIds) {
        this.verPeopleIds = verPeopleIds;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getVerFeedback() {
        return verFeedback;
    }

    public void setVerFeedback(int verFeedback) {
        this.verFeedback = verFeedback;
    }

    public String getVerContent() {
        return verContent;
    }

    public void setVerContent(String verContent) {
        this.verContent = verContent;
    }

    public String getCheckPeopleId() {
        return checkPeopleId;
    }

    public void setCheckPeopleId(String checkPeopleId) {
        this.checkPeopleId = checkPeopleId;
    }

    public String getCheckPeople() {
        return checkPeople;
    }

    public void setCheckPeople(String checkPeople) {
        this.checkPeople = checkPeople;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckReason() {
        return checkReason;
    }

    public void setCheckReason(String checkReason) {
        this.checkReason = checkReason;
    }

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

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getExp1() {
        return exp1;
    }

    public void setExp1(String exp1) {
        this.exp1 = exp1;
    }

    public String getExp2() {
        return exp2;
    }

    public void setExp2(String exp2) {
        this.exp2 = exp2;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public static class Map {
        private String addName;

        public String getAddName() {
            return addName;
        }

        public void setAddName(String addName) {
            this.addName = addName;
        }
    }
}
