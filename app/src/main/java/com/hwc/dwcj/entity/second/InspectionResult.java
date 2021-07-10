package com.hwc.dwcj.entity.second;

import java.util.List;

/**
 * Created by Christ on 2021/6/10.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class InspectionResult {

    public int total;
    public List<InspectionResultItemDTO> list;
    public int pageNum;
    public int pageSize;
    public int size;
    public int startRow;
    public int endRow;
    public int pages;
    public int prePage;
    public int nextPage;
    public boolean isFirstPage;
    public boolean isLastPage;
    public boolean hasPreviousPage;
    public boolean hasNextPage;
    public int navigatePages;
    public int navigateFirstPage;
    public int navigateLastPage;

    public static class InspectionResultItemDTO {
        public String orgName;
        public String manageIp;
        public String assetName;
        public String positionCode;
        public String taskSubId;
        public String alarmLevel;
//        @com.fasterxml.jackson.annotation.JsonProperty("ROW_ID")
//        public int rOW_ID;
    }
}
