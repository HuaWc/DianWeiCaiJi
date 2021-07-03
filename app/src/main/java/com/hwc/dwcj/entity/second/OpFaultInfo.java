package com.hwc.dwcj.entity.second;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: OpFaultInfoEntity.java</p>
 * <p>Description:工单信息表实体</p>
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * @author songxiaoliang
 * @date 2021-7-1 17:45:48
 */
public class OpFaultInfo {

    /**
     * 主键
     */
    private String id;
    /**
     * 故障编号
     */
    private String faultCode;
    /**
     * 告警ID
     */
    private String alarmId;
    /**
     * 告警名称
     */
    private String alarmName;
    /**
     * 告警编号
     */
    private String alarmCode;
    /**
     * 告警来源
     */
    private String alarmSource;
    /**
     * 告警等级
     */
    private String alarmGrade;
    /**
     * 所属机构
     */
    private String orgId;
    /**
     * 报警原因
     */
    private String alarmRemark;
    /**
     * 设备ID
     */
    private String assetId;
    /**
     * 处理人
     */
    private String handlePersionId;
    /**
     * 处理人联系方式
     */
    private String handleTel;
    /**
     * 处理状态 未处理，处理中，已处理，移交处理，协同处理，闭环
     */
    private String handleStatus;
    /**
     * 处理截止时间
     */
    private Date deadlineTime;
    /**
     * 工单恢复时间
     */
    private Date recoverTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 审核状态
     */
    private String verifyStatus;
    /**
     * 审核人
     */
    private String verifyPersionId;
    /**
     * 审核时间
     */
    private Date verifyTime;
    /**
     * 添加人/派单人
     */
    private String addId;
    /**
     * 添加时间
     */
    private Date addTime;
    /**
     * 修改人
     */
    private String modifyId;
    /**
     * 修改时间/处理时间
     */
    private Date modifyTime;
    /**
     * 是否删除(0-正常，1-删除)
     */
    private Long isDel;
    /**
     * 备用1，处理方式（维修，更换，其他方式处理，问题上报，工单退回）
     */
    private String exp1;
    /**
     * 备用2
     */
    private String exp2;
    /**
     * 备用3----所属项目
     */
    private String exp3;
    /**
     * 告警时间
     */
    private Date alarmTime;
    /**
     * 故障类别
     */
    private String faultType;
    /**
     * 闭环状态
     */
    private String closedLoopStatus;
    /**
     * 处理效率
     */
    private String serviceRating;
    /**
     * 摄像机告警类型
     */
    private Long cameraFaultType;
    /**
     * 备注(IT管理员反馈给运维人员的)
     */
    private String remark2;
    /**
     * 排障日志
     */
    private String remarkLog;
    /**
     * 处理质量
     */
    private String serviceRating2;
    /**
     * 服务态度
     */
    private String serviceRating3;
    /**
     * 后续保障
     */
    private String serviceRating4;
    /**
     * 扩展属性集合
     **/
    private final Map<String, Object> map = new HashMap<String, Object>();

    /**
     * 无参构造函数
     */
    public OpFaultInfo() {
        super();
    }

}
