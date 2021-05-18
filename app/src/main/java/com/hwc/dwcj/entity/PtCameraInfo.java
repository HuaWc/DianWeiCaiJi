package com.hwc.dwcj.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: PtCameraInfoEntity.java</p>
 * <p>Description:
 平台_摄像机表实体</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * @author songxiaoliang
 * @date 2021-5-14 17:13:40
 */
public class PtCameraInfo{

    /**ID*/
    private String id;
    /**所属点位ID*/
    private String positionId;
    /**点位编码*/
    private String positionCode;
    /**点位名称*/
    private String pointName;
    /**杆件编码*/
    private String memberbarCode;
    /**设备编码20位：中心编码、 行业编码、设备类型、网络标识、设备序号，与联网平台/共享平台国标编码一致。（天网：CAMEAR_NO 摄像头编号，项目部提供的编号，点位编码+摄像机编码的组合）*/
    private String cameraNo;
    /**设备名称：摄像头名称*/
    private String cameraName;
    /**IPV4地址*/
    private String cameraIp;
    /**IPV6地址*/
    private String cameraIp6;
    /**MAC地址*/
    private String macAddress;
    /**设备厂商：生产厂商*/
    private String manufacturer;
    /**设备型号：摄像机型号*/
    private String cameraModel;
    /**摄像机类型：1.球机；3.固定枪机；5.卡口枪机；99.其他；100.高点监控；101.半高点球机（天网：CAMERA_TYPE,摄像机类型）*/
    private String cameraType;
    /**摄像机功能类型：1.车辆卡口；2.人员卡口；3.微卡口；99.其他；100.综合治理枪机；101.综合治理球机；102.人像识别枪机（后智能）；103.虚拟卡口；104.高空枪机；105.高空球机；106.半高空球机；107.视频结构化（后智能）；108.全景拼接；109.枪球联动（球）；110.枪球联动（枪）；111.高倍高点球机；112.高倍高点云台枪机；113.机房摄像机（天网：CAMERA_USE）*/
    private String cameraFunType;
    /**安装地址*/
    private String address;
    /**经度*/
    private String longitude;
    /**纬度*/
    private String latitude;
    /**行政区域，按照标准(GB/T2260-2007）*/
    private String areaCode;
    /**社区名称*/
    private String community;
    /**街道*/
    private String street;
    /**摄像机位置类型：1.省际检查站、2.党政机关、3.车站码头、4.中心广场、5.体育场馆、6.商业中心、7.宗教场所、8.校园周边、9.治安复杂区域、10.交通干线、11-医院周边、12-金融机构周边、13-危险物品场所周边、14-博物馆展览馆、15-重点水域、航道、96.市际公安检查站；97.涉外场所；98.边境沿线；99.旅游景区。新增：100.高速路口、101.高速路段、102.城市高点、103.拥堵路段、104.旅馆周边、105.网吧周边、106.公园周边、107.娱乐场所、108.新闻媒体单位周边、109.电信邮政单位周边、110.机场周边、111.铁路沿线、112.火车站周边、113.汽车站周边、114.港口周边、115.城市轨道交通站、116.公交车站周边、117.停车场（库）、118.地下人行通道、119.隧道、120.过街天桥、121.省/市/县（区）际道路主要出入口、122.收费站通道、123.高速公路卡口卡点、124.国道卡口卡点、125.省道上的（治安）卡口卡点、126.大型桥梁通行区域、127.隧道通行区域、128.大型能源动力设施周边、129.城市（水/电/燃气/燃油/热力）能源供应单位周边、130.文博单位（博物馆/纪念馆/展览馆/档案馆/重点文物保护）、131.国家重点建设工程工地、132.居民小区、133.高架上下匝道、134.加油站、135.人脸重点区域、136.黄标车卡点、137.采血点附近。多选各参数以“ /” 分隔*/
    private String positionType;
    /**重点监控对象：1、第一道防控圈；2、第二道防控圈；3、第三道防空圈；4、第四道防控圈；5、第五道防控圈；6、第六道防控圈；99、其他（天网：重点监控对象）*/
    private String importWatch;
    /**安装位置：1.室外；2.室内*/
    private Long indoorOrNot;
    /**所属部门/行业：1.公安机关； 2.环保部门;3.文博部门;4.医疗部门;5.旅游管理;6.新闻广电;7.食品医药监督管理部门;8.教育管理部门;9.检察院;10.法院;11.金融部门;12.交通部门;13.住房和城乡建设部门;14.水利部门;15.林业部门;16.安全生产监督部门;17.市政市容委;18.国土局,可扩展， 多选各参数以“ /” 分隔*/
    private String industryOwn;
    /**分局：0.市局、1.瑶海分局、2.庐阳分局、3.蜀山分局、4.包河分局、5.高新分局、6.新站分局、7.经开分局、8.巢湖经济开发区分局、9.庐江县公安局*/
    private String fenJu;
    /**所属机构id*/
    private Long orgId;
    /**派出所*/
    private String policeStation;
    /**架设高度：1、3.5m；2、4.6m；3、5.3m；4、6m；5、6.8；6、其他（外场定义）*/
    private String installHeight;
    /**横臂1：1、0.5m；2、1m；3、1.5m；4、2m；5、7m（外场定义）*/
    private String crossArm1;
    /**横臂2：1、0.5m；2、1m；3、1.5m；4、2m；5、7m（外场定义）*/
    private String crossArm2;
    /**横臂3：1、0.5m；2、1m；3、1.5m；4、2m；5、7m（外场定义）*/
    private String crossArm3;
    /**联网属性：0 已联网； 1 未联网*/
    private Long networkProperties;
    /**联网人*/
    private String networkPropertiesUser;
    /**联网人联系方式*/
    private String networkPropertiesTel;
    /**取电方式： 1.电业局供电；2.企事业单位；3.居民用电专供；4.临街商铺专供；5.交警取电；6.其他*/
    private Long powerTakeType;
    /**取电人*/
    private String powerTakeTypeUser;
    /**取电人联系方式*/
    private String powerTakeTypeTel;
    /**点位监控类型：1.一类视频监控点；2.二类视频监控点； 3.三类视频监控点； 4 公安内部视频监控点；9.其他点位。*/
    private String monitorType;
    /**监视方位：1-东、2-西、3-南、4-北、5-东南、6-东北、7-西南、8-西北、9.全向（要求厂商定位准确）（天网：CAMERA_DIRECTION）*/
    private String cameraDirection;
    /**补光属性：1.无补光；2.红外补光；9.其他补光；10.外置白光补光；11.内置白光补光（雪亮微卡口选这个）；12.LED频闪补光（雪亮的部分实体卡口选这个）*/
    private Long fillLigthAttr;
    /**摄像机编码格式：1.MPEG-4； 2.H.264； 3.SVAC； 4.H.265?*/
    private Long cameraEncodeType;
    /**是否有拾音器：1表示是，2表示否（天网：否带语音0：不带 1：带语音告警设备）*/
    private Long soundAlarm;
    /**摄像机分辨率：1.QCIF；2.CIF；3.4CIF；4.D1；5.720P；6.1080P；7.4K及以上（天网：分辨率）*/
    private String resolution;
    /**安装时间*/
    private Date installTime;
    /**接入方式*/
    private String accessModel;
    /**所属辖区公安机关：采用公安组织机构代码(由GA/T 380规定)，公安机关建设单位或者社会资源接入后的使用单位，注明到所属辖区公安机关派出所*/
    private String policeAreaCode;
    /**建设期数：1.一期；2.二期；3.三期；4.四期；5.五期；6.支网；99、其他。单选（字段可以由数据字典维护）*/
    private Long buildPeriod;
    /**OSD名称*/
    private String osdName;
    /**管理单位*/
    private String managerUnit;
    /**管理单位联系方式*/
    private String managerUnitTel;
    /**维护单位*/
    private String maintainUnit;
    /**维护单位联系方式*/
    private String maintainUnitTel;
    /**录像保存天数：30天、90天*/
    private Long recodeSaveType;
    /**设备状态：1.在用；2.维修；3.拆除默认在用*/
    private Long deviceState;
    /**上报人，申请人*/
    private String addId;
    /**上报人联系方式*/
    private String addTel;
    /**上报时间，申请时间*/
    private Date addTime;
    /**审批人*/
    private String approvalId;
    /**审批人联系方式*/
    private String approvalTel;
    /**抄送人*/
    private String ccListId;
    /**施工图纸*/
    private String imgPath;
    /**摄像机特写照片：建设好之后，拍的摄像机场景照片（照片名称需与文件夹名称一致）*/
    private String specialPhotoPath;
    /**全景图、摄像机位置照片、勘察照片*/
    private String locationPhotoPath;
    /**背包箱内图、摄像机照射照片*/
    private String realPhotoPath;
    /**如有需要，表明是否为其他委办局委办*/
    private String attribute;
    /**生成的二维码编号*/
    private String qrCodeNumber;
    /**是否删除*/
    private Long isDel;
    /**审批状态：(1.审批中  2.已撤销  3.已驳回4.已通过 0.草稿)*/
    private Long currentStatus;
    /**备用字段：资产ID*/
    private String assetId;
    /**备用字段：(天网业务字段,与资产重复:摄像机SN号：设备发现接口取得的）*/
    private String sn;
    /**备用字段：(天网业务字段,与资产重复:供应商）*/
    private String supplier;
    /**备用字段：(天网业务字段,与资产重复:摄像头状态1 在线0离线）*/
    private String cameraState;
    /**备用字段：(天网业务字段,与资产重复:修改人）*/
    private String modifyId;
    /**备用字段：(天网业务字段,与资产重复:修改时间）*/
    private Date modifyTime;
    /**备用字段：(天网业务字段,与资产重复:像机用户名）*/
    private String userName;
    /**备用字段：(天网业务字段,与资产重复:像机密码）*/
    private String passWord;
    /**备用字段：机箱安装形式：1、立杆；2、借杆；3、壁挂；4、落地；99、其他。单选*/
    private Long caseInstallType;
    /**备用字段：子网掩码*/
    private String subnetMask;
    /**备用字段：网关*/
    private String gateway;
    /**备用字段：ONU SN码*/
    private String onuSn;
    /**备用字段：取电长度：单位为米，小数点后一位*/
    private String powerTakeLength;
    /**备用字段：摄像机软件版本*/
    private String softVersion;
    /**备用字段：镜头参数,(天网：镜头参数)*/
    private String lensParam;
    /**备用字段：是否有云台（天网：是否有云台，1 有，0无）*/
    private Long isHaveConsole;
    /**备用字段：摄像机安装方式：1、立杆；2、借杆；3、壁挂；4.其他（天网：安装方式）*/
    private String installWay;
    /**备用字段：走线方式：1、地埋；2、飞线；3、沿墙敷设；4、其他（天网：走线方式）*/
    private String linearWay;
    /**备用字段：资源存储位置：1.瑶海分局、2.庐阳分局、3.蜀山分局、4.包河分局、5.高新分局、6.新站分局、7.经开分局、8.巢湖经济开发区分局、9.庐江县公安局*/
    private String resourcePlace;
    /**删除*/
    private String watchSpecLocation;
    /**删除*/
    private String roadDirection;
    /**删除*/
    private String foulLine;
    /**删除*/
    private String installPersion;
    /**备用字段：项目名称：六安雪亮工程*/
    private String projectName;
    /**是否已注册到汇聚平台*/
    private Long isRegisterImos;
    /**是否WIFI*/
    private Long isWifi;
    /**删除*/
    private Long isFlash;
    /**备用字段：天网业务字段（摄像头的字母形式编号，项目部提供的编号，如：YH-HC-）0001-1011,提供给运维系统展示使用*/
    private String cameraNoStr;
    /**备用字段：天网业务字段（摄像机VCN编码：可能需要与摄像机编号对应）*/
    private String cameraVcnCode;
    /**备用字段：天网业务字段，域编码*/
    private String fieldNo;
    /**备用字段：重点监控单位，照射具体单位名称（如兴园小学）（天网业务字段，重点单位）*/
    private String keyUnit;
    /**删除*/
    private String unitType;
    /**删除*/
    private Double showLevel;
    /**备用字段：天网业务字段，摄像机协议类型：设备发现接口取得的*/
    private String protocolType;
    /**备用字段：天网业务字段，摄像机端口号*/
    private String cameraPort;
    /**删除*/
    private String interfaceType;
    /**删除*/
    private String channel;
    /**删除*/
    private String userObject;
    /**备用字段：天网业务字段，描述*/
    private String cameraDesc;
    /**备用字段：天网业务字段，是否已注册VCN*/
    private Long isRegisterVcn;
    /**备用字段：天网业务字段，排序值*/
    private Long orderValue;
    /**备用字段：天网业务字段，检测结果:0 正常 1  黑屏  2 冻结  3  掉线   4  亮度异常  5 清晰度异常   6 偏色  7  噪声  8  抖动  9 遮挡  10  PTZ失控*/
    private Long pollingResult;
    /**备用字段：天网业务字段，最新检测时间*/
    private Date pollingTime;
    /**备用字段：天网业务字段，诊断服务器ID*/
    private Long serverId;
    /**备用字段：天网业务字段，摄像头检索条件集合*/
    private String shortMsg;
    /**删除*/
    private String cameraBelongsId;
    /**删除*/
    private String relatedCustoms;
    /**删除*/
    private Long addedToSde;
    /**删除*/
    private String cameraBak;
    /**删除*/
    private String cameraBelongsPk;
    /**删除*/
    private Double isBranch;
    /**删除*/
    private Long isWatchpos;
    /**备用字段：监视角度，取值范围0-360度（正东方为0度，正南方为90度，正西方为180度，正北方为270度，精确到个位）*/
    private String cameraAngle;
    /**备用字段：一机一档数据同步：0未同步 1已同步*/
    private Long isSys;
    /**备用字段：摄像机存储时间*/
    private Long recordTime;
    /**备用字段：解析平台编码*/
    private String analysisNo;
    /**备用字段：WIFI 在线状态，0离线，1在线*/
    private String wifiState;
    /**删除*/
    private Long faceTaskStatus;
    /**删除*/
    private Long videoTaskStatus;
    /**删除*/
    private Long bayonetTaskStatus;
    /**备用字段：视频质量诊断结果附图URL*/
    private String vqdUrl;
    /**备用字段：一机一档同步数据类型 1:新增 2:修改 3:删除*/
    private Long sysType;
    /**删除*/
    private Long isHaveCapture;
    /** 扩展属性集合 **/
    private final Map<String,Object> map=new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
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

    public String getCameraIp6() {
        return cameraIp6;
    }

    public void setCameraIp6(String cameraIp6) {
        this.cameraIp6 = cameraIp6;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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

    public Long getIndoorOrNot() {
        return indoorOrNot;
    }

    public void setIndoorOrNot(Long indoorOrNot) {
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getInstallHeight() {
        return installHeight;
    }

    public void setInstallHeight(String installHeight) {
        this.installHeight = installHeight;
    }

    public String getCrossArm1() {
        return crossArm1;
    }

    public void setCrossArm1(String crossArm1) {
        this.crossArm1 = crossArm1;
    }

    public String getCrossArm2() {
        return crossArm2;
    }

    public void setCrossArm2(String crossArm2) {
        this.crossArm2 = crossArm2;
    }

    public String getCrossArm3() {
        return crossArm3;
    }

    public void setCrossArm3(String crossArm3) {
        this.crossArm3 = crossArm3;
    }

    public Long getNetworkProperties() {
        return networkProperties;
    }

    public void setNetworkProperties(Long networkProperties) {
        this.networkProperties = networkProperties;
    }

    public String getNetworkPropertiesUser() {
        return networkPropertiesUser;
    }

    public void setNetworkPropertiesUser(String networkPropertiesUser) {
        this.networkPropertiesUser = networkPropertiesUser;
    }

    public String getNetworkPropertiesTel() {
        return networkPropertiesTel;
    }

    public void setNetworkPropertiesTel(String networkPropertiesTel) {
        this.networkPropertiesTel = networkPropertiesTel;
    }

    public Long getPowerTakeType() {
        return powerTakeType;
    }

    public void setPowerTakeType(Long powerTakeType) {
        this.powerTakeType = powerTakeType;
    }

    public String getPowerTakeTypeUser() {
        return powerTakeTypeUser;
    }

    public void setPowerTakeTypeUser(String powerTakeTypeUser) {
        this.powerTakeTypeUser = powerTakeTypeUser;
    }

    public String getPowerTakeTypeTel() {
        return powerTakeTypeTel;
    }

    public void setPowerTakeTypeTel(String powerTakeTypeTel) {
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

    public Long getFillLigthAttr() {
        return fillLigthAttr;
    }

    public void setFillLigthAttr(Long fillLigthAttr) {
        this.fillLigthAttr = fillLigthAttr;
    }

    public Long getCameraEncodeType() {
        return cameraEncodeType;
    }

    public void setCameraEncodeType(Long cameraEncodeType) {
        this.cameraEncodeType = cameraEncodeType;
    }

    public Long getSoundAlarm() {
        return soundAlarm;
    }

    public void setSoundAlarm(Long soundAlarm) {
        this.soundAlarm = soundAlarm;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Date getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Date installTime) {
        this.installTime = installTime;
    }

    public String getAccessModel() {
        return accessModel;
    }

    public void setAccessModel(String accessModel) {
        this.accessModel = accessModel;
    }

    public String getPoliceAreaCode() {
        return policeAreaCode;
    }

    public void setPoliceAreaCode(String policeAreaCode) {
        this.policeAreaCode = policeAreaCode;
    }

    public Long getBuildPeriod() {
        return buildPeriod;
    }

    public void setBuildPeriod(Long buildPeriod) {
        this.buildPeriod = buildPeriod;
    }

    public String getOsdName() {
        return osdName;
    }

    public void setOsdName(String osdName) {
        this.osdName = osdName;
    }

    public String getManagerUnit() {
        return managerUnit;
    }

    public void setManagerUnit(String managerUnit) {
        this.managerUnit = managerUnit;
    }

    public String getManagerUnitTel() {
        return managerUnitTel;
    }

    public void setManagerUnitTel(String managerUnitTel) {
        this.managerUnitTel = managerUnitTel;
    }

    public String getMaintainUnit() {
        return maintainUnit;
    }

    public void setMaintainUnit(String maintainUnit) {
        this.maintainUnit = maintainUnit;
    }

    public String getMaintainUnitTel() {
        return maintainUnitTel;
    }

    public void setMaintainUnitTel(String maintainUnitTel) {
        this.maintainUnitTel = maintainUnitTel;
    }

    public Long getRecodeSaveType() {
        return recodeSaveType;
    }

    public void setRecodeSaveType(Long recodeSaveType) {
        this.recodeSaveType = recodeSaveType;
    }

    public Long getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(Long deviceState) {
        this.deviceState = deviceState;
    }

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String getAddTel() {
        return addTel;
    }

    public void setAddTel(String addTel) {
        this.addTel = addTel;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalTel() {
        return approvalTel;
    }

    public void setApprovalTel(String approvalTel) {
        this.approvalTel = approvalTel;
    }

    public String getCcListId() {
        return ccListId;
    }

    public void setCcListId(String ccListId) {
        this.ccListId = ccListId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getSpecialPhotoPath() {
        return specialPhotoPath;
    }

    public void setSpecialPhotoPath(String specialPhotoPath) {
        this.specialPhotoPath = specialPhotoPath;
    }

    public String getLocationPhotoPath() {
        return locationPhotoPath;
    }

    public void setLocationPhotoPath(String locationPhotoPath) {
        this.locationPhotoPath = locationPhotoPath;
    }

    public String getRealPhotoPath() {
        return realPhotoPath;
    }

    public void setRealPhotoPath(String realPhotoPath) {
        this.realPhotoPath = realPhotoPath;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getQrCodeNumber() {
        return qrCodeNumber;
    }

    public void setQrCodeNumber(String qrCodeNumber) {
        this.qrCodeNumber = qrCodeNumber;
    }

    public Long getIsDel() {
        return isDel;
    }

    public void setIsDel(Long isDel) {
        this.isDel = isDel;
    }

    public Long getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Long currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCameraState() {
        return cameraState;
    }

    public void setCameraState(String cameraState) {
        this.cameraState = cameraState;
    }

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Long getCaseInstallType() {
        return caseInstallType;
    }

    public void setCaseInstallType(Long caseInstallType) {
        this.caseInstallType = caseInstallType;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getOnuSn() {
        return onuSn;
    }

    public void setOnuSn(String onuSn) {
        this.onuSn = onuSn;
    }

    public String getPowerTakeLength() {
        return powerTakeLength;
    }

    public void setPowerTakeLength(String powerTakeLength) {
        this.powerTakeLength = powerTakeLength;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getLensParam() {
        return lensParam;
    }

    public void setLensParam(String lensParam) {
        this.lensParam = lensParam;
    }

    public Long getIsHaveConsole() {
        return isHaveConsole;
    }

    public void setIsHaveConsole(Long isHaveConsole) {
        this.isHaveConsole = isHaveConsole;
    }

    public String getInstallWay() {
        return installWay;
    }

    public void setInstallWay(String installWay) {
        this.installWay = installWay;
    }

    public String getLinearWay() {
        return linearWay;
    }

    public void setLinearWay(String linearWay) {
        this.linearWay = linearWay;
    }

    public String getResourcePlace() {
        return resourcePlace;
    }

    public void setResourcePlace(String resourcePlace) {
        this.resourcePlace = resourcePlace;
    }

    public String getWatchSpecLocation() {
        return watchSpecLocation;
    }

    public void setWatchSpecLocation(String watchSpecLocation) {
        this.watchSpecLocation = watchSpecLocation;
    }

    public String getRoadDirection() {
        return roadDirection;
    }

    public void setRoadDirection(String roadDirection) {
        this.roadDirection = roadDirection;
    }

    public String getFoulLine() {
        return foulLine;
    }

    public void setFoulLine(String foulLine) {
        this.foulLine = foulLine;
    }

    public String getInstallPersion() {
        return installPersion;
    }

    public void setInstallPersion(String installPersion) {
        this.installPersion = installPersion;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getIsRegisterImos() {
        return isRegisterImos;
    }

    public void setIsRegisterImos(Long isRegisterImos) {
        this.isRegisterImos = isRegisterImos;
    }

    public Long getIsWifi() {
        return isWifi;
    }

    public void setIsWifi(Long isWifi) {
        this.isWifi = isWifi;
    }

    public Long getIsFlash() {
        return isFlash;
    }

    public void setIsFlash(Long isFlash) {
        this.isFlash = isFlash;
    }

    public String getCameraNoStr() {
        return cameraNoStr;
    }

    public void setCameraNoStr(String cameraNoStr) {
        this.cameraNoStr = cameraNoStr;
    }

    public String getCameraVcnCode() {
        return cameraVcnCode;
    }

    public void setCameraVcnCode(String cameraVcnCode) {
        this.cameraVcnCode = cameraVcnCode;
    }

    public String getFieldNo() {
        return fieldNo;
    }

    public void setFieldNo(String fieldNo) {
        this.fieldNo = fieldNo;
    }

    public String getKeyUnit() {
        return keyUnit;
    }

    public void setKeyUnit(String keyUnit) {
        this.keyUnit = keyUnit;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public Double getShowLevel() {
        return showLevel;
    }

    public void setShowLevel(Double showLevel) {
        this.showLevel = showLevel;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getCameraPort() {
        return cameraPort;
    }

    public void setCameraPort(String cameraPort) {
        this.cameraPort = cameraPort;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUserObject() {
        return userObject;
    }

    public void setUserObject(String userObject) {
        this.userObject = userObject;
    }

    public String getCameraDesc() {
        return cameraDesc;
    }

    public void setCameraDesc(String cameraDesc) {
        this.cameraDesc = cameraDesc;
    }

    public Long getIsRegisterVcn() {
        return isRegisterVcn;
    }

    public void setIsRegisterVcn(Long isRegisterVcn) {
        this.isRegisterVcn = isRegisterVcn;
    }

    public Long getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Long orderValue) {
        this.orderValue = orderValue;
    }

    public Long getPollingResult() {
        return pollingResult;
    }

    public void setPollingResult(Long pollingResult) {
        this.pollingResult = pollingResult;
    }

    public Date getPollingTime() {
        return pollingTime;
    }

    public void setPollingTime(Date pollingTime) {
        this.pollingTime = pollingTime;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getShortMsg() {
        return shortMsg;
    }

    public void setShortMsg(String shortMsg) {
        this.shortMsg = shortMsg;
    }

    public String getCameraBelongsId() {
        return cameraBelongsId;
    }

    public void setCameraBelongsId(String cameraBelongsId) {
        this.cameraBelongsId = cameraBelongsId;
    }

    public String getRelatedCustoms() {
        return relatedCustoms;
    }

    public void setRelatedCustoms(String relatedCustoms) {
        this.relatedCustoms = relatedCustoms;
    }

    public Long getAddedToSde() {
        return addedToSde;
    }

    public void setAddedToSde(Long addedToSde) {
        this.addedToSde = addedToSde;
    }

    public String getCameraBak() {
        return cameraBak;
    }

    public void setCameraBak(String cameraBak) {
        this.cameraBak = cameraBak;
    }

    public String getCameraBelongsPk() {
        return cameraBelongsPk;
    }

    public void setCameraBelongsPk(String cameraBelongsPk) {
        this.cameraBelongsPk = cameraBelongsPk;
    }

    public Double getIsBranch() {
        return isBranch;
    }

    public void setIsBranch(Double isBranch) {
        this.isBranch = isBranch;
    }

    public Long getIsWatchpos() {
        return isWatchpos;
    }

    public void setIsWatchpos(Long isWatchpos) {
        this.isWatchpos = isWatchpos;
    }

    public String getCameraAngle() {
        return cameraAngle;
    }

    public void setCameraAngle(String cameraAngle) {
        this.cameraAngle = cameraAngle;
    }

    public Long getIsSys() {
        return isSys;
    }

    public void setIsSys(Long isSys) {
        this.isSys = isSys;
    }

    public Long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Long recordTime) {
        this.recordTime = recordTime;
    }

    public String getAnalysisNo() {
        return analysisNo;
    }

    public void setAnalysisNo(String analysisNo) {
        this.analysisNo = analysisNo;
    }

    public String getWifiState() {
        return wifiState;
    }

    public void setWifiState(String wifiState) {
        this.wifiState = wifiState;
    }

    public Long getFaceTaskStatus() {
        return faceTaskStatus;
    }

    public void setFaceTaskStatus(Long faceTaskStatus) {
        this.faceTaskStatus = faceTaskStatus;
    }

    public Long getVideoTaskStatus() {
        return videoTaskStatus;
    }

    public void setVideoTaskStatus(Long videoTaskStatus) {
        this.videoTaskStatus = videoTaskStatus;
    }

    public Long getBayonetTaskStatus() {
        return bayonetTaskStatus;
    }

    public void setBayonetTaskStatus(Long bayonetTaskStatus) {
        this.bayonetTaskStatus = bayonetTaskStatus;
    }

    public String getVqdUrl() {
        return vqdUrl;
    }

    public void setVqdUrl(String vqdUrl) {
        this.vqdUrl = vqdUrl;
    }

    public Long getSysType() {
        return sysType;
    }

    public void setSysType(Long sysType) {
        this.sysType = sysType;
    }

    public Long getIsHaveCapture() {
        return isHaveCapture;
    }

    public void setIsHaveCapture(Long isHaveCapture) {
        this.isHaveCapture = isHaveCapture;
    }

    public Map<String, Object> getMap() {
        return map;
    }
}