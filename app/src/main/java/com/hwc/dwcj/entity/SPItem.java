package com.hwc.dwcj.entity;

import java.util.List;

public class SPItem {


    private String positionName;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    private List<CameraInfoList> cameraInfoList;

    public List<CameraInfoList> getCameraInfoList() {
        return cameraInfoList;
    }

    public void setCameraInfoList(List<CameraInfoList> cameraInfoList) {
        this.cameraInfoList = cameraInfoList;
    }

    public static class Map {
    }

    public static class CameraInfoList {

        private String id;
        private Object positionId;
        private String positionCode;
        private String pointName;
        private String memberbarCode;
        private String cameraNo;
        private String cameraName;
        private String cameraIp;
        private Object cameraIp6;
        private Object macAddress;
        private String manufacturer;
        private String cameraModel;
        private String cameraType;
        private String cameraFunType;
        private String address;
        private Object longitude;
        private Object latitude;
        private String areaCode;
        private Object community;
        private Object street;
        private String positionType;
        private String importWatch;
        private int indoorOrNot;
        private String industryOwn;
        private String fenJu;
        private int orgId;
        private Object policeStation;
        private String installHeight;
        private Object crossArm1;
        private Object crossArm2;
        private Object crossArm3;
        private int networkProperties;
        private Object networkPropertiesUser;
        private Object networkPropertiesTel;
        private int powerTakeType;
        private Object powerTakeTypeUser;
        private Object powerTakeTypeTel;
        private String monitorType;
        private String cameraDirection;
        private int fillLigthAttr;
        private int cameraEncodeType;
        private int soundAlarm;
        private String resolution;
        private Object installTime;
        private Object accessModel;
        private String policeAreaCode;
        private int buildPeriod;
        private Object osdName;
        private Object managerUnit;
        private Object managerUnitTel;
        private Object maintainUnit;
        private Object maintainUnitTel;
        private Object recodeSaveType;
        private Object deviceState;
        private String addId;
        private Object addTel;
        private String addTime;
        private Object approvalId;
        private Object approvalTel;
        private Object ccListId;
        private Object imgPath;
        private Object specialPhotoPath;
        private Object locationPhotoPath;
        private Object realPhotoPath;
        private Object attribute;
        private Object qrCodeNumber;
        private int isDel;
        private Long currentStatus;
        private Object assetId;
        private Object sn;
        private Object supplier;
        private Object cameraState;
        private Object modifyId;
        private Object modifyTime;
        private Object userName;
        private Object passWord;
        private Object caseInstallType;
        private Object subnetMask;
        private Object gateway;
        private Object onuSn;
        private Object powerTakeLength;
        private Object softVersion;
        private Object lensParam;
        private Object isHaveConsole;
        private Object installWay;
        private Object linearWay;
        private Object resourcePlace;
        private Object watchSpecLocation;
        private Object roadDirection;
        private Object foulLine;
        private Object installPersion;
        private Object projectName;
        private Object isRegisterImos;
        private Object isWifi;
        private Object isFlash;
        private Object cameraNoStr;
        private Object cameraVcnCode;
        private Object fieldNo;
        private Object keyUnit;
        private Object unitType;
        private Object showLevel;
        private Object protocolType;
        private Object cameraPort;
        private Object interfaceType;
        private Object channel;
        private Object userObject;
        private Object cameraDesc;
        private Object isRegisterVcn;
        private Object orderValue;
        private Object pollingResult;
        private Object pollingTime;
        private Object serverId;
        private Object shortMsg;
        private Object cameraBelongsId;
        private Object relatedCustoms;
        private Object addedToSde;
        private Object cameraBak;
        private Object cameraBelongsPk;
        private Object isBranch;
        private Object isWatchpos;
        private Object cameraAngle;
        private Object isSys;
        private Object recordTime;
        private Object analysisNo;
        private Object wifiState;
        private Object faceTaskStatus;
        private Object videoTaskStatus;
        private Object bayonetTaskStatus;
        private Object vqdUrl;
        private Object sysType;
        private Object isHaveCapture;
        private Map map;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getPositionId() {
            return positionId;
        }

        public void setPositionId(Object positionId) {
            this.positionId = positionId;
        }

        public String getPositionCode() {
            return positionCode;
        }

        public void setPositionCode(String positionCode) {
            this.positionCode = positionCode;
        }

        public String getPointName() {
            return pointName;
        }

        public void setPointName(String pointName) {
            this.pointName = pointName;
        }

        public String getMemberbarCode() {
            return memberbarCode;
        }

        public void setMemberbarCode(String memberbarCode) {
            this.memberbarCode = memberbarCode;
        }

        public String getCameraNo() {
            return cameraNo;
        }

        public void setCameraNo(String cameraNo) {
            this.cameraNo = cameraNo;
        }

        public String getCameraName() {
            return cameraName;
        }

        public void setCameraName(String cameraName) {
            this.cameraName = cameraName;
        }

        public String getCameraIp() {
            return cameraIp;
        }

        public void setCameraIp(String cameraIp) {
            this.cameraIp = cameraIp;
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

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getCameraModel() {
            return cameraModel;
        }

        public void setCameraModel(String cameraModel) {
            this.cameraModel = cameraModel;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public Object getCommunity() {
            return community;
        }

        public void setCommunity(Object community) {
            this.community = community;
        }

        public Object getStreet() {
            return street;
        }

        public void setStreet(Object street) {
            this.street = street;
        }

        public String getPositionType() {
            return positionType;
        }

        public void setPositionType(String positionType) {
            this.positionType = positionType;
        }

        public String getImportWatch() {
            return importWatch;
        }

        public void setImportWatch(String importWatch) {
            this.importWatch = importWatch;
        }

        public int getIndoorOrNot() {
            return indoorOrNot;
        }

        public void setIndoorOrNot(int indoorOrNot) {
            this.indoorOrNot = indoorOrNot;
        }

        public String getIndustryOwn() {
            return industryOwn;
        }

        public void setIndustryOwn(String industryOwn) {
            this.industryOwn = industryOwn;
        }

        public String getFenJu() {
            return fenJu;
        }

        public void setFenJu(String fenJu) {
            this.fenJu = fenJu;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public Object getPoliceStation() {
            return policeStation;
        }

        public void setPoliceStation(Object policeStation) {
            this.policeStation = policeStation;
        }

        public String getInstallHeight() {
            return installHeight;
        }

        public void setInstallHeight(String installHeight) {
            this.installHeight = installHeight;
        }

        public Object getCrossArm1() {
            return crossArm1;
        }

        public void setCrossArm1(Object crossArm1) {
            this.crossArm1 = crossArm1;
        }

        public Object getCrossArm2() {
            return crossArm2;
        }

        public void setCrossArm2(Object crossArm2) {
            this.crossArm2 = crossArm2;
        }

        public Object getCrossArm3() {
            return crossArm3;
        }

        public void setCrossArm3(Object crossArm3) {
            this.crossArm3 = crossArm3;
        }

        public int getNetworkProperties() {
            return networkProperties;
        }

        public void setNetworkProperties(int networkProperties) {
            this.networkProperties = networkProperties;
        }

        public Object getNetworkPropertiesUser() {
            return networkPropertiesUser;
        }

        public void setNetworkPropertiesUser(Object networkPropertiesUser) {
            this.networkPropertiesUser = networkPropertiesUser;
        }

        public Object getNetworkPropertiesTel() {
            return networkPropertiesTel;
        }

        public void setNetworkPropertiesTel(Object networkPropertiesTel) {
            this.networkPropertiesTel = networkPropertiesTel;
        }

        public int getPowerTakeType() {
            return powerTakeType;
        }

        public void setPowerTakeType(int powerTakeType) {
            this.powerTakeType = powerTakeType;
        }

        public Object getPowerTakeTypeUser() {
            return powerTakeTypeUser;
        }

        public void setPowerTakeTypeUser(Object powerTakeTypeUser) {
            this.powerTakeTypeUser = powerTakeTypeUser;
        }

        public Object getPowerTakeTypeTel() {
            return powerTakeTypeTel;
        }

        public void setPowerTakeTypeTel(Object powerTakeTypeTel) {
            this.powerTakeTypeTel = powerTakeTypeTel;
        }

        public String getMonitorType() {
            return monitorType;
        }

        public void setMonitorType(String monitorType) {
            this.monitorType = monitorType;
        }

        public String getCameraDirection() {
            return cameraDirection;
        }

        public void setCameraDirection(String cameraDirection) {
            this.cameraDirection = cameraDirection;
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

        public int getSoundAlarm() {
            return soundAlarm;
        }

        public void setSoundAlarm(int soundAlarm) {
            this.soundAlarm = soundAlarm;
        }

        public String getResolution() {
            return resolution;
        }

        public void setResolution(String resolution) {
            this.resolution = resolution;
        }

        public Object getInstallTime() {
            return installTime;
        }

        public void setInstallTime(Object installTime) {
            this.installTime = installTime;
        }

        public Object getAccessModel() {
            return accessModel;
        }

        public void setAccessModel(Object accessModel) {
            this.accessModel = accessModel;
        }

        public String getPoliceAreaCode() {
            return policeAreaCode;
        }

        public void setPoliceAreaCode(String policeAreaCode) {
            this.policeAreaCode = policeAreaCode;
        }

        public int getBuildPeriod() {
            return buildPeriod;
        }

        public void setBuildPeriod(int buildPeriod) {
            this.buildPeriod = buildPeriod;
        }

        public Object getOsdName() {
            return osdName;
        }

        public void setOsdName(Object osdName) {
            this.osdName = osdName;
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

        public Object getRecodeSaveType() {
            return recodeSaveType;
        }

        public void setRecodeSaveType(Object recodeSaveType) {
            this.recodeSaveType = recodeSaveType;
        }

        public Object getDeviceState() {
            return deviceState;
        }

        public void setDeviceState(Object deviceState) {
            this.deviceState = deviceState;
        }

        public String getAddId() {
            return addId;
        }

        public void setAddId(String addId) {
            this.addId = addId;
        }

        public Object getAddTel() {
            return addTel;
        }

        public void setAddTel(Object addTel) {
            this.addTel = addTel;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public Object getApprovalId() {
            return approvalId;
        }

        public void setApprovalId(Object approvalId) {
            this.approvalId = approvalId;
        }

        public Object getApprovalTel() {
            return approvalTel;
        }

        public void setApprovalTel(Object approvalTel) {
            this.approvalTel = approvalTel;
        }

        public Object getCcListId() {
            return ccListId;
        }

        public void setCcListId(Object ccListId) {
            this.ccListId = ccListId;
        }

        public Object getImgPath() {
            return imgPath;
        }

        public void setImgPath(Object imgPath) {
            this.imgPath = imgPath;
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

        public Object getAttribute() {
            return attribute;
        }

        public void setAttribute(Object attribute) {
            this.attribute = attribute;
        }

        public Object getQrCodeNumber() {
            return qrCodeNumber;
        }

        public void setQrCodeNumber(Object qrCodeNumber) {
            this.qrCodeNumber = qrCodeNumber;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public Long getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(Long currentStatus) {
            this.currentStatus = currentStatus;
        }

        public Object getAssetId() {
            return assetId;
        }

        public void setAssetId(Object assetId) {
            this.assetId = assetId;
        }

        public Object getSn() {
            return sn;
        }

        public void setSn(Object sn) {
            this.sn = sn;
        }

        public Object getSupplier() {
            return supplier;
        }

        public void setSupplier(Object supplier) {
            this.supplier = supplier;
        }

        public Object getCameraState() {
            return cameraState;
        }

        public void setCameraState(Object cameraState) {
            this.cameraState = cameraState;
        }

        public Object getModifyId() {
            return modifyId;
        }

        public void setModifyId(Object modifyId) {
            this.modifyId = modifyId;
        }

        public Object getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(Object modifyTime) {
            this.modifyTime = modifyTime;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }

        public Object getPassWord() {
            return passWord;
        }

        public void setPassWord(Object passWord) {
            this.passWord = passWord;
        }

        public Object getCaseInstallType() {
            return caseInstallType;
        }

        public void setCaseInstallType(Object caseInstallType) {
            this.caseInstallType = caseInstallType;
        }

        public Object getSubnetMask() {
            return subnetMask;
        }

        public void setSubnetMask(Object subnetMask) {
            this.subnetMask = subnetMask;
        }

        public Object getGateway() {
            return gateway;
        }

        public void setGateway(Object gateway) {
            this.gateway = gateway;
        }

        public Object getOnuSn() {
            return onuSn;
        }

        public void setOnuSn(Object onuSn) {
            this.onuSn = onuSn;
        }

        public Object getPowerTakeLength() {
            return powerTakeLength;
        }

        public void setPowerTakeLength(Object powerTakeLength) {
            this.powerTakeLength = powerTakeLength;
        }

        public Object getSoftVersion() {
            return softVersion;
        }

        public void setSoftVersion(Object softVersion) {
            this.softVersion = softVersion;
        }

        public Object getLensParam() {
            return lensParam;
        }

        public void setLensParam(Object lensParam) {
            this.lensParam = lensParam;
        }

        public Object getIsHaveConsole() {
            return isHaveConsole;
        }

        public void setIsHaveConsole(Object isHaveConsole) {
            this.isHaveConsole = isHaveConsole;
        }

        public Object getInstallWay() {
            return installWay;
        }

        public void setInstallWay(Object installWay) {
            this.installWay = installWay;
        }

        public Object getLinearWay() {
            return linearWay;
        }

        public void setLinearWay(Object linearWay) {
            this.linearWay = linearWay;
        }

        public Object getResourcePlace() {
            return resourcePlace;
        }

        public void setResourcePlace(Object resourcePlace) {
            this.resourcePlace = resourcePlace;
        }

        public Object getWatchSpecLocation() {
            return watchSpecLocation;
        }

        public void setWatchSpecLocation(Object watchSpecLocation) {
            this.watchSpecLocation = watchSpecLocation;
        }

        public Object getRoadDirection() {
            return roadDirection;
        }

        public void setRoadDirection(Object roadDirection) {
            this.roadDirection = roadDirection;
        }

        public Object getFoulLine() {
            return foulLine;
        }

        public void setFoulLine(Object foulLine) {
            this.foulLine = foulLine;
        }

        public Object getInstallPersion() {
            return installPersion;
        }

        public void setInstallPersion(Object installPersion) {
            this.installPersion = installPersion;
        }

        public Object getProjectName() {
            return projectName;
        }

        public void setProjectName(Object projectName) {
            this.projectName = projectName;
        }

        public Object getIsRegisterImos() {
            return isRegisterImos;
        }

        public void setIsRegisterImos(Object isRegisterImos) {
            this.isRegisterImos = isRegisterImos;
        }

        public Object getIsWifi() {
            return isWifi;
        }

        public void setIsWifi(Object isWifi) {
            this.isWifi = isWifi;
        }

        public Object getIsFlash() {
            return isFlash;
        }

        public void setIsFlash(Object isFlash) {
            this.isFlash = isFlash;
        }

        public Object getCameraNoStr() {
            return cameraNoStr;
        }

        public void setCameraNoStr(Object cameraNoStr) {
            this.cameraNoStr = cameraNoStr;
        }

        public Object getCameraVcnCode() {
            return cameraVcnCode;
        }

        public void setCameraVcnCode(Object cameraVcnCode) {
            this.cameraVcnCode = cameraVcnCode;
        }

        public Object getFieldNo() {
            return fieldNo;
        }

        public void setFieldNo(Object fieldNo) {
            this.fieldNo = fieldNo;
        }

        public Object getKeyUnit() {
            return keyUnit;
        }

        public void setKeyUnit(Object keyUnit) {
            this.keyUnit = keyUnit;
        }

        public Object getUnitType() {
            return unitType;
        }

        public void setUnitType(Object unitType) {
            this.unitType = unitType;
        }

        public Object getShowLevel() {
            return showLevel;
        }

        public void setShowLevel(Object showLevel) {
            this.showLevel = showLevel;
        }

        public Object getProtocolType() {
            return protocolType;
        }

        public void setProtocolType(Object protocolType) {
            this.protocolType = protocolType;
        }

        public Object getCameraPort() {
            return cameraPort;
        }

        public void setCameraPort(Object cameraPort) {
            this.cameraPort = cameraPort;
        }

        public Object getInterfaceType() {
            return interfaceType;
        }

        public void setInterfaceType(Object interfaceType) {
            this.interfaceType = interfaceType;
        }

        public Object getChannel() {
            return channel;
        }

        public void setChannel(Object channel) {
            this.channel = channel;
        }

        public Object getUserObject() {
            return userObject;
        }

        public void setUserObject(Object userObject) {
            this.userObject = userObject;
        }

        public Object getCameraDesc() {
            return cameraDesc;
        }

        public void setCameraDesc(Object cameraDesc) {
            this.cameraDesc = cameraDesc;
        }

        public Object getIsRegisterVcn() {
            return isRegisterVcn;
        }

        public void setIsRegisterVcn(Object isRegisterVcn) {
            this.isRegisterVcn = isRegisterVcn;
        }

        public Object getOrderValue() {
            return orderValue;
        }

        public void setOrderValue(Object orderValue) {
            this.orderValue = orderValue;
        }

        public Object getPollingResult() {
            return pollingResult;
        }

        public void setPollingResult(Object pollingResult) {
            this.pollingResult = pollingResult;
        }

        public Object getPollingTime() {
            return pollingTime;
        }

        public void setPollingTime(Object pollingTime) {
            this.pollingTime = pollingTime;
        }

        public Object getServerId() {
            return serverId;
        }

        public void setServerId(Object serverId) {
            this.serverId = serverId;
        }

        public Object getShortMsg() {
            return shortMsg;
        }

        public void setShortMsg(Object shortMsg) {
            this.shortMsg = shortMsg;
        }

        public Object getCameraBelongsId() {
            return cameraBelongsId;
        }

        public void setCameraBelongsId(Object cameraBelongsId) {
            this.cameraBelongsId = cameraBelongsId;
        }

        public Object getRelatedCustoms() {
            return relatedCustoms;
        }

        public void setRelatedCustoms(Object relatedCustoms) {
            this.relatedCustoms = relatedCustoms;
        }

        public Object getAddedToSde() {
            return addedToSde;
        }

        public void setAddedToSde(Object addedToSde) {
            this.addedToSde = addedToSde;
        }

        public Object getCameraBak() {
            return cameraBak;
        }

        public void setCameraBak(Object cameraBak) {
            this.cameraBak = cameraBak;
        }

        public Object getCameraBelongsPk() {
            return cameraBelongsPk;
        }

        public void setCameraBelongsPk(Object cameraBelongsPk) {
            this.cameraBelongsPk = cameraBelongsPk;
        }

        public Object getIsBranch() {
            return isBranch;
        }

        public void setIsBranch(Object isBranch) {
            this.isBranch = isBranch;
        }

        public Object getIsWatchpos() {
            return isWatchpos;
        }

        public void setIsWatchpos(Object isWatchpos) {
            this.isWatchpos = isWatchpos;
        }

        public Object getCameraAngle() {
            return cameraAngle;
        }

        public void setCameraAngle(Object cameraAngle) {
            this.cameraAngle = cameraAngle;
        }

        public Object getIsSys() {
            return isSys;
        }

        public void setIsSys(Object isSys) {
            this.isSys = isSys;
        }

        public Object getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(Object recordTime) {
            this.recordTime = recordTime;
        }

        public Object getAnalysisNo() {
            return analysisNo;
        }

        public void setAnalysisNo(Object analysisNo) {
            this.analysisNo = analysisNo;
        }

        public Object getWifiState() {
            return wifiState;
        }

        public void setWifiState(Object wifiState) {
            this.wifiState = wifiState;
        }

        public Object getFaceTaskStatus() {
            return faceTaskStatus;
        }

        public void setFaceTaskStatus(Object faceTaskStatus) {
            this.faceTaskStatus = faceTaskStatus;
        }

        public Object getVideoTaskStatus() {
            return videoTaskStatus;
        }

        public void setVideoTaskStatus(Object videoTaskStatus) {
            this.videoTaskStatus = videoTaskStatus;
        }

        public Object getBayonetTaskStatus() {
            return bayonetTaskStatus;
        }

        public void setBayonetTaskStatus(Object bayonetTaskStatus) {
            this.bayonetTaskStatus = bayonetTaskStatus;
        }

        public Object getVqdUrl() {
            return vqdUrl;
        }

        public void setVqdUrl(Object vqdUrl) {
            this.vqdUrl = vqdUrl;
        }

        public Object getSysType() {
            return sysType;
        }

        public void setSysType(Object sysType) {
            this.sysType = sysType;
        }

        public Object getIsHaveCapture() {
            return isHaveCapture;
        }

        public void setIsHaveCapture(Object isHaveCapture) {
            this.isHaveCapture = isHaveCapture;
        }

        public Map getMap() {
            return map;
        }

        public void setMap(Map map) {
            this.map = map;
        }

        public static class Map {
            private String checkType;
            private String userName;

            public String getCheckType() {
                return checkType;
            }

            public void setCheckType(String checkType) {
                this.checkType = checkType;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
