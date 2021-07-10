package com.hwc.dwcj.entity.second;

import java.util.List;

/**
 * Created by Christ on 2021/6/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class StartInspection {
    public String pageNum;
    public String pageSize;
    public int pages;
    public List<InspectionItemDTO> list;

    public class InspectionItemDTO{
        public String id;
        public InspectionItemInfoDTO map;
    }

    public class InspectionItemInfoDTO{
        public String taskSubId;
        public String devName;
        public String groupName;
        public String ip;
        public String positionCode;
        public String memberbarCode;
        public String inspectionStatus;
        public String map;
    }
}
