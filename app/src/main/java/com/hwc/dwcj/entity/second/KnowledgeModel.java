package com.hwc.dwcj.entity.second;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Christ on 2021/6/23.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class KnowledgeModel {

    private String id;
    private String theme;
    private String knowledgeType;
    private String knowledgeClass;
    private String code;
    private String createTime;
    private String author;
    private int count;
    private String appearance;
    private String solution;
    private String snapshot;
    private String renew;
    private Map map;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getKnowledgeType() {
        return knowledgeType;
    }

    public void setKnowledgeType(String knowledgeType) {
        this.knowledgeType = knowledgeType;
    }

    public String getKnowledgeClass() {
        return knowledgeClass;
    }

    public void setKnowledgeClass(String knowledgeClass) {
        this.knowledgeClass = knowledgeClass;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public String getRenew() {
        return renew;
    }

    public void setRenew(String renew) {
        this.renew = renew;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public static class Map {
        @SerializedName("KnowledgeClass")
        private String knowledgeClass;
        private String author;
        private String knowledgeType;

        public String getKnowledgeType() {
            return knowledgeType;
        }

        public void setKnowledgeType(String knowledgeType) {
            this.knowledgeType = knowledgeType;
        }

        public String getKnowledgeClass() {
            return knowledgeClass;
        }

        public void setKnowledgeClass(String knowledgeClass) {
            this.knowledgeClass = knowledgeClass;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
