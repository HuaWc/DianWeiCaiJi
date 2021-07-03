package com.hwc.dwcj.entity.second;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: OpAlarmInfoEntity.java</p>
 * <p>Description:告警临时表实体</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * @author zhanghairong
 * @date 2018-12-10 09:40:29
 * @version V1.0
 */
public class OpAlarmInfo {

    /**主键*/
    private String id;
    /**告警编码*/
    private String alarmCode;
    /**告警名称*/
    private String alarmName;
    /**资产ID*/
    private String assetId;
    /**设备编码*/
    private String deviceCode;
    /**告警来源*/
    private String alarmSource;
    /**告警等级*/
    private String alarmLevel;
    /**所属机构,紧适用于动环*/
    private String orgId;
    /**所属机构名称，紧适用于动环*/
    private String orgName;
    /**报警原因*/
    private String alarmReason;
    /**处理状态*/
    private String alarmStatus;
    /**发生时间*/
    private Date alarmTime;
    /**告警人*/
    private String alarmPersonId;
    /**处理人*/
    private String handlePersonId;
    /**处理时间*/
    private Date handleTime;
    /**巡检任务id*/
    private String taskId;
    /**巡检分组id*/
    private String groupId;

    /**处理截止时间*/
    private Date deadlineTime;
    /**设备种类，紧适用于动环*/
    private String deviceKind;
    /**设备名称，紧适用于动环*/
    private String deviceName;
    /**ip*/
    private String ip;
    /**所属项目*/
    private String project;
    /**附图url*/
    private String pictureUrl;
    /**恢复时间*/
    private Date recoveryTime;
    /**设备或机房id，手动添加时有数据*/
    private String deviceId;
    /**巡检任务子表Id*/
    private String taskSubId;
    /**移动端是否对记录进行了更新操作，Y（更新），N（未更新）*/
    private String isUpdate;
    /**是否由移动端插入，Y（由移动端插入），N（由视图库插入）*/
    private String isInsert;
    /**资产种类*/
    private String assetNature;
    /**资产类别*/
    private String assetType;
    /**故障类型*/
    private String faultType;
    /**处理效率*/
    private String serviceRating;
    /**处理质量*/
    private String serviceRating2;
    /**服务态度*/
    private String serviceRating3;
    /**后续保障*/
    private String serviceRating4;
    /** 扩展属性集合 **/
    private final Map<String,Object> map=new HashMap<String, Object>();
    /**
     *无参构造函数
     */
    public OpAlarmInfo(){
        super();
    }
}
