package com.hwc.dwcj.entity.second;

import java.util.List;

public class InspectionResultDetailDTO {

    public String inspectionTime;
    public String otherQuestion;
    public String inspectionName;
    public String devName;
    public String orgName;
    public String ip;
    public String positionCode;
    public String memberbarCode;
    public String cameraType;
    public List<ItemListDTO> itemList;
    public String operateStatus;
    public List<?> imgIds;
    public String address;
    public List<AlarmListDTO> alarmList;

    public static class ItemListDTO {
        public String itemName;
        public String itemValue;
    }

    public static class AlarmListDTO {
        public String alarmLevel;
        public String alarmReason;
    }
}
