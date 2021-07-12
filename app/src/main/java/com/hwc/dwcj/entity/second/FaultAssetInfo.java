package com.hwc.dwcj.entity.second;

/**
 * Created by Christ on 2021/7/12.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class FaultAssetInfo {

    private String faultId;
    private String id;
    private int isMaiEqu;
    private Map map;
    private String newAssetId;
    private String oldAssetId;
    private String oldDeviceStatus;

    public String getFaultId() {
        return faultId;
    }

    public void setFaultId(String faultId) {
        this.faultId = faultId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsMaiEqu() {
        return isMaiEqu;
    }

    public void setIsMaiEqu(int isMaiEqu) {
        this.isMaiEqu = isMaiEqu;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getNewAssetId() {
        return newAssetId;
    }

    public void setNewAssetId(String newAssetId) {
        this.newAssetId = newAssetId;
    }

    public String getOldAssetId() {
        return oldAssetId;
    }

    public void setOldAssetId(String oldAssetId) {
        this.oldAssetId = oldAssetId;
    }

    public String getOldDeviceStatus() {
        return oldDeviceStatus;
    }

    public void setOldDeviceStatus(String oldDeviceStatus) {
        this.oldDeviceStatus = oldDeviceStatus;
    }

    public static class Map {
        private String newAssetCode;
        private String newAssetName;
        private String oldDeviceStatusName;

        public String getNewAssetCode() {
            return newAssetCode;
        }

        public void setNewAssetCode(String newAssetCode) {
            this.newAssetCode = newAssetCode;
        }

        public String getNewAssetName() {
            return newAssetName;
        }

        public void setNewAssetName(String newAssetName) {
            this.newAssetName = newAssetName;
        }

        public String getOldDeviceStatusName() {
            return oldDeviceStatusName;
        }

        public void setOldDeviceStatusName(String oldDeviceStatusName) {
            this.oldDeviceStatusName = oldDeviceStatusName;
        }
    }
}
