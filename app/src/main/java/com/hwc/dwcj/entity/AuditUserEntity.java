package com.hwc.dwcj.entity;

import java.util.List;

public class AuditUserEntity {

    private List<Check> check;
    private Camera camera;

    public List<Check> getCheck() {
        return check;
    }

    public void setCheck(List<Check> check) {
        this.check = check;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public static class Camera {
        private String id;
        private String positionCode;
        private Object memberbarCode;
        private String cameraCode;
        private String cameraName;
        private String cameraVender;
        private String areaCode;
        private Object monitorType;
        private Object deviceModel;
        private String pointName;
        private String cameraIp4;
        private Object cameraIp6;
        private Object macAddress;
        private String cameraType;
        private String cameraFunType;
        private int fillLigthAttr;
        private int cameraEncodeType;
        private int powerTakeType;
        private long powerTakeTel;
        private int soundAlarm;
        private Object resolution;
        private String address;
        private Object importWatch;
        private String positionType;
        private String community;
        private String street;
        private String fenJu;
        private Object policeStation;
        private String longitude;
        private String latitude;
        private String cameraDirection;
        private String installHeight;
        private int indoorOrNot;
        private Object specialPhotoPath;
        private Object locationPhotoPath;
        private Object realPhotoPath;
        private int networkProperties;
        private String networkPropertiesTel;
        private Object policeAreaCode;
        private String installTime;
        private int buildPeriod;
        private Object managerUnit;
        private Object managerUnitTel;
        private Object maintainUnit;
        private Object maintainUnitTel;
        private int recodeSaveType;
        private Object deviceState;
        private String industryOwn;
        private Object attribute;
        private String qrCodeNumber;
        private int isDel;
        private int orgId;
        private int currentStatus;
        private String addId;
        private String addDate;
        private Map map;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPositionCode() {
            return positionCode;
        }

        public void setPositionCode(String positionCode) {
            this.positionCode = positionCode;
        }

        public Object getMemberbarCode() {
            return memberbarCode;
        }

        public void setMemberbarCode(Object memberbarCode) {
            this.memberbarCode = memberbarCode;
        }

        public String getCameraCode() {
            return cameraCode;
        }

        public void setCameraCode(String cameraCode) {
            this.cameraCode = cameraCode;
        }

        public String getCameraName() {
            return cameraName;
        }

        public void setCameraName(String cameraName) {
            this.cameraName = cameraName;
        }

        public String getCameraVender() {
            return cameraVender;
        }

        public void setCameraVender(String cameraVender) {
            this.cameraVender = cameraVender;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public Object getMonitorType() {
            return monitorType;
        }

        public void setMonitorType(Object monitorType) {
            this.monitorType = monitorType;
        }

        public Object getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(Object deviceModel) {
            this.deviceModel = deviceModel;
        }

        public String getPointName() {
            return pointName;
        }

        public void setPointName(String pointName) {
            this.pointName = pointName;
        }

        public String getCameraIp4() {
            return cameraIp4;
        }

        public void setCameraIp4(String cameraIp4) {
            this.cameraIp4 = cameraIp4;
        }

        public Object getCameraIp6() {
            return cameraIp6;
        }

        public void setCameraIp6(Object cameraIp6) {
            this.cameraIp6 = cameraIp6;
        }

        public Object getMacAddress() {
            return macAddress;
        }

        public void setMacAddress(Object macAddress) {
            this.macAddress = macAddress;
        }

        public String getCameraType() {
            return cameraType;
        }

        public void setCameraType(String cameraType) {
            this.cameraType = cameraType;
        }

        public String getCameraFunType() {
            return cameraFunType;
        }

        public void setCameraFunType(String cameraFunType) {
            this.cameraFunType = cameraFunType;
        }

        public int getFillLigthAttr() {
            return fillLigthAttr;
        }

        public void setFillLigthAttr(int fillLigthAttr) {
            this.fillLigthAttr = fillLigthAttr;
        }

        public int getCameraEncodeType() {
            return cameraEncodeType;
        }

        public void setCameraEncodeType(int cameraEncodeType) {
            this.cameraEncodeType = cameraEncodeType;
        }

        public int getPowerTakeType() {
            return powerTakeType;
        }

        public void setPowerTakeType(int powerTakeType) {
            this.powerTakeType = powerTakeType;
        }

        public long getPowerTakeTel() {
            return powerTakeTel;
        }

        public void setPowerTakeTel(long powerTakeTel) {
            this.powerTakeTel = powerTakeTel;
        }

        public int getSoundAlarm() {
            return soundAlarm;
        }

        public void setSoundAlarm(int soundAlarm) {
            this.soundAlarm = soundAlarm;
        }

        public Object getResolution() {
            return resolution;
        }

        public void setResolution(Object resolution) {
            this.resolution = resolution;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getImportWatch() {
            return importWatch;
        }

        public void setImportWatch(Object importWatch) {
            this.importWatch = importWatch;
        }

        public String getPositionType() {
            return positionType;
        }

        public void setPositionType(String positionType) {
            this.positionType = positionType;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getFenJu() {
            return fenJu;
        }

        public void setFenJu(String fenJu) {
            this.fenJu = fenJu;
        }

        public Object getPoliceStation() {
            return policeStation;
        }

        public void setPoliceStation(Object policeStation) {
            this.policeStation = policeStation;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getCameraDirection() {
            return cameraDirection;
        }

        public void setCameraDirection(String cameraDirection) {
            this.cameraDirection = cameraDirection;
        }

        public String getInstallHeight() {
            return installHeight;
        }

        public void setInstallHeight(String installHeight) {
            this.installHeight = installHeight;
        }

        public int getIndoorOrNot() {
            return indoorOrNot;
        }

        public void setIndoorOrNot(int indoorOrNot) {
            this.indoorOrNot = indoorOrNot;
        }

        public Object getSpecialPhotoPath() {
            return specialPhotoPath;
        }

        public void setSpecialPhotoPath(Object specialPhotoPath) {
            this.specialPhotoPath = specialPhotoPath;
        }

        public Object getLocationPhotoPath() {
            return locationPhotoPath;
        }

        public void setLocationPhotoPath(Object locationPhotoPath) {
            this.locationPhotoPath = locationPhotoPath;
        }

        public Object getRealPhotoPath() {
            return realPhotoPath;
        }

        public void setRealPhotoPath(Object realPhotoPath) {
            this.realPhotoPath = realPhotoPath;
        }

        public int getNetworkProperties() {
            return networkProperties;
        }

        public void setNetworkProperties(int networkProperties) {
            this.networkProperties = networkProperties;
        }

        public String getNetworkPropertiesTel() {
            return networkPropertiesTel;
        }

        public void setNetworkPropertiesTel(String networkPropertiesTel) {
            this.networkPropertiesTel = networkPropertiesTel;
        }

        public Object getPoliceAreaCode() {
            return policeAreaCode;
        }

        public void setPoliceAreaCode(Object policeAreaCode) {
            this.policeAreaCode = policeAreaCode;
        }

        public String getInstallTime() {
            return installTime;
        }

        public void setInstallTime(String installTime) {
            this.installTime = installTime;
        }

        public int getBuildPeriod() {
            return buildPeriod;
        }

        public void setBuildPeriod(int buildPeriod) {
            this.buildPeriod = buildPeriod;
        }

        public Object getManagerUnit() {
            return managerUnit;
        }

        public void setManagerUnit(Object managerUnit) {
            this.managerUnit = managerUnit;
        }

        public Object getManagerUnitTel() {
            return managerUnitTel;
        }

        public void setManagerUnitTel(Object managerUnitTel) {
            this.managerUnitTel = managerUnitTel;
        }

        public Object getMaintainUnit() {
            return maintainUnit;
        }

        public void setMaintainUnit(Object maintainUnit) {
            this.maintainUnit = maintainUnit;
        }

        public Object getMaintainUnitTel() {
            return maintainUnitTel;
        }

        public void setMaintainUnitTel(Object maintainUnitTel) {
            this.maintainUnitTel = maintainUnitTel;
        }

        public int getRecodeSaveType() {
            return recodeSaveType;
        }

        public void setRecodeSaveType(int recodeSaveType) {
            this.recodeSaveType = recodeSaveType;
        }

        public Object getDeviceState() {
            return deviceState;
        }

        public void setDeviceState(Object deviceState) {
            this.deviceState = deviceState;
        }

        public String getIndustryOwn() {
            return industryOwn;
        }

        public void setIndustryOwn(String industryOwn) {
            this.industryOwn = industryOwn;
        }

        public Object getAttribute() {
            return attribute;
        }

        public void setAttribute(Object attribute) {
            this.attribute = attribute;
        }

        public String getQrCodeNumber() {
            return qrCodeNumber;
        }

        public void setQrCodeNumber(String qrCodeNumber) {
            this.qrCodeNumber = qrCodeNumber;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public int getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(int currentStatus) {
            this.currentStatus = currentStatus;
        }

        public String getAddId() {
            return addId;
        }

        public void setAddId(String addId) {
            this.addId = addId;
        }

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }

        public Map getMap() {
            return map;
        }

        public void setMap(Map map) {
            this.map = map;
        }

        public static class Map {
        }
    }

    public static class Check {
        private String checkUserName;
        private int checkType;
        private Object type;
        private boolean me;

        public boolean isMe() {
            return me;
        }

        public void setMe(boolean me) {
            this.me = me;
        }

        public String getCheckUserName() {
            return checkUserName;
        }

        public void setCheckUserName(String checkUserName) {
            this.checkUserName = checkUserName;
        }

        public int getCheckType() {
            return checkType;
        }

        public void setCheckType(int checkType) {
            this.checkType = checkType;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }
    }
}
