package com.hwc.dwcj.entity.second;

import java.util.List;

public class InspectionManageDetailDTO {

    public String id;
    public String ruleId;
    public String urgentType;
    public String inspectionId;
    public String inspectionStatus;
    public String inspectionSum;
    public String taskTimeStart;
    public String taskTimeEnd;
    public String effectiveTimeStart;
    public String effectiveTimeEnd;
    public String taskType;
    public String taskName;
    public String inspectionName;
    public String ckeckType;
    public Object approvalOpinion;
    public MapDTO map;

    public static class MapDTO {
        public List<OpInspectionGroupListDTO> opInspectionGroupList;
        public String groupName;
        public String taskType;
        public String urgentType;
        public String inspectionStatus;
        public String inspectionSumNow;
        public String inspectionOver;
        public String overtimeStatus;
        public String inspectionProgress;

        public static class OpInspectionGroupListDTO {
            public String id;
            public Object groupType;
            public String groupName;
            public Object groupDesc;
            public Object isDel;
            public Object templateId;
            public Object addId;
            public Object addTime;
            public Object modifyId;
            public Object modifyTime;
            public Object exp1;
            public Object exp2;
            public Object exp3;
            public MapModel map;
        }
    }

    public class MapModel {
        public List<InspectionManageDetailInfoDTO> alarmList;
    }
}
