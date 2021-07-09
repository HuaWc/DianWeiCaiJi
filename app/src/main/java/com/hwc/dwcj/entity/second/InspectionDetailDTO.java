package com.hwc.dwcj.entity.second;

import java.util.List;

public class InspectionDetailDTO {
    public String taskSubId;
    public String devName;
    public String orgName;
    public String ip;
    public String cameraType;
    public String positionCode;
    public List<ItemListDTO> itemList;

    public static class ItemListDTO {
        public String itemName;
        public String itemType;//0是非必填，1是必填
        public String itemInputType;//0是下拉，1是文本框
        public String runStatus;
        public String itemId;
    }
}
